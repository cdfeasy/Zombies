package zombies.dto.builder;

import zombies.dto.reply.ConnectionReply;
import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;

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
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.CONNECTION.getId());
        userReply.setConnectionReply(new ConnectionReply(getToken()));
        return userReply;
    }
}