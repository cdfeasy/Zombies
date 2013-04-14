package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import com.google.inject.Inject;
import zombies.dto.reply.UserReply;
import zombies.server.game.LobbyManager;
import zombies.server.game.UserInfo;

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
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
        UserInfo ui=lobby.getUser(userAction.getName());
        ui.getManager().addOrder(ui, userAction);
        return null;
    }
}
