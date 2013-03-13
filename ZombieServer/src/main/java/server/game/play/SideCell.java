package server.game.play;

import game.Card;
import game.CardTypeEnum;
import support.CardWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.01.13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class SideCell {
    private ArrayList<CardWrapper> cards=new ArrayList<CardWrapper>() ;
    private GameManager manager;


    public SideCell(GameManager manager) {
        this.manager = manager;
    }

    private SideCell(){

    }

    public void setCards(ArrayList<Card> ocards) {
        cards.clear();
        for(Card c:ocards) {
            addCard(c);
        }
    }



    public int hit(int damage,CardWrapper c){
        int spend=0;
        if(damage<c.resultDamage()){
            return 0;
        }

        if(damage>c.getHp()+c.resultDamage()){
            spend=c.getHp()+c.resultDamage();
            c.setHp(0);
            return spend;
        }
        c.setHp(c.getHp()-damage+c.resultDamage());
        return damage;
    }

    public CardWrapper getTopCard(){
        if(cards.isEmpty())
            return null;
        int max=-1;
        int index=-1;
        for(int i=0;i<cards.size();i++){
            if(cards.get(i).getCard().getThreadLevel()>max && cards.get(i).getHp()>0){
               index=i;
               max=cards.get(i).getCard().getThreadLevel();
            }
            if(CardTypeEnum.getValue(cards.get(i).getCard().getCardType()).equals(CardTypeEnum.transport) && cards.get(i).getHp()>0){
                index=i;
                max=9999;
            }
        }
        if(index==-1){
            return null;
        }

        CardWrapper top= cards.get(index);
        return  top;
    }

    public List<CardWrapper> getCards(){
        return  cards;
    }

    /**
     * возвращает или первую или последнюю карту, в зависимости от флага
     * @param isTop
     * @return
     */
    public CardWrapper getCard(boolean isTop){
        if(isTop)
            return getTopCard();
        else
            return getLastCard();

    }

    public CardWrapper getLastCard(){
        if(cards.isEmpty())
            return null;
        int min=9999;
        int index=-1;
        int strIndex=-1;
        for(int i=0;i<cards.size();i++){
            if(cards.get(i).getCard().getThreadLevel()<min && cards.get(i).getHp()>0 && !CardTypeEnum.getValue(cards.get(i).getCard().getCardType()).equals(CardTypeEnum.structure)){
                index=i;
                min=cards.get(i).getCard().getThreadLevel();
            }
            if(CardTypeEnum.getValue(cards.get(i).getCard().getCardType()).equals(CardTypeEnum.transport) && cards.get(i).getHp()>0){
                index=i;
                min=-9999;
            }
            if(cards.get(i).getCard().getThreadLevel()<min && cards.get(i).getHp()>0 && CardTypeEnum.getValue(cards.get(i).getCard().getCardType()).equals(CardTypeEnum.structure)){
                strIndex=i;
            }
          }
        if(index==-1){
            if(strIndex!=-1){
                return  cards.get(strIndex) ;
            }
            return null;
        }
        CardWrapper last= cards.get(index);
        return  last;
    }
    public void clearDead(){
        for(int i=cards.size()-1;i>=0;i--) {
            if(cards.get(i).getHp()<=0)
                cards.remove(cards.get(i));
        }
    }

    int i=0;
    public CardWrapper addCard(Card c){
        CardWrapper wrapper=new CardWrapper(c,manager.incrementAndGetCardWrapperNum());
      //  CardWrapper wrapper=new CardWrapper(c,i++);
        cards.add(wrapper);
        return wrapper;
    }

    public static void main(String... args){
        Card c1=new Card();
        c1.setName("card1");
        c1.setArmour(1);
        c1.setHp(10);
        c1.setThreadLevel(9);
        c1.setCardType(CardTypeEnum.creature.getId());

        Card c2=new Card();
        c2.setName("card2");
        c2.setArmour(1);
        c2.setHp(10);
        c2.setThreadLevel(10);
        c2.setCardType(CardTypeEnum.creature.getId());

        Card c3=new Card();
        c3.setName("card3");
        c3.setArmour(1);
        c3.setHp(10);
        c3.setThreadLevel(8);
        c3.setCardType(CardTypeEnum.creature.getId());

        Card c4=new Card();
        c4.setName("card4");
        c4.setArmour(1);
        c4.setHp(10);
        c4.setThreadLevel(2);
        c4.setCardType(CardTypeEnum.transport.getId());

        Card c5=new Card();
        c5.setName("card5");
        c5.setArmour(1);
        c5.setHp(10);
        c5.setThreadLevel(3);
        c5.setCardType(CardTypeEnum.creature.getId());

        SideCell p2cell=new SideCell();
        p2cell.addCard(c1);
        p2cell.addCard(c2);
        p2cell.addCard(c3);
        p2cell.addCard(c4);
        p2cell.addCard(c5);

        int damage=30;
        int spend=0;
        CardWrapper cw=null;
        while((cw=p2cell.getTopCard())!=null && (spend=p2cell.hit(damage,cw))>0){
            System.out.println(String.format("hit %s on %s damage",cw.getCard().getName(),Integer.toString(spend)));
            damage=damage-spend;
        }
        System.out.println(damage);





    }

}
