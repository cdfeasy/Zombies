package reply;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import support.CardWrapper;
import support.GameInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class TurnReply {
    public static enum actionEnum{
        turn,
        endturn,
        uwin,
        ulose;
    }
    //0-turn, 1-end turn, 2-you win,3-you lose
    private Integer action;
    private Integer turnNumber;
    private String nextTurnUser;
    //карт, ответ на ход противника
    private Long cardId;
    //Позиция карты, ответ на ход противника
    private Integer position;

    private GameInfo info;

    /**
     * Мапа, ключ- кардВрапперИд, значение-стринговая иформация, например погибла, получила 10 урона от итд
     */
    private Map<Integer,String> actions;
    private Map<Integer,List<CardWrapper>> player1Card;

    private Map<Integer,List<CardWrapper>> player2Card;

    private List<Long> playerHand;

    private Integer res1;
    private Integer res2;
    private Integer res3;

    public TurnReply() {
    }

    public TurnReply(int action, int turnNumber, String nextTurnUser, Long cardId, Integer position, GameInfo info, Map<Integer, String> actions, Map<Integer, List<CardWrapper>> player1Card, Map<Integer, List<CardWrapper>> player2Card, List<Long> playerHand) {
        this.action = action;
        this.turnNumber = turnNumber;
        this.nextTurnUser = nextTurnUser;
        this.cardId = cardId;
        this.position = position;
        this.info = info;
        this.actions = actions;
        this.player1Card = player1Card;
        this.player2Card = player2Card;
        this.playerHand = playerHand;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public GameInfo getInfo() {
        return info;
    }

    public void setInfo(GameInfo info) {
        this.info = info;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Map<Integer, String> getActions() {
        return actions;
    }


    public void setActions(Map<Integer, String> actions) {
        this.actions = actions;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getNextTurnUser() {
        return nextTurnUser;
    }

    public void setNextTurnUser(String nextTurnUser) {
        this.nextTurnUser = nextTurnUser;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Map<Integer, List<CardWrapper>> getPlayer1Card() {
        return player1Card;
    }

    public void setPlayer1Card(Map<Integer, List<CardWrapper>> player1Card) {
        this.player1Card = player1Card;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Map<Integer, List<CardWrapper>> getPlayer2Card() {
        return player2Card;
    }

    public void setPlayer2Card(Map<Integer, List<CardWrapper>> player2Card) {
        this.player2Card = player2Card;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<Long> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<Long> playerHand) {
        this.playerHand = playerHand;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Integer getRes1() {
        return res1;
    }

    public void setRes1(Integer res1) {
        this.res1 = res1;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Integer getRes2() {
        return res2;
    }

    public void setRes2(Integer res2) {
        this.res2 = res2;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Integer getRes3() {
        return res3;
    }

    public void setRes3(Integer res3) {
        this.res3 = res3;
    }
}
