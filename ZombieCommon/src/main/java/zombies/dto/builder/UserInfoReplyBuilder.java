package zombies.dto.builder;

import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;
import zombies.dto.reply.UserInfoReply;
import zombies.entity.server.User;

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
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.USERINFO.getId());
        userReply.setUserInfoReply(new UserInfoReply(getUser()));
        return userReply;
    }
}
