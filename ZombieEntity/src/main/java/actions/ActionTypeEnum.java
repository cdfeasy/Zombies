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
    SEARCH(15),
    GETUSERINFO(20);

    int id;

    public int getId() {
        return id;
    }

    ActionTypeEnum(int id){
        this.id=id;
    }
    public static ActionTypeEnum getValue(int i){
         for(ActionTypeEnum e:ActionTypeEnum.values()){
             if(e.getId()==i)
                 return e;
         }
        return null;
    }


}
