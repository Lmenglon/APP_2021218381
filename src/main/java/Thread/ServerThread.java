package Thread;

import Database.FilePath;
import Message.Message;
import chat.Server;
import Message.MessageType;
import Message.FileMessage;

import java.io.*;
import java.sql.SQLException;

public class ServerThread extends Thread{
    public ObjectInputStream accept;
    public ObjectOutputStream send;
    public String user_name;
    public ServerThread(ObjectInputStream accept, ObjectOutputStream send,String user_name) {
        this.accept = accept;
        this.send = send;
        this.user_name = user_name;
    }

    @Override
    public void run() {
        while (true){
            try {
                Message message = (Message) accept.readObject();
                message.setFrom_user(user_name);
//                System.out.println("进去1");
                int type = message.type;
//                System.out.println(type);
                switch (type){
                    case MessageType.file:
                        save_file(message);
                        break;
                    case MessageType.communicate:
                    case MessageType.Communicate_receive:
                        //                        System.out.println("收到comm");
                        communicate(message);
                        break;
                }
                //这里可以判断如果是视频/音频文件的话进行存储
            }
            catch(IOException | ClassNotFoundException | SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
    public void communicate(Message message) throws IOException {
        String accept_user_name = message.to_user;
        // 对不同的信息进行处理
        // 将在线的人的信息传回去
        int accept_user_id = Server.id.get(accept_user_name);
        ServerThread serverThread_accept = Server.sockets.get(accept_user_id);

        Message info_user = new Message(MessageType.User_information,accept_user_name,Server.id);
        serverThread_accept.send.writeObject(info_user);    //将上线的人数写回去
        serverThread_accept.send.writeObject(message);
    }
    public void save_file(Message message) throws IOException, SQLException {
        FileMessage file = (FileMessage) message.getObject();
        //System.out.println("进去2");
//        System.out.println(file.id+":"+ file.length);
        String path = "E:\\IJ\\Project\\APP_2021218381\\file\\" +file.file_name;
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path),false);
        fileOutputStream.write(file.getBytes(),0,file.length);
        fileOutputStream.close();
        // 数据库存储路径
        FilePath filePath = new FilePath(path,message.from_user);
        filePath.saveFileLog();
    }
}
