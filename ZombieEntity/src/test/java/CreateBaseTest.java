import game.Abilities;
import game.Card;
import game.Fraction;
import game.Subfraction;
import org.junit.Test;
import org.hibernate.Query;
import org.hibernate.Session;
import server.HibernateUtil;
import server.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.12.12
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class CreateBaseTest {
    @Test
    public void create(){
        try{
        Session ses= HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
       // Query query = ses.createQuery("select card from Card card").setMaxResults(10);
        Card slow=new Card("Медленный зомби","Медленный зомби",1,10,0,4,false,1,0,0);
        Card zombie=new Card("Зомби","Обычный зомби",1,10,0,5,false,2,0,0);
        Card fast=new Card("Быстрый зомби","Быстрый зомби",1,5,0,4, false,2,0,0);
        Card fat=new Card("Толстяк","Толстяк, очень много жизней",1,30,0,6,false,4,0,0);
        Card half=new Card("Ползун","Ползун, нападает через баррикады",1,10,0,1,false,3,0,0);
        Card hospital=new Card("Больница","Увеличивает приток зомби",0,0,0,0,true,5,0,0);
        Card aero=new Card("Аэропорт","Увеличивает приток вируса",0,0,0,0,true,5,0,0);
        Card tec=new Card("Теплоэлектростанция","Увеличивает приток энергии",0,0,0,0,true,5,0,0);

        Abilities genereateCorpse=new Abilities("Увеличение притока тел","Увеличение притока тел","+2res1");
        Abilities genereateVirus=new Abilities("Увеличение притока вируса","Увеличение притока вируса","+2res2");
        Abilities genereateEnergy=new Abilities("Увеличение притока энергии","Увеличение притока энергии","+2res3");

        hospital.getAbilities().add(genereateCorpse) ;
        aero.getAbilities().add(genereateVirus) ;
        tec.getAbilities().add(genereateEnergy) ;

        Abilities ignoreBlock=new Abilities("Атакует за баррикадами","Атакует за баррикадами","reverse threat");
        Abilities meatmass=new Abilities("Без лимита на ячейку","Без лимита на ячейку","no limit");
        Abilities unweldy=new Abilities("Промахивается каждый третий удар","Промахивается каждый третий удар","miss30");
        Abilities evade=new Abilities("Игнорирует половину урона","Игнорирует половину урона","evade50");

        slow.getAbilities().add(meatmass);
        slow.getAbilities().add(unweldy);
        half.getAbilities().add(ignoreBlock);
        fast.getAbilities().add(evade);


        Subfraction simpleSombies=new Subfraction("Простые зомби","Простые зомби");
        Fraction zombies=new Fraction("Зомби","Зомби");
        simpleSombies.setFraction(zombies);

        simpleSombies.getDeck().add(slow) ;
        simpleSombies.getDeck().add(zombie) ;
        simpleSombies.getDeck().add(fast) ;
        simpleSombies.getDeck().add(fat) ;
        simpleSombies.getDeck().add(half) ;
        simpleSombies.getDeck().add(hospital) ;
        simpleSombies.getDeck().add(aero) ;
        simpleSombies.getDeck().add(tec) ;

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
        User u1=new User();
        u1.setLevel(1);
        u1.setXp(100);
        u1.setName("User1");
        u1.setPass("12345");

            User u2=new User();
            u2.setLevel(2);
            u2.setXp(200);
            u2.setName("User2");
            u2.setPass("123456");
            User u3=new User();
            u3.setLevel(3);
            u3.setXp(300);
            u3.setName("User3");
            u3.setPass("1234567");
            User u4=new User();
            u3.setLevel(4);
            u3.setXp(400);
            u3.setName("User4");
            u3.setPass("12345678");

            ses.persist(u1);
            ses.persist(u2);
            ses.persist(u3);
            ses.persist(u4);

        ses.getTransaction().commit();

        //List<Long> itemlist=query.list();
        ses.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
