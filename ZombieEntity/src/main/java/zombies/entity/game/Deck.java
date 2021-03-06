package zombies.entity.game;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 14.01.13
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Deck {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    @JoinTable(name = "Deck_Cards",
            joinColumns = {@JoinColumn(name = "Deck_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "Card_ID", referencedColumnName = "id")})
    private List<Card> deckCards;

    private String name;
    private String description;


    public Deck() {
    }

    public Deck(List<Card> deckCards) {
        this.deckCards = deckCards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getDeckCards() {
        return deckCards;
    }

    public void setDeckCards(List<Card> deck) {
        this.deckCards = deck;
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

    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", deck=" + deckCards +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck deck = (Deck) o;

        if (!id.equals(deck.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
