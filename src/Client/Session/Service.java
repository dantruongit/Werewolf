package Client.Session;

import Client.GameRoom;
import Client.TemplateFrm;
import Client.utils.gui;
import Model.Player;
import Model.PlayerEffect;
import Model.Room;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import payload.Message;
import config.Constaint;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Service {
    private static final Service service = new Service();
    private Service(){}
    
    public static Service gI(){
        return service;
    }
    
    private Socket socket;
    public Listener listener;
    public Writer writer;
    
    //UI handle
    public TemplateFrm frm;
    public GameRoom panelGame;
    
    public DataSource dataSource = new DataSource();
    
    public void init(String username){
        dataSource.player.namePlayer = username;
        //Join server
        this.sendMessage(Constaint.MESSAGE_JOIN_SERVER, this.dataSource.player);
        this.sendMessage(Constaint.MESSAGE_LOAD_ROOM, null);
        //Load config role
        this.sendMessage(Constaint.MESSAGE_GET_CONFIG_ROLE, null);
        
    }
    
    public void joinGame(){
        try {
            socket = new Socket(config.Connect.host,config.Connect.port);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            this.listener = new Listener();
            this.listener.setOis(ois);
            writer = new Writer();
            writer.oos = oos;
            //Start-listener
            this.listener.start();
        } catch (Exception e) {
            gui.showError("Lỗi", "Không thể khởi tạo socket !", true);
        }
    }
    
    public void sendMessage(byte code, Object data){
        Message msg = new Message(code, data);
        this.writer.writeMessage(msg);
//        System.out.println("Send: " + code);
    }
}
