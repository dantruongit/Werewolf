package Model;

import java.io.Serializable;


public abstract class TaskInterval extends Thread implements Serializable{
    public boolean flag = true;
    
    public abstract void main();
    
    @Override
    public void run() {
        while(flag){
            try {
                main();
            } catch (Exception e) {
            }
        }
    }
    
}