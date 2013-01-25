package game;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private int strength;
    private int hp;
    private int armour;
    @Basic(fetch= FetchType.LAZY)
    @Lob
    private byte[] img;
    private int threadLevel;
    private int cardType;
    private int cardLevel;
    private int cardGoldCost;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private List<Abilities> abilities=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="subfraction_id")
    private Subfraction subfraction;

    private int resourceCost1;
    private int resourceCost2;
    private int resourceCost3;

    public Card() {
    }

    public Card(String name, String description, int strength, int hp, int armour, int threadLevel, int cardType, int resourceCost1, int resourceCost2, int resourceCost3) {
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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public Subfraction getSubfraction() {
        return subfraction;
    }

    public void setSubfraction(Subfraction subfraction) {
        this.subfraction=subfraction;
        subfraction.addCard(this);
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int block) {
        this.armour = block;
    }

    public int getThreadLevel() {
        return threadLevel;
    }

    public void setThreadLevel(int threadLevel) {
        this.threadLevel = threadLevel;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getResourceCost1() {
        return resourceCost1;
    }

    public void setResourceCost1(int resourceCost1) {
        this.resourceCost1 = resourceCost1;
    }

    public int getResourceCost2() {
        return resourceCost2;
    }

    public void setResourceCost2(int resourceCost2) {
        this.resourceCost2 = resourceCost2;
    }

    public int getResourceCost3() {
        return resourceCost3;
    }

    public void setResourceCost3(int resourceCost3) {
        this.resourceCost3 = resourceCost3;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public int getCardGoldCost() {
        return cardGoldCost;
    }

    public void setCardGoldCost(int cardGoldCost) {
        this.cardGoldCost = cardGoldCost;
    }
}
