package Log;

public class FileLog {
    private String file_name;
    private String from_user;

    public FileLog(String file_name, String from_user) {
        this.file_name = file_name;
        this.from_user = from_user;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getFrom_user() {
        return from_user;
    }
}
