package builder;

import reply.Reply;
import reply.ReplyTypeEnum;
import reply.TurnReply;
import reply.UserInfoReply;
import support.CardWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 09.02.13
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class TurnReplyBuilder implements Builder{
    TurnReply turnReply =new TurnReply();

    /**
     * 0-turn, 1-end turn, 2-you win,3-you lose
     * @param action
     * @return
     */
    public TurnReplyBuilder setAction(int action) {
        this.turnReply.setAction(action);
        return this;
    }
    public TurnReplyBuilder setCardId(Long cardId) {
        this.turnReply.setCardId(cardId);
        return this;
    }
    public TurnReplyBuilder setPosition(Integer position) {
        this.turnReply.setPosition(position);
        return this;
    }

    public  TurnReplyBuilder addActionInfo(Integer cardWrapperId, String action ){
        if(turnReply.getActions()==null)                         {
            turnReply.setActions(new HashMap<Integer, String>());
        }
        turnReply.getActions().put(cardWrapperId,action);
        return this;
    }

    public  TurnReplyBuilder setActionInfo(Map<Integer, String> actionsInfo ){
        turnReply.setActions(actionsInfo);
        return this;
    }

    public  TurnReplyBuilder setPlayer1Cards(Map<Integer,CardWrapper> cards ){
        turnReply.setPlayer1Card(cards);
        return this;
    }

    public  TurnReplyBuilder setTurnNumber(Integer turn ){
        turnReply.setTurnNumber(turn);
        return this;
    }

    public  TurnReplyBuilder setPlayer2Cards(Map<Integer,CardWrapper> cards ){
        turnReply.setPlayer2Card(cards);
        return this;
    }


    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.TURN.getId());
        reply.setTurnReply(turnReply);
        return reply;
    }
}
