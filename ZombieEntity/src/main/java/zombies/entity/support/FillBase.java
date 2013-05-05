package zombies.entity.support;

import zombies.entity.game.*;
import org.hibernate.Session;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 03.02.13
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public class FillBase {
    public static void  createZombies(Session ses) throws IOException {
        try{
        Card slow = new Card(0l,"Медленный зомби", "Медленный зомби", 1, 10, 0, 4, CardTypeEnum.creature.getId(), 1, 0, 0,1);
        Card zombie = new Card(1l,"Зомби", "Обычный зомби", 1, 10, 0, 5, CardTypeEnum.creature.getId(), 2, 0, 0,1);
        Card fast = new Card(2l,"Быстрый зомби", "Быстрый зомби", 1, 5, 0, 4, CardTypeEnum.creature.getId(), 2, 0, 0,1);
        Card fat = new Card(3l,"Толстяк", "Толстяк, очень много жизней", 1, 30, 0, 6, CardTypeEnum.bigCreature.getId(), 4, 0, 0,2);
        Card half = new Card(4l,"Ползун", "Ползун, нападает через баррикады", 1, 10, 0, 1, CardTypeEnum.creature.getId(), 3, 0, 0,2);

        Card hospital = new Card(5l,"Больница", "Увеличивает приток зомби", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);
        Card aero = new Card(6l,"Аэропорт", "Увеличивает приток вируса", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);
        Card tec = new Card(7l,"Теплоэлектростанция", "Увеличивает приток энергии", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);


        Abilities nonAbility = new Abilities(0l,"Без способностей", "Без способностей", "");

        Abilities genereateCorpse = new Abilities(1l,"Увеличение притока тел", "Увеличение притока тел", "res1=2");
        Abilities genereateVirus = new Abilities(2l,"Увеличение притока вируса", "Увеличение притока вируса", "res2=2");
        Abilities genereateEnergy = new Abilities(3l,"Увеличение притока энергии", "Увеличение притока энергии", "res3=2");

        hospital.getAbilities().add(genereateCorpse);
        aero.getAbilities().add(genereateVirus);
        tec.getAbilities().add(genereateEnergy);

        Abilities ignoreBlock = new Abilities(4l,"Атакует за баррикадами", "Атакует за баррикадами", "backstub");
        Abilities meatmass = new Abilities(5l,"Без лимита на ячейку", "Без лимита на ячейку", "nolimit");
        Abilities unweldy = new Abilities(6l,"Промахивается каждый третий удар", "Промахивается каждый третий удар", "miss=30");
        Abilities evade = new Abilities(7l,"Игнорирует половину урона", "Игнорирует половину урона", "evade=50");

        slow.getAbilities().add(meatmass);
        slow.getAbilities().add(unweldy);
        half.getAbilities().add(ignoreBlock);
        fast.getAbilities().add(evade);


        SubFraction simpleSombies = new SubFraction("Простые зомби", "Простые зомби");
        simpleSombies.setLevel(1);
            simpleSombies.setRes1(2);
            simpleSombies.setRes2(0);
            simpleSombies.setRes3(0);
        Fraction zombies = new Fraction("Зомби", "Зомби");
        zombies.setId(1l);
        simpleSombies.setFraction(zombies);


        slow.setSubFraction(simpleSombies);
        zombie.setSubFraction(simpleSombies);
        fast.setSubFraction(simpleSombies);
        fat.setSubFraction(simpleSombies);
        half.setSubFraction(simpleSombies);
        hospital.setSubFraction(simpleSombies);
        aero.setSubFraction(simpleSombies);
        tec.setSubFraction(simpleSombies);

            ses.persist(nonAbility);
        ses.persist(genereateCorpse);
        ses.persist(genereateVirus);
        ses.persist(genereateEnergy);
        ses.persist(ignoreBlock);
        ses.persist(meatmass);
        ses.persist(unweldy);
        ses.persist(evade);

        ses.persist(slow);
        ses.persist(zombie);
        ses.persist(fast);
        ses.persist(fat);
        ses.persist(half);
        ses.persist(hospital);
        ses.persist(aero);
        ses.persist(tec);

        ses.persist(simpleSombies);
        ses.persist(zombies);
        } catch (Throwable th){
            th.printStackTrace();
        }


    }

    public static void createPeoples(Session ses) {
        try{
        Card peysan = new Card(1000l,"Горожанин", "Обычный горожанин с топором", 1, 10, 0, 4, CardTypeEnum.creature.getId(), 1, 0, 0,1);
        Card shotgun = new Card(1001l,"Горожанин с дробовиком", "горожанин с дробовиком", 1, 10, 0, 5, CardTypeEnum.creature.getId(), 2, 0, 0,1);
        Card medic = new Card(1002l,"Медик", "Медик", 1, 5, 0, 4, CardTypeEnum.creature.getId(), 2, 0, 0,1);
        Card molotov = new Card(1003l,"Горожанин с коктейлем молотова", "Горожанин с коктейлем молотова", 1, 30, 0, 6, CardTypeEnum.creature.getId(), 4, 0, 0,2);
        Card zombiebus = new Card(1004l,"Зомбибус", "Автобус модифицированный для зомби апокалипсиса", 1, 30, 0, 6, CardTypeEnum.transport.getId(), 4, 0, 0,2);
        Card barricade = new Card(1005l,"Баррикада", "Сколоченные доски, защищающие от зомби", 1, 30, 0, 6, CardTypeEnum.bigCreature.getId(), 4, 0, 0,1);

        Card napalm = new Card(1006l,"Напалм", "Авиационная бомбардировка, бьет всех врагов", 0, 0, 0, 0, CardTypeEnum.damageSpell.getId(), 0, 0, 2,1);
        Card armorBuff = new Card(1007l,"Военные запасы", "Найденные военные запасы, добавляет атаку и защиту", 0, 0, 0, 0, CardTypeEnum.buffSpell.getId(), 0, 0, 2,2);
        Card global = new Card(1008l,"Пополнение", "Добавляет вам в руку три карты, убирает три карты у противника", 0, 0, 0, 0, CardTypeEnum.buffSpell.getId(), 0, 0, 2,2);

        Card hospital = new Card(1009l,"Больница", "Увеличивает приток горожан", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);
        Card tech = new Card(1010l,"Автостоянка", "Увеличивает приток техники", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);
        Card market = new Card(1011l,"Оружиейны магазин", "Увеличивает приток оружия", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);

        Abilities genereatePeoples = new Abilities(10001l,"Увеличение притока людей", "Увеличение притока людей", "res1=2");
        Abilities genereateTech = new Abilities(10002l,"Увеличение притока техники", "Увеличение притока техники", "res2=2");
        Abilities genereateWeapon = new Abilities(10003l,"Увеличение притока оружия", "Увеличение притока оружия", "res3=2");

        hospital.getAbilities().add(genereatePeoples);
        tech.getAbilities().add(genereateTech);
        market.getAbilities().add(genereateWeapon);

        Abilities molotovCoctail = new Abilities(10004l,"Коктейл молотова", "Поджигает всех врагов", "splash=1");
        Abilities healAbility = new Abilities(10005l,"Лечение", "Лечит союзников", "heal=5");

        medic.getAbilities().add(healAbility);
        molotov.getAbilities().add(molotovCoctail);

        SubFraction simplePeoples = new SubFraction("Горожане", "Горожане");
        simplePeoples.setLevel(1);
            simplePeoples.setRes1(2);
            simplePeoples.setRes2(0);
            simplePeoples.setRes3(0);
        Fraction survival = new Fraction("Выжившие", "Выжившие");
        survival.setId(0l);
        simplePeoples.setFraction(survival);

        peysan.setSubFraction(simplePeoples);
        shotgun.setSubFraction(simplePeoples);
        medic.setSubFraction(simplePeoples);
        molotov.setSubFraction(simplePeoples);
        barricade.setSubFraction(simplePeoples);
        zombiebus.setSubFraction(simplePeoples);
        peysan.setSubFraction(simplePeoples);
        hospital.setSubFraction(simplePeoples);
        tech.setSubFraction(simplePeoples);
        market.setSubFraction(simplePeoples);

        ses.persist(genereatePeoples);
        ses.persist(genereateTech);
        ses.persist(genereateWeapon);
        ses.persist(molotovCoctail);
        ses.persist(healAbility);


        ses.persist(peysan);
        ses.persist(barricade);
        ses.persist(shotgun);
        ses.persist(medic);
        ses.persist(molotov);
        ses.persist(zombiebus);

        ses.persist(hospital);
        ses.persist(tech);
        ses.persist(market);

        ses.persist(napalm);
        ses.persist(armorBuff);
        ses.persist(global);

        ses.persist(simplePeoples);
        ses.persist(survival);
        } catch (Throwable th){
            th.printStackTrace();
        }
    }
}
