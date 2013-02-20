package ifree.zombieserver;

import actions.*;
import ifree.GameBot;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import reply.Reply;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 03.02.13
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public class ComplexGameTest extends TestBase {


    @Test
    public void ConnectionTest() throws InterruptedException, IOException {
        try {

            GameBot r1=new GameBot();
            r1.setUsername("user10");
            r1.setSide(1l);

            GameBot r2=new GameBot();
            r2.setUsername("user11");
            r2.setSide(0l);

            Thread t1=new Thread(r1);
            Thread t2=new Thread(r2);

            t1.start();
            t2.start();

            t1.join();
            t2.join();


        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
