package zombies.server.game;


import com.google.inject.Inject;
import zombies.entity.game.Card;
import zombies.entity.game.Fraction;
import zombies.entity.game.SubFraction;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.netty.channel.Channel;
import zombies.server.game.play.AbilitiesProcessor;
import zombies.server.game.play.GameEndProcessor;
import zombies.server.game.play.GameManager;
import zombies.entity.support.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class LobbyManager {
    private HashMap<String,UserInfo> users=new HashMap<>();
    private HashMap<Long,Card> cards;
    private HashMap<Long,Fraction> fractions;
    private ArrayList<GameManager> runningGames=new ArrayList<GameManager>();
    private ReentrantLock userLock =new ReentrantLock();
    private ReentrantLock managerLock =new ReentrantLock();
    private LinkedBlockingQueue<Request> requestQueue=new LinkedBlockingQueue<>();
    private ScheduledExecutorService gameTicker=Executors.newSingleThreadScheduledExecutor();


    ScheduledExecutorService requestParser= Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService newGameStarter= Executors.newSingleThreadScheduledExecutor();


    @Inject
    NewGameStarter newGameStarterThread;
    @Inject
    RequestManager requestManager;
    @Inject
    GameEndProcessor gameEnd;
    @Inject
    AbilitiesProcessor ability;



    public LobbyManager(){

    }
    @Inject
    public void init(){
        cards=new HashMap<Long,Card>();
        fractions=new HashMap<Long,Fraction>();
        Session ses = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = ses.createQuery("select fraction from Fraction fraction");
            List<Fraction> list=  (List<Fraction>)query.list();
                for(Fraction fr: (List<Fraction>)query.list()) {
                    fractions.put(fr.getId(),fr);
                    int i=0;
                    for(SubFraction sub: fr.getSubFractions()){
                        sub.getAbilities();
                        Hibernate.initialize(sub.getAbilities());
                        for(Card c:sub.getDeck()){
                            cards.put(c.getId(),c);
                            Hibernate.initialize(c.getAbilities());

                        }
                    }
                }

        } finally {
            ses.close();
        }


        requestManager.setRequestQueue(requestQueue);
        requestParser.scheduleWithFixedDelay(requestManager,0,10,TimeUnit.MILLISECONDS);
        newGameStarter.scheduleWithFixedDelay(newGameStarterThread,0,1000,TimeUnit.MILLISECONDS);
        ability.init();
    }

    public UserInfo getUser(String name){
           return users.get(name);
    }
    public UserInfo saveUser(String name){
        userLock.lock();
        try{
            UserInfo cur=  users.get(name);
            if(cur==null) {
                cur =new UserInfo();
                users.put(name,cur);
            }
            return cur;
        }finally {
            userLock.unlock();
        }
    }

    public void startGame(UserInfo user1,UserInfo user2) throws IOException {
      //  System.out.println(user1);
     //   System.out.println(user2);
       runningGames.add(new GameManager(user1,user2,this,gameEnd, ability));

    }

    public void endGame(GameManager manager) throws IOException {

        runningGames.remove(manager);

    }

    public void parseRequest(Channel channel, String request) {
        requestQueue.add(new Request(channel,request));
    }

    public HashMap<Long, Card> getCards() {
        return cards;
    }

    public HashMap<Long, Fraction> getFractions() {
        return fractions;
    }

    public void addGameTicker(Runnable tick,int timeout){
        gameTicker.schedule(tick,timeout,TimeUnit.SECONDS);
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

}
