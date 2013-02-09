package builder;

import reply.GameStartedReply;
import reply.Reply;
import reply.ReplyTypeEnum;
import reply.UserInfoReply;
import server.User;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 03.02.13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class GameStartedReplyBuilder implements Builder{
    User user;

    public User getUser() {
        return user;
    }

    public GameStartedReplyBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.GAME_STARTED.getId());
        reply.setGameStartedReply(new GameStartedReply(getUser()));
        return reply;
    }
}
