package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import org.hibernate.Query;
import org.hibernate.Session;
import reply.Reply;
import reply.ReplyBuilder;
import server.User;
import server.base.HibernateUtil;
import server.game.LobbyManager;
import server.game.NewGameStarter;
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
    public Reply processAction(Action action) throws Exception {
        Session ses= server.HibernateUtil.getSessionFactory().openSession();
        try{
        Query query = ses.createQuery("select user from UserPlayer user where user.name=:name");
        query.setParameter("name", action.getName());
        if(!query.list().isEmpty()){
            User user= (User) query.list().get(0);
            if(!user.getPass().equals(action.getConnectAction().getPass())){
                return ReplyBuilder.getErrorReplyBuilderBuilder().setErrorText("incorrect password").build();
            }
            String token= UUID.randomUUID().toString();
            UserInfo ui=lobbyManager.saveUser(action.getName());
            ui.setUser(user);
            ui.setToken(token);
            return ReplyBuilder.getConnectionReplyBuilder().setToken(token).build();
        }else{
            return ReplyBuilder.getErrorReplyBuilderBuilder().setErrorText("user not found").build();
        }

        }finally {
            ses.close();
        }
    }
}