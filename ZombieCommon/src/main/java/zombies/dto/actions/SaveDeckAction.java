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
    private Long deckId;
    private String name;
    private String description;
    private List<Integer> cardsIds;


    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public List<Integer> getCardsIds() {
        return cardsIds;
    }

    public void setCardsIds(List<Integer> cardsIds) {
        this.cardsIds = cardsIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
