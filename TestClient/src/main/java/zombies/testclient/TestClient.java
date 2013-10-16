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

    public static final int cnt=40;

    public static void main(String[] args) throws JsonMappingException, InterruptedException {
        int totalgames=cnt*100;
        Thread[] t=new Thread[cnt];
        GameBot[] r=new GameBot[cnt];
        for(int i=0;i< cnt;i++)    {
            GameBot r1=new GameBot("user10"+Integer.toString(i),(long)(i%2),false);
            r[i]=r1;
            t[i]=new Thread(r1);
        }

        for(int i=0;i< cnt;i++){
            t[i].start();
        }
        while (totalgames>0)
        for(int i=0;i< cnt;i++){
            if(!t[i].isAlive()){
                if(totalgames-->=0){
                    t[i]=new Thread(r[i]);
                    r[i].restart();
                    t[i].start();
                }
                Thread.sleep(10);
            } else{
                Thread.sleep(10);
            }
        }
        for(int i=0;i< cnt;i++){
            t[i].join();
        }
        System.out.println("total end");

    }
}
