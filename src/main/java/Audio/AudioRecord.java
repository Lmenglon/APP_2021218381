package Audio;

import Message.*;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AudioRecord extends Thread{
    private ObjectOutputStream send;
    private String to_user;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public AudioRecord(String to_user, ObjectOutputStream send) {
        this.send = send;
        this.to_user = to_user;
    }
    public void record() throws IOException, LineUnavailableException {
        VideoCapture cap = new VideoCapture(0);
        int frame_width = (int)cap.get(3);
        int frame_height = (int)cap.get(4);
        Size size = new Size();
        size.width = frame_width;
        size.height = frame_height;
        JFrame jFrame = HighGui.createJFrame("2021218381刘梦龙：本地视频通话中",HighGui.WINDOW_AUTOSIZE);
        jFrame.setBounds(1000,300,600,400);
        JLabel jLabel = new JLabel();
        jFrame.add(jLabel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 音频
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000, 16, 1, 2, 8000, true);
        TargetDataLine dataLine = AudioSystem.getTargetDataLine(format);
        dataLine.open();
        dataLine.start();

        while (jFrame.isShowing()){   //
            Mat mat = new Mat();
            boolean reg = cap.read(mat);
            if (reg) {
                // 在本地显示
                Image image = HighGui.toBufferedImage(mat);
//                image.
                MatOfByte matOfByte = new MatOfByte();
                Imgcodecs.imencode(".jpg",mat,matOfByte);
                byte[] bytes = matOfByte.toArray();
                jLabel.setIcon(new ImageIcon(image));
                // 传输mat图像,mat不是序列化的对象，我们要将其转化为字节流传输
                ByteMessage byteMessage = new ByteMessage(bytes,bytes.length);
                Message message = new Message(MessageType.communicate,MessageType.type_mat,to_user,byteMessage);
                send.writeObject(message);
            }

            // 音频
            byte[] buf = new byte[1024];
            int cnt = dataLine.read(buf, 0, buf.length);
//            sourceDataLine.write(buf, 0, cnt);
            ByteMessage byteMessage = new ByteMessage(buf,cnt);
            Message message = new Message(MessageType.communicate,MessageType.type_sound,to_user,byteMessage);
            send.writeObject(message);
        }
        HighGui.destroyAllWindows();
        cap.release();
    }
    @Override
    public void run() {
        try {
            record();
        } catch (IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
