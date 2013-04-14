package zombies.server.game.play;

import zombies.dto.builder.TurnReplyBuilder;
import com.google.inject.Inject;
import zombies.entity.game.Abilities;
import zombies.entity.game.Card;
import zombies.entity.game.Fraction;
import zombies.entity.game.SubFraction;
import zombies.server.game.LobbyManager;
import zombies.entity.support.CardWrapper;

import java.util.HashMap;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 10.03.13
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public class AbilitiesProcessor {

    private Random random=new Random();
    private HashMap<type,HashMap<Long,Integer>> abilities=new HashMap<>();
    @Inject
    private LobbyManager manager;


   // @Inject
    public void init(){
        for(type t:type.values()){
            abilities.put(t,new HashMap<Long,Integer>());
        }
        for(Fraction fr:manager.getFractions().values()){
            for(SubFraction sub:fr.getSubFractions()) {
               for(Card c:sub.getDeck()){
                   for(Abilities ab:c.getAbilities()){
                         initAbility(c,ab);
                   }
                   initAbility(c,c.getUniqueAbility());
               }
            }
        }




    }
    private void initAbility(Card c,Abilities ability){
        if(ability==null)
            return;
        String val=ability.getAction();
        String[] parts=val.split("=");

       tmpAbility ab=null;
        switch (parts[0]){
            case "direct":ab=new tmpAbility(type.direct_damage,Integer.valueOf(parts[1]));break;
            case "splash":ab=new tmpAbility(type.splash,Integer.valueOf(parts[1]));break;
            case "buffdamage":ab=new tmpAbility(type.buffdamage,Integer.valueOf(parts[1]));break;
            case "buffarmour":ab=new tmpAbility(type.buffarmour,Integer.valueOf(parts[1]));break;
            case "heal":ab=new tmpAbility(type.heal,Integer.valueOf(parts[1]));break;
            case "zombyfication":ab=new tmpAbility(type.zombyfication,Integer.valueOf(parts[1]));break;
            case "addres1":ab=new tmpAbility(type.addres1,Integer.valueOf(parts[1]));break;
            case "addres2":ab=new tmpAbility(type.addres2,Integer.valueOf(parts[1]));break;
            case "addres3":ab=new tmpAbility(type.addres3,Integer.valueOf(parts[1]));break;
            case "backstub":ab=new tmpAbility(type.backstub,0);break;
            case "miss":ab=new tmpAbility(type.miss,Integer.valueOf(parts[1]));break;
            case "evade":ab=new tmpAbility(type.evade,Integer.valueOf(parts[1]));break;
            case "nolimit":ab=new tmpAbility(type.nolimit,0);break;
        }
        if(ab!=null)
          abilities.get(ab.getType()).put(c.getId(),ab.getValue());
    }


    public void activatePreBattleAbilities(TurnReplyBuilder turnReplyBuilder,GameTable table){
        for (int i = 0; i < TableSide.CELL_COUNT; i++) {
            SideCell p1cell = table.getPlayer1Side().getCell(i);
            SideCell p2cell = table.getPlayer2Side().getCell(i);
        }


    }
    public void activatePostBattleAbilities(TurnReplyBuilder turnReplyBuilder,GameTable table){

    }


    /**
     * если враги убитые этой картой становятся зомби
     * @param cw
     * @return
     */
    public byte Zombification(CardWrapper cw){
        if(cw==null)
            return 0;
        Integer val=abilities.get(type.zombyfication).get(cw.getCard().getId());
        if(val==null)
            return 0;
      return val.byteValue();
    }

    public byte Heal(CardWrapper cw){
        if(cw==null)
            return 0;
        Integer val=abilities.get(type.heal).get(cw.getCard().getId());
        if(val==null)
            return 0;
        return val.byteValue();
    }

    public byte Splash(CardWrapper cw){
        if(cw==null)
            return 0;
        Integer val=abilities.get(type.splash).get(cw.getCard().getId());
        if(val==null)
            return 0;
        return val.byteValue();
    }

    /**
     * если эта карта бьет не первого, а последнего в ячейке
     * @param cw
     * @return
     */
    public boolean isBackStabber(CardWrapper cw){
        if(cw==null)
            return false;
        return abilities.get(type.backstub).containsKey(cw.getCard().getId());
    }


    public boolean Evade(CardWrapper cw){
        if(cw==null)
            return false;
        Integer val=abilities.get(type.evade).get(cw.getCard().getId());
        if(val==null)
            return false;
        return random.nextInt(100)<val;
    }

    public boolean Miss(CardWrapper cw){
        if(cw==null)
            return false;
        Integer val=abilities.get(type.miss).get(cw.getCard().getId());
        if(val==null)
            return false;
        return random.nextInt(100)<val;
    }

     private enum type{
         direct_damage,
         splash,
         buffdamage,
         buffarmour,
         heal,
         zombyfication,
         addres1,
         addres2,
         addres3,
         backstub,
         miss,
         evade,
         nolimit;
     }


    private class tmpAbility {
        //0-direct damage, 1-splash, 2-buff, 3-heal, 4-zombyfication, 5-addres1,6-addres2,7address3
        private type type;
        private int value;

        private tmpAbility(type type, int value) {
            this.type = type;
            this.value = value;
        }

        public type getType() {
            return type;
        }

        public int getValue() {
            return value;
        }
    }
}
