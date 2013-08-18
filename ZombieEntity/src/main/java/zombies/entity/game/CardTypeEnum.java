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
    support(2),
    general(3),
    bigCreature(4),
    mechanical(5),
    transport(6),
    structure(7),
    damageSpell(8),
    buffSpell(9),
    globalSpell(10);

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
    public static CardTypeEnum getValue(String value){
        for(CardTypeEnum e:CardTypeEnum.values()){
            if(e.name().equals(value))
                return e;
        }
        return null;
    }
}
