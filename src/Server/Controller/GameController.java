package Server.Controller;

import Model.Game;
import Model.Player;
import Model.PlayerEffect;
import Model.PlayerVote;
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
        game.players = room.players;
        game.isShowRoleWhenDie = room.isShowRoleWhenDie;
        game.players.forEach(p -> {
            p.playerVote = new PlayerVote();
        });
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
            if(p.isBot) continue;
            Message message = new Message();
            //Chưa ngỏm thì gọi dậy thực hiện vai trò
            if(!p.isDie){
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
            MessageService.gI().sendMessagePrivate(p, message);
        }
    }
    
    private void wakeUpShooter(){
        for(Player p: game.players){
            if(p.isBot) continue;
            Message message = new Message();
            //Chưa ngỏm thì gọi dậy thực hiện vai trò xạ thủ
            if(!p.isDie && p.playerEffect.idRole == Constaint.ROLE_XATHU){
                message.setMessageCode(Constaint.WakeUp.ROLE_XATHU);
                MessageService.gI().sendMessagePrivate(p, message);
            }
        }
    }
    
    /**
     * Hàm xử lý ban đêm: Gửi tín hiệu vote cho sói
     */
    private void reloadWolfVotes(){
        Message message = new Message(Constaint.MESSAGE_UPDATE_VOTING, game.players);
        MessageService.gI().sendMessageForTeam(game.teamWolf, message, Constaint.TEAM_WOLF, true);
    }
    
    private void seeOtherWolf(){
        Message message = new Message(Constaint.MESSAGE_LIST_OTHER_WOLFS, game.teamWolf);
        MessageService.gI().sendMessageForTeam(game.teamWolf, message, Constaint.TEAM_WOLF, true);
    }
    
    private Player getPlayerByUsername(String username){
        for(var p: game.players) if(p.namePlayer.equals(username)) return p;
        return null;
    }
    
    private void alertVoteInDay(){
        String content = String.format("[Server]: Đến giờ bỏ phiếu ! Tối thiểu %d phiếu.", 
                (game.players.size() - game.playersInHell.size() + 1) / 2) ;
        Message message = new Message(Constaint.MESSAGE_CHAT, content);
        MessageService.gI().sendMessageInRoom(game, message);
    }
    
    private List<Player> getPlayerVoteDie(){
        final List<Player> result = new ArrayList<>();
        int max = 0;
        for(Player p: game.players){
            if(!p.isDie){
                int voteCount = p.playerVote.voteCount;
                max = max < voteCount ? voteCount : max;
            }
        }
        final int maxmax = max;
        game.players.forEach(p ->{
            if(!p.isDie){
                int voteCount = p.playerVote.voteCount;
                if(voteCount != 0 && voteCount == maxmax){
                    result.add(p);
                }
            }
        });
        return result;
    }
    
    private void setDiePlayer(Player p){
        Message message = new Message(Constaint.MESSAGE_PLAYER_DIE, p);
        Byte bite = game.isShowRoleWhenDie ? p.playerEffect.idRole : -1;
        message.setTmp(bite);
        MessageService.gI().sendMessageInRoom(game, message);
        game.playersInHell.add(p);
    }
    
    private void showRolePlayer(Player source){
        Message message = new Message(Constaint.MESSAGE_XATHU_SHOOT, source.namePlayer);
        MessageService.gI().sendMessageInRoom(game, message);
    }
    
    private void calculateStage(){
        try {
            List<Player> playersDie = getPlayerVoteDie();
//            List<Player> playersDie = new ArrayList<>();
//            playersDie.add(getPlayerByUsername("admin"));
            if(playersDie.size() == 1){
                String content;
                Player playerDie = playersDie.get(0);
                //Vote ban đêm của sói
                if(gameState == Constaint.STAGE_SLEEPING){
                    if(!playerDie.playerEffect.duocBaoVe){
                        content = String.format("[Server]: Người chơi %s đã bị sói cắn chết đêm qua.", playerDie.namePlayer);
                        setDiePlayer(playerDie);
                    }
                    else{
                        content = "[Server]: Không có ai chết đêm qua cả.";
                    }
                    Message message = new Message(Constaint.MESSAGE_CHAT, 
                        content);
                    MessageService.gI().sendMessageInRoom(game, message);
                }
                //Vote ban ngày của dân
                else{
                    setDiePlayer(playerDie);
                    content = "[Server]: " + playerDie.namePlayer +" đã bị dân làng treo cổ.";
                    Message message = new Message(Constaint.MESSAGE_CHAT, 
                        content);
                    MessageService.gI().sendMessageInRoom(game, message);
                }
            }
            //Không có phiếu bầu hoặc nhiều phiếu giống nhau
            else{
                if(gameState == Constaint.STAGE_VOTING){
                    Message message = new Message(Constaint.MESSAGE_CHAT, 
                        "[Server]: Không có ai bị chết cả.");
                    MessageService.gI().sendMessageInRoom(game, message);
                }
            }
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }
    
    private void resetState(){
        //Clear vote cũ
        for(Player p: game.players){
            p.playerVote.voteCount = 0;
            p.playerVote.target = null;
            p.playerEffect.duocBaoVe = false;
        }
        
    }
    
    private void changeState(){
        if(gameState == Constaint.STAGE_VOTING){
            gameState = Constaint.STAGE_SLEEPING;
            game.isShootSameDay = false;
        }
        else if (gameState == Constaint.STAGE_SLEEPING){
            gameState = Constaint.STAGE_DISCUSSING;
        }
        else if (gameState == Constaint.STAGE_DISCUSSING){
            gameState = Constaint.STAGE_VOTING;
        }
    }
    
    private void start(){
        System.out.println("Players " + game.players);
        System.out.println("Wolf " + game.teamWolf);
        task = new TaskInterval() {
            @Override
            public void main() {
                int delay = 10000;
                byte currentState = gameState;
                MessageService.gI().sendMessageInRoom(game.room, 
                        new Message(Constaint.STAGE_CHANGE, currentState));
                
                //Bắt đầu xử lý các stage hiện tại 
                switch(currentState){
                    //Thảo luận ban ngày
                    case Constaint.STAGE_DISCUSSING:{
                        delay = Constaint.Time.TIME_DISCUSSING;
                        //Gọi xạ thủ dậy
                        wakeUpShooter();
                        break;
                    }
                    //Voting ban ngày
                    case Constaint.STAGE_VOTING:{
                        delay = Constaint.Time.TIME_VOTING;
                        //Gọi xạ thủ dậy
                        if(!game.isShootSameDay)
                            wakeUpShooter();
                        alertVoteInDay();
                        updateStateVoting(game.players);
                        break;
                    }
                    //Gọi các vai trò dậy ở ban đêm ở đây, voting sói các kiểu
                    case Constaint.STAGE_SLEEPING:{
                        delay = Constaint.Time.TIME_SLEEPING;
                        //Gọi các vai trò đặc biệt dậy
                        wakeUpSpecial();
                        //Gửi danh sách mấy con sói cho nhau
                        seeOtherWolf();
                        //Gửi votes cho ma sói
                        reloadWolfVotes();
                        break;
                    }
                }
                //Kết thúc stage
                try {
                    Thread.sleep(delay + 800);
                } catch (Exception e) {
                }
                //Tính toán kết quả stage và thông báo cho players
                calculateStage();
                //Reset state của player
                resetState();
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
        MessageService.gI().sendMessageForTeam(game.players, message,Constaint.TEAM_WOLF, true);
    }
    
    /**
     *
     * @param player
     * @param message
     */
    public void processMessage(Player player, Message message){
        switch(message.getMessageCode()){
            //Done
            case Constaint.MESSAGE_PLAYER_VOTES:{
                String targetVote = (String)message.getData();
                if(gameState == Constaint.STAGE_VOTING){
                    Player pTarget = getPlayerByUsername(targetVote);
                    //Nếu trước đó có vote cho ai khác thì gỡ đi
                    if(player.playerVote.target != null){
                        player.playerVote.target.playerVote.voteCount-=1;
                    }
                    player.playerVote.target = pTarget;
                    pTarget.playerVote.voteCount ++;
                    updateStateVoting(game.players);
                }
                break;
            }
            //Done
            case Constaint.MESSAGE_PLAYER_CANCEL_VOTES:{
                String targetVote = (String)message.getData();
                if(gameState == Constaint.STAGE_VOTING){
                    Player pTarget = getPlayerByUsername(targetVote);
                    if(pTarget != null){
                        player.playerVote.target = null;
                        pTarget.playerVote.voteCount -=1 ;
                    }
                    updateStateVoting(game.players);
                }
                break;
            }
            //Done
            case Constaint.MESSAGE_XATHU_SHOOT:{
                Player target = getPlayerByUsername((String)message.getData());
                //Kiểm tra đạn và vai trò
                if(player.playerEffect.isXaThu && player.playerEffect.bullet > 0 && !target.isDie){
                    //Thực hiện hành động của player
                    player.playerEffect.bullet--;
                    player.playerEffect.isShowRole = true;
                    //Hủy vote của đối tượng bị bắn và setDie
                    target.isDie = true;
                    target.playerVote.target = null;
                    target.playerVote.voteCount = 0;
                    //Hủy vote của mọi người vào đối tượng bị bắn
                    for(var p : game.players){
                        if(p.playerVote.target == target){
                            p.playerVote.target = null;
                        }
                    }
                    //Set đã shoot trong ngày
                    game.isShootSameDay = true;
                    //Update state cho các player khác
                    String alert = String.format("[Server]: Xạ thủ %s đã bắn chết %s",
                            player.namePlayer, target.namePlayer);
                    MessageService.gI().sendMessageInRoom(game, 
                            new Message(Constaint.MESSAGE_CHAT, alert));
                    showRolePlayer(player);
                    setDiePlayer(target);
                    updateStateVoting(game.players);
                }
                //Hết đạn
                if(player.playerEffect.isXaThu && player.playerEffect.bullet == 0){
                    MessageService.gI().sendMessagePrivate(player, 
                            new Message(Constaint.MESSAGE_XATHU_OUT_OF_BULLET, null));
                }
                break;
            }
            //Done
            case Constaint.MESSAGE_WOLF_VOTES:{
                System.out.println("[ReceiveVote] " + message.getData().toString());
                String targetVote = (String)message.getData();
                if(player.game.gameState == Constaint.STAGE_SLEEPING){
                    Player pTarget = getPlayerByUsername(targetVote);
                    //Nếu trước đó có vote cho ai khác thì gỡ đi
                    if(player.playerVote.target != null){
                        player.playerVote.target.playerVote.voteCount -=1;
                    }
                    player.playerVote.target = pTarget;
                    pTarget.playerVote.voteCount+=1;
                    updateStateVotingWolfs(game.players);
                }
                break;
            }
            //Done
            case Constaint.MESSAGE_WOLF_CANCEL_VOTES:{
                String targetVote = (String)message.getData();
                if(player.game.gameState == Constaint.STAGE_SLEEPING){
                    Player pTarget = getPlayerByUsername(targetVote);
                    if(pTarget != null){
                        player.playerVote.target = null;
                        pTarget.playerVote.voteCount -=1 ;
                    }
                    updateStateVotingWolfs(game.players);
                }
                break;
            }
            //Done
            case Constaint.MESSAGE_TIENTRI_SEE:{
                Player pTarget = RoomService.gI().getPlayerByUsername(player.room, (String)message.getData());
                if(!pTarget.isDie){
                    MessageService.gI().sendMessagePrivate(player,
                            new Message(Constaint.MESSAGE_TIENTRI_SEE, pTarget.playerEffect.idRole));
                }
                break;
            }
            //Done
            case Constaint.MESSAGE_THAYBOI_SEE:{
                Player pTarget = RoomService.gI().getPlayerByUsername(player.room, (String)message.getData());
                if(!pTarget.isDie){
                    PlayerEffect pE = pTarget.playerEffect;
                    byte teamTarget = pE.isWolf() ? Constaint.TEAM_WOLF : pE.isUnknown() ? Constaint.TEAM_THIRD : Constaint.TEAM_VILLAGE;
                    MessageService.gI().sendMessagePrivate(player,
                            new Message(Constaint.MESSAGE_THAYBOI_SEE, teamTarget));
                }
                break;
            }
            //Done
            case Constaint.MESSAGE_BACSI_BAOVE:{
                String targetVote = (String)message.getData();
                if(gameState == Constaint.STAGE_SLEEPING){
                    for(var p: game.players){
                        p.playerEffect.duocBaoVe = false;
                    }
                    Player pTarget = getPlayerByUsername(targetVote);
                    pTarget.playerEffect.duocBaoVe = true;
                    MessageService.gI().sendMessagePrivate(player, 
                            new Message(Constaint.MESSAGE_BACSI_BAOVE, pTarget.namePlayer));
                }
                break;
            }
            //Done
            case Constaint.MESSAGE_BACSI_HUY_BAOVE:{
                if(gameState == Constaint.STAGE_SLEEPING){
                    for(var p: game.players){
                        p.playerEffect.duocBaoVe = false;
                    }
                    MessageService.gI().sendMessagePrivate(player, 
                            new Message(Constaint.MESSAGE_BACSI_BAOVE, null));
                }
                break;
            }
            
            case Constaint.MESSAGE_THAYDONG_HOISINH:{
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
                MessageService.gI().sendMessagePrivate(player.game.playersInHell, msg);
                break;
            }
            
            //Done
            case Constaint.MESSAGE_SOITIENTRI_SEE:{
                System.out.println("Wolf team : " + game.teamWolf);
                Player pTarget = RoomService.gI().getPlayerByUsername(player.room, (String)message.getData());
                if(!pTarget.isDie){
                    Message msg =  new Message(Constaint.MESSAGE_SOITIENTRI_SEE, pTarget);
                    msg.setTmp(pTarget.playerEffect.idRole);
                    MessageService.gI().sendMessagePrivate(player.game.teamWolf,
                            msg);
                }
                break;
            }
            case Constaint.MESSAGE_CHAT_FROM_WOLF:{
                break;
            }
        }
    }
}
