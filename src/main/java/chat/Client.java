package chat;

import users.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The type Client.
 * @author 刘梦龙
 * @since 客户端处理消息通信，通过对象的方法发送消息
 */
public class Client {

    public final ObjectOutputStream user_send; // 客户端的输出流
    public final ObjectInputStream user_accept; // 客户端的输入流

    public Client(String name) throws IOException {
        Socket socket = new Socket("192.158.53.41",8888);
        user_send = new ObjectOutputStream(socket.getOutputStream());
        user_accept = new ObjectInputStream(socket.getInputStream());
        user_send.writeObject(name);    // 发送消息给主机记录通信人的身份
    }
}
