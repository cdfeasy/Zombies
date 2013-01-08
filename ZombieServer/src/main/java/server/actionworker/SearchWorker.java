package server.actionworker;

import actions.Action;
import com.google.inject.Inject;
import reply.Reply;
import server.game.NewGameStarter;

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

    @Override
    public Reply processAction(Action action) throws Exception {
      //  action.
      //  action.getSearchAction();
        return null;
    }
}
