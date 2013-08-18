package zombies.entity.game;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 17.08.13
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
public class GameCards {
    private List<Fraction> fractions;
    private List<Abilities> abilitieses;

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions;
    }

    public List<Abilities> getAbilitieses() {
        return abilitieses;
    }

    public void setAbilitieses(List<Abilities> abilitieses) {
        this.abilitieses = abilitieses;
    }
}
