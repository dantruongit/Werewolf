package Server.service;

import Model.AIPlayer;
import java.util.ArrayList;
import java.util.List;
import Model.Player;
import Model.PlayerEffect;
import Model.PlayerVote;

public class PlayerService implements TemplateService{
    private static final PlayerService playerService = new PlayerService();
    private PlayerService(){}
    public static PlayerService gI(){
        return playerService;
    }
    
    private List<Player> players = new ArrayList<>();
    private List<AIPlayer> bots = new ArrayList<>();
    
    public void addPlayer(Player p){
        players.add(p);
        update();
    }
    
    public void removePlayer(Player p){
        players.removeIf(p1 -> p1.namePlayer.equals(p.namePlayer));
        update();
    }
    
    public void addBot(String nameBot){
        AIPlayer ai = makeBot(nameBot);
        bots.add(ai);
    }
    
    public AIPlayer getBot(int index){
        return bots.get(index);
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
    
    private AIPlayer makeBot(String nameBot){
        AIPlayer p = new AIPlayer();
        p.avatarId = (byte)Utils.RandomUtils.nextInt(1, 16);
        p.namePlayer = nameBot;
        p.playerEffect = new PlayerEffect();
        p.isBot = true;
        p.playerVote = new PlayerVote();
        return p;
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getServiceName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update() {
        try {
            ManagerService.gI().model.setRowCount(0);
            for(var p : players){
                int roomid = -1;
                if(p.room != null){
                    roomid = p.room.idRoom;
                }
                ManagerService.gI().model.addRow(new Object[]{ p.namePlayer, roomid, p.gameWin});
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
