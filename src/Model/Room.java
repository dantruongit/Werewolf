package Model;

import Server.service.RoleService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable{
    public static int id = 0;
    public int idRoom;
    public String nameRoom;
    public Player owner;
    
    public boolean isStartingGame = false;
    public boolean startedGame = false;
    public boolean isRandom = false;
    public byte lastMessage = -1 ;
    
    public List<Role> configs = new ArrayList<>();
    public final List<Player> players = new ArrayList<>();
    
    public final List<Player> listBan = new ArrayList<>();

    @Override
    public String toString() {
        return "Room{" + "idRoom=" + idRoom + ", owner=" + owner + ", players=" + players + '}';
    }

    
    
    
}
