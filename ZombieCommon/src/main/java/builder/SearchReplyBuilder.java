package builder;

import reply.Reply;
import reply.ReplyTypeEnum;
import reply.SearchReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.01.13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class SearchReplyBuilder implements Builder{

    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.SEARCH.getId());
        reply.setSearchReply(new SearchReply("Searching started"));
        return reply;
    }
}
