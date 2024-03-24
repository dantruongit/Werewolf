package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable{
    public static int id = 0;
    public int idRoom;
    public String nameRoom;
    public Player owner;
    
    public boolean startedGame = false;
    
    public byte lastMessage = -1 ;
    
    public List<Role> configs = new ArrayList<>();
    //Role có random ngẫu nhiên hay không (có lợi cho sói) - cùng bật/tắt với isShowRoleWhenDie
    public boolean isRandom = false;
    //Có hiện role khi chết không  (có lợi cho dân) - cùng bật/tắt với isRandom
    public boolean isShowRoleWhenDie = false;
    public final List<Player> players = new ArrayList<>();

    @Override
    public String toString() {
        return "Room{" + "idRoom=" + idRoom + ", owner=" + owner + ", players=" + players + '}';
    }

    
    
    
}
