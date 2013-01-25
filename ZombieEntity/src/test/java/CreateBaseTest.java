import game.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.hibernate.Query;
import org.hibernate.Session;
import server.HibernateUtil;
import server.User;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.12.12
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class CreateBaseTest {

    public void createZombies(Session ses) throws IOException {
        Card slow = new Card("Медленный зомби", "Медленный зомби", 1, 10, 0, 4, CardTypeEnum.creature.getId(), 1, 0, 0);
        Card zombie = new Card("Зомби", "Обычный зомби", 1, 10, 0, 5, CardTypeEnum.creature.getId(), 2, 0, 0);
        Card fast = new Card("Быстрый зомби", "Быстрый зомби", 1, 5, 0, 4, CardTypeEnum.creature.getId(), 2, 0, 0);
        Card fat = new Card("Толстяк", "Толстяк, очень много жизней", 1, 30, 0, 6, CardTypeEnum.bigCreature.getId(), 4, 0, 0);
        Card half = new Card("Ползун", "Ползун, нападает через баррикады", 1, 10, 0, 1, CardTypeEnum.creature.getId(), 3, 0, 0);

        Card hospital = new Card("Больница", "Увеличивает приток зомби", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0);
        Card aero = new Card("Аэропорт", "Увеличивает приток вируса", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0);
        Card tec = new Card("Теплоэлектростанция", "Увеличивает приток энергии", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0);

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
        Fraction zombies = new Fraction("Зомби", "Зомби");
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

    public void createPeoples(Session ses) {
        Card peysan = new Card("Горожанин", "Обычный горожанин с топором", 1, 10, 0, 4, CardTypeEnum.creature.getId(), 1, 0, 0);
        Card shotgun = new Card("Горожанин с дробовиком", "горожанин с дробовиком", 1, 10, 0, 5, CardTypeEnum.creature.getId(), 2, 0, 0);
        Card medic = new Card("Медик", "Медик", 1, 5, 0, 4, CardTypeEnum.creature.getId(), 2, 0, 0);
        Card molotov = new Card("Горожанин с коктейлем молотова", "Горожанин с коктейлем молотова", 1, 30, 0, 6, CardTypeEnum.creature.getId(), 4, 0, 0);
        Card zombiebus = new Card("Зомбибус", "Автобус модифицированный для зомби апокалипсиса", 1, 30, 0, 6, CardTypeEnum.transport.getId(), 4, 0, 0);
        Card barricade = new Card("Баррикада", "Сколоченные доски, защищающие от зомби", 1, 30, 0, 6, CardTypeEnum.bigCreature.getId(), 4, 0, 0);

        Card napalm = new Card("Напалм", "Авиационная бомбардировка, бьет всех врагов", 0, 0, 0, 0, CardTypeEnum.damageSpell.getId(), 0, 0, 2);
        Card armorBuff = new Card("Военные запасы", "Найденные военные запасы, добавляет атаку и защиту", 0, 0, 0, 0, CardTypeEnum.buffSpell.getId(), 0, 0, 2);
        Card global = new Card("Пополнение", "Добавляет вам в руку три карты, убирает три карты у противника", 0, 0, 0, 0, CardTypeEnum.buffSpell.getId(), 0, 0, 2);

        Card hospital = new Card("Больница", "Увеличивает приток горожан", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0);
        Card tech = new Card("Автостоянка", "Увеличивает приток техники", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0);
        Card market = new Card("Оружиейны магазин", "Увеличивает приток оружия", 0, 0, 0, 0, CardTypeEnum.structure.getId(), 5, 0, 0);

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
        Fraction survival = new Fraction("Выжившие", "Выжившие");
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

    @Test
    public void create() {
        try {
            Session ses = HibernateUtil.getSessionFactory().openSession();
            ses.getTransaction().begin();
            // Query query = ses.createQuery("select card from Card card").setMaxResults(10);
            createZombies(ses);
            createPeoples(ses);

            User u1 = new User();
            Deck d1=new Deck();
            u1.getDecks().add(d1);
            u1.setLevel(1);
            u1.setXp(100);
            u1.setName("User1");
            u1.setPass("12345");

            User u2 = new User();
            u2.setLevel(2);
            u2.setXp(200);
            u2.setName("User2");
            u2.setPass("123456");
            User u3 = new User();
            u3.setLevel(3);
            u3.setXp(300);
            u3.setName("User3");
            u3.setPass("1234567");
            User u4 = new User();
            u3.setLevel(4);
            u3.setXp(400);
            u3.setName("User4");
            u3.setPass("12345678");

            ses.persist(d1);
            ses.persist(u1);
            ses.persist(u2);
            ses.persist(u3);
            ses.persist(u4);

            ses.getTransaction().commit();
            Query query = ses.createQuery("select fraction from Fraction fraction where name=\'Выжившие\'");
            List<Fraction> f=query.list();
            Fraction zmb=f.get(0);
            zmb.getDeck();
            ObjectMapper requestMapper = new ObjectMapper();
            requestMapper.generateJsonSchema(Fraction.class);
            String s=requestMapper.writeValueAsString(f.get(0));
            System.out.println(s);

            //List<Long> itemlist=query.list();
            ses.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
