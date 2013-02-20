package server.game.play;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import game.Card;
import game.Deck;
import game.SubFraction;
import server.Constants;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 21.01.13
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class PlayerCards {
    @Inject
    @Named(value = Constants.PLAYER_HAND_SIZE)
    Integer handSize = 6;
    @Inject
    @Named(value = Constants.FRACTION_BONUS_SIZE)
    Integer fractionSize = 10;
    Random rand = new Random();
    private List<Card> playerDeck = new ArrayList<>();
    private LinkedList<Card> playerGraveyard = new LinkedList<>();
    private ArrayList<Card> playerHand = new ArrayList<>();
    private int baseRes1 = 0;
    private int baseRes2 = 0;
    private int baseRes3 = 0;

    public PlayerCards(List<Card> playerDeck) {
        this.playerDeck = playerDeck;

        HashMap<SubFraction, Integer> cardCount = new HashMap<SubFraction, Integer>();
        for (Card c : playerDeck) {
            Integer value = cardCount.get(c.getSubFraction());
            if (value == null) {
                cardCount.put(c.getSubFraction(), 1);
            } else {
                cardCount.put(c.getSubFraction(), value + 1);
            }
        }
        for (Map.Entry<SubFraction, Integer> e : cardCount.entrySet()) {
            baseRes1 += e.getKey().getRes1() * (e.getValue() / fractionSize);
            baseRes2 += e.getKey().getRes2() * (e.getValue() / fractionSize);
            baseRes3 += e.getKey().getRes3() * (e.getValue() / fractionSize);
        }
        getHand(handSize);

    }

    public static void main(String[] args) {
        ArrayList<Card> arr = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Card c = new Card();
            c.setName(Integer.toString(i));
            arr.add(c);
        }
        PlayerCards pc = new PlayerCards(arr);
        Card c = null;
        while ((c = pc.getRandomCard()) != null) {
            System.out.println(c.getName());
        }

    }

    public List<Card> getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(Deck deck) {
        for (Card c : deck.getDeck()) {
            this.playerDeck.add(c);
        }
    }

    public LinkedList<Card> getPlayerGraveyard() {
        return playerGraveyard;
    }

    public void setPlayerGraveyard(LinkedList<Card> playerGraveyard) {
        this.playerGraveyard = playerGraveyard;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(ArrayList<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public Card getRandomCard() {
        if (playerDeck.size() == 0)
            return null;
        int index = rand.nextInt(playerDeck.size());
        Card c = playerDeck.get(index);
        playerDeck.remove(index);
        return c;
    }

    public void resurrectFromGraveyard() {
        playerDeck.addAll(playerGraveyard);
        playerGraveyard.clear();
    }

    public void getHand(int cnt) {
        for (int i = 0; i < cnt; i++) {
            Card c = getRandomCard();
            if (c == null) {
                resurrectFromGraveyard();
                c = getRandomCard();
            }

            playerHand.add(c);
        }
    }

    public void fillHand() {
        if (playerHand.size() == handSize) {
            int random = rand.nextInt(handSize);
            Card c = playerDeck.get(random);
            playerGraveyard.add(c);
            playerDeck.remove(random);
        }
        if (playerHand.size() == 0) {
            getHand(2);
        } else {
            getHand(1);
        }

    }

    public int getBaseRes3() {
        return baseRes3;
    }

    public int getBaseRes1() {
        return baseRes1;
    }

    public int getBaseRes2() {
        return baseRes2;
    }

}
