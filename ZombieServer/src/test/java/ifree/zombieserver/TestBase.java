package ifree.zombieserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import game.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import server.HibernateUtil;
import server.User;
import server.guice.ServerModule;
import server.netty.Server;
import support.FillBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 30.01.13
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class TestBase{
    static Server server;
    static Boolean isCreated=false;
    @BeforeClass
    public static void initBase(){
        if(!isCreated){
             create();
             isCreated=true;
        }
        try{
            int port=18080;
            Injector injector = Guice.createInjector(new ServerModule());

            server = injector.getInstance(Server.class);
            server.run();
        }catch (Exception ex){

        }


    }


    @AfterClass
    public static void stop(){
        try{
        server.stop();
        }catch (Exception ex){

        }

    }

    public static void create() {
        try {
            Session ses = HibernateUtil.getSessionFactory().openSession();
            ses.getTransaction().begin();
            // Query query = ses.createQuery("select card from Card card").setMaxResults(10);
            FillBase.createZombies(ses);
            FillBase.createPeoples(ses);

            User u1 = new User();
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
            u4.setLevel(4);
            u4.setXp(400);
            u4.setName("User4");
            u4.setPass("12345678");

            ses.persist(u1);
            ses.persist(u2);
            ses.persist(u3);
            ses.persist(u4);

            ses.getTransaction().commit();
            ses.getTransaction().begin();
            Query query = ses.createQuery("select fraction from Fraction fraction where name=\'Выжившие\'");
            List<Fraction> f=query.list();
            Fraction zmb=f.get(0);
            zmb.getSubFractions();
            Deck deck=new Deck();
            List<Card> av1=new ArrayList<Card>(zmb.getSubFractions().get(0).getDeck().size());
            av1.addAll(zmb.getSubFractions().get(0).getDeck());
            deck.setDeck(av1);
            ses.persist(deck);

            u1.getDecks().add(deck);
            u1.setActiveDeck(deck);
            List<Card> av=new ArrayList<Card>(zmb.getSubFractions().get(0).getDeck().size());
            av.addAll(zmb.getSubFractions().get(0).getDeck());
            u1.setAvailableCards(av);
            ses.merge(u1);
            ses.save(u1);

            ObjectMapper requestMapper = new ObjectMapper();
            requestMapper.generateJsonSchema(Fraction.class);
            String s=requestMapper.writeValueAsString(f.get(0));
            System.out.println(s);

            //List<Long> itemlist=query.list();
            ses.getTransaction().commit();
            ses.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//   @Test
//    public void empty(){
//
//   }
}