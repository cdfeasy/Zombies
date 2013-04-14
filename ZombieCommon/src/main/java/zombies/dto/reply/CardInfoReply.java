package zombies.dto.reply;

import zombies.entity.game.Fraction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 17.01.13
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class CardInfoReply {
    private List<Fraction> fractions;

    public CardInfoReply(List<Fraction> fractions) {
        this.fractions = fractions;
    }

    public CardInfoReply() {
    }

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions;
    }
}
