package ifree.zombieserver;

import ifree.GameBot;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import zombies.entity.server.DetailStatistic;
import zombies.entity.support.HibernateUtil;
import zombies.entity.server.History;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 03.02.13
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public class ComplexGameTest extends TestBase {
    private int cnt=2;

    @Test
    public void ConnectionTest() throws InterruptedException, IOException {
        startBots(true);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
//        startBots(false);
    }

    private void startBots(boolean needCreate) {
        try {
            Thread[] t=new Thread[cnt];
            for(int i=0;i<cnt;i++)    {
                GameBot r1=new GameBot();
                r1.setUsername("user10"+Integer.toString(i));
                r1.setSide((long)(i%2));
                r1.setNeedCreate(needCreate);
                t[i]=new Thread(r1);
            }

            for(int i=0;i<cnt;i++){
                t[i].start();
            }
            for(int i=0;i<cnt;i++){
                t[i].join();
            }
            Thread.sleep(1000);

            Session ses = HibernateUtil.getSessionFactory().openSession();

           Query querydel1= ses.createQuery("select h from History h");
            for(History u:(List<History>)querydel1.list()){
                System.out.println(u);
            }

            Query querydel2= ses.createQuery("select h from DetailStatistic h");
            for(DetailStatistic u:(List<DetailStatistic>)querydel2.list()){
                System.out.println(u);
            }
            ses.close();

        } catch (Throwable th) {
            th.printStackTrace();
        }
    }


}
