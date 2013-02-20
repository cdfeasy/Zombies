package ifree;

import actions.*;
import ifree.zombieserver.Client;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import reply.Reply;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 19.02.13
 * Time: 21:48
 * To change this template use File | Settings | File Templates.
 */
public class GameBot implements Runnable{
    String username;
    Long side;

    ObjectMapper mapper = new ObjectMapper();

    ObjectMapper reply = new ObjectMapper();



    public GameBot() throws JsonMappingException {
        mapper.generateJsonSchema(Action.class);
        reply.generateJsonSchema(Reply.class);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getSide() {
        return side;
    }

    public void setSide(Long side) {
        this.side = side;
    }

    private void  create(Client c) throws IOException, InterruptedException {
        Action createUser = new Action();
        createUser.setAction(ActionTypeEnum.CREATE_USER.getId());
        CreateUserAction cra = new CreateUserAction(username, "12345");
        cra.setSide(side);
        createUser.setCreateUserAction(cra);

        c.setMessage(mapper.writeValueAsString(createUser));
        c.run();
        int i = 0;
        while (c.getReceive().size() == 0) {
            if (i++ > 100)
                break;
            Thread.sleep(100);
        }
        String receive = c.getReceive().get(0);
    }

    private String  connect(Client c) throws IOException, InterruptedException {
        Action connectact = new Action();
        connectact.setAction(ActionTypeEnum.CONNECT.getId());
        ConnectAction ca = new ConnectAction();
        ca.setPass("12345");
        connectact.setName(username);
        connectact.setConnectAction(ca);

        c.setMessage(mapper.writeValueAsString(connectact));
        c.run();
        int i = 0;
        while (c.getReceive().size() ==1) {
            if (i++ > 100)
                break;
            Thread.sleep(100);
        }
        String receive = c.getReceive().get(1);


        Reply rep = reply.readValue(receive, Reply.class);
        final String token = rep.getConnectionReply().getToken();
        return token;

    }

    private void search(Client c,String token) throws IOException, InterruptedException {
        Action act = new Action();
        act.setName(username);
        act.setToken(token);
        act.setAction(ActionTypeEnum.SEARCH.getId());
        //   act.setSearchAction(new SearchAction());
        c.setMessage(mapper.writeValueAsString(act));
        c.run();
        int i = 0;
        while (c.getReceive().size() ==2) {
            if (i++ > 100)
                break;
            Thread.sleep(100);
        }
    }

    @Override
    public void run() {
        try {
            Client c = new Client("localhost", 18080);
            create(c);
            String token=connect(c);
            search(c,token);

            int i = 0;
            while (c.getReceive().size() ==3) {
                if (i++ > 10000)
                    break;
                Thread.sleep(100);
            }

            Thread.sleep(100000);


        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}

