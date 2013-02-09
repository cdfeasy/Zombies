package reply;

import server.User;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 03.02.13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class GameStartedReply {
    private User enemy;

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


}
