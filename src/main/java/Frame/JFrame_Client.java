package Frame;

import Audio.AudioRecord;
import Audio.Receive;
import File.FileSend;
import Message.Message;
import Message.MessageType;
import Message.ReceiveMessage;
import Sound.SoundRecord;
import chat.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Thread.ClienThread;
public class JFrame_Client {
    private JTextArea textArea1;
    private JButton button_collect;
    private JButton button_watermark;
    private JButton button_communicate;
    private JPanel JPanel_main;
    private JButton Button_send;
    private JPanel user_info;
    private JLabel chat_user;

    public JLabel getChat_user() {
        return chat_user;
    }

    private JFrame_Collect jFrameCollect;

    /**
     * Gets user info.
     *
     * @return the user info
     * @since  为了生成在线用户的情况
     */
    public JPanel getUser_info() {
        return user_info;
    }

    static Client client;

    public JTextArea getTextArea1() {
        return textArea1;
    }
    public JFrame_Client() throws IOException {
//        client = new Client("纳西妲");
        client = new Client("八重神子");
        button_collect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JFrame_Collect();
            }
        });
        Button_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileSend fileSend = new FileSend(new FileChoose(JPanel_main),client.user_send,textArea1);
                fileSend.start();
            }
        });
        button_watermark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JFrame_Encrypt();
            }
        });
        button_communicate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String to_user = chat_user.getText();
                textArea1.append("和"+to_user+"通信");
                ReceiveMessage receiveMessage = new ReceiveMessage(false);
                // 发送一个视频通讯请求的消息
                Message message = new Message(MessageType.Communicate_receive,to_user,receiveMessage);
                try {
                    client.user_send.writeObject(message);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                AudioRecord audioRecord = new AudioRecord(to_user,client.user_send);
                audioRecord.start();
                // SoundRecord soundRecord = new SoundRecord(to_user,client.user_send);
                // soundRecord.start();
            }
        });
        chat_user.setText("服务器");
        JFrame jFrame = new JFrame();
        jFrame.setTitle("2021218381刘梦龙");
        jFrame.setContentPane(JPanel_main);
        jFrame.setBounds(1000,300,600,400);
        user_info.setLayout(new GridLayout(50, 1, 4, 4));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        ClienThread clienThread = new ClienThread(client.user_accept,client.user_send,this);
        clienThread.start();
    }

    public static void main(String[] args) throws IOException {
        new JFrame_Client();
    }
}
