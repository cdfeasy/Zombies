package builder;

import reply.Reply;
import reply.ReplyTypeEnum;
import reply.StopSearchReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.01.13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class StopSearchReplyBuilder implements Builder{

    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.STOP_SEARCH.getId());
        reply.setStopSearchReply(new StopSearchReply());
        return reply;
    }
}