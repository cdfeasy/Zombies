package zombies.dto.reply;

import zombies.entity.server.User;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoReply {
    private User user;

    public UserInfoReply(User user) {
        this.user = user;
    }

    public UserInfoReply() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
