package Server.Controller;

import Model.Game;
import Model.Player;
import Model.PlayerEffect;
import Model.Room;
import Model.TaskInterval;
import Model.Vote;
import Server.service.MessageService;
import Server.service.RoleService;
import Server.service.RoomService;
import config.Constaint;
import java.util.ArrayList;
import java.util.List;
import payload.Message;

public class GameController {
    
    private GameController(Game game){ this.game = game; }
    private final Game game;
    private final static List<GameController> games = new ArrayList<>();
    private byte gameState = Constaint.STAGE_SLEEPING;
    private TaskInterval task;
    
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
    
    public static GameController gI(Player player){
        for(GameController gameController: games){
            if(gameController.game.idGame == player.game.idGame){
                return gameController;
            }
        }
        return null;
    }
    
    public List<Vote> voteTickets = new ArrayList<>();
    
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
    
    //Hàm xử lý ban đêm: gọi các vai trò đặc biệt dậy
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
    
    private void updateStatePlayer(Room room, List<Player> players){
        Message message = new Message(Constaint.MESSAGE_UPDATE_STATE_PLAYERS, players);
        MessageService.gI().sendMessageInRoom(room, message);
    }
    
    private void updateStateVoting(Room room, List<Player> wolfs, List<Vote> votes){
        Message message = new Message(Constaint.MESSAGE_UPDATE_VOTING, votes);
        if(wolfs != null){
            MessageService.gI().sendMessageForTeam(room, message, Constaint.TEAM_WOLF);
        }
        else{
            MessageService.gI().sendMessageInRoom(room, message);
        }
    }
    
    //Process voting
    public Vote getPlayerVote(Player player){
        for(Vote v: voteTickets){
            if(v.playerVote == player) return v;
        }
        return null;
    }
    
    public void processMessage(Player player, Message message){
        switch(message.getMessageCode()){
            case Constaint.MESSAGE_PLAYER_VOTES:{
                Vote vote_ticket = (Vote)message.getData();
                if(player.game.gameState == Constaint.STAGE_VOTING){
                    Vote old = getPlayerVote(player);
                    if(old != null){
                        voteTickets.remove(old);
                    }
                    voteTickets.add(vote_ticket);
                    updateStateVoting(player.room, null, voteTickets);
                }
                break;
            }
            case Constaint.MESSAGE_PLAYER_CANCEL_VOTES:{
                if(player.game.gameState == Constaint.STAGE_VOTING){
                    Vote old = getPlayerVote(player);
                    if(old != null){
                        voteTickets.remove(old);
                    }
                    updateStateVoting(player.room, null, voteTickets);
                }
                break;
            }
            case Constaint.MESSAGE_XATHU_SHOOT:{
                Player target = (Player)message.getData();
                if(player.playerEffect.isXaThu && player.playerEffect.bullet > 0 && !target.playerEffect.isDie){
                    target.playerEffect.isDie = true;
                    player.playerEffect.bullet--;
                    player.playerEffect.isShowRole = true;
                    updateStatePlayer(player.game.room, player.game.players);
                }
                break;
            }
            case Constaint.MESSAGE_WOLF_VOTES:{
                Vote vote_ticket = (Vote)message.getData();
                if(player.game.gameState == Constaint.STAGE_VOTING){
                    Vote old = getPlayerVote(player);
                    if(old != null){
                        voteTickets.remove(old);
                    }
                    voteTickets.add(vote_ticket);
                    updateStateVoting(player.room, player.game.teamWolf, voteTickets);
                }
                break;
            }
            case Constaint.MESSAGE_TIENTRI_SEE:{
                Player pTarget = RoomService.gI().getPlayerById(player.room, (int)message.getData());
                if(!pTarget.playerEffect.isDie){
                    MessageService.gI().sendMessagePrivate(player,
                            new Message(Constaint.MESSAGE_TIENTRI_SEE, pTarget.playerEffect.idRole));
                }
                break;
            }
            case Constaint.MESSAGE_THAYBOI_SEE:{
                Player pTarget = RoomService.gI().getPlayerById(player.room, (int)message.getData());
                if(!pTarget.playerEffect.isDie){
                    PlayerEffect pE = pTarget.playerEffect;
                    byte teamTarget = pE.isWolf() ? Constaint.TEAM_WOLF : pE.isUnknown() ? Constaint.TEAM_THIRD : Constaint.TEAM_VILLAGE;
                    MessageService.gI().sendMessagePrivate(player,
                            new Message(Constaint.MESSAGE_THAYBOI_SEE, teamTarget));
                }
                break;
            }
            case Constaint.MESSAGE_BACSI_ACT:{
                Player pTarget = RoomService.gI().getPlayerById(player.room, (int)message.getData());
                if(!pTarget.playerEffect.isDie){
                    player.game.players.forEach(p ->{
                        p.playerEffect.duocBaoVe = false;
                    });
                    pTarget.playerEffect.duocBaoVe = true;
                }
                break;
            }
            case Constaint.MESSAGE_CHAT_FROM_HELL:{
                Message msg = new Message(Constaint.MESSAGE_CHAT_FROM_HELL, (String)message.getData());
                MessageService.gI().sendMessagePrivate(player.game.playersDie, msg);
                break;
            }
            case Constaint.MESSAGE_SOITIENTRI_SEE:{
                Player pTarget = RoomService.gI().getPlayerById(player.room, (int)message.getData());
                if(!pTarget.playerEffect.isDie){
                    MessageService.gI().sendMessagePrivate(player.game.teamWolf,
                            new Message(Constaint.MESSAGE_TIENTRI_SEE, pTarget.playerEffect.idRole));
                }
                break;
            }
        }
    }
}
