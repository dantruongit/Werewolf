package Model;

import java.io.Serializable;

public class Stage implements Serializable{
    public byte time;
    public String message;
    public int seconds;

    public Stage(byte time, String message, int seconds) {
        this.time = time;
        this.message = message;
        this.seconds = seconds;
    }
}
