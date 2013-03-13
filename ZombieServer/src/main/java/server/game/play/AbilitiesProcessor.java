package server.game.play;

import builder.TurnReplyBuilder;
import com.google.inject.Inject;
import game.Abilities;
import game.Card;
import game.Fraction;
import game.SubFraction;
import server.game.LobbyManager;
import server.game.play.GameManager;
import support.CardWrapper;

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

    private HashMap<Long,ability> abilities;
    Random random=new Random();


    @Inject
    public void init(LobbyManager manager){
        for(Fraction fr:manager.getFractions().values()){
            for(SubFraction sub:fr.getSubFractions()) {
               for(Card c:sub.getDeck()){
                   for(Abilities ab:c.getAbilities()){
                       if(!abilities.containsKey(ab.getId())){
                           initAbility(ab);
                       }
                   }
               }
            }
        }

    }
    private void initAbility(Abilities ability){
        String val=ability.getAction();
        String[] parts=val.split("=");

       ability ab=null;
        switch (parts[0]){
            case "direct":ab=new ability(type.direct_damage,Integer.valueOf(parts[1]));break;
            case "splash":ab=new ability(type.splash,Integer.valueOf(parts[1]));break;
            case "buffdamage":ab=new ability(type.buffdamage,Integer.valueOf(parts[1]));break;
            case "buffarmour":ab=new ability(type.buffarmour,Integer.valueOf(parts[1]));break;
            case "heal":ab=new ability(type.heal,Integer.valueOf(parts[1]));break;
            case "zombyfication":ab=new ability(type.zombyfication,Integer.valueOf(parts[1]));break;
            case "addres1":ab=new ability(type.addres1,Integer.valueOf(parts[1]));break;
            case "addres2":ab=new ability(type.addres2,Integer.valueOf(parts[1]));break;
            case "addres3":ab=new ability(type.addres3,Integer.valueOf(parts[1]));break;
            case "backstub":ab=new ability(type.backstub,0);break;
            case "miss":ab=new ability(type.miss,Integer.valueOf(parts[1]));break;
            case "evade":ab=new ability(type.evade,Integer.valueOf(parts[1]));break;
            case "nolimit":ab=new ability(type.nolimit,0);break;
        }
        abilities.put(ability.getId(),ab);

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
      return 0;
    }

    /**
     * если эта карта бьет не первого, а последнего в ячейке
     * @param cw
     * @return
     */
    public boolean isBackStabber(CardWrapper cw){
        return false;
    }


    public boolean Evade(CardWrapper cw){
        return false;
    }

    public boolean Miss(CardWrapper cw){
        return false;
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


    private class ability{
        //0-direct damage, 1-splash, 2-buff, 3-heal, 4-zombyfication, 5-addres1,6-addres2,7address3
        private type type;
        private int value;

        private ability(type type, int value) {
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
