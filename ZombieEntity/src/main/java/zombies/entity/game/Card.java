package zombies.entity.game;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 24.12.12
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Card {
    @Id
    private Long id;
    private String name;
    private String nameEng;
    private String description;
    private String descriptionEng;
    private int strength;
    private int hp;
    private int armour;
    private int imgResourceId;
    private int shortImgResourceId;
    private int threadLevel;
    private int cardType;
    private int cardLevel;
    private int cardGoldCost;
    private boolean uniqueCard;
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private List<Abilities> abilities=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="subFraction_id")
    private SubFraction subFraction;

    @ManyToOne
    @JoinColumn(name="uniqueAbility_id")
    private Abilities uniqueAbility;

    private int resourceCost1;
    private int resourceCost2;
    private int resourceCost3;

    public Card() {
    }

    public Card(Long id,String name, String description, int strength, int hp, int armour, int threadLevel, int cardType, int resourceCost1, int resourceCost2, int resourceCost3,int cardLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.hp = hp;
        this.armour = armour;
        this.threadLevel = threadLevel;
        this.cardType = cardType;
        this.resourceCost1 = resourceCost1;
        this.resourceCost2 = resourceCost2;
        this.resourceCost3 = resourceCost3;
        this.cardLevel=cardLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public int getStrength() {
        return strength;
    }

    public int getHp() {
        return hp;
    }

    public int getArmour() {
        return armour;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public int getShortImgResourceId() {
        return shortImgResourceId;
    }

    public int getThreadLevel() {
        return threadLevel;
    }

    public int getCardType() {
        return cardType;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public int getCardGoldCost() {
        return cardGoldCost;
    }

    public boolean isUniqueCard() {
        return uniqueCard;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public SubFraction getSubFraction() {
        return subFraction;
    }

    public Abilities getUniqueAbility() {
        return uniqueAbility;
    }

    public int getResourceCost1() {
        return resourceCost1;
    }

    public int getResourceCost2() {
        return resourceCost2;
    }

    public int getResourceCost3() {
        return resourceCost3;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public void setImgResourceId(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }

    public void setShortImgResourceId(int shortImgResourceId) {
        this.shortImgResourceId = shortImgResourceId;
    }

    public void setThreadLevel(int threadLevel) {
        this.threadLevel = threadLevel;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public void setCardGoldCost(int cardGoldCost) {
        this.cardGoldCost = cardGoldCost;
    }

    public void setUniqueCard(boolean uniqueCard) {
        this.uniqueCard = uniqueCard;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
    }

    public void setSubFraction(SubFraction subFraction) {
        this.subFraction = subFraction;
    }

    public void setUniqueAbility(Abilities uniqueAbility) {
        this.uniqueAbility = uniqueAbility;
    }

    public void setResourceCost1(int resourceCost1) {
        this.resourceCost1 = resourceCost1;
    }

    public void setResourceCost2(int resourceCost2) {
        this.resourceCost2 = resourceCost2;
    }

    public void setResourceCost3(int resourceCost3) {
        this.resourceCost3 = resourceCost3;
    }





    public void setId(String id) {
        this.id = Long.parseLong(id);
    }



    public void setStrength(String strength) {
        this.strength = Integer.parseInt(strength);
    }

    public void setHp(String hp) {
        this.hp = Integer.parseInt(hp);
    }

    public void setArmour(String armour) {
        this.armour = Integer.parseInt(armour);
    }

    public void setImgResourceId(String imgResourceId) {
        this.imgResourceId = Integer.parseInt(imgResourceId);
    }

    public void setShortImgResourceId(String shortImgResourceId) {
        this.shortImgResourceId = Integer.parseInt(shortImgResourceId);
    }

    public void setThreadLevel(String threadLevel) {
        this.threadLevel =Integer.parseInt( threadLevel);
    }

    public void setCardType(String cardType) {
        this.cardType = Integer.parseInt(cardType);
    }

    public void setCardLevel(String cardLevel) {
        this.cardLevel = Integer.parseInt(cardLevel);
    }

    public void setCardGoldCost(String cardGoldCost) {
        this.cardGoldCost = Integer.parseInt(cardGoldCost);
    }

    public void setUniqueCard(String uniqueCard) {
        this.uniqueCard = "true".equals(uniqueCard);
    }

    public void setEnabled(String enabled) {
        this.enabled = "true".equals(enabled);
    }

    public void setResourceCost1(String resourceCost1) {
        this.resourceCost1 = Integer.parseInt(resourceCost1);
    }

    public void setResourceCost2(String resourceCost2) {
        this.resourceCost2 = Integer.parseInt(resourceCost2);
    }

    public void setResourceCost3(String resourceCost3) {
        this.resourceCost3 = Integer.parseInt(resourceCost3);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", description='" + description + '\'' +
                ", descriptionEng='" + descriptionEng + '\'' +
                ", strength=" + strength +
                ", hp=" + hp +
                ", armour=" + armour +
                ", imgResourceId=" + imgResourceId +
                ", shortImgResourceId=" + shortImgResourceId +
                ", threadLevel=" + threadLevel +
                ", cardType=" + cardType +
                ", cardLevel=" + cardLevel +
                ", cardGoldCost=" + cardGoldCost +
                ", uniqueCard=" + uniqueCard +
                ", abilities=" + abilities +
                ", uniqueAbility=" + uniqueAbility +
                ", resourceCost1=" + resourceCost1 +
                ", resourceCost2=" + resourceCost2 +
                ", resourceCost3=" + resourceCost3 +
                ", subFraction=" + subFraction +
                '}';
    }
}
