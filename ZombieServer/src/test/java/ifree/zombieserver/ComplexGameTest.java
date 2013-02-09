package ifree.zombieserver;

import actions.*;
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
    class process implements Runnable{
        String username;
        Long side;

        @Override
        public void run() {
            try {

                ObjectMapper mapper = new ObjectMapper();
                mapper.generateJsonSchema(Action.class);
                ObjectMapper reply = new ObjectMapper();
                reply.generateJsonSchema(Reply.class);

                Action createUser = new Action();
                createUser.setAction(ActionTypeEnum.CREATE_USER.getId());
                CreateUserAction cra = new CreateUserAction(username, "12345");
                cra.setSide(side);
                createUser.setCreateUserAction(cra);


                Action connectact = new Action();
                connectact.setAction(ActionTypeEnum.CONNECT.getId());
                ConnectAction ca = new ConnectAction();
                ca.setPass("12345");
                connectact.setName(username);
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
                act.setName(username);
                act.setToken(token);
                act.setAction(ActionTypeEnum.SEARCH.getId());
             //   act.setSearchAction(new SearchAction());
                c.setMessage(mapper.writeValueAsString(act));
                c.run();
                i = 0;
                while (c.getReceive().size() ==2) {
                    if (i++ > 100)
                        break;
                    Thread.sleep(100);
                }
                i = 0;
                while (c.getReceive().size() ==3) {
                    if (i++ > 10000)
                        break;
                    Thread.sleep(100);
                }

            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }



    @Test
    public void ConnectionTest() throws InterruptedException, IOException {
        try {

            process r1=new process();
            r1.username="user10";
            r1.side=1l;

            process r2=new process();
            r2.username="user11";
            r2.side=0l;

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
