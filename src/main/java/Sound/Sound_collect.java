package Sound;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class Sound_collect extends Thread{
    public String file_name;
    private Boolean stopflag = false;
    private JTextArea jTextArea;
    public Sound_collect(String file_name,JTextArea jTextArea) {
        this.file_name = file_name;
        this.jTextArea = jTextArea;
    }

    @Override
    public void run() {
        try {
            collect_sound();
        } catch (LineUnavailableException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void  collect_sound() throws LineUnavailableException, InterruptedException, IOException {
        jTextArea.append("开始录音中\n");
        File outputFile = new File(file_name+".wav");
        AudioFormat audioFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F,
                false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        JLabel jLabel = new JLabel("正在录音中,点击按钮停止");
        JFrame jFrame= new JFrame("2021218381刘梦龙采集音频");
        jFrame.setLayout(new FlowLayout());
        JButton jButton = new JButton("停止");
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetDataLine.close();
                jTextArea.append("音频："+file_name+".wav"+"采集成功\n");
                // 释放窗口
                jFrame.dispose();
            }
        });
        jFrame.setBounds(1000,300,600,400);
        jFrame.add(jLabel);
        jFrame.add(jButton);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        new Thread() {
            public void run() {
                    AudioInputStream cin = new AudioInputStream(targetDataLine);
                    try {
                        AudioSystem.write(cin, AudioFileFormat.Type.WAVE,
                                outputFile);
//                        System.out.println("over");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            };
        }.start();
    }
    public static void main(String[] args) {
        new Sound_collect("demo1",new JTextArea()).start();
    }
}
