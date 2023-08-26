package File;

import Frame.FileChoose;
import Message.Message;
import Message.MessageType;
import Message.FileMessage;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileSend extends Thread{
    private File[] files;
    private ObjectOutputStream objectOutputStream;
    private JTextArea jTextArea;
    public FileSend(File[] files, ObjectOutputStream objectOutputStream) {
        this.files = files;
        this.objectOutputStream = objectOutputStream;
    }

    public FileSend(FileChoose fileChoose, ObjectOutputStream objectOutputStream, JTextArea jTextArea) {
        files = fileChoose.showFilesOpenDialog();
        this.objectOutputStream = objectOutputStream;
        this.jTextArea = jTextArea;
    }

    public void send_file() throws IOException {
        for (File file : files) {
            String file_name = file.getName();
            // 在主界面呈现消息
            jTextArea.append("发送文件:"+file.getAbsolutePath()+"中\n");
            Message message = new Message(MessageType.file);
            byte[] bytes = new byte[1024];
            int length;
            int id = 1;
            FileInputStream fileInputStream = new FileInputStream(file);
            bytes = fileInputStream.readAllBytes();
            // 不知道为什么下面这种方法传输时，服务器一直接受相同的信息
//            while ((length = fileInputStream.read(bytes,0,bytes.length))>0){
////                id = id+1;
//                FileMessage fileMessage = new FileMessage(file_name,bytes,length,id++,false);
//                message.setObject(fileMessage);
//                objectOutputStream.writeObject(message);
//            }
            FileMessage fileMessage = new FileMessage(file_name,bytes,bytes.length);
            message.setObject(fileMessage);
            objectOutputStream.writeObject(message);
            jTextArea.append("发送文件:"+file.getAbsolutePath()+"成功\n");
        }
    }

    @Override
    public void run() {
        try {
            send_file();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
