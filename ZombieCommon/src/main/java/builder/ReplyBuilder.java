package builder;

import game.Fraction;
import server.User;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class ReplyBuilder {

    public static ConnectionReplyBuilder getConnectionReplyBuilder(){
        return new ConnectionReplyBuilder();
    }
    public static ErrorReplyBuilder getErrorReplyBuilder(){
        return new ErrorReplyBuilder();
    }
    public static SearchReplyBuilder getSearchReplyBuilder(){
        return new SearchReplyBuilder();
    }
    public static StopSearchReplyBuilder getStopSearchReplyBuilder(){
        return new StopSearchReplyBuilder();
    }

    public static GetCardInfoReplyBuilder getGetCardInfoReplyBuilder(){
        return new GetCardInfoReplyBuilder();
    }

    public static UserInfoReplyBuilder getUserInfoReplyBuilder(){
        return new UserInfoReplyBuilder();
    }

    public static SetDeckActiveBuilder getSetDeckActiveBuilder(){
        return new SetDeckActiveBuilder();
    }

    public static SaveDeckReplyBuilder getSaveDeckReplyBuilder(){
        return new SaveDeckReplyBuilder();
    }

    public static SuccessReplyBuilder getSuccessReplyBuilder(){
        return new SuccessReplyBuilder();
    }
}
