package builder;

import reply.Reply;
import reply.ReplyTypeEnum;
import reply.SaveDeckReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.01.13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class SaveDeckReplyBuilder implements Builder{
    private Integer deckId;

    public Integer getDeckId() {
        return deckId;
    }

    public SaveDeckReplyBuilder setDeckId(Integer deckId) {
        this.deckId = deckId;
        return this;
    }
    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.SAVE_DECK.getId());
        reply.setSaveDeckReply(new SaveDeckReply(getDeckId()));
        return reply;
    }

}
