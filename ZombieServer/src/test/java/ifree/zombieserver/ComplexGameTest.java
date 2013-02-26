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
    private int cnt=2;

    @Test
    public void ConnectionTest() throws InterruptedException, IOException {
        try {
            Thread[] t=new Thread[cnt];
            for(int i=0;i<cnt;i++)    {
                GameBot r1=new GameBot();
                r1.setUsername("user10"+Integer.toString(i));
                r1.setSide((long)(i%2));
                t[i]=new Thread(r1);
            }

            for(int i=0;i<cnt;i++){
                t[i].start();
            }
            for(int i=0;i<cnt;i++){
                t[i].join();
            }

        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
