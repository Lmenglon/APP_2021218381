package Message;


import java.io.Serializable;

/**
 * The type Receive.
 * @since 是否同意视频通话的请求
 */
public class ReceiveMessage implements Serializable {
    boolean receive;

    public ReceiveMessage(boolean receive) {
        this.receive = receive;
    }
}
