package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import game.Card;
import game.Fraction;
import game.Subfraction;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import reply.Reply;
import builder.ReplyBuilder;
import server.game.LobbyManager;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 20.01.13
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
public class CardInfoWorker implements IProcessor {
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
                        for(Subfraction sub: fr.getSubFractions()){
                            Hibernate.initialize(sub.getAbilities());
                            for(Card c:sub.getDeck()){
                                Hibernate.initialize(c.getAbilities());
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