package reply;

import game.Fraction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 17.01.13
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class GetCardInfoReply {
    private List<Fraction> fractions;

    public GetCardInfoReply(List<Fraction> fractions) {
        this.fractions = fractions;
    }

    public GetCardInfoReply() {
    }

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions;
    }
}
