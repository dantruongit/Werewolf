package Client.Session;

import Client.utils.gui;
import java.io.ObjectOutputStream;
import payload.Message;

public class Writer {
    public ObjectOutputStream oos;
    public void writeMessage(Message message){
        try {
            //System.out.println("Send " + message.getMessageCode());
            oos.writeObject(message);
            oos.reset();
            oos.flush();
        } catch (Exception e) {
            gui.showError("Lỗi Writer", e.getMessage(), false);
        }
    }
}
