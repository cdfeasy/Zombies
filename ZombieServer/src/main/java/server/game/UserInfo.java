package server.game;

import org.jboss.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {
    private long id;
    private Channel channel;
    private GameManager manager;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public GameManager getManager() {
        return manager;
    }

    public void setManager(GameManager manager) {
        this.manager = manager;
    }
}
