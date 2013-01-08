package server.actionworker;

import actions.Action;
import actions.ActionTypeEnum;
import com.google.inject.Inject;
import reply.Reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 08.01.13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class ActionManager implements IProcessor{

    @Inject
    ConnectionWorker connectionWorker;

    @Override
    public Reply processAction(Action action) throws Exception {
        ActionTypeEnum type= ActionTypeEnum.getValue(action.getAction());
        Reply rep=null;
        if(type==null)
            throw new Exception("неизвестный тип");
        switch (type){
            case CONNECT:
                rep= connectionWorker.processAction(action);
                break;
            case GETUSERINFO:break;
            case SEARCH:break;
            case TURN:break;
        }
        return rep;
    }
}
