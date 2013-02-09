package server.game.play;

import actions.Action;
import actions.TurnAction;
import builder.ReplyBuilder;
import com.google.inject.Inject;
import game.Card;
import game.Deck;
import reply.ErrorReply;
import reply.Reply;
import server.User;
import server.game.LobbyManager;
import server.game.RequestManager;
import server.game.UserInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

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
    private boolean player1Surrender = false;
    private boolean player2Surrender = false;
    private String player1Name;
    private String player2Name;
    private UserInfo player1;
    private UserInfo player2;
    private int turnSize = 30;
    private LobbyManager lobbyManager;
    private List<TurnAction> currTurnAction = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    RequestManager requestManager;

    public GameManager(UserInfo player1, UserInfo player2, RequestManager requestManager, LobbyManager lobbyManager) throws IOException {
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
        lock.lock();
        try {
            if (turn.get() != turnNumber) {
                return;
            }
            turn.incrementAndGet();
            //регистрируем ход на то чтобы он тикнул через нужное количество секунд, на случай если один из игроков не отвечает
            lobbyManager.addGameTicker(new ticker(this, turn.get()), turnSize);
            player1Complete = false;
            player2Complete = false;


            currTurnAction.clear();
        } finally {
            lock.unlock();
        }
    }

    public void addOrder(UserInfo player, Action order) throws IOException {
        int localTurn = turn.get();
        lock.lock();
        try {
            TurnAction ta = order.getTurnAction();

            if(!checkTurnNumberCorrect(player, ta)){
                return;
            }

            if(!checkCorrectTurn(player, localTurn)){
                return;
            }

            if (ta.getAction() == TurnAction.actionEnum.turn.ordinal()) {
                currTurnAction.add(ta);
                if (player1Name.equals(player.getUser().getName())) {
                    Reply reply = ReplyBuilder.getTurnReplyBuilder().setAction(ta.getAction()).setCardId(ta.getCardId()).setPosition(ta.getPosition()).setTurnNumber(turn.get()).build();
                    requestManager.sendReply(player2.getChannel(), reply);
                } else {
                    Reply reply = ReplyBuilder.getTurnReplyBuilder().setAction(ta.getAction()).setCardId(ta.getCardId()).setPosition(ta.getPosition()).setTurnNumber(turn.get()).build();
                    requestManager.sendReply(player1.getChannel(), reply);
                }
            }
            if (ta.getAction() == TurnAction.actionEnum.endturn.ordinal()) {
                if (player1Name.equals(player.getUser().getName())) {
                    player1Complete = true;
                } else {
                    player2Complete = true;
                }
            }
            if (ta.getAction() == TurnAction.actionEnum.surrender.ordinal()) {

            }
        } finally {
            lock.unlock();
        }
        if (player1Complete && localTurn%2==1) {
            processTurn(localTurn);
        }
        if (player2Complete && localTurn%2==0) {
            processTurn(localTurn);
        }
    }

    /**
     * Проверка что это не устаревшее сообщение
     * @param player
     * @param ta
     * @return
     * @throws IOException
     */
    private boolean checkTurnNumberCorrect(UserInfo player, TurnAction ta) throws IOException {
        if (ta.getTurnNumber() != turn.get()) {
            if (player1Name.equals(player.getUser().getName())) {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.missing_turn.getId()).setErrorText("miss turn").build();
                requestManager.sendReply(player1.getChannel(), reply);
                return false;
            } else {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.missing_turn.getId()).setErrorText("miss turn").build();
                requestManager.sendReply(player2.getChannel(), reply);
                return false;
            }
        }
        return true;
    }

    /**
     * Проверка что игрок ходит в свой ход
     * @param player
     * @param localTurn
     * @return
     * @throws IOException
     */
    private boolean checkCorrectTurn(UserInfo player, int localTurn) throws IOException {
        if (player1Name.equals(player.getUser().getName())){
            if( localTurn%2==0) {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.not_you_turn.getId()).setErrorText("not you turn").build();
                requestManager.sendReply(player1.getChannel(), reply);
                return false;
            }
        } else{
            if( localTurn%2==1) {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.not_you_turn.getId()).setErrorText("not you turn").build();
                requestManager.sendReply(player2.getChannel(), reply);
                return false;
            }
        }
        return true;
    }

    class ticker implements Runnable {
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
