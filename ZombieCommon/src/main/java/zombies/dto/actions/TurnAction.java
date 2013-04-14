package zombies.dto.actions;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.12.12
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public class TurnAction {
    public static enum actionEnum{
        turn,
        endturn,
        surrender;
    }
    //0-turn, 1-end turn, 2-surrender
    private Integer action;
    private Integer turnNumber;
    private Long cardId;
    private Integer position;

    public TurnAction() {
    }

    public TurnAction(int action, Long cardId, int position) {
        this.action = action;
        this.cardId = cardId;
        this.position = position;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Integer turnNumber) {
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

    @Override
    public String toString() {
        return "TurnAction{" +
                "action=" + action +
                ", turnNumber=" + turnNumber +
                ", cardId=" + cardId +
                ", position=" + position +
                '}';
    }
}
