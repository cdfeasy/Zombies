package actions;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.12.12
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public enum ActionTypeEnum {
    CONNECT(0),
    TURN(10),
    SEARCH(20),
    STOP_SEARCH(30),
    GETUSERINFO(40),
    SAVE_DECK(50),
    SET_DECK_ACTIVE(60),
    GET_CARD_INFO(70),
    CREATE_USER(80);


    int id;

    public int getId() {
        return id;
    }

    ActionTypeEnum(int id) {
        this.id = id;
    }

    public static ActionTypeEnum getValue(int i) {
        for (ActionTypeEnum e : ActionTypeEnum.values()) {
            if (e.getId() == i)
                return e;
        }
        return null;
    }

    public boolean isLong() {
        if (id == CONNECT.getId() ||
                id == GETUSERINFO.getId() ||
                id == SAVE_DECK.getId() ||
                id == SET_DECK_ACTIVE.getId()  ||
                id == GET_CARD_INFO.getId()  ||
                id == CREATE_USER.getId())
            return true;
        return false;
    }


}
