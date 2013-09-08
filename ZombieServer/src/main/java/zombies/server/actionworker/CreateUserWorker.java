package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import com.google.inject.Inject;
import zombies.dto.reply.UserReply;
import zombies.entity.game.Card;
import zombies.entity.game.Deck;
import zombies.entity.game.Fraction;
import zombies.entity.game.SubFraction;
import org.hibernate.Query;
import org.hibernate.Session;
import zombies.dto.builder.ReplyBuilder;
import zombies.entity.server.User;
import zombies.server.game.LobbyManager;
import zombies.server.game.UserInfo;
import zombies.entity.support.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

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
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
      //  lock.acquire();
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
        try {
          //  Query query = ses.createQuery("select card from Card card where card.subFraction.fraction.id=:id and card.subFraction.level=1 and card.cardLevel=1");
         //   query.setParameter("id", userAction.getCreateUserAction().getSide());
         //   List<Card> list = (List<Card>) query.list();
            List<Card> list = new ArrayList<>();
            //TODO на время теста у юзера все карты
            for(SubFraction sub:lobbyManager.getFractions().get(userAction.getCreateUserAction().getSide()).getSubFractions() ){
               // if(sub.getLevel()==1){
                   for(Card c:sub.getDeck()){
                    //   if(c.getCardLevel()==1){
                           list.add(c);
                      // }
                   }
              //  }

            }


            Query query1 = ses.createQuery("select user from UserPlayer user where user.name=:name");
            query1.setParameter("name", userAction.getCreateUserAction().getName());
            if (!query1.list().isEmpty()) {
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("user already exist").build();
            }
            User usr = new User();
            usr.setLevel(1);
            usr.setXp(0);
            usr.setName(userAction.getCreateUserAction().getName());
            usr.setPass(userAction.getCreateUserAction().getPass());
            usr.setSide(userAction.getCreateUserAction().getSide());
            ses.persist(usr);
            usr.setAvailableCards(list);
            final Deck deck=new Deck();
            ArrayList<Card> cd=new ArrayList<>();
            Random r=new Random();
            for(int i=0;i<deckSize;i++){
                int ind=r.nextInt(list.size());
                cd.add(list.get(ind));
            }
            deck.setDeckCards(cd);
            ses.persist(deck);
            usr.setDecks(new ArrayList<Deck>(){{add(deck);}});
            usr.setActiveDeck(deck);
            ses.merge(usr);


            UserInfo ui = lobbyManager.saveUser(userAction.getName());
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