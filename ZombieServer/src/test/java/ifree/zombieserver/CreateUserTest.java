package ifree.zombieserver;

import actions.*;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import reply.Reply;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 31.01.13
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */
public class CreateUserTest extends TestBase {
    @Test
    public void test(){

    }
   // @Test
    public void ConnectionTest() throws InterruptedException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.generateJsonSchema(Action.class);
            ObjectMapper reply = new ObjectMapper();
            mapper.generateJsonSchema(Reply.class);

            Action createUser = new Action();
            createUser.setAction(ActionTypeEnum.CREATE_USER.getId());
            CreateUserAction cra = new CreateUserAction("User5", "12345");
            cra.setSide(0l);
            createUser.setCreateUserAction(cra);


            Action connectact = new Action();
            connectact.setAction(ActionTypeEnum.CONNECT.getId());
            ConnectAction ca = new ConnectAction();
            ca.setPass("12345");
            connectact.setName("User5");
            connectact.setConnectAction(ca);


            Client c = new Client("localhost", 18080);

            c.setMessage(mapper.writeValueAsString(createUser));
            c.run();
            int i = 0;
            while (c.getReceive().size() == 0) {
                if (i++ > 100)
                    break;
                Thread.sleep(100);
            }
            String receive = c.getReceive().get(0);

            c.setMessage(mapper.writeValueAsString(connectact));
            c.run();
            i = 0;
            while (c.getReceive().size() ==1) {
                if (i++ > 100)
                    break;
                Thread.sleep(100);
            }
            receive = c.getReceive().get(1);


            Reply rep = reply.readValue(receive, Reply.class);
            final String token = rep.getConnectionReply().getToken();

            CardInfoAction getCardInfo = new CardInfoAction();
            Action act = new Action();
            act.setName("User5");
            act.setToken(token);
            act.setAction(ActionTypeEnum.GETUSERINFO.getId());
            act.setUserInfo(new UserInfoAction("User5"));
            ObjectMapper lmapper = new ObjectMapper();

            lmapper.generateJsonSchema(Action.class);
            c.setMessage(lmapper.writeValueAsString(act));
            c.run();
            while (c.getReceive().size() ==2) {
                if (i++ > 100)
                    break;
                Thread.sleep(100);
            }

        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
