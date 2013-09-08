package zombies.server.actionworker;

import zombies.dto.actions.UserAction;
import zombies.dto.actions.ActionTypeEnum;
import com.google.inject.Inject;
import org.slf4j.LoggerFactory;
import zombies.dto.reply.UserReply;
import zombies.dto.builder.ReplyBuilder;
import zombies.server.game.LobbyManager;

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
    SaveDeckWorker saveDeckWorker;
    @Inject
    LobbyManager manager;


    public UserReply processAction(UserAction userAction,Object... params) throws Exception {
        logger.debug("Start process userAction "+ userAction);
        ActionTypeEnum type= ActionTypeEnum.getValue(userAction.getAction());
        if(type!=ActionTypeEnum.CONNECT && type!=ActionTypeEnum.CREATE_USER){
            if(manager.getUser(userAction.getName())==null){
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("user not connected").build();
            }
            if(!manager.getUser(userAction.getName()).getToken().equals(userAction.getToken())){
                return ReplyBuilder.getErrorReplyBuilder().setErrorText("invalide connection token").build();
            }
        }


        UserReply rep=null;
        if(type==null)
            throw new Exception("неизвестный тип");
        switch (type){
            case CONNECT:
                rep= connectionWorker.processAction(userAction,params[0]);
                break;
            case GETUSERINFO:
                rep= userInfoWorker.processAction(userAction);
                break;
            case SEARCH:
                rep=searchWorker.processAction(userAction);
                break;
            case STOP_SEARCH:
                rep=stopSearchWorker.processAction(userAction);
                break;
            case TURN:
                rep=turnWorker.processAction(userAction);
                break;
            case GET_CARD_INFO:
                rep= cardInfoWorker.processAction(userAction);
                break;
            case CREATE_USER:
                rep= createUserWorker.processAction(userAction);
                break;
            case SAVE_DECK:
                rep= saveDeckWorker.processAction(userAction);
                break;
        }
        return rep;
    }
}
