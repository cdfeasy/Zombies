package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import game.Deck;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.netty.channel.Channel;
import reply.Reply;
import builder.ReplyBuilder;
import server.User;
import server.game.LobbyManager;
import server.game.UserInfo;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 08.01.13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionWorker implements IProcessor {
    @Inject
    LobbyManager lobbyManager;

    @Override
    public Reply processAction(Action action, Object... params) throws Exception {
        Session ses = server.HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = ses.createQuery("select user from UserPlayer user where user.name=:name");
            query.setParameter("name", action.getName());
            if (!query.list().isEmpty()) {
                User user = (User) query.list().get(0);
                if (!user.getPass().equals(action.getConnectAction().getPass())) {
                    return ReplyBuilder.getErrorReplyBuilder().setErrorText("incorrect password").build();
                }
                String token = UUID.randomUUID().toString();
                UserInfo ui = lobbyManager.saveUser(action.getName());
                User us=user.CopyUser(false,false,true,lobbyManager.getCards());
                ui.setUser(us);
                ui.setToken(token);
                ui.setChannel((Channel) params[0]);
                return ReplyBuilder.getConnectionReplyBuilder().setToken(token).build();
            } else {
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("user not found").build();
            }

        } finally {
            ses.close();
        }
    }
}