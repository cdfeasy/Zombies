package game;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public class Subfraction {
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy ="subfraction"  , cascade = CascadeType.REFRESH)
    private List<Card> deck;
    @ManyToOne
    @JoinColumn(name="fraction_id")
    private Fraction fraction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Fraction getFraction() {
        return fraction;
    }

    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
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
}
