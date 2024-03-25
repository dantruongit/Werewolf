package Server.service;

import Model.Game;
import Model.Player;
import Model.PlayerEffect;
import Model.Room;
import config.Constaint;
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
            if(p != null && p.writer != null){
                sendMessagePrivate(p, message);
            }
        }
    }
    
    public void sendMessageInRoom(Game game, Message message){
        for(Player p: game.players){
            if(p!= null && p.writer != null){
                sendMessagePrivate(p, message);
            }
        }
    }
    
    public void chat(Player playerSend, String content){
        String cont = playerSend.namePlayer + ": " + content;
        Message message = new Message(config.Constaint.MESSAGE_CHAT,cont);
        this.sendMessageInRoom(playerSend.room, message);
    }
    
    public void sendMessageForTeam(List<Player> players,Message message ,byte team, boolean includeDiePlayer){
        for(var p: players){
            PlayerEffect pE = p.playerEffect;
            if(p.writer != null){
                boolean canSend = 
                        (team == Constaint.TEAM_WOLF && pE.isWolfTeam())||
                        (team == Constaint.TEAM_VILLAGE && pE.isVillage()) ||
                        (team == Constaint.TEAM_THIRD && pE.isUnknown()) ||
                        (team == Constaint.TEAM_HELL && (p.isDie || p.playerEffect.idRole == Constaint.ROLE_THAYDONG));
                boolean sendToDiePlayer = includeDiePlayer ? true : !p.isDie;
                if(canSend && sendToDiePlayer)
                    sendMessagePrivate(p, message);
            }
        }
    }
    
    public void sendMessagePrivate(Player player, Message message){
        if(!player.isBot)
        try {
            player.writer.writeObject(message);
            player.writer.reset();
            player.writer.flush();
            //System.out.println("[sendMessagePrivate] " + message.getMessageCode());
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    
    public void sendMessagePrivate(List<Player> players, Message message){
        for(Player p: players){
            sendMessagePrivate(p, message);
        }
    }
}
