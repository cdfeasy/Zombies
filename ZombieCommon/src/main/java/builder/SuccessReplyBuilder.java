package builder;

import reply.Reply;
import reply.ReplyTypeEnum;
import reply.SuccessReply;

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
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.ERROR.getId());
        reply.setSuccessReply(new SuccessReply( successText));
        return reply;
    }
}
