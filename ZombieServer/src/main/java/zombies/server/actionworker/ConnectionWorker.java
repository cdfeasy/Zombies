package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import com.google.inject.Inject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.netty.channel.Channel;
import zombies.dto.reply.UserReply;
import zombies.dto.builder.ReplyBuilder;
import zombies.entity.server.User;
import zombies.server.game.LobbyManager;
import zombies.server.game.UserInfo;
import zombies.entity.support.HibernateUtil;

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
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
        UserInfo uiCached = lobbyManager.saveUser(userAction.getName());
        if(uiCached.getUser()!=null){
            if (!uiCached.getUser().getPass().equals(userAction.getConnectAction().getPass())) {
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("incorrect password").build();
            }
            String token = UUID.randomUUID().toString().substring(0,10);
            uiCached.setToken(token);
            uiCached.setChannel((Channel) params[0]);
            return ReplyBuilder.getConnectionReplyBuilder().setToken(token).build();
        }


        Session ses = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = ses.createQuery("select user from UserPlayer user where user.name=:name");
            query.setParameter("name", userAction.getName());
            if (!query.list().isEmpty()) {
                User user = (User) query.list().get(0);
                if (!user.getPass().equals(userAction.getConnectAction().getPass())) {
                    return ReplyBuilder.getErrorReplyBuilder().setErrorText("incorrect password").build();
                }
                String token = UUID.randomUUID().toString().substring(0,10);
                UserInfo ui = lobbyManager.saveUser(userAction.getName());
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