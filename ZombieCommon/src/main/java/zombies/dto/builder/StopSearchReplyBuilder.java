package zombies.dto.builder;

import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;
import zombies.dto.reply.StopSearchReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.01.13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class StopSearchReplyBuilder implements Builder{

    @Override
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.STOP_SEARCH.getId());
        userReply.setStopSearchReply(new StopSearchReply());
        return userReply;
    }
}