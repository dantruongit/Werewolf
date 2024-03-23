package Server.service;

import Model.Game;
import Model.Room;
import Model.Player;
import java.util.ArrayList;
import java.util.List;
import config.Constaint;
import payload.Message;

public class RoomService implements TemplateService{
    private static final RoomService roomService = new RoomService();
    private final String nameService = "RoomService";
    public static RoomService gI() {
        return roomService;
    }
    
    public List<Room> rooms;
    
    @Override
    public void init(){
        rooms = new ArrayList<>();
        Room room = new Room();
        room.idRoom = -1;
        room.owner = new Player();
        room.owner.namePlayer = "Admin";
        rooms.add(room);
        ManagerService.initSuccess(nameService);
    }
    
    public Room getRoomById(int id){
        for(Room r: rooms){
            if(r.idRoom == id) return r;
        }
        return null;
    }
    
    public void removeRoom(Room room){
        MessageService.gI().sendMessagePrivate(room.players, 
                new Message(Constaint.MESSAGE_ROOM_REMOVED, null));
        rooms.remove(room);
    }
    
    public void reloadPlayer(Room room){
        if(!room.players.isEmpty())
            MessageService.gI().sendMessageInRoom(room, 
                                    new Message(Constaint.MESSAGE_RELOAD_PLAYER, room));
        else{
            removeRoom(room);
        }
    }
    
    public void leavedPlayer(Player target, Room room){
        room.players.removeIf(p -> p.idPlayer == target.idPlayer);
        target.room = null;
        if(room.owner.idPlayer == target.idPlayer && !room.startedGame){
            removeRoom(room);
        }
        if(target.game != null){
            target.game.players.removeIf(p -> p.idPlayer == target.idPlayer);
            target.game.playersDie.removeIf(p -> p.idPlayer == target.idPlayer);
        }
        MessageService.gI().sendMessageInRoom(room, 
                new Message(Constaint.MESSAGE_CHAT, "[Server]: " + target.namePlayer + " đã rời khỏi phòng."));
    }
    
    public void startGame(Room room, Game game){
        
    }
    
    public Player getPlayerById(Room room, int id){
        for(Player p: room.players){
            if(p.idPlayer == id) return p;
        }
        return null;
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void stop() {
        rooms.clear();
    }
    
    @Override
    public String getServiceName() {
        return nameService;
    }
}
