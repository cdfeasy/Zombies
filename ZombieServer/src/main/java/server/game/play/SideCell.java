package server.game.play;

import game.Card;
import game.CardTypeEnum;
import support.CardWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.01.13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class SideCell {
    private ArrayList<CardWrapper> cards=new ArrayList<CardWrapper>() ;

    Comparator<CardWrapper> comp=new Comparator<CardWrapper>(){
        @Override
        public int compare(CardWrapper o1, CardWrapper o2) {
            if(o1.getCard().getCardType()==CardTypeEnum.transport.getId())
                return 1;
            if(o2.getCard().getCardType()==CardTypeEnum.transport.getId())
                return -1;
            return o1.getCard().getThreadLevel()-o2.getCard().getThreadLevel();
        }
    };

    public void setCards(ArrayList<Card> ocards) {
        cards.clear();
        for(Card c:ocards)
            cards.add(new CardWrapper(c));
        Collections.sort(cards,comp);
    }

    public CardWrapper getTopCard(){
        if(cards.isEmpty())
            return null;
        CardWrapper top= cards.get(cards.size()-1);
        return  top;
    }

    public CardWrapper getLastCard(){
        if(cards.isEmpty())
            return null;
        CardWrapper top= cards.get(cards.size()-1);
        CardWrapper last= cards.get(0);
        if(top.getCard().getCardType()==CardTypeEnum.transport.getId())
            return top;
        return  last;
    }
    public void clearDead(){
        for(int i=cards.size()-1;i>=0;i--) {
            if(cards.get(i).getHp()<=0)
                cards.remove(cards.get(i));
        }
    }
    public void addCard(Card c){
        cards.add(new CardWrapper(c));
        Collections.sort(cards,comp);
    }

}
