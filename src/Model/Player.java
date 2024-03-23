package Model;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Player implements Serializable{
    public static int id = 0;
    
    public int idPlayer;
    public String namePlayer;
    transient public PlayerEffect playerEffect = new PlayerEffect();
    public Room room;
    public Game game;
    transient public ObjectOutputStream writer;

    @Override
    public String toString() {
        return "Player{" + "idPlayer=" + idPlayer + ", namePlayer=" + namePlayer + '}';
    }
    
    
}
