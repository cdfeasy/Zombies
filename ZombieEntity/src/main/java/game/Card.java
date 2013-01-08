package game;

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
    private boolean isBuilding;
    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Abilities> abilities=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="subfraction_id")
    private Subfraction subfraction;

    private int resourceCost1;
    private int resourceCost2;
    private int resourceCost3;

    public Card() {
    }

    public Card(String name, String description, int strength, int hp, int armour, int threadLevel, boolean building, int resourceCost1, int resourceCost2, int resourceCost3) {
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.hp = hp;
        this.armour = armour;
        this.threadLevel = threadLevel;
        this.isBuilding = building;
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

    public Subfraction getSubfraction() {
        return subfraction;
    }

    public void setSubfraction(Subfraction subfraction) {
        this.subfraction = subfraction;
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

    public boolean isBuilding() {
        return isBuilding;
    }

    public void setBuilding(boolean building) {
        isBuilding = building;
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
}
