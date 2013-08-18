import zombies.entity.game.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.hibernate.Query;
import org.hibernate.Session;
import zombies.entity.server.FriendList;
import zombies.entity.support.HibernateUtil;
import zombies.entity.server.User;
import zombies.entity.support.FillBase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    public void createFromFile() throws IOException {
        InputStream ios=this.getClass().getResourceAsStream("GameCards.txt");
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
        FillBase.LoadBaseFromString(ios,ses);
        ses.getTransaction().commit();
        ses.close();
        Session ses1 = HibernateUtil.getSessionFactory().openSession();
        ses1.getTransaction().begin();
        System.out.println(FillBase.SaveBaseToString(ses1));
        ses1.getTransaction().commit();
        ses1.close();
    }

   // @Test
    public void create() throws IOException {
        try {
            Session ses = HibernateUtil.getSessionFactory().openSession();
            ses.getTransaction().begin();
            // Query query = ses.createQuery("select card from Card card").setMaxResults(10);
            FillBase.createZombies(ses);
            FillBase.createPeoples(ses);
            ses.getTransaction().commit();
            ses.close();
            ses = HibernateUtil.getSessionFactory().openSession();
            ses.getTransaction().begin();

            User u1 = new User();
            u1.setLevel(1);
            u1.setXp(100);
            u1.setName("User1");
            u1.setPass("12345");
            u1.setSide(0l);

            final User u2 = new User();
            u2.setLevel(2);
            u2.setXp(200);
            u2.setName("User2");
            u2.setPass("123456");
            u2.setSide(1l);
            User u3 = new User();
            u3.setLevel(3);
            u3.setXp(300);
            u3.setName("User3");
            u3.setPass("1234567");
            u3.setSide(0l);
            User u4 = new User();
            u4.setLevel(4);
            u4.setXp(400);
            u4.setName("User4");
            u4.setPass("12345678");
            u4.setSide(1l);

            ses.persist(u1);
            ses.persist(u2);
            ses.persist(u3);
            ses.persist(u4);
            FriendList f1=new FriendList();
            f1.setFriends(new ArrayList<Long>(){{add(u2.getId());}});
            u1.setFriendList(f1);
            ses.merge(u1) ;

            ses.getTransaction().commit();
            ses.getTransaction().begin();
           addDeck(u1,ses);
            addDeck(u2,ses);
            addDeck(u3,ses);
            addDeck(u4,ses);

            ObjectMapper requestMapper = new ObjectMapper();
            requestMapper.generateJsonSchema(Fraction.class);
            //String s=requestMapper.writeValueAsString(f.get(0));
           // System.out.println(s);

            //List<Long> itemlist=query.list();
            ses.getTransaction().commit();
            ses.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
        System.out.println(FillBase.SaveBaseToString(ses));
        ses.getTransaction().commit();
        ses.close();
    }
    private void addDeck(User u,Session ses){
        Query query;
        if(u.getSide()==0)   {
             query = ses.createQuery("select fraction from Fraction fraction where fraction.id=0");
        }  else{
            query = ses.createQuery("select fraction from Fraction fraction where fraction.id=1");
        }
        List<Fraction> f=query.list();
        List<Card> av1=new ArrayList<Card>();
        for(SubFraction sub:f.get(0).getSubFractions() ){
            if(sub.getLevel()==1){
                for(Card c:sub.getDeck()){
                    if(c.getCardLevel()==1){
                        av1.add(c);
                    }
                }
            }
        }


        Fraction zmb=f.get(0);
        zmb.getSubFractions();
        Deck deck=new Deck();
        deck.setDeckCards(av1);
        ses.persist(deck);

        u.getDecks().add(deck);
        u.setActiveDeck(deck);
        u.setAvailableCards(av1);
        ses.merge(u);
      //  ses.save(u);
    }
}
