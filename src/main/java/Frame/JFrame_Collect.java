package Frame;

import Audio.Audio_collect;
import Sound.Sound_collect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFrame_Collect extends Thread{
    private JPanel panel1;
    private JButton button_begin_video;
    private JTextField textField_fillename;
    private JLabel JLabel1;
    private JButton Button_begin_Sound;
    private JTextArea textArea1;
    Boolean flag = false;
    public JFrame_Collect() {
        button_begin_video.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio_collect audioCollect = new Audio_collect(textField_fillename.getText(),false,textArea1);
                textField_fillename.setText("");
                audioCollect.start();
            }
        });
        Button_begin_Sound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sound_collect soundCollect = new Sound_collect(textField_fillename.getText(),textArea1);
                textField_fillename.setText("");
                soundCollect.start();
            }
        });
        JFrame_init jFrameInit = new JFrame_init(panel1);
    }
    public static void main(String[] args) {
        new JFrame_Collect();
    }
}
