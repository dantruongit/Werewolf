package Server.service;

import Model.AIPlayer;
import java.util.ArrayList;
import java.util.List;
import Model.Player;
import Model.PlayerEffect;
import Model.PlayerVote;

public class PlayerService {
    private static final PlayerService playerService = new PlayerService();
    private PlayerService(){}
    public static PlayerService gI(){
        return playerService;
    }
    
    public List<Player> players = new ArrayList<>();
    public List<AIPlayer> bots = new ArrayList<>();
    
    public void addPlayer(Player p){
        int roomid = -1;
        if(p.room != null){
            roomid = p.room.idRoom;
        }
        ManagerService.gI().model.addRow(new Object[]{ p.namePlayer, roomid});
    }
    
    public void reloadPlayer(){
        ManagerService.gI().model.setRowCount(0);
        for(Player p : PlayerService.gI().players){
            ManagerService.gI().model.addRow(new Object[]{ p.namePlayer, p.room.idRoom});
        }
    }
    
    public Player getPlayerByUsername(String username){
        for(var p : players) if(p.namePlayer.equals(username)) return p;
        return null;
    }
    
    public AIPlayer makeBot(String nameBot){
        AIPlayer p = new AIPlayer();
        p.namePlayer = nameBot;
        p.playerEffect = new PlayerEffect();
        p.isBot = true;
        p.playerVote = new PlayerVote();
        return p;
    }
}
