package server.actionworker;

import actions.Action;
import game.Card;
import game.Fraction;
import game.Subfraction;
import org.hibernate.Query;
import org.hibernate.Session;
import reply.Reply;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.01.13
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public class SaveDeckWorker implements IProcessor{
    @Override
    public Reply processAction(Action action, Object... params) throws Exception {
//        Session ses = server.HibernateUtil.getSessionFactory().openSession();
//        try {
//            if(action.getSaveDeckAction().getDeckId()!=null){
//                Query query = ses.createQuery("select fraction from Fraction fraction");
//                List<Fraction> list=  (List<Fraction>)query.list();
//                for(Fraction fr: (List<Fraction>)query.list()) {
//                    for(Subfraction sub: fr.getSubFractions()){
//                        for(Card c:sub.getDeck()){
//                            c.getImg();
//                        }
//                    }
//                }
//            }
//
//        } finally {
//
//            ses.close();
//        }
        return null;
    }
}
