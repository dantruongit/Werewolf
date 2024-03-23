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
        try {
            room.players.removeIf(p -> p.namePlayer == target.namePlayer);
            target.room = null;
            if(room.owner.namePlayer == target.namePlayer && !room.startedGame){
                removeRoom(room);
            }
            if(target.game != null){
                target.game.players.removeIf(p -> p.namePlayer == target.namePlayer);
                target.game.playersDie.removeIf(p -> p.namePlayer == target.namePlayer);
            }
            MessageService.gI().sendMessageInRoom(room, 
                    new Message(Constaint.MESSAGE_CHAT, "[Server]: " + target.namePlayer + " đã rời khỏi phòng."));
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    
    public void startGame(Room room, Game game){
        
    }
    
    public Player getPlayerByUsername(Room room, String username){
        for(Player p: room.players){
            if(p.namePlayer.equals(username)) return p;
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
