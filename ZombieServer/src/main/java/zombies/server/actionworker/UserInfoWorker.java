package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import com.google.inject.Inject;
import zombies.dto.reply.UserReply;
import zombies.entity.game.Fraction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.LoggerFactory;
import zombies.dto.builder.ReplyBuilder;
import zombies.entity.server.User;
import zombies.server.game.LobbyManager;
import zombies.entity.support.HibernateUtil;

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
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
        if (frList == null) {
         //   lock.lock();
            if (frList == null) {
                Session ses = HibernateUtil.getSessionFactory().openSession();
                try {
                    Query query = ses.createQuery("select user from UserPlayer user where user.name=:name");
                    query.setParameter("name", userAction.getUserInfo().getUserName());
                    if (!query.list().isEmpty()) {
                        User user = (User) query.list().get(0);
                        User retUser;
                        if(userAction.getName().equals(userAction.getUserInfo().getUserName())) {
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
