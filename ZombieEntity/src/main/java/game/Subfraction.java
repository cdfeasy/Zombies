package game;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Subfraction {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy ="subfraction")
    private List<Card> deck=new ArrayList<Card>();

    @ManyToOne
    @JoinColumn(name="fraction_id")
    private Fraction fraction;
    /**
     * Дополнительные абилки фракции за большое число карт
     */
    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Abilities> abilities=new ArrayList<>();

    /**
     * Сколько ресурса дает эта колода за 10 карт
     */
    private int res1;
    private int res2;
    private int res3;

    public Subfraction() {
    }

    public Subfraction(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addCard(Card c){
        if(!this.equals(c.getSubfraction()))
            c.setSubfraction(this);
        if(!deck.contains(c))
            deck.add(c);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    @JsonIgnore
    public Fraction getFraction() {
        return fraction;
    }

    public void setFraction(Fraction fraction) {
        this.fraction=fraction;
        fraction.addSubfraction(this);
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

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
    }

    public int getRes1() {
        return res1;
    }

    public void setRes1(int res1) {
        this.res1 = res1;
    }

    public int getRes2() {
        return res2;
    }

    public void setRes2(int res2) {
        this.res2 = res2;
    }

    public int getRes3() {
        return res3;
    }

    public void setRes3(int res3) {
        this.res3 = res3;
    }

    @Override
    public String toString() {
        return "Subfraction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deck=" + deck +
                ", fraction=" + fraction +
                ", abilities=" + abilities +
                ", res1=" + res1 +
                ", res2=" + res2 +
                ", res3=" + res3 +
                '}';
    }
}
