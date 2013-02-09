package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import game.Deck;
import org.hibernate.Query;
import org.hibernate.Session;
import reply.Reply;
import builder.ReplyBuilder;
import server.User;
import server.game.LobbyManager;
import server.game.UserInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.01.13
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public class SetDeckActiveWorker implements IProcessor {
    @Inject
    LobbyManager lobbyManager;

    @Override
    public Reply processAction(Action action, Object... params) throws Exception {
        Session ses = server.HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = ses.createQuery("select user from UserPlayer user where user.name=:name");
            query.setParameter("name", action.getName());
            Query deckQuery = ses.createQuery("select deck from Deck deck where deck.id=:id");
            deckQuery.setParameter("id", action.getSetDeckActiveAction().getDeckId());
            List<Deck> lst=deckQuery.list();
            if(lst.size()==0){
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("deck not found").build();
            }
            Deck d=lst.get(0);
            int i=d.getDeck().size();

            if (!query.list().isEmpty()) {
                User user = (User) query.list().get(0);
                UserInfo ui = lobbyManager.saveUser(action.getName());
                ui.getUser().setActiveDeck(d);
                user.setActiveDeck(d);
                ses.merge(user);

                return ReplyBuilder.getSetDeckActiveBuilder().build();
            } else {
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("user not found").build();
            }

        } finally {
            ses.close();
        }
    }
}