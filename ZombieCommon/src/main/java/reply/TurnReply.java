package reply;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import support.CardWrapper;
import support.GameInfo;

import java.util.HashMap;
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
    private int action;
    private int turnNumber;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Long cardId;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer position;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private GameInfo info;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    /**
     * Мапа, ключ- кардВрапперИд, значение-стринговая иформация, например погибла, получила 10 урона от итд
     */
    private Map<Integer,String> actions;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Map<Integer,CardWrapper> player1Card;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Map<Integer,CardWrapper> player2Card;

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

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public GameInfo getInfo() {
        return info;
    }

    public void setInfo(GameInfo info) {
        this.info = info;
    }

    public Map<Integer, String> getActions() {
        return actions;
    }

    public void setActions(Map<Integer, String> actions) {
        this.actions = actions;
    }

    public Map<Integer, CardWrapper> getPlayer1Card() {
        return player1Card;
    }

    public void setPlayer1Card(Map<Integer, CardWrapper> player1Card) {
        this.player1Card = player1Card;
    }

    public Map<Integer, CardWrapper> getPlayer2Card() {
        return player2Card;
    }

    public void setPlayer2Card(Map<Integer, CardWrapper> player2Card) {
        this.player2Card = player2Card;
    }
}
