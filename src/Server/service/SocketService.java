package Server.service;

import Client.utils.gui;
import Model.*;
import Server.Controller.GameController;
import config.Constaint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import payload.Message;

public class SocketService implements TemplateService{
    private static final SocketService socketService = new SocketService();
    private final String nameService = "SocketService";
    public boolean stillRunning = true;
    
    public static SocketService gI(){
        return socketService;
    }
    
    private ServerSocket ss;

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void stop() {
        try {
            ss.close();
        } catch (IOException e) {
            ManagerService.err(this, "stop");
        }
    }
    
    private static void debug(String t){
        System.out.println(t);
    }
    
    private static class ClientHandle implements Runnable{
        private final Socket socket;
        private ObjectInputStream reader;
        private ObjectOutputStream writer;
        private Player player;
        
        public ClientHandle(Socket socket){
            this.socket = socket;
            try {
                writer = new ObjectOutputStream(this.socket.getOutputStream());
                reader = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                ManagerService.err(socketService, "init ClientHandle");
            }
        }
        
        private void writeMessage(Message msg){
            try {
                writer.writeObject(msg); 
                writer.reset();
                writer.flush();
                System.out.println("Send: " + msg.getMessageCode() + " " + msg.getData().toString());
            } catch (Exception e) {
                
            }
        }
        
        @Override
        public void run() {
            while(true){
                try {
                    Message message = (Message)reader.readObject();
                    System.out.println("Receive: " + message.getMessageCode());
                    Message response = new Message(message.getMessageCode(), null);
                    switch(message.getMessageCode()){
                        case Constaint.MESSAGE_JOIN_SERVER:{
                            Player p = (Player)message.getData();
                            gui.debug("Receive player " + p.toString());
                            p.idPlayer = Player.id++;
                            PlayerService.gI().players.add(p);
                            PlayerService.gI().addPlayer(p);
                            this.player = p;
                            this.player.writer = writer;
                            response.setData(p);
                            writeMessage(response);
                            break;
                        }
                        case Constaint.MESSAGE_GET_CONFIG_ROLE:{
                            response.setData(RoleService.gI().roles);
                            writeMessage(response);
                            break;
                        }
                        case Constaint.MESSAGE_CREATE_ROOM:{
                            List<Role> roleConfigs = (List<Role>)message.getData();
                            Room room = new Room();
                            room.idRoom = Room.id++;
                            room.configs = roleConfigs;
                            room.players.add(player);
                            room.owner = player;
                            response.setData(room);
                            RoomService.gI().rooms.add(room);
                            writeMessage(response);
                            break;
                        }
                        case Constaint.MESSAGE_LOAD_ROOM:{
                            System.out.println("Đang có " + RoomService.gI().rooms.size() + " rooms");
                            response.setData(RoomService.gI().rooms);
                            writeMessage(response);
                            break;
                        }
                        case Constaint.MESSAGE_JOIN_ROOM:{
                            int id = (int)message.getData();
                            Room room = RoomService.gI().getRoomById(id);
                            if(room != null){
                                if(!room.listBan.contains(this.player)){
                                    room.players.add(player);
                                    player.room = room;
                                    response.setData(room);
                                }
                                else{
                                    response.setMessageCode(Constaint.MESSAGE_HAS_BANNED);
                                }
                            }
                            else{
                                response.setData(null);
                            }
                            writeMessage(response);
                            RoomService.gI().reloadPlayer(room);
                            break;
                        }
                        //Note
                        case Constaint.MESSAGE_START_GAME:{
                            if(this.player.room.owner == this.player){
                                MessageService.gI().sendMessageInRoom(this.player.room, response);
                                this.player.room.isStartingGame = true;
                                new Task(this.player, 10000) {
                                    @Override
                                    public void main() {
                                        if(this.player.room.isStartingGame){
                                            MessageService.gI().sendMessageInRoom(this.player.room,
                                                    new Message(Constaint.MESSAGE_GAME_STARTING, null));
                                            GameController.gI().startGame(this.player.room);
                                        }
                                    }
                                }.start();
                            }
                            break;
                        }
                        case Constaint.MESSAGE_CHAT:{
                            MessageService.gI().chat(this.player, (String)message.getData());
                            break;
                        }
                        case Constaint.MESSAGE_KICK_PLAYER:{
                            Player p = (Player)message.getData();
                            Room room = RoomService.gI().getRoomById(this.player.room.idRoom);
                            if(room.owner.idPlayer == this.player.idPlayer){
                                RoomService.gI().kickPlayer(p, room, false);
                                RoomService.gI().reloadPlayer(room);
                            }
                            break;
                        }
                        case Constaint.MESSAGE_BAN_PLAYER:{
                            Player p = (Player)message.getData();
                            Room room = RoomService.gI().getRoomById(this.player.room.idRoom);
                            if(room.owner.idPlayer == this.player.idPlayer){
                                RoomService.gI().kickPlayer(p, room, true);
                                RoomService.gI().reloadPlayer(room);
                            }
                            break;
                        }
                        case Constaint.MESSAGE_STOP_GAME:{
                            if(this.player.room.owner == this.player){
                                MessageService.gI().sendMessageInRoom(this.player.room, response);
                                this.player.room.isStartingGame = false;
                            }
                            break;
                        }
                        //Message ingame from client
                        case Constaint.MESSAGE_PLAYER_VOTES:
                        case Constaint.MESSAGE_PLAYER_CANCEL_VOTES:
                        case Constaint.MESSAGE_XATHU_SHOOT:
                        case Constaint.MESSAGE_WOLF_VOTES:
                        case Constaint.MESSAGE_TIENTRI_SEE:
                        case Constaint.MESSAGE_THAYBOI_SEE:
                        case Constaint.MESSAGE_BACSI_ACT:
                        case Constaint.MESSAGE_CHAT_FROM_HELL:
                        case Constaint.MESSAGE_CHAT_FROM_WOLF:
                        case Constaint.MESSAGE_SOITIENTRI_SEE:{
                            GameController.gI().processMessage(this.player, message);
                        }
                    }
                } catch (ClassNotFoundException | IOException e) {
                    try {
                        reader.close();
                        writer.close();
                        socket.close();   
                    } catch (IOException e1) {
                        ManagerService.err(socketService, "close socket ClientHandle");
                    }
                    break;
                }
            }
        }
        
    }
    
    private static class ClientListener implements Runnable{
        private final ServerSocket ss;
        public ClientListener(ServerSocket _ss){
            ss = _ss;
        }
        
        @Override
        public void run() {
            while(socketService.stillRunning){
                try {
                    Socket socket = ss.accept();
                    new Thread(new ClientHandle(socket)).start();
                } catch (IOException e) {
                    ManagerService.err(socketService, "init ClientListener");
                }
            }
        }
        
    }
    
    @Override
    public void init(){
        try {
            ss = new ServerSocket(8888);
            ManagerService.initSuccess(nameService);
            new Thread(new ClientListener(ss)).start();
            ManagerService.initSuccess(nameService);
        } catch (IOException e) {
            ManagerService.err(this, "init");
        }
    }
    
    @Override
    public String getServiceName() {
        return nameService;
    }
}
