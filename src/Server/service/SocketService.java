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
                            Player old = PlayerService.gI().getPlayerByUsername(p.namePlayer);
                            if(old != null){
                                response.setData(null);
                            }
                            else{
                                p.playerEffect = new PlayerEffect();
                                PlayerService.gI().players.add(p);
                                PlayerService.gI().addPlayer(p);
                                this.player = p;
                                this.player.writer = writer;
                                response.setData(p);
                            }
                            writeMessage(response);
                            break;
                        }
                        case Constaint.MESSAGE_GET_CONFIG_ROLE:{
                            response.setData(RoleService.gI().roles);
                            writeMessage(response);
                            break;
                        }
                        case Constaint.MESSAGE_CREATE_ROOM:{
                            Room room = (Room)message.getData();
                            room.idRoom = Room.id++;
                            room.players.add(player);
                            room.owner = player;
                            response.setData(room);
                            player.room = room;
                            RoomService.gI().rooms.add(room);
                            writeMessage(response);
                            break;
                        }
                        case Constaint.MESSAGE_LOAD_ROOM:{
                            response.setData(RoomService.gI().rooms);
                            writeMessage(response);
                            break;
                        }
                        case Constaint.MESSAGE_JOIN_ROOM:{
                            int id = (int)message.getData();
                            Room room = RoomService.gI().getRoomById(id);
                            if(room != null){
                                room.players.add(player);
                                player.room = room;
                                response.setData(room);
                            }
                            else{
                                response.setData(null);
                            }
                            writeMessage(response);
                            RoomService.gI().reloadPlayer(room);
                            break;
                        }
                        
                        case Constaint.MESSAGE_LEAVE_ROOM:{
                            Room room = RoomService.gI().getRoomById(player.room.idRoom);
                            if(room != null){
                                RoomService.gI().leavedPlayer(player, room);
                                RoomService.gI().reloadPlayer(room);
                            }
                            break;
                        }
                        
                        //Note
                        case Constaint.MESSAGE_START_GAME:{
                            if(this.player.room.owner.namePlayer == this.player.namePlayer){
                                this.player.room.startedGame = true;
                                Game game = GameController.initNewGame(this.player.room);
                                response.setData(game);
                                MessageService.gI().sendMessageInRoom(this.player.room, response);
                            }
                            break;
                        }
                        case Constaint.MESSAGE_CHAT:{
                            MessageService.gI().chat(this.player, (String)message.getData());
                            break;
                        }
                        //Các message khác cứ quăng cho GameController xử lý
                        default:{
                            GameController.gI(player).processMessage(this.player, message);
                        }
                    }
                } catch (ClassNotFoundException | IOException e) {
                    if(player.room != null){
                        Room room = RoomService.gI().getRoomById(player.room.idRoom);
                        RoomService.gI().leavedPlayer(player, room);
                        RoomService.gI().reloadPlayer(room);
                    }
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
            ss = new ServerSocket(config.Connect.port);
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
