package Frame;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JFrame_Communicate {
    private JButton Button_finish;
    private JLabel Label_comm;
    private JPanel JPanel2;
    private JLabel Label_user;
    private JPanel JPanel_main;

    public JLabel getLabel_comm() {
        return Label_comm;
    }

    public JLabel getLabel_user() {
        return Label_user;
    }

    private String to_user;
    private JFrame jFrame;

    public JFrame_Communicate() {
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(JPanel_main);
        jFrame.setBounds(300,300,600,400);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.jFrame = jFrame;

        Button_finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//              添加结束按钮
//                jFrame.dispose();

            }
        });
    }
    public void setvisible(){
        jFrame.setVisible(true);
    }
}
