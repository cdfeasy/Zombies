package zombies.dto.actions;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 15.01.13
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
public class SaveDeckAction {
    private Integer deckId;
    private List<Integer> cardsIds;

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public List<Integer> getCardsIds() {
        return cardsIds;
    }

    public void setCardsIds(List<Integer> cardsIds) {
        this.cardsIds = cardsIds;
    }
}
