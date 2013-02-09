package support;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 09.02.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class GameInfo {
    private String enemyName;
    private int zombyKilled;
    private int survivalsKilled;
    private int xp;
    private int gold;

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getZombyKilled() {
        return zombyKilled;
    }

    public void setZombyKilled(int zombyKilled) {
        this.zombyKilled = zombyKilled;
    }

    public int getSurvivalsKilled() {
        return survivalsKilled;
    }

    public void setSurvivalsKilled(int survivalsKilled) {
        this.survivalsKilled = survivalsKilled;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
