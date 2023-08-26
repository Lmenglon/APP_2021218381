package Thread;

import Audio.AudioRecord;
import Audio.Play;
import Frame.JFrame_Client;
import Frame.JFrame_Communicate;
import Message.Message;
import Message.MessageType;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

/**
 * The type Clien thread.
 */
public class ClienThread extends Thread{
    /**
     * The Accept.
     */
    public ObjectInputStream accept;
    public ObjectOutputStream send;
    private JFrame_Client jFrameClient;
    private JFrame_Communicate jFrameCommunicate;
    /**
     * Instantiates a new Clien thread.
     *
     * @param accept       the accept
     * @param jFrameClient the j frame client
     */
    public ClienThread(ObjectInputStream accept,ObjectOutputStream send,JFrame_Client jFrameClient) {
        this.accept = accept;
        this.send = send;
        this.jFrameClient = jFrameClient;
        jFrameCommunicate = new JFrame_Communicate();
    }

    @Override
    public void run() {
        while (true){
            try {
                Message message = (Message) accept.readObject();
                int type = message.type;

                switch (type){
                    case MessageType.User_information:  //传返回的是id信息
                        Jlabel_users(message);
                        break;
                    case MessageType.communicate:
                        jFrameCommunicate.setvisible();
                        Play play = new Play(message,jFrameCommunicate);
//                        System.out.println("收到");
                        play.imshow(message);
                        break;
                    case MessageType.Communicate_receive:
                        //
                        String to_user = message.from_user;
                        AudioRecord audioRecord = new AudioRecord(to_user,send);
                        audioRecord.start();
                        break;
                }
            } catch (IOException | ClassNotFoundException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Print info.
     *
     * @param message the message
     * @since 对服务端传回来的用户消息进行验证是否通讯成功
     */
    public void print_info(Message message){
        JTextArea jTextArea = jFrameClient.getTextArea1();
        HashMap<String,Integer>id = (HashMap<String, Integer>) message.getObject();
        for (Map.Entry<String, Integer> entry : id.entrySet()) {
            jTextArea.append(entry.getKey()+"\n");
        }

    }
    public void Jlabel_users(Message message){
        // 显示在线的好友信息
        JPanel jPanel = jFrameClient.getUser_info();
        // 显示聊天的人的信息
        JLabel chat_user = jFrameClient.getChat_user();
        // 清空之前的用户信息，将新的用户信息添加进去
        jPanel.removeAll();
        JTextArea jTextArea = jFrameClient.getTextArea1();
        // 用户信息的表
        HashMap<String,Integer>id = (HashMap<String, Integer>) message.getObject();
        for (Map.Entry<String, Integer> entry : id.entrySet()) {
//            jTextArea.append("s");
            jTextArea.setText("");
            JLabel user_label = new JLabel(entry.getKey());
            jPanel.add(user_label);
            // 添加监听器，为了显示正在聊天的人是谁
            user_label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2){
                        //得到好友的名字
                        String chat_name = ((JLabel)e.getSource()).getText();
                        chat_user.setText(chat_name);
                    }
                }
            });
        }
    }
}
