package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import com.google.inject.Inject;
import zombies.dto.builder.ReplyBuilder;
import zombies.dto.reply.UserReply;
import zombies.server.game.LobbyManager;
import zombies.server.game.NewGameStarter;
import zombies.server.game.UserInfo;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 08.01.13
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class SearchWorker implements IProcessor{
    @Inject
    NewGameStarter searcher;

    @Inject
    LobbyManager lobby;

    @Override
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
        UserInfo ui=lobby.getUser(userAction.getName());
        if(!searcher.registerPlayerInQueue(ui)){
            try {
                ui.getManager().resendGameInfo(ui);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return ReplyBuilder.getSearchReplyBuilder().build();
    }
}
