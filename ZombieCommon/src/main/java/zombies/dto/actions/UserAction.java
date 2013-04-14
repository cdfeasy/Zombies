package zombies.dto.actions;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.12.12
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class UserAction {
    private int action;
    private String token;
    private String name;
    private ConnectAction connectAction;
    private UserInfoAction userInfo;
    private SearchAction searchAction;
    private TurnAction turnAction;
    private CardInfoAction cardInfoAction;
    private SetDeckActiveAction setDeckActiveAction;
    private  SaveDeckAction saveDeckAction;
    private  CreateUserAction createUserAction;

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
    public UserInfoAction getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoAction userInfo) {
        this.userInfo = userInfo;
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
    public CardInfoAction getCardInfoAction() {
        return cardInfoAction;
    }

    public void setCardInfoAction(CardInfoAction cardInfoAction) {
        this.cardInfoAction = cardInfoAction;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public SetDeckActiveAction getSetDeckActiveAction() {
        return setDeckActiveAction;
    }

    public void setSetDeckActiveAction(SetDeckActiveAction setDeckActiveAction) {
        this.setDeckActiveAction = setDeckActiveAction;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public SaveDeckAction getSaveDeckAction() {
        return saveDeckAction;
    }

    public void setSaveDeckAction(SaveDeckAction saveDeckAction) {
        this.saveDeckAction = saveDeckAction;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public CreateUserAction getCreateUserAction() {
        return createUserAction;
    }

    public void setCreateUserAction(CreateUserAction createUserAction) {
        this.createUserAction = createUserAction;
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
        mapper.generateJsonSchema(UserAction.class);
        UserAction act=new UserAction();
        act.setAction(ActionTypeEnum.CONNECT.id);
        ConnectAction ca=new ConnectAction();
        act.setName("ul");
        act.setConnectAction(ca);
       System.out.println(mapper.writeValueAsString(act))  ;

    }

    @Override
    public String toString() {
        return "UserAction{" +
                "action=" + action +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", turnAction=" + turnAction +
                '}';
    }
}

