package support;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 09.02.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class GameInfo {
    private String enemyName;
    private int zombieKilled;
    private int survivalsKilled;
    private int xp;
    private int gold;
    private Map<Long,Integer> killed=new HashMap<Long,Integer>();
    private Map<Long,Integer> dead=new HashMap<Long,Integer>();
    private Map<Long,Integer> used=new HashMap<Long,Integer>();

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getZombieKilled() {
        return zombieKilled;
    }

    public void setZombieKilled(int zombieKilled) {
        this.zombieKilled = zombieKilled;
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

    public Map<Long, Integer> getKilled() {
        return killed;
    }

    public void setKilled(Map<Long, Integer> killed) {
        this.killed = killed;
    }

    public void incKilled(Long card) {
        Integer cnt=getKilled().get(card);
        if(cnt==null){
            getKilled().put(card,1);
        } else{
            getKilled().put(card,cnt+1);
        }
    }

    public Map<Long, Integer> getDead() {
        return dead;
    }

    public void setDead(Map<Long, Integer> dead) {
        this.dead = dead;
    }
    public void incDead(Long card) {
        Integer cnt=getDead().get(card);
        if(cnt==null){
            getDead().put(card,1);
        } else{
            getDead().put(card,cnt+1);
        }
    }

    public Map<Long, Integer> getUsed() {
        return used;
    }

    public void setUsed(Map<Long, Integer> used) {
        this.used = used;
    }

    public void incUsed(Long card) {
        Integer cnt=getUsed().get(card);
        if(cnt==null){
            getUsed().put(card,1);
        } else{
            getUsed().put(card,cnt+1);
        }
    }
    public void incZombieKilled(){
        zombieKilled++;
    }
    public void incSurvivalsKilled(){
        survivalsKilled++;
    }
}
