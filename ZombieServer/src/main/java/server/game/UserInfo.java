package server.game;

import org.jboss.netty.channel.Channel;
import server.User;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {
    private long id;
    private String token;
    private Channel channel;
    private GameManager manager;
    private User user;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameManager getManager() {
        return manager;
    }

    public void setManager(GameManager manager) {
        this.manager = manager;
    }
}
