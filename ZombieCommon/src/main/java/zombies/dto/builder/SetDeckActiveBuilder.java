package zombies.dto.builder;

import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;
import zombies.dto.reply.SuccessReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.01.13
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */
public class SetDeckActiveBuilder implements Builder{

    @Override
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.SET_DECK_ACTIVE.getId());
        userReply.setSuccessReply(new SuccessReply("ok"));
        return userReply;
    }
}
