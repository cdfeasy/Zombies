package server.game.play;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import game.Card;
import game.Deck;
import server.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

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
    Integer handSize=6;

    private ArrayList<Card> playerDeck=new ArrayList<>();
    private LinkedList<Card> playerGraveyard=new LinkedList<>();
    private ArrayList<Card> playerHand=new ArrayList<>();
    Random rand=new Random();

    public PlayerCards(ArrayList<Card> playerDeck) {
        this.playerDeck = playerDeck;
    }

    public ArrayList<Card> getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(Deck deck) {
        for(Card c:deck.getDeck()){
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

    public Card getRandomCard(){
        if(playerDeck.size()==0)
            return null;
        int index=rand.nextInt(playerDeck.size());
        Card c=playerDeck.get(index);
        playerDeck.remove(index);
        return c;
    }

    public void resurrectFromGraveyard(){
        Card c=playerGraveyard.pollFirst();
        if(c!=null)
            playerDeck.add(c);
    }

    public void getHand(int cnt){
        Card c= getRandomCard();
        if(c==null){
            resurrectFromGraveyard();
        }
        c= getRandomCard();
        playerHand.add(c);
    }

    public static void main(String[] args){
        ArrayList<Card> arr=new ArrayList<>();
        for(int i=0;i<10;i++) {
             Card c=new Card();
            c.setName(Integer.toString(i));
            arr.add(c);
        }
        PlayerCards pc=new PlayerCards(arr);
        Card c=null;
        while((c=pc.getRandomCard())!=null){
            System.out.println(c.getName());
        }

    }

}
