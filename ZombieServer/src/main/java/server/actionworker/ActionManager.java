package server.actionworker;

import actions.Action;
import actions.ActionTypeEnum;
import com.google.inject.Inject;
import reply.Reply;
import reply.ReplyBuilder;
import server.game.LobbyManager;

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
    @Inject
    GetUserInfoWorker getUserInfoWorker;
    @Inject
    SearchWorker searchWorker;
    @Inject
    StopSearchWorker stopSearchWorker;
    @Inject
    TurnWorker turnWorker;
    @Inject
    GetCardInfoWorker getCardInfoWorker;

    @Inject
    LobbyManager manager;

    @Override
    public Reply processAction(Action action) throws Exception {
        ActionTypeEnum type= ActionTypeEnum.getValue(action.getAction());
        if(type!=ActionTypeEnum.CONNECT){
            if(manager.getUser(action.getName())==null){
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("user not connected").build();
            }
            if(!manager.getUser(action.getName()).getToken().equals(action.getToken())){
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("invalide connection token").build();
            }
        }


        Reply rep=null;
        if(type==null)
            throw new Exception("неизвестный тип");
        switch (type){
            case CONNECT:
                rep= connectionWorker.processAction(action);
                break;
            case GETUSERINFO:
                rep= getUserInfoWorker.processAction(action);
                break;
            case SEARCH:
                rep=searchWorker.processAction(action);
                break;
            case STOP_SEARCH:
                rep=stopSearchWorker.processAction(action);
                break;
            case TURN:
                rep=turnWorker.processAction(action);
                break;
            case GET_CARD_INFO:
                rep=getCardInfoWorker.processAction(action);
                break;
        }
        return rep;
    }
}
