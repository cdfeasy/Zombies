package game;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public class Fraction {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy ="fraction"  , cascade = CascadeType.REFRESH)
    private List<Subfraction> deck;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Subfraction> getDeck() {
        return deck;
    }

    public void setDeck(List<Subfraction> deck) {
        this.deck = deck;
    }
}
