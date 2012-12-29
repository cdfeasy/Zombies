package server.game;

import com.google.common.collect.Ordering;
import com.google.inject.Inject;
import server.User;

import javax.swing.event.TreeModelListener;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.12.12
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class NewGameStarter implements Runnable {
    Comparator<User> comp=new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getLevel()-o2.getLevel();
        }
    };
    ReentrantLock lock=new ReentrantLock();
    private ArrayList<User> queue=new   ArrayList<User>();
    int levelconst=2;
    @Inject
    LobbyManager manager;

    public NewGameStarter(){

    }

    @Override
    public void run() {
        StartGames();
    }

    public void registerPlayerInQueue(User user){
        lock.lock();
        try{
          queue.add(user);
        }finally{
            lock.unlock();
        }
    }
    private void StartGames(){
        lock.lock();
        try{
            Collections.sort(queue,comp);
            int index=queue.size()-1;
            while(index>0){
                User first= queue.get(index);
                for(int i=index-1;i>=0;i--){
                    User second= queue.get(i);
                    if(first.getLevel()-second.getLevel()<levelconst) {
                        manager.startGame(first,second);
                        queue.remove(first);
                        queue.remove(second);
                        index=queue.size()-1;
                        break;
                    }
                    else{
                        index--;
                        break;
                    }
                }

            }

        }finally{
            lock.unlock();
        }
    }

    public static void main(String[] args){
        NewGameStarter s=new NewGameStarter();
        User us1=new User();
        User us2=new User();
        User us3=new User();
        User us4=new User();
        us1.setId(1l);
        us1.setName("us1");
        us1.setLevel(1);
        us2.setId(2l);
        us2.setName("us2");
        us2.setLevel(2);
        us3.setId(3l);
        us3.setName("us3");
        us3.setLevel(3);
        us4.setId(4l);
        us4.setName("us4");
        us4.setLevel(4);

        s.registerPlayerInQueue(us2 );
        s.registerPlayerInQueue(us4 );
        s.registerPlayerInQueue(us3 );
        s.registerPlayerInQueue(us1 );
       // s.sort();

    }
}
