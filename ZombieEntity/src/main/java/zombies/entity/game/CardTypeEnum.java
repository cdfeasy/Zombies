package zombies.entity.game;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.01.13
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public enum CardTypeEnum {
    creature(0),
    bigCreature(1),
    vehicle(2),
    transport(3),
    structure(4),
    damageSpell(5),
    buffSpell(6),
    globalSpell(7);

    int id;

    public int getId() {
        return id;
    }

    CardTypeEnum(int id){
        this.id=id;
    }
    public static CardTypeEnum getValue(int i){
        for(CardTypeEnum e:CardTypeEnum.values()){
            if(e.getId()==i)
                return e;
        }
        return null;
    }
}
