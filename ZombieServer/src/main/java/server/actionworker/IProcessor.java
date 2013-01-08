package server.actionworker;

import actions.Action;
import reply.Reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 08.01.13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public interface IProcessor {
    Reply processAction(Action action) throws Exception;
}
