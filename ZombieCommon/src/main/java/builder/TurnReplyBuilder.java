package builder;

import game.Card;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import reply.Reply;
import reply.ReplyTypeEnum;
import reply.TurnReply;
import reply.UserInfoReply;
import support.CardWrapper;
import support.GameInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 09.02.13
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class TurnReplyBuilder implements Builder{

    private int action;
    private int turnNumber;
    private Integer nextTurnUser;
    private Long cardId;
    private Integer position;
    private GameInfo info;
    private Map<Integer,List<String>> actions;
    private Map<Integer,List<CardWrapper>> player1Card;
    private Map<Integer,List<CardWrapper>> player2Card;
    private List<Long> playerHand;
    private Integer res1;
    private Integer res2;
    private Integer res3;

    private int getTurnNumber() {
        return turnNumber;
    }

    private GameInfo getInfo() {
        return info;
    }

    private Map<Integer, List<String>> getActions() {
        return actions;
    }

    private Map<Integer, List<CardWrapper>> getPlayer1Card() {
        return player1Card;
    }

    private Map<Integer, List<CardWrapper>> getPlayer2Card() {
        return player2Card;
    }

    private List<Long> getPlayerHand() {
        return playerHand;
    }



    /**
     * 0-turn, 1-end turn, 2-you win,3-you lose
     * @param action
     * @return
     */
    public TurnReplyBuilder setAction(int action) {
        this.action=action;
        return this;
    }
    public TurnReplyBuilder setCardId(Long cardId) {
        this.cardId=cardId;
        return this;
    }
    public TurnReplyBuilder setPosition(Integer position) {
        this.position=position;
        return this;
    }

    public TurnReplyBuilder setRes(int res1,int res2,int res3) {
        this.res1=res1;
        this.res2=res2;
        this.res3=res3;
        return this;
    }

    public  TurnReplyBuilder addActionInfo(Integer cardWrapperId, String action ){
        if(getActions()==null)                         {
            this.actions=new HashMap<Integer, List<String>>();
        }
        if(getActions().get(cardWrapperId)==null){
            getActions().put(cardWrapperId,new ArrayList<String>()) ;
        }
        getActions().get(cardWrapperId).add(action);
        return this;
    }

    public  TurnReplyBuilder setActionInfo(Map<Integer, List<String>> actionsInfo ){
        this.actions=actionsInfo;
        return this;
    }

    public  TurnReplyBuilder setPlayer1Cards(Integer index, List<CardWrapper> cards ){
        if(getPlayer1Card()==null){
            this.player1Card=new HashMap<Integer, List<CardWrapper>>();
        }
//        List<CardWrapper> tclone=new ArrayList<>();
//        for(CardWrapper cw:cards){
//            tclone.add(cw.transportClone());
//        }
        if(!cards.isEmpty()) {
            getPlayer1Card().put(index,cards)  ;
        }
        return this;
    }

    public  TurnReplyBuilder setTurnNumber(Integer turn ){
        this.turnNumber=turn;
        return this;
    }

    public  TurnReplyBuilder setInfo(GameInfo info ){
        this.info=info;
        return this;
    }

    public  TurnReplyBuilder setNextTurnUser(Integer name ){
        this.nextTurnUser=name;
        return this;
    }

    public TurnReplyBuilder setCards(List<Card> cards) {
        List<Long> cardIds=new ArrayList<Long>();
        for(Card c:cards){
            cardIds.add(c.getId());
        }
        this.playerHand=cardIds;
        return this;
    }

    public  TurnReplyBuilder setPlayer2Cards(Integer index, List<CardWrapper> cards ){
        if(getPlayer2Card()==null){
            this.player2Card=new HashMap<Integer, List<CardWrapper>>();
        }
//        List<CardWrapper> tclone=new ArrayList<>();
//        for(CardWrapper cw:cards){
//            tclone.add(cw.transportClone());
//        }
        if(!cards.isEmpty()) {
             getPlayer2Card().put(index,cards)  ;
        }
        return this;
    }


    @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.TURN.getId());
        TurnReply turnReply =new TurnReply(action,turnNumber,nextTurnUser,cardId,position,info,actions,player1Card,player2Card,playerHand);
        turnReply.setRes1(res1);
        turnReply.setRes2(res2);
        turnReply.setRes3(res3);
        reply.setTurnReply(turnReply);
        return reply;
    }
}
