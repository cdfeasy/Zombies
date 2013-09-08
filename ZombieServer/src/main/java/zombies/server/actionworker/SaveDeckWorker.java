package zombies.server.actionworker;

import com.google.inject.Inject;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import zombies.dto.actions.UserAction;
import zombies.dto.builder.ReplyBuilder;
import zombies.dto.reply.UserReply;
import zombies.entity.game.Card;
import zombies.entity.game.Deck;
import zombies.entity.server.User;
import zombies.server.game.LobbyManager;
import zombies.server.game.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.01.13
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public class SaveDeckWorker implements IProcessor{
    @Inject
    LobbyManager lobby;

    @Override
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
        Session ses = zombies.entity.support.HibernateUtil.getSessionFactory().openSession();
        UserInfo ui=lobby.getUser(userAction.getName());

        try {
            if(userAction.getSaveDeckAction().getDeckId()!=null){
                if ( userAction.getSaveDeckAction().getDeckId()!=null) {
                    Query query = ses.createQuery("select deck from Deck deck where deck.id=:id");
                    query.setParameter("id", userAction.getSaveDeckAction().getDeckId());
                    List<Card> cards=new ArrayList<>();
                    Deck deck = (Deck) query.list().get(0);
                    for(Integer id:userAction.getSaveDeckAction().getCardsIds()) {
                        cards.add( lobby.getCards().get(new Long(id)));
                    }
                    deck.setDeckCards(cards);
                    deck.setDescription(userAction.getSaveDeckAction().getDescription());
                    deck.setName(userAction.getSaveDeckAction().getName());
                    ses.merge(deck);

                    Query query1 = ses.createQuery("select user from UserPlayer user where user.name=:name");
                    query1.setParameter("name", userAction.getName());
                    User us=((User)query1.list().get(0)).CopyUser(false,false,true,lobby.getCards());
                    ui.setUser(us);
                    return ReplyBuilder.getSuccessReplyBuilder().setSuccessText("deck saved").build();
                } else {
                    List<Card> cards=new ArrayList<>();
                    Deck deck = new Deck();
                    for(Integer id:userAction.getSaveDeckAction().getCardsIds()) {
                        cards.add( lobby.getCards().get(new Long(id)));
                    }
                    deck.setDeckCards(cards);
                    deck.setDescription(userAction.getSaveDeckAction().getDescription());
                    deck.setName(userAction.getSaveDeckAction().getName());
                    ses.persist(deck);
                    Query query1 = ses.createQuery("select user from UserPlayer user where user.name=:name");
                    query1.setParameter("name", userAction.getName());
                    User us=((User)query1.list().get(0));
                    us.getDecks().add(deck);
                    ses.merge(us);
                    User coped=us.CopyUser(false,false,true,lobby.getCards());
                    ui.setUser(coped);
                    return ReplyBuilder.getSuccessReplyBuilder().setSuccessText("deck saved").build();
                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        finally {

            ses.close();
        }
        return null;
    }
}
