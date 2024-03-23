package Model;

import Server.service.MessageService;
import java.util.ArrayList;
import java.util.List;
import config.Constaint;
import java.io.Serializable;
import payload.Message;

public class Game implements Serializable{
    public static int id = 0;
    public int idGame ;
    public Room room;
    public byte gameState = Constaint.STAGE_SLEEPING;
    public List<Player> players = new ArrayList<>();
    transient public List<Player> teamWolf = new ArrayList<>();
    public List<Player> playersDie = new ArrayList<>();
    
}
