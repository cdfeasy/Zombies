package ifree.zombieserver;

import zombies.dto.actions.UserAction;
import zombies.dto.actions.ActionTypeEnum;
import zombies.dto.actions.ConnectAction;
import zombies.dto.actions.CardInfoAction;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import zombies.dto.reply.UserReply;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 20.01.13
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class CardInfoTest {

    @Test
    public void empty()   {

    }

    //@Test
    public void ConnectionTest() throws InterruptedException, IOException {
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.generateJsonSchema(UserAction.class);
            ObjectMapper reply = new ObjectMapper();
            mapper.generateJsonSchema(UserReply.class);
            UserAction connectact=new UserAction();
            connectact.setAction(ActionTypeEnum.CONNECT.getId());
            ConnectAction ca=new ConnectAction();
            ca.setPass("12345");
            connectact.setName("User1");
            connectact.setConnectAction(ca);


          //  Client c=new Client("78.47.52.69",18080);

            Client c=new Client("192.168.0.10",18080);
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
            UserReply rep=reply.readValue(receive,UserReply.class);


            CardInfoAction getCardInfo=new CardInfoAction();
            UserAction act=new UserAction();
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
