package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import game.Deck;
import game.Fraction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.LoggerFactory;
import reply.Reply;
import builder.ReplyBuilder;
import server.User;
import server.game.LobbyManager;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 08.01.13
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoWorker implements IProcessor {

    org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());
    @Inject
    LobbyManager lobbyManager;


    volatile List<Fraction> frList = null;
    ReentrantLock lock = new ReentrantLock();

    @Override
    public Reply processAction(Action action, Object... params) throws Exception {
        if (frList == null) {
         //   lock.lock();
            if (frList == null) {
                Session ses = server.HibernateUtil.getSessionFactory().openSession();
                try {
                    Query query = ses.createQuery("select user from UserPlayer user where user.name=:name");
                    query.setParameter("name", action.getUserInfo().getUserName());
                    if (!query.list().isEmpty()) {
                        User user = (User) query.list().get(0);
                        User retUser;
                        if(action.getName().equals(action.getUserInfo().getUserName())) {
                            retUser=user.CopyUser(true,true,false,null);
                        } else{
                            retUser=user.CopyUser(false,false,false,null);
                        }
                        return ReplyBuilder.getUserInfoReplyBuilder().setUser(retUser).build();
                    } else {
                        return ReplyBuilder.getErrorReplyBuilder().setErrorText("user not found").build();
                    }
                } finally {
                  //  lock.unlock();
                    ses.close();
                }
            }
        }
        return ReplyBuilder.getGetCardInfoReplyBuilder().setFractions(frList).build();
    }
}
