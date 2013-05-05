package sample;

import javafx.beans.property.SimpleStringProperty;
import zombies.entity.game.Abilities;
import zombies.entity.game.Card;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.04.13
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public class AbilityDto {
    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    private final SimpleStringProperty description;
    private final SimpleStringProperty units;
    public AbilityDto(Abilities ab,String units){
        this.name=new SimpleStringProperty(ab.getName());
        this.id=new SimpleStringProperty(Long.toString(ab.getId()));
        this.description=new SimpleStringProperty(ab.getDescription());
        this.units=new  SimpleStringProperty(units);
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

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getUnits() {
        return units.get();
    }

    public SimpleStringProperty unitsProperty() {
        return units;
    }

    public void setUnits(String units) {
        this.units.set(units);
    }
    @Override
    public String toString() {
        return name.get();
    }
}
