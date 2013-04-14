package ifree;

import zombies.dto.actions.UserAction;
import zombies.dto.actions.ActionTypeEnum;
import zombies.dto.actions.TurnAction;
import zombies.dto.reply.UserReply;
import zombies.entity.game.CardTypeEnum;
import ifree.zombieserver.Client;
import zombies.dto.reply.TurnReply;

import java.io.IOException;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.02.13
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
public class TurnProcessor implements Runnable {
    private GameBot bot;
    private boolean isAlive=true;
    private int curReceive;
    Client client;
    private long endDate=0;
    private long start=0;
    private long sumDate=0;
    private int  turnCnt=0;

    public TurnProcessor(Client client, GameBot bot) {
        this.client = client;
        this.bot = bot;
    }
    private void process(String receive) throws IOException, InterruptedException {
        UserReply rep = bot.reply.readValue(receive, UserReply.class);
        if(rep.getTurnReply()==null){
            System.out.println("ERROR!!!!!!"+bot.getUsername());
            System.out.println("ERROR!!!!!!"+rep.toString());

        }
        if(rep.getTurnReply().getAction()== TurnReply.actionEnum.turn.ordinal()){
            //nope
        }
        if(rep.getTurnReply().getAction()== TurnReply.actionEnum.uwin.ordinal()){
            isAlive=false;
            System.out.println("END!!!!"+Long.toString(sumDate) +"/"+ Long.toString(sumDate/bot.currTurn) );
        }
        if(rep.getTurnReply().getAction()== TurnReply.actionEnum.ulose.ordinal()){
            isAlive=false;
            System.out.println("END!!!!"+Long.toString(sumDate)+"/"+ Long.toString(sumDate/bot.currTurn) );
        }
        if(bot.currTurn>=50){
            sendSurrender();
            System.out.println("Surrender" );
        } else
        if(rep.getTurnReply().getAction()== TurnReply.actionEnum.endturn.ordinal()){
            if(rep.getTurnReply().getNextTurnUser()==bot.playerQueue){
              //  System.out.println("turn zombies.dto.reply "+bot.getUsername()+"/"+bot.currTurn+"/");
                Date d=new Date();
                endDate=d.getTime();
                if(endDate>0 && start>0){
                    sumDate+=endDate-start-1000;
                }
                start=d.getTime();
                bot.currCards.clear();
                bot.currTurn=  rep.getTurnReply().getTurnNumber()+1;
                for(Long l:rep.getTurnReply().getPlayerHand())   {
                    bot.currCards.add(bot.cards.get(l));
                }
                sendCardAction();
                Thread.sleep(1000);
                sendEndTurn();
            }
        }



    }

    private void sendCardAction() throws IOException {
        UserAction act = new UserAction();
        act.setName(bot.username);
        act.setToken(bot.token);
        act.setAction(ActionTypeEnum.TURN.getId());
        TurnAction ta=new TurnAction();
        ta.setAction(TurnAction.actionEnum.turn.ordinal());
        for(int i=0;i<bot.currCards.size();i++){
            if(bot.currCards.get(i).getCardType()== CardTypeEnum.creature.getId()){
                ta.setCardId(bot.currCards.get(i).getId());
                break;
            }
        }
        if(ta.getCardId()==null)
            return;
        ta.setPosition(0);
        ta.setTurnNumber(bot.currTurn);
        act.setTurnAction(ta);
        client.setMessage(bot.mapper.writeValueAsString(act));
        client.send();
    }

    private void sendEndTurn() throws IOException {
        UserAction act = new UserAction();
        act.setName(bot.username);
        act.setToken(bot.token);
        act.setAction(ActionTypeEnum.TURN.getId());
        TurnAction ta=new TurnAction();
        ta.setAction(TurnAction.actionEnum.endturn.ordinal());
        ta.setTurnNumber(bot.currTurn);
        act.setTurnAction(ta);
        client.setMessage(bot.mapper.writeValueAsString(act));
        client.send();

    }


    private void sendSurrender() throws IOException {
        UserAction act = new UserAction();
        act.setName(bot.username);
        act.setToken(bot.token);
        act.setAction(ActionTypeEnum.TURN.getId());
        TurnAction ta=new TurnAction();
        ta.setAction(TurnAction.actionEnum.surrender.ordinal());
        ta.setTurnNumber(bot.currTurn);
        act.setTurnAction(ta);
        client.setMessage(bot.mapper.writeValueAsString(act));
        client.send();

    }

    @Override
    public void run() {
        try{
        curReceive=client.getReceive().size();
        while (isAlive)  {
            while (isAlive && client.getReceive().size() ==curReceive) {
                Thread.sleep(100);
            }
            if(!isAlive){
                break;
            }
            String receive= client.getReceive().get(curReceive);
            process(receive);
            curReceive++;

        }
        }catch (Throwable th){
            th.printStackTrace();
        }
    }
}
