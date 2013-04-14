package zombies.dto.reply;

import zombies.entity.server.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 03.02.13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class GameStartedReply {
    private User enemy;
    private List<Long> cards;
    private int position;
    private int res1;
    private int res2;
    private int res3;

    public GameStartedReply() {
    }

    public GameStartedReply(User enemy) {
        this.enemy = enemy;
    }

    public User getEnemy() {
        return enemy;
    }

    public void setEnemy(User enemy) {
        this.enemy = enemy;
    }

    public List<Long> getCards() {
        return cards;
    }

    public void setCards(List<Long> cards) {
        this.cards = cards;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRes1() {
        return res1;
    }

    public void setRes1(int res1) {
        this.res1 = res1;
    }

    public int getRes2() {
        return res2;
    }

    public void setRes2(int res2) {
        this.res2 = res2;
    }

    public int getRes3() {
        return res3;
    }

    public void setRes3(int res3) {
        this.res3 = res3;
    }
}
