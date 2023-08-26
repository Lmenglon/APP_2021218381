package Database;

import JDBC.JdbcUtil;
import Log.FileLog;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class FilePath {
    private FileLog fileLog;

    public FilePath(FileLog fileLog) {
        this.fileLog = fileLog;
    }
    public FilePath(String FilePath, String from_user){
        this.fileLog = new FileLog(FilePath,from_user);
    }
    public void saveFileLog() throws SQLException {
        Connection conn = JdbcUtil.getConnection();
        String sql = "insert into file(File_path,from_user,datatime) values (?,?,?)";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        java.util.Date date = new Date();//获得当前时间
        String time = df.format(date);  // 当前时间
        System.out.println(time);
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1,fileLog.getFile_name());
        stat.setString(2, fileLog.getFrom_user());
        stat.setString(3,time);
        stat.executeUpdate();
        stat.close();
        conn.close();
    }
}
