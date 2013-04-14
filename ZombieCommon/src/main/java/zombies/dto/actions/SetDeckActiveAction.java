package zombies.dto.actions;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 15.01.13
 * Time: 23:31
 * To change this template use File | Settings | File Templates.
 */
public class SetDeckActiveAction {
    Integer deckId;

    public SetDeckActiveAction() {
    }

    public SetDeckActiveAction(Integer deckId) {
        this.deckId = deckId;
    }

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

}
