package ifree.zombieserver;

import actions.Action;
import actions.ActionTypeEnum;
import actions.ConnectAction;
import actions.CardInfoAction;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import reply.Reply;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 20.01.13
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class CardInfoTest extends TestBase{
    @Test
    public void ConnectionTest() throws InterruptedException, IOException {
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.generateJsonSchema(Action.class);
            ObjectMapper reply = new ObjectMapper();
            mapper.generateJsonSchema(Reply.class);
            Action connectact=new Action();
            connectact.setAction(ActionTypeEnum.CONNECT.getId());
            ConnectAction ca=new ConnectAction();
            ca.setPass("12345");
            connectact.setName("User1");
            connectact.setConnectAction(ca);


            Client c=new Client("localhost",18080);
            c.setMessage(mapper.writeValueAsString(connectact));
            c.run();
            c.send();
            int i=0;
            while (c.getReceive().size()==0){
                if(i++>100)
                    break;
                Thread.sleep(100);
            }
            String receive= c.getReceive().get(0);
            Reply rep=reply.readValue(receive,Reply.class);


            CardInfoAction getCardInfo=new CardInfoAction();
            Action act=new Action();
            act.setName("User1");
            act.setToken(rep.getConnectionReply().getToken());
            act.setAction(ActionTypeEnum.GET_CARD_INFO.getId());

          //  act.setCardInfoAction(getCardInfo);

            c.setMessage(mapper.writeValueAsString(act));
            c.send();
            Thread.sleep(10000);
        }catch (Throwable th){
            th.printStackTrace();
        }
    }
}
