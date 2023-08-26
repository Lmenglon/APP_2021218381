package Audio;

import java.io.ObjectInputStream;
import java.io.Serializable;

public class AudioReceive extends Thread implements Receive {
    private String from_user;
    private ObjectInputStream accept;
    @Override
    public void receive() {

    }
}
