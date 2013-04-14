package zombies.dto.builder;

import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;
import zombies.dto.reply.SaveDeckReply;

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
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.SAVE_DECK.getId());
        userReply.setSaveDeckReply(new SaveDeckReply(getDeckId()));
        return userReply;
    }

}
