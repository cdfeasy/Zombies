package zombies.server.base;

import org.hibernate.Query;
import org.hibernate.Session;
import zombies.entity.server.DetailStatistic;
import zombies.entity.server.History;
import zombies.entity.server.User;
import zombies.server.game.UserInfo;
import zombies.entity.support.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 05.03.13
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class DTOWorker {


    /**
     * Процессит статистику пользователя, обвновляет значение экспы, левела итд. Сами значения меняются в GameEndProcessor
     * @param ses
     * @param player
     * @param playerInfo
     * @return
     */
    private User processUser(Session ses,UserInfo player,GameInfo playerInfo) {
        Query query = ses.createQuery("select user from UserPlayer user where user.name=:name");
        query.setParameter("name", player.getUser().getName());
        User user = (User) query.list().get(0);
        user.setXp(player.getUser().getXp());
        user.setGold(player.getUser().getGold());
        user.setLevel(player.getUser().getLevel());
        user.setZombieKilled(player.getUser().getZombieKilled());
        user.setSurvivalsKilled(player.getUser().getSurvivalsKilled());
        ses.merge(user);
        return user;
    }

    /**
     * Процессит детальную статистику карт, сколько раз каждая карта была использована, сколько убила, сколько умерла
     * @param ses
     * @param player
     * @param playerInfo
     */
    private void processUserStatistic(Session ses,User player,GameInfo playerInfo) {
        Set<Long> usedCard=new HashSet<>();
        for(Long id:playerInfo.getUsed().keySet()){
            usedCard.add(id);
        }
        for(Long id:playerInfo.getDead().keySet()){
            usedCard.add(id);
        }
        for(Long id:playerInfo.getKilled().keySet()){
            usedCard.add(id);
        }
        Query query = ses.createQuery("select dt from DetailStatistic dt where dt.user.name=:name and cardId in (:ids)");
        query.setParameter("name", player.getName());
        query.setParameterList("ids",usedCard);
        List<DetailStatistic> dts=query.list();
        for(Long id:usedCard) {
            boolean exist=false;
            for(DetailStatistic d:dts){
                if(d.getCardId().equals(id)){
                    exist=true;
                    if(playerInfo.getDead().get(id)!=null){
                        d.setDead(d.getDead()+playerInfo.getDead().get(id));
                    }
                    if(playerInfo.getUsed().get(id)!=null){
                        d.setUsed(d.getUsed() + playerInfo.getUsed().get(id));
                    }
                    if(playerInfo.getKilled().get(id)!=null){
                        d.setKilling(d.getKilling() + playerInfo.getKilled().get(id));
                    }
                    ses.merge(d);
                }
            }
            if(!exist){
                DetailStatistic d=new DetailStatistic();
                d.setUser(player);
                d.setCardId(id);
                d.setDead(0);
                d.setUsed(0);
                d.setKilling(0);
                if(playerInfo.getDead().get(id)!=null){
                    d.setDead(playerInfo.getDead().get(id));
                }
                if(playerInfo.getUsed().get(id)!=null){
                    d.setUsed( playerInfo.getUsed().get(id));
                }
                if(playerInfo.getKilled().get(id)!=null){
                    d.setKilling(playerInfo.getKilled().get(id));
                }
                ses.persist(d);
            }

        }
    }

    /**
     * Процессит в бд результат игры, обновляет статистику игроков
     * @param playerWin
     * @param playerLoose
     * @param playerWinInfo
     * @param playerLooseInfo
     */
    public void processGameResult( UserInfo playerWin,UserInfo playerLoose, GameInfo playerWinInfo, GameInfo playerLooseInfo){
        Session ses = zombies.entity.support.HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
        try {
            User user1 = processUser(ses, playerWin, playerWinInfo);
            User user2 = processUser(ses,playerLoose,playerLooseInfo);

            History history=new History();
            history.setPlayer1(user1);
            history.setPlayer2(user2);
            history.setGameType(playerWinInfo.getGameType());
            history.setResult((byte)0);
            ses.persist(history);

            processUserStatistic(ses,user1,playerWinInfo);
            processUserStatistic(ses,user2,playerLooseInfo);

        } finally {
            ses.getTransaction().commit();
            ses.close();
        }
    }

}
