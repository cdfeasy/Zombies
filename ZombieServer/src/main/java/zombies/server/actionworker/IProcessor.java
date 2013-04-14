package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import zombies.dto.reply.UserReply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 08.01.13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public interface IProcessor {
    UserReply processAction(UserAction userAction, Object... params) throws Exception;

}
