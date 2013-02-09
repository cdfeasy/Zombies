package reply;

import actions.ActionTypeEnum;
import actions.ConnectAction;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.Date;

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
    private SearchReply searchReply;
    private StopSearchReply stopSearchReply;
    private CardInfoReply cardInfoReply;
    private SuccessReply successReply;
    private SaveDeckReply saveDeckReply;
    private GameStartedReply gameStartedReply;

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

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public SearchReply getSearchReply() {
        return searchReply;
    }

    public void setSearchReply(SearchReply searchReply) {
        this.searchReply = searchReply;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public StopSearchReply getStopSearchReply() {
        return stopSearchReply;
    }

    public void setStopSearchReply(StopSearchReply stopSearchReply) {
        this.stopSearchReply = stopSearchReply;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public CardInfoReply getCardInfoReply() {
        return cardInfoReply;
    }

    public void setCardInfoReply(CardInfoReply cardInfoReply) {
        this.cardInfoReply = cardInfoReply;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public SuccessReply getSuccessReply() {
        return successReply;
    }

    public void setSuccessReply(SuccessReply successReply) {
        this.successReply = successReply;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public SaveDeckReply getSaveDeckReply() {
        return saveDeckReply;
    }

    public void setSaveDeckReply(SaveDeckReply saveDeckReply) {
        this.saveDeckReply = saveDeckReply;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public GameStartedReply getGameStartedReply() {
        return gameStartedReply;
    }

    public void setGameStartedReply(GameStartedReply gameStartedReply) {
        this.gameStartedReply = gameStartedReply;
    }


    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.generateJsonSchema(Reply.class);
        Date d1 = new Date();
        for (int i = 0; i < 10000; i++) {
            Reply act = new Reply();
            act.setReply(5);
            ConnectionReply cr = new ConnectionReply();
            cr.setToken("ul");
            cr.setVersion("1");
            act.setConnectionReply(cr);
            String s = mapper.writeValueAsString(act);
        }
//        System.out.println(mapper.writeValueAsString(act))  ;
        Date d2 = new Date();
        for (int i = 0; i < 10000; i++) {
            mapper.readValue("{\"reply\":5,\"connectionReply\":{\"token\":\"ul\",\"version\":\"1\"}}", Reply.class);
        }
        Date d3 = new Date();
        System.out.println(String.format("time1 [%s] time2 [%s]", Long.toString(d2.getTime() - d1.getTime()), Long.toString(d3.getTime() - d2.getTime())));

    }
}
