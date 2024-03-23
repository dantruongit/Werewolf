package Model;

import java.io.Serializable;

public class Stage implements Serializable{
    public byte time;
    public String message;
    public int miliseconds;
    public byte currentStage;
    
    public Stage(byte time, String message, int miliseconds) {
        this.time = time;
        this.message = message;
        this.miliseconds = miliseconds;
    }
}
