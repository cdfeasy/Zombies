package ifree;

import zombies.dto.actions.*;
import zombies.dto.reply.UserReply;
import zombies.entity.game.Card;
import zombies.entity.game.Fraction;
import zombies.entity.game.SubFraction;
import ifree.zombieserver.Client;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 19.02.13
 * Time: 21:48
 * To change this template use File | Settings | File Templates.
 */
public class GameBot implements Runnable{
    public String username;
    public Long side;
    public String token;
    public List<Fraction> fr;
    public Map<Long,Card> cards=new HashMap<>() ;
    public List<Card> currCards=new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();

    ObjectMapper reply = new ObjectMapper();
    public int currReceive=0;
    public int currTurn=0;
    public int playerQueue=0;



    public GameBot() throws JsonMappingException {
        mapper.generateJsonSchema(UserAction.class);
        reply.generateJsonSchema(UserReply.class);
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
        UserAction createUser = new UserAction();
        createUser.setAction(ActionTypeEnum.CREATE_USER.getId());
        CreateUserAction cra = new CreateUserAction(username, "12345");
        cra.setSide(side);
        createUser.setCreateUserAction(cra);

        c.setMessage(mapper.writeValueAsString(createUser));
        c.send();
        int i = 0;
        while (c.getReceive().size() == currReceive) {
            if (i++ > 10000)
                break;
            Thread.sleep(100);
        }
        currReceive++;
        String receive = c.getReceive().get(0);
    }

    private String  connect(Client c) throws IOException, InterruptedException {
        UserAction connectact = new UserAction();
        connectact.setAction(ActionTypeEnum.CONNECT.getId());
        ConnectAction ca = new ConnectAction();
        ca.setPass("12345");
        connectact.setName(username);
        connectact.setConnectAction(ca);

        c.setMessage(mapper.writeValueAsString(connectact));
        c.send();
        int i = 0;
        while (c.getReceive().size() ==currReceive) {
            if (i++ > 10000)
                break;
            Thread.sleep(100);
        }
        String receive = c.getReceive().get(currReceive);
        currReceive++;


        UserReply rep = reply.readValue(receive, UserReply.class);
        final String token = rep.getConnectionReply().getToken();
        return token;

    }

    private void  cardInfo(Client c,String token) throws IOException, InterruptedException {
        CardInfoAction getCardInfo=new CardInfoAction();
        UserAction act=new UserAction();
        act.setName(username);
        act.setToken(token);
        act.setAction(ActionTypeEnum.GET_CARD_INFO.getId());

        c.setMessage(mapper.writeValueAsString(act));
        c.send();
        int i = 0;
        while (c.getReceive().size() ==currReceive) {
            if (i++ > 10000)
                break;
            Thread.sleep(100);
        }
        String receive = c.getReceive().get(currReceive);
        currReceive++;


        UserReply rep = reply.readValue(receive, UserReply.class);
        fr=rep.getCardInfoReply().getFractions();
        for(Fraction f:fr){
            for(SubFraction sub:f.getSubFractions()){
                for(Card cd:sub.getDeck()){
                     cards.put(cd.getId(),cd);
                }
            }
        }


    }

    private void search(Client c,String token) throws IOException, InterruptedException {
        UserAction act = new UserAction();
        act.setName(username);
        act.setToken(token);
        act.setAction(ActionTypeEnum.SEARCH.getId());
        //   act.setSearchAction(new SearchAction());
        c.setMessage(mapper.writeValueAsString(act));
        c.send();
//        int i = 0;
//        while (c.getReceive().size() ==currReceive) {
//            if (i++ > 10000)
//                break;
//            Thread.sleep(100);
//        }
//        currReceive++;
    }

    private void doTurn(Client c,String token) throws IOException, InterruptedException {
        UserAction act = new UserAction();
        act.setName(username);
        act.setToken(token);
        act.setAction(ActionTypeEnum.TURN.getId());
        TurnAction ta=new TurnAction();
        ta.setTurnNumber(1);
        ta.setAction(TurnAction.actionEnum.endturn.ordinal());
        act.setTurnAction(ta);
        c.setMessage(mapper.writeValueAsString(act));
        c.send();
    }


    @Override
    public void run() {
        try {
            Client c = new Client("localhost", 18080);
            c.run();
            create(c);
            String _token=connect(c);
            this.token=_token;
            cardInfo(c,_token);
            search(c,_token);

            int i = 0;
            while (c.getReceive().size() ==currReceive) {
                if (i++ > 10000)
                    break;
                Thread.sleep(100);
            }
            UserReply rep = reply.readValue(c.getReceive().get(currReceive), UserReply.class);
            currReceive++;

            if(rep.getGameStartedReply()==null){
                int ind=new Random().nextInt(100000);
                for(int k=0;k<c.getReceive().size();k++){
                      System.out.println(Integer.toString(ind)+"ERROR!!!"+c.getReceive().get(k));
                }
            }
            for(Long l:rep.getGameStartedReply().getCards())   {
                currCards.add(cards.get(l));
            }
            playerQueue=rep.getGameStartedReply().getPosition();
            TurnProcessor tp=new TurnProcessor(c,this);
            Thread ttp=new Thread(tp);
            ttp.start();
            if(playerQueue==0){
                doTurn(c,token);
            }

            ttp.join();

        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}

