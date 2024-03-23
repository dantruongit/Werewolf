package Server.service;

import Model.Player;
import Model.Room;
import java.io.IOException;
import java.util.List;
import payload.Message;

public class MessageService {
    private static final MessageService messageService = new MessageService();
    public static MessageService gI(){
        return messageService;
    }
    
    public void sendMessageInRoom(Room r, Message message){
        for(Player p: r.players){
            if(p!= null && p.writer != null){
                sendMessagePrivate(p, message);
            }
        }
    }
    
    public void chat(Player playerSend, String content){
        String cont = playerSend.namePlayer + " : " + content;
        Message message = new Message(config.Constaint.MESSAGE_CHAT,cont);
        this.sendMessageInRoom(playerSend.room, message);
    }
    
    public void sendMessageForTeam(Room room,Message message ,byte team){
        
    }
    
    public void sendMessagePrivate(Player player, Message message){
        try {
            player.writer.writeObject(message);
            player.writer.reset();
            player.writer.flush();
            System.out.println("[sendMessagePrivate] " + message.getMessageCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessagePrivate(List<Player> players, Message message){
        for(Player p: players){
            sendMessagePrivate(p, message);
        }
    }
}
