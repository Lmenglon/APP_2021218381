package chat;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import Message.Message;
import Message.MessageType;
import Thread.ServerThread;
import users.State;

/**
 * The type Server.
 * @since 具
 */
public class Server {
    public static  HashMap<String,Integer> id = new HashMap<>(); // 通过名字映射生成唯一的id
    public static  HashMap<Integer, ServerThread> sockets = new HashMap<>(); // 将id与通信连接起来
    public static  HashMap<Integer, State> income = new HashMap<>(); // 记录用户的上线的消息

    public Server() {
//        init_state();
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while(true){
                Socket socket = serverSocket.accept();
                ObjectInputStream accept = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
                String user_name = (String)accept.readObject();
                ServerThread serverThread = new ServerThread(accept,send,user_name);
                int user_id = id.size();
                id.put(user_name,user_id);        // 存放用户的信息，生成一个唯一的id查找
                State state = new State(true);
                income.put(user_id,state);
                sockets.put(user_id,serverThread);
                serverThread.start();

                // 将上线的用户信息传回客户端
                Message message = new Message(MessageType.User_information,user_name,id);
//                send.writeObject(message);  //将用户的信息传回客户端
                for (Map.Entry<Integer, ServerThread> entry : sockets.entrySet()) {
                    ServerThread serverThread1 = entry.getValue();
                    serverThread1.send.writeObject(message);
                }
                for (Map.Entry<String, Integer> entry : id.entrySet()) {
                    System.out.println(entry.getKey());
                }
            }
        }
        catch(IOException|ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}

