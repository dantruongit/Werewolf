package Client.Session;

import Client.GameRoom;
import Client.Room_List;
import Client.utils.gui;
import Model.*;
import config.Constaint;
import java.io.ObjectInputStream;
import java.util.List;
import payload.Message;

public class Listener extends Thread{
    private ObjectInputStream ois;

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
    
    @Override
    public void run() {
        Service service = Service.gI();
        while(true){
            try {
                Message message = (Message)ois.readObject();
                Object data = message.getData();
                System.out.println("[Receive] " + message.getMessageCode());
                switch(message.getMessageCode()){
                    case Constaint.MESSAGE_GET_CONFIG_ROLE:{
                        service.dataSource.roleConfigs = (List<Role>)data;
                        break;
                    }
                    case Constaint.MESSAGE_CREATE_ROOM:{
                        Room room = (Room) data;
                        if(room != null){
                            System.out.println("[CREATE] " +room.toString());
                            service.dataSource.player.room = room;
                            service.frm.changePanel(new GameRoom());
                        }
                        break;
                    }
                    case Constaint.MESSAGE_LOAD_ROOM:{
                        service.dataSource.rooms = (List<Room>)data;
                        break;
                    }
                    case Constaint.MESSAGE_JOIN_SERVER:{
                        service.dataSource.player = (Player)data;
                        break;
                    }
                    case Constaint.MESSAGE_JOIN_ROOM:{
                        if(data == null){
                            gui.showMessage("Phòng này không khả dụng !");
                            break;
                        }
                        service.dataSource.player.room = (Room)data;
                        service.frm.changePanel(new GameRoom());
                        break;
                    }
                    case Constaint.MESSAGE_HAS_BANNED:{
                        gui.showMessage("Bạn đã bị ban khỏi phòng này");
                        break;
                    }
                    case Constaint.MESSAGE_CHAT:{
                        if(service.panelGame != null){
                            String chat = (String)data;
                            service.panelGame.addMessage(chat);
                        }
                        break;
                    }
                    case Constaint.MESSAGE_KICK_PLAYER:{
                        Player player = (Player)data;
                        if(player.idPlayer == service.dataSource.player.idPlayer){
                            gui.showMessage("Bạn đã bị kick bởi chủ phòng !");
                            service.panelGame = null;
                            service.dataSource.player.room = null;
                            service.frm.changePanel(new Room_List());
                        }
                        else{
                            String chat = player.namePlayer + " đã bị kick khỏi phòng !";
                            service.panelGame.addMessage(chat);
                        }
                        break;
                    }
                    case Constaint.MESSAGE_BAN_PLAYER:{
                        Player player = (Player)data;
                        if(player.idPlayer == service.dataSource.player.idPlayer){
                            gui.showMessage("Bạn đã bị ban bởi chủ phòng !");
                            service.panelGame = null;
                            service.dataSource.player.room = null;
                            service.frm.changePanel(new Room_List());
                        }
                        else{
                            String chat = player.namePlayer + " đã bị ban khỏi phòng !";
                            service.panelGame.addMessage(chat);
                        }
                        break;
                    }
                    case Constaint.MESSAGE_RELOAD_PLAYER:{
                        Room room = (Room)data;
                        if(room != null && service.panelGame != null){
                            service.dataSource.player.room = room;
                            service.panelGame.reloadPlayers();
                        }
                        break;
                    }
                    
                }
            } catch (Exception e) {
            }
        }
    }
    
}