package zombies.testclient;

import zombies.dto.actions.ActionTypeEnum;
import zombies.dto.actions.TurnAction;
import zombies.dto.actions.UserAction;
import zombies.dto.reply.TurnReply;
import zombies.dto.reply.UserReply;
import zombies.entity.game.Card;
import zombies.entity.game.CardTypeEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.02.13
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
public class TurnProcessor  {
    private GameBot bot;
    private boolean isAlive=true;
    private int curReceive;
    private GameClient client;
    private long endDate=0;
    private long start=0;
    private long sumDate=0;
    private int currTurn=1;
    public int playerQueue=0;
    private List<Card> currCards=new ArrayList<>();
    private Semaphore endSem=new Semaphore(0);
    private GameClient.GameClientListener listener=new GameClient.GameClientListener() {
        @Override
        public void turnReceived(UserReply reply) {
            try {
                Thread.sleep(1000);
                process(reply);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    };

    public TurnProcessor(GameClient client, GameBot bot,List<Card> currCards, int playerQueue) {
        this.client = client;
        this.bot = bot;
        this.currCards=currCards;
        this.playerQueue=playerQueue;
        client.setListener(listener);
    }
    public void awaitEnd() throws InterruptedException {
        endSem.acquire();
    }

    private void process(UserReply rep) throws IOException, InterruptedException {
        if(rep.getTurnReply()==null){
            System.out.println("ERROR!!!!!!"+bot.getUsername());
            System.out.println("ERROR!!!!!!"+rep.toString());

        }
        if(rep.getTurnReply().getAction()== TurnReply.actionEnum.turn.ordinal()){
            //nope
        }
        if(rep.getTurnReply().getAction()== TurnReply.actionEnum.uwin.ordinal()){
            isAlive=false;
            endSem.release();
            System.out.println("END!!!!"+Long.toString(sumDate) +"/"+ Long.toString(sumDate/currTurn) );
        }
        if(rep.getTurnReply().getAction()== TurnReply.actionEnum.ulose.ordinal()){
            isAlive=false;
            endSem.release();
            System.out.println("END!!!!"+Long.toString(sumDate)+"/"+ Long.toString(sumDate/currTurn) );
        }
        if(currTurn>=50){
            sendSurrender();
            System.out.println("Surrender" );
        } else
        if(rep.getTurnReply().getAction()== TurnReply.actionEnum.endturn.ordinal()){
            if(rep.getTurnReply().getNextTurnUser()==playerQueue){
              //  System.out.println("turn zombies.dto.reply "+bot.getUsername()+"/"+bot.currTurn+"/");
                Date d=new Date();
                endDate=d.getTime();
                if(endDate>0 && start>0){
                    sumDate+=endDate-start-1000;
                }
                start=d.getTime();
                currCards.clear();
                currTurn=  rep.getTurnReply().getTurnNumber()+1;
                for(Long l:rep.getTurnReply().getPlayerHand())   {
                    currCards.add(client.cards.get(l));
                }
                sendCardAction();
                Thread.sleep(100);
                sendEndTurn();
            }
        }



    }

    private void sendCardAction() throws IOException, InterruptedException {
        UserAction act = new UserAction();
        act.setName(bot.username);
        act.setToken(bot.token);
        act.setAction(ActionTypeEnum.TURN.getId());
        TurnAction ta=new TurnAction();
        ta.setAction(TurnAction.actionEnum.turn.ordinal());
        for(int i=0;i<currCards.size();i++){
            if(currCards.get(i).getCardType()== CardTypeEnum.creature.getId()){
                ta.setCardId(currCards.get(i).getId());
                break;
            }
        }
        if(ta.getCardId()==null)
            return;
        ta.setPosition(0);
        ta.setTurnNumber(currTurn);
        act.setTurnAction(ta);
        client.doTurn(act);
    }

    public void sendEndTurn() throws IOException, InterruptedException {
        UserAction act = new UserAction();
        act.setName(bot.username);
        act.setToken(bot.token);
        act.setAction(ActionTypeEnum.TURN.getId());
        TurnAction ta=new TurnAction();
        ta.setAction(TurnAction.actionEnum.endturn.ordinal());
        ta.setTurnNumber(currTurn);
        act.setTurnAction(ta);
        client.doTurn(act);

    }


    private void sendSurrender() throws IOException, InterruptedException {
        UserAction act = new UserAction();
        act.setName(bot.username);
        act.setToken(bot.token);
        act.setAction(ActionTypeEnum.TURN.getId());
        TurnAction ta=new TurnAction();
        ta.setAction(TurnAction.actionEnum.surrender.ordinal());
        ta.setTurnNumber(currTurn);
        act.setTurnAction(ta);
        client.doTurn(act);
       // process(receive);
    }
}
