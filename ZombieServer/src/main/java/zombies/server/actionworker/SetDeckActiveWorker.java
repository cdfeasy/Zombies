package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import com.google.inject.Inject;
import zombies.dto.reply.UserReply;
import zombies.entity.game.Deck;
import org.hibernate.Query;
import org.hibernate.Session;
import zombies.dto.builder.ReplyBuilder;
import zombies.entity.server.User;
import zombies.server.game.LobbyManager;
import zombies.server.game.UserInfo;
import zombies.entity.support.HibernateUtil;

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
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = ses.createQuery("select user from UserPlayer user where user.name=:name");
            query.setParameter("name", userAction.getName());
            Query deckQuery = ses.createQuery("select deckCards from Deck deck where deckCards.id=:id");
            deckQuery.setParameter("id", userAction.getSetDeckActiveAction().getDeckId());
            List<Deck> lst=deckQuery.list();
            if(lst.size()==0){
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("deck not found").build();
            }
            Deck d=lst.get(0);
            int i=d.getDeckCards().size();

            if (!query.list().isEmpty()) {
                User user = (User) query.list().get(0);
                UserInfo ui = lobbyManager.saveUser(userAction.getName());
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