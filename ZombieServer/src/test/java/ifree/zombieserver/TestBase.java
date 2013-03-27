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
import server.History;
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
             ex.printStackTrace();
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
            Query querydel1= ses.createQuery("select h from History h");
            for(History u:(List<History>)querydel1.list()){
                ses.delete(u);
            }

            Query querydel5= ses.createQuery("select h from Deck h");
            for(Deck u:(List<Deck>)querydel5.list()){
                ses.delete(u);
            }

            Query querydel2= ses.createQuery("select up from UserPlayer up");
            for(User u:(List<User>)querydel2.list()){
                ses.delete(u);
            }


            Query querydel3 = ses.createQuery("select fraction from Fraction fraction");
            List<Fraction> fdel1=querydel3.list();
            for(Fraction fl:fdel1){
                for(SubFraction s:fl.getSubFractions()){
                    for(Card c:s.getDeck()) {
                        ses.delete(c);
                    }
                    ses.delete(s);
                }
                ses.delete(fl);
            }
            Query querydel4 = ses.createQuery("select ab from Abilities ab");
            List<Abilities> ab=querydel4.list();
            for(Abilities u:ab){
                ses.delete(u);
            }



            ses.getTransaction().commit();
            ses.close();
            ses = HibernateUtil.getSessionFactory().openSession();

            ses.getTransaction().begin();
            // Query query = ses.createQuery("select card from Card card").setMaxResults(10);
            FillBase.createZombies(ses);
            FillBase.createPeoples(ses);
            ses.getTransaction().commit();
            ses.getTransaction().begin();
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


            Query query1 = ses.createQuery("select fraction from Fraction fraction where fraction.id=1");
            List<Fraction> f1=query1.list();
            Fraction zmb1=f.get(0);
            zmb1.getSubFractions();
            Deck deck1=new Deck();
            List<Card> av2=new ArrayList<Card>(zmb1.getSubFractions().get(0).getDeck().size());
            av2.addAll(zmb1.getSubFractions().get(0).getDeck());
            deck1.setDeck(av1);
            ses.persist(deck1);

            u2.getDecks().add(deck1);
            u2.setActiveDeck(deck1);
            List<Card> av3=new ArrayList<Card>(zmb1.getSubFractions().get(0).getDeck().size());
            av3.addAll(zmb1.getSubFractions().get(0).getDeck());
            u2.setAvailableCards(av3);
            ses.merge(u2);
            ses.save(u2);

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
