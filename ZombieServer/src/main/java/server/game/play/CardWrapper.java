package server.game.play;

import game.Card;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.01.13
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class CardWrapper {
    private Card card;
    private int hp;
    private boolean active;

    public CardWrapper() {
    }

    public CardWrapper(Card card) {
        this.card = card;
        this.hp=card.getHp();
    }

    public CardWrapper(Card card, boolean active) {
        this.card = card;
        this.active = active;
        this.hp=card.getHp();
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
