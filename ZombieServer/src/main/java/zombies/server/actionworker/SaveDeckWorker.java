package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import zombies.dto.reply.UserReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.01.13
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public class SaveDeckWorker implements IProcessor{
    @Override
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
//        Session ses = zombies.entity.support.HibernateUtil.getSessionFactory().openSession();
//        try {
//            if(userAction.getSaveDeckAction().getDeckId()!=null){
//                Query query = ses.createQuery("select fraction from Fraction fraction");
//                List<Fraction> list=  (List<Fraction>)query.list();
//                for(Fraction fr: (List<Fraction>)query.list()) {
//                    for(SubFraction sub: fr.getSubFractions()){
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
