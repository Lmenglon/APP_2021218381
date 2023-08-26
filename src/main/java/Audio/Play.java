package Audio;

import Message.*;
import Frame.JFrame_Communicate;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Play {
    private Message message;
    private JFrame_Communicate jFrameCommunicate;
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public Play(Message message, JFrame_Communicate jFrameCommunicate) {
        this.message = message;
        this.jFrameCommunicate = jFrameCommunicate;
    }

//    @Override
//    public void run() {
//        try {
//            imshow(message);
//        } catch (LineUnavailableException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public void imshow(Message message) throws LineUnavailableException {
        int type = message.file_type;
        JLabel Label_comm = jFrameCommunicate.getLabel_comm();
        Label_comm.setText("2021218381刘梦龙：和"+message.from_user+"视频通话中");
        ByteMessage byteMessage = (ByteMessage) message.getObject();
        switch (type){
            case MessageType.type_mat:
//                System.out.println("收到mat");
                play_video(byteMessage);
                break;
            case MessageType.type_sound:
//                System.out.println("收到sound");
                play_Sound(byteMessage);
                break;
        }
    }
    public void play_video(ByteMessage byteMessage){
        MatOfByte matIntOfByte = new MatOfByte(byteMessage.bytes);
        Mat mat = Imgcodecs.imdecode(matIntOfByte, Imgcodecs.IMREAD_UNCHANGED);
        Image image = HighGui.toBufferedImage(mat);
        JLabel Label_user = jFrameCommunicate.getLabel_user();
        Label_user.setIcon(new ImageIcon(image));
    }
    public void play_Sound(ByteMessage byteMessage) throws LineUnavailableException {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000,
                16, 1, 2, 8000, true);
        SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(format);
        sourceDataLine.open();
        sourceDataLine.start();
        sourceDataLine.write(byteMessage.bytes,0,byteMessage.length);
    }

}
