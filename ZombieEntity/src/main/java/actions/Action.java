package actions;

import org.codehaus.jackson.annotate.JsonWriteNullProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.12.12
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class Action {
    private int action;
    private String token;
    private String name;
    private ConnectAction connectAction;
    private GetUserInfoAction getUserInfo;
    private SearchAction searchAction;
    private TurnAction turnAction;
    private GetCardInfoAction getCardInfoAction;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public ConnectAction getConnectAction() {
        return connectAction;
    }

    public void setConnectAction(ConnectAction connectAction) {
        this.connectAction = connectAction;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public GetUserInfoAction getGetUserInfo() {
        return getUserInfo;
    }

    public void setGetUserInfo(GetUserInfoAction getUserInfo) {
        this.getUserInfo = getUserInfo;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public SearchAction getSearchAction() {
        return searchAction;
    }

    public void setSearchAction(SearchAction searchAction) {
        this.searchAction = searchAction;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public TurnAction getTurnAction() {
        return turnAction;
    }

    public void setTurnAction(TurnAction turnAction) {
        this.turnAction = turnAction;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public GetCardInfoAction getGetCardInfoAction() {
        return getCardInfoAction;
    }

    public void setGetCardInfoAction(GetCardInfoAction getCardInfoAction) {
        this.getCardInfoAction = getCardInfoAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.generateJsonSchema(Action.class);
        Action act=new Action();
        act.setAction(ActionTypeEnum.CONNECT.id);
        ConnectAction ca=new ConnectAction();
        act.setName("ul");
        act.setConnectAction(ca);
       System.out.println(mapper.writeValueAsString(act))  ;

    }
}
