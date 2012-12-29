package reply;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.12.12
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public class Reply {
    private int reply;
    private ConnectionReply connectionReply;
    private TurnReply turnReply;
    private UserInfoReply userInfoReply;
    private ErrorReply errorReply;

    public Reply(int reply) {
        this.reply = reply;
    }

    public Reply() {
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public ConnectionReply getConnectionReply() {
        return connectionReply;
    }

    public void setConnectionReply(ConnectionReply connectionReply) {
        this.connectionReply = connectionReply;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public TurnReply getTurnReply() {
        return turnReply;
    }

    public void setTurnReply(TurnReply turnReply) {
        this.turnReply = turnReply;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public UserInfoReply getUserInfoReply() {
        return userInfoReply;
    }

    public void setUserInfoReply(UserInfoReply userInfoReply) {
        this.userInfoReply = userInfoReply;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public ErrorReply getErrorReply() {
        return errorReply;
    }

    public void setErrorReply(ErrorReply errorReply) {
        this.errorReply = errorReply;
    }
}