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
    CONNECTION(15),
    USERINFO(20);

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
