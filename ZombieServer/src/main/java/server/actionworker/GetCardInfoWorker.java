package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import game.Card;
import game.Fraction;
import game.Subfraction;
import org.hibernate.Query;
import org.hibernate.Session;
import reply.Reply;
import reply.ReplyBuilder;
import server.User;
import server.game.LobbyManager;
import server.game.UserInfo;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 20.01.13
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
public class GetCardInfoWorker implements IProcessor {
    @Inject
    LobbyManager lobbyManager;

    volatile List<Fraction> frList=null;
    ReentrantLock lock=new ReentrantLock();

    @Override
    public Reply processAction(Action action) throws Exception {
        if(frList==null){
            lock.lock();
            if(frList==null){
                Session ses = server.HibernateUtil.getSessionFactory().openSession();
                try {
                    Query query = ses.createQuery("select fraction from Fraction fraction");
                    List<Fraction> list=  (List<Fraction>)query.list();
                    for(Fraction fr: (List<Fraction>)query.list()) {
                        for(Subfraction sub: fr.getDeck()){
                            for(Card c:sub.getDeck()){
                                c.getImg();
                            }
                        }
                    }
                    frList=list;
                } finally {
                    lock.unlock();
                    ses.close();
                }
            }
        }
        return ReplyBuilder.getGetCardInfoReplyBuilder().setFractions(frList).build();
    }
}