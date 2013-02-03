package builder;

import reply.Reply;
import reply.ReplyTypeEnum;
import reply.UserInfoReply;
import server.User;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.01.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoReplyBuilder implements Builder{
    User user;

    public User getUser() {
        return user;
    }

    public UserInfoReplyBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.USERINFO.getId());
        reply.setUserInfoReply(new UserInfoReply(getUser()));
        return reply;
    }
}
