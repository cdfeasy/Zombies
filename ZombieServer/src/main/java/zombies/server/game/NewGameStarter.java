package zombies.server.game;

import com.google.inject.Inject;
import org.slf4j.LoggerFactory;
import zombies.entity.server.User;

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
    private  static org.slf4j.Logger logger= LoggerFactory.getLogger(NewGameStarter.class);
    Comparator<UserInfo> comp=new Comparator<UserInfo>() {
        @Override
        public int compare(UserInfo o1, UserInfo o2) {
            return o1.getUser().getLevel()-o2.getUser().getLevel();
        }
    };
    ReentrantLock lock=new ReentrantLock();
    private ArrayList<UserInfo> queue=new   ArrayList<>();
    int levelconst=3;
    @Inject
    LobbyManager manager;

    public NewGameStarter(){

    }

    @Override
    public void run() {
        StartGames();
    }

    public void registerPlayerInQueue(UserInfo user){
        lock.lock();
        try{
          queue.add(user);
        }finally{
            lock.unlock();
        }
    }

    public void dropPlayerFromQueue(UserInfo user){
        lock.lock();
        try{
            queue.remove(user);
        }finally{
            lock.unlock();
        }
    }
    private void StartGames(){
        lock.lock();
       // logger.info("Queue size {}",Integer.toString(queue.size()));
        try{
            Collections.sort(queue,comp);
            int index=queue.size()-1;
            while(index>0){
                UserInfo first= queue.get(index);
                for(int i=index-1;i>=0;i--){
                    UserInfo second= queue.get(i);
                    if(first.getUser().getLevel()-second.getUser().getLevel()<levelconst) {
                        try{
                         manager.startGame(first,second);
                        }catch (Exception io){
                            io.printStackTrace();
                        }

                        //System.out.println("Start zombies.entity.game:"+first+";"+second);
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
        for(int i=0;i<10;i++){
            User us1=new User();
            us1.setId(Long.valueOf(i));
            us1.setName("us"+i);
            us1.setLevel(i*2);
            UserInfo ui=new UserInfo();
            ui.setUser(us1);
            ui.setId(i);
            s.registerPlayerInQueue(ui );

        }
        s.StartGames();


       // s.registerPlayerInQueue(us2 );
      //  s.registerPlayerInQueue(us4 );
      //  s.registerPlayerInQueue(us3 );
      //  s.registerPlayerInQueue(us1 );
       // s.sort();

    }
}
