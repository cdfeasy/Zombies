package zombies.server.game;

import org.jboss.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 24.12.12
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class Request {
    private Channel channel;
    private String request;

    Request(Channel channel, String request) {
        this.channel = channel;
        this.request = request;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
