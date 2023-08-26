package Message;

import java.io.Serializable;

public class Message implements Serializable {
    public int type;
    public String to_user;
    public String from_user;
    private Object object;
    public int file_type;
    public Message(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public Message(int type, String to_user, Object object) {
        this.type = type;
        this.to_user = to_user;
        this.object = object;
    }

    public Message(int type, int file_type,Object object ) {
        this.type = type;
        this.object = object;
        this.file_type = file_type;
    }

    public Message(int type, int file_type,String to_user, Object object) {
        this.type = type;
        this.to_user = to_user;
        this.object = object;
        this.file_type = file_type;
    }

    public Message(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", to_user='" + to_user + '\'' +
                ", object=" + object +
                ", file_type=" + file_type +
                '}';
    }
}
