package builder;

import game.Fraction;
import reply.CardInfoReply;
import reply.Reply;
import reply.ReplyTypeEnum;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.01.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class GetCardInfoReplyBuilder implements Builder{
    List<Fraction> fraction;

    public List<Fraction> getFraction() {
        return fraction;
    }

    public GetCardInfoReplyBuilder setFractions(List<Fraction> fractions) {
        this.fraction = fractions;
        return this;
    }

    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.GET_CARD_INFO.getId());
        reply.setCardInfoReply(new CardInfoReply(getFraction()));
        return reply;
    }
}