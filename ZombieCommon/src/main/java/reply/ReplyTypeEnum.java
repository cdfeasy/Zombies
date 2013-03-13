package reply;

import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.12.12
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public enum ReplyTypeEnum {
    ERROR(0),
    TURN(10),
    CONNECTION(20),
    USERINFO(30),
    SEARCH(40),
    STOP_SEARCH(50),
    SAVE_DECK(60),
    SET_DECK_ACTIVE(70),
    GET_CARD_INFO(80),
    GAME_STARTED(90),
    ACHIEVEMENT(100);


    int id;

    public int getId() {
        return id;
    }

    ReplyTypeEnum(int id){
        this.id=id;
    }
    public static ReplyTypeEnum getValue(int i){
        for(ReplyTypeEnum e:ReplyTypeEnum.values()){
            if(e.getId()==i)
                return e;
        }
        return null;
    }
}
