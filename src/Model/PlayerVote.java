package Model;

import java.io.Serializable;

public class PlayerVote implements Serializable{
    public int voteCount = 0;
    public Player target;

    @Override
    public String toString() {
        return "PlayerVote{" + "voters=" + voteCount + ", target=" + target + '}';
    }
    
    
}
