package zombies.testclient;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import zombies.dto.actions.*;
import zombies.dto.reply.UserReply;
import zombies.entity.game.Card;
import zombies.entity.game.Fraction;
import zombies.entity.game.SubFraction;

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
    private boolean needCreate=true;
    private GameClient gc;
    private Client c;



    public GameBot(String username,Long side,boolean needCreate) throws JsonMappingException {
        c = new Client("78.47.52.69", 18080);
        c.run();
        this.username=username;
        this.side=side;
        this.needCreate=needCreate;
        gc=new GameClient(c,username,side);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        try {
            if(needCreate) {
              gc.create();
            }
            gc.connect();
            gc.cardInfo();
            gc.UserInfo();
            gc.RandomDeck();
            gc.cardInfo();
            gc.search();

            UserReply gameStarted=gc.awaitGameStart();
            List<Card> currCards=new ArrayList<>();

            for(Long l:gameStarted.getGameStartedReply().getCards())   {
                currCards.add(gc.cards.get(l));
            }
            int playerQueue=gameStarted.getGameStartedReply().getPosition();
            TurnProcessor tp=new TurnProcessor(gc,this,currCards, playerQueue);
            if(playerQueue==0){
                tp.sendEndTurn();
            }
            System.out.println("await end");
            tp.awaitEnd();
            c.close();
            System.out.println("end");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}

