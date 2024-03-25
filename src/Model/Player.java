package Model;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Player implements Serializable{
    public String namePlayer;
    public byte avatarId = 2;
    transient public PlayerEffect playerEffect = new PlayerEffect();
    public boolean isDie = false;
    public PlayerVote playerVote = new PlayerVote();
    public Room room;
    public Game game;
    public boolean isBot = false;
    transient public ObjectOutputStream writer;
    
    //Achievement
    public int gameWin = 0;

    @Override
    public String toString() {
        return "Player namePlayer=" + namePlayer;
    }
    
    
}
