package server.game;


import com.google.inject.Inject;
import game.Card;
import game.Fraction;
import game.SubFraction;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.netty.channel.Channel;
import server.game.play.GameManager;

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
    private ConcurrentLinkedQueue<Request> requestQueue=new ConcurrentLinkedQueue<>();
    private ScheduledExecutorService gameTicker=Executors.newScheduledThreadPool(10);


    ScheduledExecutorService requestParser= Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService newGameStarter= Executors.newSingleThreadScheduledExecutor();


    @Inject
    NewGameStarter newGameStarterThread;
    @Inject
    RequestManager requestManager;

    public LobbyManager(){

    }
    @Inject
    public void init(){
        cards=new HashMap<Long,Card>();
        fractions=new HashMap<Long,Fraction>();
        Session ses = server.HibernateUtil.getSessionFactory().openSession();
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
       runningGames.add(new GameManager(user1,user2,this));

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
