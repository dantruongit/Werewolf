package Server.Controller;

import Model.Game;
import Model.Player;
import Model.PlayerEffect;
import Model.Room;
import Model.TaskInterval;
import Server.service.MessageService;
import Server.service.PlayerService;
import Server.service.RoleService;
import Server.service.RoomService;
import config.Constaint;
import java.util.ArrayList;
import java.util.List;
import payload.Message;

/**
 *
 * @author cr4zyb0t
 */
public class GameController {
    
    private GameController(Game game){ this.game = game; }
    private final Game game;
    private final static List<GameController> games = new ArrayList<>();
    private byte gameState = Constaint.STAGE_SLEEPING;
    private TaskInterval task;
    
    /**
     * Khởi tạo 1 game mới
     * @param room Room khởi tạo ra cái game đó
     * @return Một đối tượng Game để gửi cho client
     */
    public static Game initNewGame(Room room){
        Game game = new Game();
        game.idGame = Game.id++;
        game.room = room;
        room.players.forEach(p -> {
            p.game = game;
            if(p.playerEffect.isWolf()){
                game.teamWolf.add(p);
            }
        });
        game.players = room.players;
        GameController gameC = new GameController(game);
        gameC.startGame();
        games.add(gameC);
        return game;
    }
    
    /**
     *
     * @param player
     * @return
     */
    public static GameController gI(Player player){
        for(GameController gameController: games){
            if(gameController.game.idGame == player.game.idGame){
                return gameController;
            }
        }
        return null;
    }
    
    
    
    /**
     *
     */
    public void startGame(){
        //Quay số role
        RoleService.gI().rollRoles(this.game.room, this.game);
        //Start game
        new Thread(() ->{
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            this.start();
        }).start();
    }
    
    /**
     * Hàm xử lý ban đêm: gọi các vai trò đặc biệt dậy
     */
    private void wakeUpSpecial(){
        for(Player p: game.players){
            Message message = new Message();
            //Chưa ngỏm thì gọi dậy thực hiện vai trò
            if(!p.playerEffect.isDie){
                switch(p.playerEffect.idRole){
                    case Constaint.ROLE_THAYBOI:{
                        message.setMessageCode(Constaint.WakeUp.ROLE_THAYBOI);
                        break;
                    }
                    case Constaint.ROLE_BACSI:{
                        message.setMessageCode(Constaint.WakeUp.ROLE_BACSI);
                        break;
                    }
                    case Constaint.ROLE_THAYDONG:{
                        message.setMessageCode(Constaint.WakeUp.ROLE_THAYDONG);
                        break;
                    }
                    case Constaint.ROLE_TIENTRI:{
                        message.setMessageCode(Constaint.WakeUp.ROLE_TIENTRI);
                        break;
                    }
                    case Constaint.ROLE_SOITIENTRI:{
                        message.setMessageCode(Constaint.WakeUp.ROLE_SOITIENTRI);
                        break;
                    }
                    case Constaint.ROLE_SOI:{
                        message.setMessageCode(Constaint.WakeUp.ROLE_SOI);
                        break;
                    }
                }
            }
            //Ngỏm rồi thì thêm vào hell
            else{
                message.setMessageCode(Constaint.CAN_CHAT_IN_HELL);
            }
            MessageService.gI().sendMessagePrivate(p, message);
        }
    }
    
    /**
     * Hàm xử lý ban đêm: Gửi tín hiệu vote cho sói
     */
    private void reloadWolfVotes(){
        Message message = new Message(Constaint.MESSAGE_WOLF_VOTES, game.players);
        MessageService.gI().sendMessageForTeam(game.teamWolf, message, Constaint.TEAM_WOLF);
    }
    
    private void updateStatePlayer(Player p){
        Message message = new Message(Constaint.MESSAGE_UPDATE_PLAYER, p);
        MessageService.gI().sendMessageInRoom(game, message);
    }
    
    private void calculateStage(){
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
    }
    
    private void changeState(){
        this.gameState = 
        this.gameState == Constaint.STAGE_VOTING ? Constaint.STAGE_SLEEPING :
        this.gameState == Constaint.STAGE_SLEEPING ? Constaint.STAGE_DISCUSSING :
        Constaint.STAGE_VOTING;
    }
    
    private void start(){
        task = new TaskInterval() {
            @Override
            public void main() {
                int delay = 10000;
                byte currentState = gameState;
                MessageService.gI().sendMessageInRoom(game.room, 
                        new Message(Constaint.STAGE_CHANGE, currentState));
                //Clear vote cũ
                for(Player p: game.players){
                    p.playerVote.voters.clear();
                    p.playerVote.target = null;
                }
                //Bắt đầu xử lý các stage hiện tại 
                switch(currentState){
                    //Thảo luận ban ngày
                    case Constaint.STAGE_DISCUSSING:{
                        delay = Constaint.Time.TIME_DISCUSSING;
                        break;
                    }
                    //Voting ban ngày
                    case Constaint.STAGE_VOTING:{
                        delay = Constaint.Time.TIME_VOTING;
                        break;
                    }
                    //Gọi các vai trò dậy ở ban đêm ở đây, voting sói các kiểu
                    case Constaint.STAGE_SLEEPING:{
                        delay = Constaint.Time.TIME_SLEEPING;
                        //Gọi các vai trò đặc biệt dậy
                        wakeUpSpecial();
                        //Gửi votes cho ma sói
                        reloadWolfVotes();
                        break;
                    }
                }
                //Kết thúc stage
                try {
                    Thread.sleep(delay + 2000);
                } catch (Exception e) {
                }
                //Tính toán kết quả stage và thông báo cho players
                //calculateStage();
                //Thay đổi stage
                changeState();
            }
        };
        task.start();
    }
    
    //Update game
    //Process voting
    /**
     * Hàm cập nhật voting cho mọi người
     */
    private void updateStateVoting(List<Player> dataPlayers){
        Message message = new Message(Constaint.MESSAGE_UPDATE_VOTING, dataPlayers);
        MessageService.gI().sendMessageInRoom(game, message);
    }
    
    /**
     * Hàm cập nhật voting cho sói
     */
    private void updateStateVotingWolfs(List<Player> dataPlayers){
        Message message = new Message(Constaint.MESSAGE_UPDATE_VOTING, dataPlayers);
        MessageService.gI().sendMessageForTeam(game.players, message,Constaint.TEAM_WOLF);
    }
    
    /**
     *
     * @param player
     * @param message
     */
    public void processMessage(Player player, Message message){
        switch(message.getMessageCode()){
            case Constaint.MESSAGE_PLAYER_VOTES:{
                String targetVote = (String)message.getData();
                if(player.game.gameState == Constaint.STAGE_VOTING){
                    Player pTarget = PlayerService.gI().getPlayerByUsername(targetVote);
                    //Nếu trước đó có vote cho ai khác thì gỡ đi
                    if(player.playerVote.target != null){
                        player.playerVote.target.playerVote.voters.remove(player);
                    }
                    player.playerVote.target = pTarget;
                    pTarget.playerVote.voters.add(player);
                    updateStateVoting(game.players);
                }
                break;
            }
            case Constaint.MESSAGE_PLAYER_CANCEL_VOTES:{
                if(player.game.gameState == Constaint.STAGE_VOTING){
                    //Nếu trước đó có vote cho ai khác thì gỡ đi
                    if(player.playerVote.target != null){
                        player.playerVote.target.playerVote.voters.remove(player);
                        player.playerVote.target = null;
                    }
                    updateStateVoting(game.players);
                }
                break;
            }
            case Constaint.MESSAGE_XATHU_SHOOT:{
                Player target = (Player)message.getData();
                //Kiểm tra đạn và vai trò
                if(player.playerEffect.isXaThu && player.playerEffect.bullet > 0 && !target.playerEffect.isDie){
                    target.playerEffect.isDie = true;
                    player.playerEffect.bullet--;
                    player.playerEffect.isShowRole = true;
                    updateStatePlayer(player);
                }
                break;
            }
            case Constaint.MESSAGE_WOLF_VOTES:{
                String targetVote = (String)message.getData();
                if(player.game.gameState == Constaint.STAGE_VOTING){
                    Player pTarget = PlayerService.gI().getPlayerByUsername(targetVote);
                    //Nếu trước đó có vote cho ai khác thì gỡ đi
                    if(player.playerVote.target != null){
                        player.playerVote.target.playerVote.voters.remove(player);
                    }
                    player.playerVote.target = pTarget;
                    pTarget.playerVote.voters.add(player);
                    updateStateVotingWolfs(game.players);
                }
                break;
            }
            case Constaint.MESSAGE_TIENTRI_SEE:{
                Player pTarget = RoomService.gI().getPlayerByUsername(player.room, (String)message.getData());
                if(!pTarget.playerEffect.isDie){
                    MessageService.gI().sendMessagePrivate(player,
                            new Message(Constaint.MESSAGE_TIENTRI_SEE, pTarget.playerEffect.idRole));
                }
                break;
            }
            case Constaint.MESSAGE_THAYBOI_SEE:{
                Player pTarget = RoomService.gI().getPlayerByUsername(player.room, (String)message.getData());
                if(!pTarget.playerEffect.isDie){
                    PlayerEffect pE = pTarget.playerEffect;
                    byte teamTarget = pE.isWolf() ? Constaint.TEAM_WOLF : pE.isUnknown() ? Constaint.TEAM_THIRD : Constaint.TEAM_VILLAGE;
                    MessageService.gI().sendMessagePrivate(player,
                            new Message(Constaint.MESSAGE_THAYBOI_SEE, teamTarget));
                }
                break;
            }
            case Constaint.MESSAGE_BACSI_ACT:{
                Message msg = new Message(Constaint.MESSAGE_BACSI_ACT, null);
                Player pTarget = RoomService.gI().getPlayerByUsername(player.room, (String)message.getData());
                if(pTarget.playerEffect.duocBaoVe){
                    pTarget.playerEffect.duocBaoVe = false;
                    break;
                }
                else if(!pTarget.playerEffect.isDie){
                    player.game.players.forEach(p ->{
                        p.playerEffect.duocBaoVe = false;
                    });
                    pTarget.playerEffect.duocBaoVe = true;
                    msg.setData(pTarget.namePlayer);
                }
                MessageService.gI().sendMessagePrivate(player, msg);
                break;
            }
            case Constaint.MESSAGE_CHAT_FROM_HELL:{
                String content;
                if(player.playerEffect.isThayDong){
                    content = "Thầy đồng: " + (String)message.getData();
                }
                else{
                    content = player.namePlayer + ": " + (String)message.getData();
                }
                Message msg = new Message(Constaint.MESSAGE_CHAT_FROM_HELL, content);
                MessageService.gI().sendMessagePrivate(player.game.playersDie, msg);
                break;
            }
            case Constaint.MESSAGE_SOITIENTRI_SEE:{
                Player pTarget = RoomService.gI().getPlayerByUsername(player.room, (String)message.getData());
                if(!pTarget.playerEffect.isDie){
                    MessageService.gI().sendMessagePrivate(player.game.teamWolf,
                            new Message(Constaint.MESSAGE_SOITIENTRI_SEE, pTarget.playerEffect.idRole));
                }
                break;
            }
        }
    }
}
