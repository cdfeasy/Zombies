package ifree.zombieserver;

import zombies.dto.actions.*;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import zombies.dto.reply.UserReply;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.01.13
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoTest extends TestBase{
    @Test
    public void ConnectionTest() throws InterruptedException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.generateJsonSchema(UserAction.class);
            ObjectMapper reply = new ObjectMapper();
            mapper.generateJsonSchema(UserReply.class);
            UserAction connectact = new UserAction();
            connectact.setAction(ActionTypeEnum.CONNECT.getId());
            ConnectAction ca = new ConnectAction();
            ca.setPass("12345");
            connectact.setName("User1");
            connectact.setConnectAction(ca);


            Client c = new Client("localhost", 18080);

            c.setMessage(mapper.writeValueAsString(connectact));
            c.run();
            c.send();
            int i = 0;
            while (c.getReceive().size() == 0) {
                if (i++ > 100)
                    break;
                Thread.sleep(100);
            }
            String receive = c.getReceive().get(0);
            UserReply rep = reply.readValue(receive, UserReply.class);
            final String token = rep.getConnectionReply().getToken();

            Thread[] th = new Thread[2];
            for (int k = 0; k < 2; k++) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            Client cl = new Client("localhost", 18080);
                            CardInfoAction getCardInfo = new CardInfoAction();
                            UserAction act = new UserAction();
                            act.setName("User1");
                            act.setToken(token);
                            act.setAction(ActionTypeEnum.GETUSERINFO.getId());
                            act.setUserInfo(new UserInfoAction("User1"));
                            ObjectMapper lmapper = new ObjectMapper();

                            lmapper.generateJsonSchema(UserAction.class);
                            cl.setMessage(lmapper.writeValueAsString(act));
                            cl.run();
                            Thread.sleep(5000);
                        } catch (JsonMappingException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        } catch (JsonGenerationException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                };
                th[k] = t;
            }
            for (int k = 0; k < 2; k++){
                th[k].start();
            }
            for (int k = 0; k < 2; k++){
                th[k].join();
            }

        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
