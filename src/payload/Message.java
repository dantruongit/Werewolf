package payload;

import java.io.Serializable;

public class Message implements Serializable{
    private byte messageCode;
    private Object tmp;
    private Object data;
    public Message(){}
    public Message(byte messageCode, Object data) {
        this.messageCode = messageCode;
        this.data = data;
    }

    public byte getMessageCode() {
        return messageCode;
    }

    public Object getData() {
        return data;
    }

    public void setMessageCode(byte messageCode) {
        this.messageCode = messageCode;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public void setTmp(Object tmp){
        this.tmp = tmp;
    }
    
    public Object getTmp(){
        return this.tmp;
    }
    
}
