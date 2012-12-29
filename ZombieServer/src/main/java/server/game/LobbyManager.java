package server.game;


import com.google.inject.Inject;
import org.jboss.netty.channel.Channel;
import server.User;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<Long,UserInfo> users=new HashMap<Long,UserInfo>();
    private ArrayList<GameManager> runningGames=new ArrayList<GameManager>();
    private ReentrantLock userLock =new ReentrantLock();
    private ReentrantLock managerLock =new ReentrantLock();
    private ConcurrentLinkedQueue<Request> requestQueue=new ConcurrentLinkedQueue<>();


    ScheduledExecutorService requestParser;
    ScheduledExecutorService newGameStarter;


    @Inject
    RequestManager requestManagerThread;
    @Inject
    NewGameStarter newGameStarterThread;

    public LobbyManager(){

    }
    @Inject
    public void init(){
        requestParser= Executors.newSingleThreadScheduledExecutor();
        newGameStarter= Executors.newSingleThreadScheduledExecutor();
        requestParser.scheduleWithFixedDelay(requestManagerThread,0,10,TimeUnit.MILLISECONDS);
        newGameStarter.scheduleWithFixedDelay(newGameStarterThread,0,1000,TimeUnit.MILLISECONDS);
    }

    private UserInfo getUser(Long id){
           return users.get(id);
    }
    private void saveUser(long id){
        userLock.lock();
        try{

        }finally {
            userLock.unlock();
        }
    }

    public void startGame(User user1,User user2){
        runningGames.add(new GameManager(user1,user2));
    }

    public void parseRequest(Channel channel, String request) {
        requestQueue.add(new Request(channel,request));
    }
}
