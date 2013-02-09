package support;

import game.Card;
import game.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 07.02.13
 * Time: 22:05
 * To change this template use File | Settings | File Templates.
 */
public class DeckInfo {
    private Long deckId;
    private List<Long> deckCardsIds;

    public DeckInfo(Long deckId, List<Card> cards) {
        this.deckId = deckId;
        if(!cards.isEmpty()){
            deckCardsIds=new ArrayList<Long>(cards.size());
        }
        for(Card c:cards) {
            deckCardsIds.add(c.getId());
        }
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public List<Long> getDeckCardsIds() {
        return deckCardsIds;
    }

    public void setDeckCardsIds(List<Long> deckCardsIds) {
        this.deckCardsIds = deckCardsIds;
    }
}
