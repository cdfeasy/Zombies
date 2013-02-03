package actions;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.12.12
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoAction {
    private String userName;

    public UserInfoAction() {
    }

    public UserInfoAction(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
