package builder;

import reply.Reply;
import reply.ReplyTypeEnum;
import reply.SuccessReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.01.13
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */
public class SetDeckActiveBuilder implements Builder{

    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.SET_DECK_ACTIVE.getId());
        reply.setSuccessReply(new SuccessReply("ok"));
        return reply;
    }
}
