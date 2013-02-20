package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import game.Card;
import game.Deck;
import game.Fraction;
import org.hibernate.Query;
import org.hibernate.Session;
import reply.Reply;
import builder.ReplyBuilder;
import server.User;
import server.game.LobbyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 31.01.13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public class CreateUserWorker implements IProcessor {
    @Inject
    LobbyManager lobbyManager;

    volatile List<Fraction> frList = null;
    ReentrantLock lock = new ReentrantLock();

    private int deckSize=30;

    @Override
    public Reply processAction(Action action, Object... params) throws Exception {
        lock.lock();
        Session ses = server.HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
        try {
            Query query = ses.createQuery("select card from Card card where card.subFraction.fraction.id=:id and card.subFraction.level=1 and card.cardLevel=1");
            query.setParameter("id", action.getCreateUserAction().getSide());
            List<Card> list = (List<Card>) query.list();
            Query query1 = ses.createQuery("select user from UserPlayer user where user.name=:name");
            query1.setParameter("name", action.getCreateUserAction().getName());
            if (!query1.list().isEmpty()) {
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("user already exist").build();
            }
            User usr = new User();
            usr.setLevel(0);
            usr.setXp(0);
            usr.setName(action.getCreateUserAction().getName());
            usr.setPass(action.getCreateUserAction().getPass());
            usr.setSide(action.getCreateUserAction().getSide());
            ses.persist(usr);
            usr.setAvailableCards(list);
            final Deck deck=new Deck();
            ArrayList<Card> cd=new ArrayList<>();
            Random r=new Random();
            for(int i=0;i<deckSize;i++){
                int ind=r.nextInt(list.size());
                cd.add(list.get(ind));
            }
            deck.setDeck(cd);
            ses.persist(deck);
            usr.setDecks(new ArrayList<Deck>(){{add(deck);}});
            usr.setActiveDeck(deck);
            ses.merge(usr);
            return ReplyBuilder.getSuccessReplyBuilder().setSuccessText("user already exist").build();

        } finally {
            ses.getTransaction().commit();
            ses.close();
            lock.unlock();

        }

    }
}