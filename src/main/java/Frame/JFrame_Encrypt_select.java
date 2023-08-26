package Frame;

import Encrypt.XORUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JFrame_Encrypt_select {
    private JPanel panel1;
    private JButton Button_src;
    private JButton Button_key;
    private JTextArea textArea1;
    private JButton Button_pos;
    private JButton Button_begin;
    private File[] files;
    private File key;
    private File output;    //输出的文件夹
    public JFrame_Encrypt_select() {
        Button_src.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChoose fileChoose = new FileChoose(panel1);
                files = fileChoose.showFilesOpenDialog();
                textArea1.append("选择文件有:\n");
                if(files!=null){
                    for(File file :files){
                        textArea1.append(file.getAbsolutePath()+"\n");
                    }
                }else {
                    textArea1.append("没有选择操作的文件\n");
                }

            }
        });
        Button_key.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChoose fileChoose = new FileChoose(panel1);
                key = fileChoose.showFileOpenDialog();
                if(key != null){
                    textArea1.append("加密/解密文件:\n");
                    textArea1.append(key.getAbsolutePath()+"\n");
                }
                else {
                    textArea1.append("密钥的图片文件:未选择\n");
                }
            }
        });
        Button_pos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChoose fileChoose = new FileChoose(panel1);
                output = fileChoose.showDirectoryOpenDialog();
                if(output != null){
                    textArea1.append("存储的文件夹:\n");
                    textArea1.append(output.getAbsolutePath()+"\n");
                }
                else {
                    textArea1.append("存储的文件夹:未选择\n");
                }
            }
        });
        Button_begin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    entryFile();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JFrame_init jFrameInit = new JFrame_init(panel1);
    }
    public void entryFile() throws Exception {
        String OutputDir = output.getAbsolutePath();   // 获得输出的文件夹
        for(File file:files){
            String OutputPath = OutputDir+"\\"+file.getName();
            File output = new File(OutputPath);
            XORUtils.encryptFile(file,output,key);
            textArea1.append(file.getAbsolutePath()+"加密/解密成功\n");
        }
    }
}
