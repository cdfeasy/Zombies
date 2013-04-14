package zombies.dto.builder;

import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;
import zombies.dto.reply.SuccessReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 31.01.13
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
public class SuccessReplyBuilder  implements Builder{
    String successText;
    public SuccessReplyBuilder setSuccessText(String successText) {
        this.successText = successText;
        return this;
    }


    @Override
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.ERROR.getId());
        userReply.setSuccessReply(new SuccessReply( successText));
        return userReply;
    }
}
