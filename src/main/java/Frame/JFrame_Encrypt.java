package Frame;

import Encrypt.WaterMark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JFrame_Encrypt {
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton Button_addmark;
    private JButton Button_removemark;
    private JButton Button_Encrypt;
public JFrame_Encrypt() {
    Button_addmark.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileChoose fileChoose = new FileChoose(panel1);     // 将文件选择器绑定在这个父类窗口
            File[] files = fileChoose.showFilesOpenDialog();
            for (File file:files) {
                WaterMark waterMark = new WaterMark(file,textArea1);
                waterMark.dealWaterMark(WaterMark.add);
            }
        }
    });
    Button_removemark.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileChoose fileChoose = new FileChoose(panel1);     // 将文件选择器绑定在这个父类窗口
            File[] files = fileChoose.showFilesOpenDialog();
            for (File file:files) {
                WaterMark waterMark = new WaterMark(file,textArea1);
                waterMark.dealWaterMark(WaterMark.remove);
            }
        }
    });
    Button_Encrypt.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new JFrame_Encrypt_select();
        }
    });
    JFrame_init jFrameInit = new JFrame_init(panel1);
    }
}
