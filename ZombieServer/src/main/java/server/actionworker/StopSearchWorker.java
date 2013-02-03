package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import reply.Reply;
import builder.ReplyBuilder;
import server.game.LobbyManager;
import server.game.NewGameStarter;
import server.game.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.01.13
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public class StopSearchWorker implements IProcessor{
    @Inject
    NewGameStarter searcher;

    @Inject
    LobbyManager lobby;

    @Override
    public Reply processAction(Action action) throws Exception {
        UserInfo ui=lobby.getUser(action.getName());
        searcher.dropPlayerFromQueue(ui);
        return ReplyBuilder.getStopSearchReplyBuilder().build();
    }
}