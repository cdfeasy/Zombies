package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import game.Card;
import game.Deck;
import game.Fraction;
import game.SubFraction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.netty.channel.Channel;
import reply.Reply;
import builder.ReplyBuilder;
import server.User;
import server.game.LobbyManager;
import server.game.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
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
    Semaphore lock = new Semaphore(5);

    private int deckSize=30;

    @Override
    public Reply processAction(Action action, Object... params) throws Exception {
      //  lock.acquire();
        Session ses = server.HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
        try {
          //  Query query = ses.createQuery("select card from Card card where card.subFraction.fraction.id=:id and card.subFraction.level=1 and card.cardLevel=1");
         //   query.setParameter("id", action.getCreateUserAction().getSide());
         //   List<Card> list = (List<Card>) query.list();
            List<Card> list = new ArrayList<>();
            for(SubFraction sub:lobbyManager.getFractions().get(action.getCreateUserAction().getSide()).getSubFractions() ){
                if(sub.getLevel()==1){
                   for(Card c:sub.getDeck()){
                       if(c.getCardLevel()==1){
                           list.add(c);
                       }
                   }
                }

            }


            Query query1 = ses.createQuery("select user from UserPlayer user where user.name=:name");
            query1.setParameter("name", action.getCreateUserAction().getName());
            if (!query1.list().isEmpty()) {
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("user already exist").build();
            }
            User usr = new User();
            usr.setLevel(1);
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


            UserInfo ui = lobbyManager.saveUser(action.getName());
            User us=usr.CopyUser(false,false,true,lobbyManager.getCards());
            ui.setUser(us);

            return ReplyBuilder.getSuccessReplyBuilder().setSuccessText("user successfully created").build();

        } finally {
            ses.getTransaction().commit();
            ses.close();
          //  lock.release();

        }

    }
}