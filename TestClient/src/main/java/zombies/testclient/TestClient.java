package zombies.testclient;

import org.codehaus.jackson.map.JsonMappingException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 18.08.13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class TestClient {

    public static final int cnt=2;

    public static void main(String[] args) throws JsonMappingException, InterruptedException {
        Thread[] t=new Thread[cnt];
        for(int i=0;i< cnt;i++)    {
            GameBot r1=new GameBot("user10"+Integer.toString(i),(long)(i%2),false);
            t[i]=new Thread(r1);
        }

        for(int i=0;i< cnt;i++){
            t[i].start();
        }
        for(int i=0;i< cnt;i++){
            t[i].join();
        }
        System.out.println("total end");

    }
}
