package sample;

import javafx.beans.property.SimpleStringProperty;
import zombies.entity.game.Card;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.04.13
 * Time: 12:20
 * To change this template use File | Settings | File Templates.
 */
public class CardDTO {
    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    public CardDTO(Card card){
        this.name=new SimpleStringProperty(card.getName());
        this.id=new SimpleStringProperty(Long.toString(card.getId()));
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }
}
