package zombies.testclient;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import zombies.dto.actions.*;
import zombies.dto.reply.ReplyTypeEnum;
import zombies.dto.reply.UserReply;
import zombies.entity.game.Card;
import zombies.entity.game.Fraction;
import zombies.entity.game.SubFraction;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 25.08.13
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class GameClient {

    public String username;
    public Long side;
    public String token;
    public List<Fraction> fr;
    public Map<Long, Card> cards = new HashMap<>();
    public Long activeDeckId;
    public Long sideId;
    private Client client;
    private ObjectMapper mapper = new ObjectMapper();
    private int deckSize = 30;
    private ObjectMapper reply = new ObjectMapper();
    private AtomicReference<UserReply> lastReply = new AtomicReference<>();
    private AtomicReference<UserReply> lastGameStartedReply = new AtomicReference<>();
    private GameClientListener listener;
    private Semaphore gameStarted = new Semaphore(0);
    private Semaphore connect = new Semaphore(0);
    private Semaphore cardInfo = new Semaphore(0);
    private Semaphore userInfo = new Semaphore(0);
    private Semaphore search = new Semaphore(0);
    private Semaphore turn = new Semaphore(0);
    private Semaphore saveDeck = new Semaphore(0);
    private Semaphore success = new Semaphore(0);
    public void close(){
       gameStarted .release();
       connect.release();
       cardInfo.release();
       userInfo.release();
       search .release();
       turn.release();
       saveDeck.release();
       success .release();
    }

    public GameClient(Client client,String username,Long side) throws JsonMappingException {
        this.client = client;
        this.username=username;
        this.side=side;
        mapper.generateJsonSchema(UserAction.class);
        reply.generateJsonSchema(UserReply.class);
        client.setListener(new ChannelListener());
    }

    public UserReply awaitGameStart() throws InterruptedException {
        gameStarted.acquire();
        return lastGameStartedReply.get();
    }

    public GameClientListener getListener() {
        return listener;
    }

    public void setListener(GameClientListener listener) {
        this.listener = listener;
    }

    public void create() throws IOException, InterruptedException {
        UserAction createUser = new UserAction();
        createUser.setAction(ActionTypeEnum.CREATE_USER.getId());
        CreateUserAction cra = new CreateUserAction(username, "12345");
        cra.setSide(side);
        createUser.setCreateUserAction(cra);

        client.setMessage(mapper.writeValueAsString(createUser));
        client.send();
        success.acquire();
    }

    public void connect() throws IOException, InterruptedException {
        UserAction connectact = new UserAction();
        connectact.setAction(ActionTypeEnum.CONNECT.getId());
        ConnectAction ca = new ConnectAction();
        ca.setPass("12345");
        connectact.setName(username);
        connectact.setConnectAction(ca);

        client.setMessage(mapper.writeValueAsString(connectact));
        client.send();
        connect.acquire();
        token = lastReply.get().getConnectionReply().getToken();
    }

    public void RandomDeck() throws IOException, InterruptedException {
        CardInfoAction getCardInfo = new CardInfoAction();
        UserAction act = new UserAction();
        act.setName(username);
        act.setToken(token);
        act.setAction(ActionTypeEnum.SAVE_DECK.getId());
        SaveDeckAction sda = new SaveDeckAction();
        Random r = new Random();
        List<Integer> ids = new ArrayList<>();
        int cnt = 0;
        while (true) {
            int index = r.nextInt(cards.size());
            ids.add(((Long) cards.keySet().toArray()[index]).intValue());
            cnt++;
            if (cnt == deckSize) {
                break;
            }
        }
        sda.setCardsIds(ids);
        sda.setDeckId(activeDeckId);
        act.setSaveDeckAction(sda);
        client.setMessage(mapper.writeValueAsString(act));
        client.send();
        success.acquire();
    }

    public void cardInfo() throws IOException, InterruptedException {
        CardInfoAction getCardInfo = new CardInfoAction();
        UserAction act = new UserAction();
        act.setName(username);
        act.setToken(token);
        act.setAction(ActionTypeEnum.GET_CARD_INFO.getId());

        client.setMessage(mapper.writeValueAsString(act));
        client.send();
        cardInfo.acquire();

        UserReply rep = lastReply.get();
        fr = rep.getCardInfoReply().getFractions();
        cards.clear();
        for (Fraction f : fr) {
            for (SubFraction sub : f.getSubFractions()) {
                for (Card cd : sub.getDeck()) {
                    cards.put(cd.getId(), cd);
                }
            }
        }
    }

    public void UserInfo() throws IOException, InterruptedException {
        CardInfoAction getCardInfo = new CardInfoAction();
        UserAction act = new UserAction();
        act.setName(username);
        act.setToken(token);
        act.setAction(ActionTypeEnum.GETUSERINFO.getId());
        UserInfoAction uia = new UserInfoAction();
        uia.setUserName(username);
        act.setUserInfo(uia);

        client.setMessage(mapper.writeValueAsString(act));
        client.send();
        userInfo.acquire();
        UserReply rep = lastReply.get();
        activeDeckId = rep.getUserInfoReply().getUser().getActiveDeckIds().getDeckId();
        sideId = rep.getUserInfoReply().getUser().getSide();
    }

    public void search() throws IOException, InterruptedException {
        UserAction act = new UserAction();
        act.setName(username);
        act.setToken(token);
        act.setAction(ActionTypeEnum.SEARCH.getId());
        client.setMessage(mapper.writeValueAsString(act));
        client.send();
        search.acquire();
    }

    public void doTurn(UserAction turnAct) throws IOException, InterruptedException {
        turnAct.setName(username);
        turnAct.setToken(token);
        turnAct.setAction(ActionTypeEnum.TURN.getId());
        client.setMessage(mapper.writeValueAsString(turnAct));
        client.send();
       // turn.acquire();
       // return lastReply.get();
    }

    public interface GameClientListener {
        void turnReceived(UserReply reply);
    }

    class ChannelListener implements Client.listener {

        @Override
        public void onMessageReceived(String message) throws IOException {
            UserReply rep = reply.readValue(message, UserReply.class);
            lastReply.set(rep);
            switch (ReplyTypeEnum.getValue(rep.getReply())) {
                case CONNECTION:
                    connect.release();
                    break;
                case GET_CARD_INFO:
                    cardInfo.release();
                    break;
                case USERINFO:
                    userInfo.release();
                    break;
                case TURN:
                   // turn.release();
                    if (getListener() != null) getListener().turnReceived(rep);
                    break;
                case SEARCH:
                    search.release();
                    break;
                case SAVE_DECK:
                    saveDeck.release();
                    break;
                case ERROR:
                    success.release();
                    break;
                case GAME_STARTED:
                    gameStarted.release();
                    lastGameStartedReply.set(rep);
                    break;
            }
        }
    }

}
