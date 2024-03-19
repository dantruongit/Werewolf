package Server.Controller;

import Model.Game;
import Model.Player;
import Model.PlayerEffect;
import Model.Room;
import Model.Vote;
import Server.service.MessageService;
import Server.service.RoleService;
import Server.service.RoomService;
import config.Constaint;
import java.util.ArrayList;
import java.util.List;
import payload.Message;

public class GameController {
    private static final GameController gameController = new GameController();
    
    public static GameController gI(){
        return gameController;
    }
    
    public List<Game> gamesRunning = new ArrayList<>();
    public List<Vote> voteTickets = new ArrayList<>();
    
    
    public void addNewGame(Room room){
        Game game = new Game();
        game.currentTime = Constaint.TIME_DAY;
        game.room = room;
        room.players.forEach(p -> {
            p.game = game;
            if(p.playerEffect.isWolf()){
                game.teamWolf.add(p);
            }
        });
        game.players = room.players;
        gamesRunning.add(game);
    }
    
    public void startGame(Room room){
        MessageService.gI().sendMessageInRoom(room, new Message(Constaint.MESSAGE_GAME_STARTED, true));
        RoleService.gI().rollRoles(room);
        room.startedGame = true;
        this.addNewGame(room);
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
