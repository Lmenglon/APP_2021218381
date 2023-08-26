package users;


/**
 * The type User.
 * @since 对用户信息的描述的类
 */
public class User {
    public String name;
    public int id = 0;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
