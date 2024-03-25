package Model;

import java.util.ArrayList;
import java.util.List;
import config.Constaint;
import java.io.Serializable;

public class Game implements Serializable{
    public static int id = 0;
    public int idGame ;
    public Room room;
    public byte gameState = Constaint.STAGE_SLEEPING;
    public boolean isShowRoleWhenDie = false;
    public boolean isShootSameDay = false;
    public List<Player> players = new ArrayList<>();
    transient public List<Player> teamWolf = new ArrayList<>();
    public Player lastPlayerRevival = null;
    
    public int getPlayerAlive(){
        int cnt = 0;
        for(var p: players){
            if(!p.isDie) cnt += 1;
        }
        return cnt;
    }
}
