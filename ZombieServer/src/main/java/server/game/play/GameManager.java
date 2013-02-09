package server.game.play;

import actions.Action;
import actions.TurnAction;
import builder.ReplyBuilder;
import com.google.inject.Inject;
import game.Card;
import game.Deck;
import reply.Reply;
import server.User;
import server.game.LobbyManager;
import server.game.RequestManager;
import server.game.UserInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class GameManager {
    private GameTable table;
    private AtomicInteger turn = new AtomicInteger(1);
    private boolean player1Complete = false;
    private boolean player2Complete = false;
    private boolean player1Surrender= false;
    private boolean player2Surrender = false;
    private String player1Name;
    private String player2Name;
    private UserInfo player1;
    private UserInfo player2;
    private int turnSize=30;
    private LobbyManager lobbyManager;

    RequestManager requestManager;

    public GameManager(UserInfo player1, UserInfo player2, RequestManager requestManager,LobbyManager lobbyManager) throws IOException {
        this.requestManager = requestManager;
        this.lobbyManager = lobbyManager;
        table = new GameTable(player1, player2);
        player1Name = player1.getUser().getName();
        player2Name = player2.getUser().getName();
        sendGameStartedMessage(player1, player2);
    }

    public void sendGameStartedMessage(UserInfo player1, UserInfo player2) throws IOException {
        this.player1 = player1;
        this.player2 = player2;
        Reply reply1 = ReplyBuilder.getGameStartedReplyBuilder().setUser(player1.getUser()).build();
        Reply reply2 = ReplyBuilder.getGameStartedReplyBuilder().setUser(player2.getUser()).build();

        requestManager.sendReply(player1.getChannel(), reply2);
        requestManager.sendReply(player2.getChannel(), reply1);
    }


    protected void processTurn(int turnNumber) {
        if(turn.get()!=turnNumber){
            return;
        }
        turn.incrementAndGet();
        //регистрируем ход на то чтобы он тикнул через нужное количество секунд, на случай если один из игроков не отвечает
        lobbyManager.addGameTicker(new ticker(this,turn.get()),turnSize);
        player1Complete=false;
        player2Complete=false;
    }

    public void addOrder(UserInfo player, Action order) throws IOException {
        TurnAction ta = order.getTurnAction();
        if (ta.getAction() == TurnAction.actionEnum.turn.ordinal()) {
            if (player1Name.equals(player.getUser().getName())) {
                Reply reply = ReplyBuilder.getTurnReplyBuilder().setAction(ta.getAction()).setCardId(ta.getCardId()).setPosition(ta.getPosition()).build();
                requestManager.sendReply(player2.getChannel(), reply);
            } else {
                Reply reply = ReplyBuilder.getTurnReplyBuilder().setAction(ta.getAction()).setCardId(ta.getCardId()).setPosition(ta.getPosition()).build();
                requestManager.sendReply(player1.getChannel(), reply);
            }
        }
        if(ta.getAction()== TurnAction.actionEnum.endturn.ordinal()){
            if (player1Name.equals(player.getUser().getName())){
                player1Complete=true;
            } else{
                player2Complete=true;
            }
        }
        if(ta.getAction()==TurnAction.actionEnum.surrender.ordinal()) {

        }

        if(player1Complete && player2Complete){
            processTurn(turn.get());
        }
    }

    class ticker implements Runnable{
        private GameManager manager;
        private int turn;
        ticker(GameManager manager, int turn) {
            this.manager = manager;
            this.turn = turn;
        }
        @Override
        public void run() {
            manager.processTurn(turn);
        }
    }


}
