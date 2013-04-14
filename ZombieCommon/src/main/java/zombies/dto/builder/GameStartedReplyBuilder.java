package zombies.dto.builder;

import zombies.dto.reply.UserReply;
import zombies.entity.game.Card;
import zombies.dto.reply.GameStartedReply;
import zombies.dto.reply.ReplyTypeEnum;
import zombies.entity.server.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 03.02.13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class GameStartedReplyBuilder implements Builder{
    private User user;
    private List<Long> cardIds;
    private int position;
    private int res1;
    private int res2;
    private int res3;

    public User getUser() {
        return user;
    }

    public GameStartedReplyBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public GameStartedReplyBuilder setCards(List<Card> cards) {
        List<Long> cardIds=new ArrayList<Long>();
        for(Card c:cards){
            cardIds.add(c.getId());
        }
        this.cardIds=cardIds;
        return this;
    }

    public GameStartedReplyBuilder setPosition(int position) {
        this.position=position;
        return this;
    }

    public GameStartedReplyBuilder setRes(int res1,int res2,int res3) {
        this.res1=res1;
        this.res2=res2;
        this.res3=res3;
        return this;
    }

    @Override
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.GAME_STARTED.getId());
        GameStartedReply gsr= new GameStartedReply(getUser().CopyUser(false,false,false,null));
        gsr.setPosition(position);
        gsr.setCards(cardIds);
        gsr.setRes1(res1);
        gsr.setRes2(res2);
        gsr.setRes3(res3);
        userReply.setGameStartedReply(gsr);
        return userReply;
    }
}
