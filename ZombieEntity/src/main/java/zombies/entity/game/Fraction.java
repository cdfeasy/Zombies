package zombies.entity.game;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Fraction {
    @Id
    private Long id;
    private String name;
    private String nameEng;
    private String description;
    private String descriptionEng;
    @OneToMany(mappedBy ="fraction")
    private List<SubFraction> subFractions =new ArrayList<>();

    public Fraction() {
    }

    public Fraction(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public void addSubFraction(SubFraction sub){
        if(!this.equals(sub.getFraction()))
            sub.setFraction(this);
        if(!subFractions.contains(sub))
            subFractions.add(sub);

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


    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubFraction> getSubFractions() {
        return subFractions;
    }

    public void setSubFractions(List<SubFraction> deck) {
        this.subFractions = deck;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }
}
