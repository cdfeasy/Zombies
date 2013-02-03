package builder;

import reply.ConnectionReply;
import reply.Reply;
import reply.ReplyTypeEnum;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.01.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionReplyBuilder implements Builder{
    String token;
    private String getToken() {
        return token;
    }
    public ConnectionReplyBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.CONNECTION.getId());
        reply.setConnectionReply(new ConnectionReply(getToken()));
        return reply;
    }
}