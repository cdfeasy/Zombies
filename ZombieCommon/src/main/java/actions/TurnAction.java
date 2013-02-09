package actions;

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
    private int action;
    private Long cardId;
    private int position;

    public TurnAction() {
    }

    public TurnAction(int action, Long cardId, int position) {
        this.action = action;
        this.cardId = cardId;
        this.position = position;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
