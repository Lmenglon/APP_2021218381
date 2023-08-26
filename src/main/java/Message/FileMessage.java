package Message;

import java.io.Serializable;
import java.util.Arrays;

public class FileMessage implements Serializable {
    public String file_name;
    public byte[] bytes;
    public int length;
    public FileMessage(String file_name, byte[] bytes,int length) {
        this.file_name = file_name;
        this.bytes = bytes;
        this.length = length;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "FileMessage{" +
                "file_name='" + file_name + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
