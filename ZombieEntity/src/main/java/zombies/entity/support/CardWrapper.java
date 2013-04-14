package zombies.entity.support;

import zombies.entity.game.Card;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.01.13
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class CardWrapper  {
    private Card card;
    private Long cardId;
    private int wrapperId;
    private byte strengthBuff;
    private byte hp;
    private byte armourBuff;
    private byte virus;
    private boolean active=false;

    public CardWrapper() {
    }

    public CardWrapper(Card card,int wrapperId) {
        this.card = card;
        this.hp=(byte)card.getHp();
        this.armourBuff =(byte)card.getArmour();
        this.strengthBuff =(byte)card.getStrength();
        this.wrapperId = wrapperId;
        this.cardId=card.getId();
    }

    public CardWrapper(Card card, int wrapperId, boolean active) {
        this.card = card;
        this.active = active;
        this.wrapperId = wrapperId;
        this.hp=(byte)card.getHp();
        this.armourBuff =(byte)card.getArmour();
        this.strengthBuff =(byte)card.getStrength();
        this.cardId=card.getId();
    }
    @JsonIgnore(value = true)
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
        this.hp = (byte)hp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getStrengthBuff() {
        return strengthBuff;
    }

    public void setStrengthBuff(int strengthBuff) {
        this.strengthBuff = (byte)strengthBuff;
    }

    public int getArmourBuff() {
        return armourBuff;
    }

    public void setArmourBuff(int armourBuff) {
        this.armourBuff = (byte)armourBuff;
    }

    public int getWrapperId() {
        return wrapperId;
    }

    public void setWrapperId(int wrapperId) {
        this.wrapperId = wrapperId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public byte getVirus() {
        return virus;
    }

    public void setVirus(byte virus) {
        this.virus = virus;
    }

    public int resultArmour(){
        return getCard().getArmour()+getArmourBuff();
    }
    public int resultDamage(){
        return getCard().getStrength()+getStrengthBuff();
    }

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.generateJsonSchema(CardWrapper.class);
        Card c=new Card();
        c.setHp(1);
        c.setName("b;a");
        c.setThreadLevel(2);
        c.setArmour(2);
        c.setId(1l);
        CardWrapper cw=new CardWrapper(c,3);


        System.out.println(mapper.writeValueAsString(cw))  ;

    }
}
