package Frame;

import javax.swing.*;

public class JFrame_init extends JFrame{
    public JPanel panel;

    public JFrame_init(JPanel panel) {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("2021218381刘梦龙");
        jFrame.setContentPane(panel);
        jFrame.setBounds(1000,300,600,400);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
