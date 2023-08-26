package Message;

import java.io.Serializable;

public class ByteMessage implements Serializable {
    public byte[] bytes;
    public int length;

    public ByteMessage(byte[] bytes, int length) {
        this.bytes = bytes;
        this.length = length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getLength() {
        return length;
    }
}
