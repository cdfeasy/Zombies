package server.actionworker;

import actions.Action;
import actions.ActionTypeEnum;
import com.google.inject.Inject;
import org.jboss.netty.channel.Channel;
import org.slf4j.LoggerFactory;
import reply.Reply;
import builder.ReplyBuilder;
import server.game.LobbyManager;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 08.01.13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class ActionManager implements IProcessor{
    org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());

    @Inject
    ConnectionWorker connectionWorker;
    @Inject
    UserInfoWorker userInfoWorker;
    @Inject
    SearchWorker searchWorker;
    @Inject
    StopSearchWorker stopSearchWorker;
    @Inject
    TurnWorker turnWorker;
    @Inject
    CardInfoWorker cardInfoWorker;
    @Inject
    CreateUserWorker createUserWorker;
    @Inject
    LobbyManager manager;


    public Reply processAction(Action action,Object... params) throws Exception {
        logger.debug("Start process action "+action);
        ActionTypeEnum type= ActionTypeEnum.getValue(action.getAction());
        if(type!=ActionTypeEnum.CONNECT && type!=ActionTypeEnum.CREATE_USER){
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
                rep= connectionWorker.processAction(action,params[0]);
                break;
            case GETUSERINFO:
                rep= userInfoWorker.processAction(action);
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
                rep= cardInfoWorker.processAction(action);
                break;
            case CREATE_USER:
                rep= createUserWorker.processAction(action);
                break;
        }
        return rep;
    }
}
