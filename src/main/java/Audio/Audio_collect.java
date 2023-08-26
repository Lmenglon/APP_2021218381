package Audio;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import Frame.JFrame_Collect;

import static java.lang.System.exit;

public class Audio_collect extends Thread{
    public String file_name;
    public boolean finish = false;
    private JTextArea textArea;
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public Audio_collect() {

    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Audio_collect(String file_name, boolean finish,JTextArea textArea) {
        this.file_name = file_name;
        this.finish = finish;
        this.textArea = textArea;
    }
    public void run(){
        collect_audio();
    }
    public void collect_audio() {
        VideoCapture cap = new VideoCapture(0);
        int frame_width = (int)cap.get(3);
        int frame_height = (int)cap.get(4);
        Size size = new Size();
        size.width = frame_width;
        size.height = frame_height;
        VideoWriter vw = new VideoWriter(file_name+".avi",VideoWriter.fourcc('M', 'J', 'P', 'G'),10,size);
        JFrame jFrame = HighGui.createJFrame("2021218381刘梦龙采集视频",HighGui.WINDOW_AUTOSIZE);
        jFrame.setBounds(1000,300,600,400);
        JLabel jLabel = new JLabel();
        jFrame.add(jLabel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        while (jFrame.isShowing()){
            Mat mat = new Mat();
            boolean reg = cap.read(mat);
            if (reg) {
                vw.write(mat);
                Image image = HighGui.toBufferedImage(mat);
                jLabel.setIcon(new ImageIcon(image));
                jLabel.repaint();
                jFrame.repaint();
            }
        }
        textArea.append("视频"+file_name+".avi"+"采集成功\n");
        vw.release();
        HighGui.destroyAllWindows();
        cap.release();
    }

    public static void main(String[] args){
        Audio_collect audioCollect = new Audio_collect("demo1",false,new JTextArea());
        audioCollect.collect_audio();
    }
}
