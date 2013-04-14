package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import com.google.inject.Inject;
import zombies.entity.game.Fraction;
import zombies.dto.reply.UserReply;
import zombies.dto.builder.ReplyBuilder;
import zombies.server.game.LobbyManager;

import java.util.ArrayList;
import java.util.List;

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
    public UserReply processAction(UserAction userAction, Object... params) throws Exception {
        List<Fraction> list= new ArrayList<>(lobbyManager.getFractions().values());
        return ReplyBuilder.getGetCardInfoReplyBuilder().setFractions(list).build();
    }
}