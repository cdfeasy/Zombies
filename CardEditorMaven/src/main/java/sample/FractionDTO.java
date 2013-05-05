package sample;

import javafx.beans.property.SimpleStringProperty;
import zombies.entity.game.Card;
import zombies.entity.game.Fraction;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.04.13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class FractionDTO {
    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    public FractionDTO(Fraction fr){
        this.name=new SimpleStringProperty(fr.getName());
        this.id=new SimpleStringProperty(Long.toString(fr.getId()));
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


    @Override
    public String toString() {
        return name.get();
    }
}
