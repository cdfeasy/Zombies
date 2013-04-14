package zombies.dto.builder;

import zombies.entity.game.Fraction;
import zombies.dto.reply.CardInfoReply;
import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;

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
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.GET_CARD_INFO.getId());
        userReply.setCardInfoReply(new CardInfoReply(getFraction()));
        return userReply;
    }
}