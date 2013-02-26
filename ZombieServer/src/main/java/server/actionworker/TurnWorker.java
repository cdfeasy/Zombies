package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import reply.Reply;
import server.game.LobbyManager;
import server.game.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 08.01.13
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class TurnWorker implements IProcessor{
    @Inject
    LobbyManager lobby;

    @Override
    public Reply processAction(Action action, Object... params) throws Exception {
        UserInfo ui=lobby.getUser(action.getName());
        ui.getManager().addOrder(ui,action);
        return null;
    }
}
