package Model;

import java.io.Serializable;

public abstract class Task extends Thread implements Serializable{
    protected final int delay;
    protected final Player player;
    
    public Task(Player playerAct, int delay){
        this.delay = delay;
        player = playerAct;
    }
    
    public abstract void main();
    
    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            main();
        } catch (Exception e) {
        }
    }
    
}
