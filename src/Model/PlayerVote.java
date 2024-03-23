package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerVote implements Serializable{
    public final List<Player> voters = new ArrayList<>();
    public Player target;
}
