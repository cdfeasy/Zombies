package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import game.Card;
import game.Fraction;
import game.SubFraction;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import reply.Reply;
import builder.ReplyBuilder;
import server.game.LobbyManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 20.01.13
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
public class CardInfoWorker implements IProcessor {
    @Inject
    LobbyManager lobbyManager;

    @Override
    public Reply processAction(Action action, Object... params) throws Exception {
        List<Fraction> list= new ArrayList<>(lobbyManager.getFractions().values());
        return ReplyBuilder.getGetCardInfoReplyBuilder().setFractions(list).build();
    }
}