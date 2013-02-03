package actions;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 15.01.13
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
public class SaveDeckAction {
    private Integer deckId;
    private ListModel<Integer> cardsIds;

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public ListModel<Integer> getCardsIds() {
        return cardsIds;
    }

    public void setCardsIds(ListModel<Integer> cardsIds) {
        this.cardsIds = cardsIds;
    }
}
