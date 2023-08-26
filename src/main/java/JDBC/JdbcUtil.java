package JDBC;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcUtil {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    static {
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/app_2021218381";
        user = "root";
        password = "061213";
    }
    public static Connection getConnection() {
        try {
            //注册数据库的驱动
            Class.forName(driver);
            //获取数据库连接（里面内容依次是：主机名和端口、用户名、密码）
            Connection connection = DriverManager.getConnection(url, user, password);
            //返回数据库连接
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
