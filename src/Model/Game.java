package Model;

import Server.service.MessageService;
import java.util.ArrayList;
import java.util.List;
import config.Constaint;
import java.io.Serializable;
import payload.Message;

public class Game implements Serializable{
    public Room room;
    public byte currentTime = Constaint.TIME_DAY;
    public byte gameState = Constaint.STAGE_SLEEPING;
    public List<Player> players = new ArrayList<>();
    public List<Player> playersDie = new ArrayList<>();
    public List<Player> teamWolf = new ArrayList<>();
    
    TaskInterval task; 
    
    public void calculateStage(){
        
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
    }
    
    public void changeState(){
        this.gameState = 
        this.gameState == Constaint.STAGE_VOTING ? Constaint.STAGE_SLEEPING :
        this.gameState == Constaint.STAGE_SLEEPING ? Constaint.STAGE_DISCUSSING :
        Constaint.STAGE_VOTING;
    }
    
    public void start(){
        task = new TaskInterval() {
            @Override
            public void main() {
                int delay = 10000;
                byte currentState = gameState;
                switch(currentState){
                    case Constaint.STAGE_DISCUSSING:{
                        delay = 40000;
                        break;
                    }
                    case Constaint.STAGE_VOTING:{
                        delay = 20000;
                        break;
                    }
                    case Constaint.STAGE_SLEEPING:{
                        delay = 25000;
                        break;
                    }
                }
                MessageService.gI().sendMessageInRoom(room, 
                        new Message(Constaint.STAGE_CHANGE, currentState));
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                }
                calculateStage();
                changeState();
            }
        };
        task.start();
    }
}
