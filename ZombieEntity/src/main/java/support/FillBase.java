package support;

import game.*;
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
        Card slow = new Card("Медленный зомби", "Медленный зомби", 1, 10, 0, 4, CardTypeEnum.creature.getId(), 1, 0, 0,1);
        Card zombie = new Card("Зомби", "Обычный зомби", 1, 10, 0, 5, CardTypeEnum.creature.getId(), 2, 0, 0,1);
        Card fast = new Card("Быстрый зомби", "Быстрый зомби", 1, 5, 0, 4, CardTypeEnum.creature.getId(), 2, 0, 0,1);
        Card fat = new Card("Толстяк", "Толстяк, очень много жизней", 1, 30, 0, 6, CardTypeEnum.bigCreature.getId(), 4, 0, 0,2);
        Card half = new Card("Ползун", "Ползун, нападает через баррикады", 1, 10, 0, 1, CardTypeEnum.creature.getId(), 3, 0, 0,2);

        Card hospital = new Card("Больница", "Увеличивает приток зомби", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);
        Card aero = new Card("Аэропорт", "Увеличивает приток вируса", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);
        Card tec = new Card("Теплоэлектростанция", "Увеличивает приток энергии", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);

        Abilities genereateCorpse = new Abilities("Увеличение притока тел", "Увеличение притока тел", "+2res1");
        Abilities genereateVirus = new Abilities("Увеличение притока вируса", "Увеличение притока вируса", "+2res2");
        Abilities genereateEnergy = new Abilities("Увеличение притока энергии", "Увеличение притока энергии", "+2res3");

        hospital.getAbilities().add(genereateCorpse);
        aero.getAbilities().add(genereateVirus);
        tec.getAbilities().add(genereateEnergy);

        Abilities ignoreBlock = new Abilities("Атакует за баррикадами", "Атакует за баррикадами", "reverse threat");
        Abilities meatmass = new Abilities("Без лимита на ячейку", "Без лимита на ячейку", "no limit");
        Abilities unweldy = new Abilities("Промахивается каждый третий удар", "Промахивается каждый третий удар", "miss30");
        Abilities evade = new Abilities("Игнорирует половину урона", "Игнорирует половину урона", "evade50");

        slow.getAbilities().add(meatmass);
        slow.getAbilities().add(unweldy);
        half.getAbilities().add(ignoreBlock);
        fast.getAbilities().add(evade);


        Subfraction simpleSombies = new Subfraction("Простые зомби", "Простые зомби");
        simpleSombies.setLevel(1);
        Fraction zombies = new Fraction("Зомби", "Зомби");
        zombies.setId(1l);
        simpleSombies.setFraction(zombies);


        slow.setSubfraction(simpleSombies);
        zombie.setSubfraction(simpleSombies);
        fast.setSubfraction(simpleSombies);
        fat.setSubfraction(simpleSombies);
        half.setSubfraction(simpleSombies);
        hospital.setSubfraction(simpleSombies);
        aero.setSubfraction(simpleSombies);
        tec.setSubfraction(simpleSombies);

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


    }

    public static void createPeoples(Session ses) {
        Card peysan = new Card("Горожанин", "Обычный горожанин с топором", 1, 10, 0, 4, CardTypeEnum.creature.getId(), 1, 0, 0,1);
        Card shotgun = new Card("Горожанин с дробовиком", "горожанин с дробовиком", 1, 10, 0, 5, CardTypeEnum.creature.getId(), 2, 0, 0,1);
        Card medic = new Card("Медик", "Медик", 1, 5, 0, 4, CardTypeEnum.creature.getId(), 2, 0, 0,1);
        Card molotov = new Card("Горожанин с коктейлем молотова", "Горожанин с коктейлем молотова", 1, 30, 0, 6, CardTypeEnum.creature.getId(), 4, 0, 0,2);
        Card zombiebus = new Card("Зомбибус", "Автобус модифицированный для зомби апокалипсиса", 1, 30, 0, 6, CardTypeEnum.transport.getId(), 4, 0, 0,2);
        Card barricade = new Card("Баррикада", "Сколоченные доски, защищающие от зомби", 1, 30, 0, 6, CardTypeEnum.bigCreature.getId(), 4, 0, 0,1);

        Card napalm = new Card("Напалм", "Авиационная бомбардировка, бьет всех врагов", 0, 0, 0, 0, CardTypeEnum.damageSpell.getId(), 0, 0, 2,1);
        Card armorBuff = new Card("Военные запасы", "Найденные военные запасы, добавляет атаку и защиту", 0, 0, 0, 0, CardTypeEnum.buffSpell.getId(), 0, 0, 2,2);
        Card global = new Card("Пополнение", "Добавляет вам в руку три карты, убирает три карты у противника", 0, 0, 0, 0, CardTypeEnum.buffSpell.getId(), 0, 0, 2,2);

        Card hospital = new Card("Больница", "Увеличивает приток горожан", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);
        Card tech = new Card("Автостоянка", "Увеличивает приток техники", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);
        Card market = new Card("Оружиейны магазин", "Увеличивает приток оружия", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0,1);

        Abilities genereatePeoples = new Abilities("Увеличение притока людей", "Увеличение притока людей", "+2res1");
        Abilities genereateTech = new Abilities("Увеличение притока техники", "Увеличение притока техники", "+2res2");
        Abilities genereateWeapon = new Abilities("Увеличение притока оружия", "Увеличение притока оружия", "+2res3");

        hospital.getAbilities().add(genereatePeoples);
        tech.getAbilities().add(genereateTech);
        market.getAbilities().add(genereateWeapon);

        Abilities molotovCoctail = new Abilities("Коктейл молотова", "Поджигает всех врагов", "splash=1");
        Abilities healAbility = new Abilities("Лечение", "Лечит союзников", "heal=5");
        Abilities bunkerBus = new Abilities("Транспорт", "Пока не уничтожен никто не получает урона", "transport");

        medic.getAbilities().add(healAbility);
        zombiebus.getAbilities().add(bunkerBus);
        molotov.getAbilities().add(molotovCoctail);

        Subfraction simplePeoples = new Subfraction("Горожане", "Горожане");
        simplePeoples.setLevel(1);
        Fraction survival = new Fraction("Выжившие", "Выжившие");
        survival.setId(0l);
        simplePeoples.setFraction(survival);

        peysan.setSubfraction(simplePeoples);
        shotgun.setSubfraction(simplePeoples);
        medic.setSubfraction(simplePeoples);
        molotov.setSubfraction(simplePeoples);
        barricade.setSubfraction(simplePeoples);
        zombiebus.setSubfraction(simplePeoples);
        peysan.setSubfraction(simplePeoples);
        hospital.setSubfraction(simplePeoples);
        tech.setSubfraction(simplePeoples);
        market.setSubfraction(simplePeoples);

        ses.persist(genereatePeoples);
        ses.persist(genereateTech);
        ses.persist(genereateWeapon);
        ses.persist(molotovCoctail);
        ses.persist(healAbility);
        ses.persist(bunkerBus);


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
    }
}
