package Sound;

import Message.Message;
import Message.ByteMessage;
import Message.MessageType;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SoundRecord extends Thread{
    private ObjectOutputStream send;
    private String to_user;
    public SoundRecord(String to_user,ObjectOutputStream send) {
        this.send = send;
        this.to_user = to_user;
    }

    @Override
    public void run() {
        try {

            record();
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void record() throws LineUnavailableException, IOException {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000, 16, 1, 2, 8000, true);
        TargetDataLine dataLine = AudioSystem.getTargetDataLine(format);
        dataLine.open();
        dataLine.start();

        while (true) {
            byte[] buf = new byte[1024];
            int cnt = dataLine.read(buf, 0, buf.length);
//            sourceDataLine.write(buf, 0, cnt);
            ByteMessage byteMessage = new ByteMessage(buf,cnt);
            Message message = new Message(MessageType.communicate,MessageType.type_sound,to_user,byteMessage);
            send.writeObject(message);
        }
    }
}
