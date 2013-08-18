package zombies.server.game.play;

import zombies.dto.builder.TurnReplyBuilder;
import zombies.entity.support.CardWrapper;
import zombies.entity.support.GameInfo;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 16.03.13
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public class GameManagerSupport {

    /**
     * Обработка удара, учитываются 2 абилки- удар сзади, меняющий направляение удара и зомбификейшн делающий убитого зомби
     * @param turnReplyBuilder
     * @param p1cell
     * @param p2cell
     * @param info
     * @return
     */
    public static int processCell(TurnReplyBuilder turnReplyBuilder, SideCell p1cell, SideCell p2cell,GameInfo info,AbilitiesProcessor ability) {
        int sumDmg = 0;
        for (CardWrapper cr : p1cell.getCards()) {
            if(!cr.isActive()){
                continue;
            }
            //проверяем не промахнулся ли перс
            if(ability.Miss(cr)){
                continue;
            }

            boolean isTop= ability.isBackStabber(cr);
            //проверяем не увернулась ли цель
            if(ability.Evade(p2cell.getCard(isTop))){
                continue;
            }

            int damage = cr.resultDamage();
            int spend = 0;
            CardWrapper cw = null;
            while ((cw = p2cell.getCard(isTop)) != null && (spend = p2cell.hit(damage, cw)) > 0) {
                turnReplyBuilder.addActionInfo((int)cr.getWrapperId(), String.format("%s hit %s on %s damage", cr.getCard().getName(), cw.getCard().getName(), Integer.toString(spend)));
                damage = damage - spend;
                int z=ability.Zombification(cr);
                if(z>0){
                    cw.setVirus((byte)(cw.getVirus()+z));
                }
                if(cw.getHp()==0){
                    info.incKilled(cr.getCard().getId());
                }
            }
            sumDmg += damage;
            if(damage>0){
                turnReplyBuilder.addActionInfo((int)cr.getWrapperId(), String.format("%s hit player on %s damage", cr.getCard().getName(), Integer.toString(damage)));
            }
        }
        return sumDmg;
    }


    public static void processSplash(TurnReplyBuilder turnReplyBuilder, SideCell p1cell, SideCell p2cell,GameInfo info,AbilitiesProcessor ability) {
        for (CardWrapper cr : p1cell.getCards()) {
            if(!cr.isActive()){
                continue;
            }
            byte splash= ability.Splash(cr);
            if(splash>0)
            for(CardWrapper cw:p2cell.getAllLiveCard()){
                int spend = p2cell.hit(splash, cw);
                if(spend>0){
                    turnReplyBuilder.addActionInfo((int)cr.getWrapperId(), String.format("%s hit %s on %s damage", cr.getCard().getName(), cw.getCard().getName(), Integer.toString(spend)));
                }
                if(cw.getHp()==0){
                    info.incKilled(cr.getCard().getId());
                }
            }
        }
    }

    public static void processHeal(TurnReplyBuilder turnReplyBuilder, SideCell p1cell,GameInfo info,AbilitiesProcessor ability) {
        for (CardWrapper cr : p1cell.getCards()) {
            if(!cr.isActive()){
                continue;
            }
            byte heal= ability.Heal(cr);
            if(heal>0){
                CardWrapper cw=p1cell.getDamagedCard();
                if(cw!=null){
                    int hp=cw.getHp()+heal;
                    if(hp>cw.getCard().getHp()){
                        cw.setHp(cw.getCard().getHp());
                    } else{
                        cw.setHp(hp);
                    }
                    turnReplyBuilder.addActionInfo((int)cr.getWrapperId(), String.format("%s heal %s on %s", cr.getCard().getName(), cw.getCard().getName(), Integer.toString(heal)));
                }
            }
        }
    }

    public static void processRegeneration(TurnReplyBuilder turnReplyBuilder, SideCell p1cell,GameInfo info,AbilitiesProcessor ability) {
        for (CardWrapper cr : p1cell.getCards()) {
            if(!cr.isActive()){
                continue;
            }
            byte heal= ability.Regeneration(cr);
            if(heal>0){
                if(cr.getHp()<cr.getCard().getHp()){
                    cr.setHp(Math.min(cr.getHp()+heal,cr.getCard().getHp()));
                    turnReplyBuilder.addActionInfo((int)cr.getWrapperId(), String.format("%s regenerate %s", cr.getCard().getName(),Integer.toString(heal)));
                }
            }
        }
    }

}
