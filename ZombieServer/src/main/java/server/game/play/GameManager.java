package server.game.play;

import actions.Action;
import actions.TurnAction;
import builder.ReplyBuilder;
import builder.TurnReplyBuilder;
import game.Card;
import game.CardTypeEnum;
import reply.ErrorReply;
import reply.Reply;
import reply.TurnReply;
import server.game.LobbyManager;
import server.game.UserInfo;
import support.CardWrapper;
import support.GameInfo;

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
    private int cardWrapperNum = 0;
    private int user1Hp=100;
    private int user2Hp=100;
    private GameInfo player1Info;
    private GameInfo player2Info;


    public GameManager(UserInfo player1, UserInfo player2, LobbyManager lobbyManager) throws IOException {
        this.lobbyManager = lobbyManager;
        table = new GameTable(player1, player2, this);
        player1Name = player1.getUser().getName();
        player2Name = player2.getUser().getName();
        player1Info=new GameInfo();
        player1Info.setEnemyName(player2Name);
        player2Info=new GameInfo();
        player2Info.setEnemyName(player1Name);
        sendGameStartedMessage(player1, player2);
    }

    public void sendGameStartedMessage(UserInfo player1, UserInfo player2) throws IOException {
        this.player1 = player1;
        this.player2 = player2;
        Reply reply1 = ReplyBuilder.getGameStartedReplyBuilder().setUser(player1.getUser()).build();
        Reply reply2 = ReplyBuilder.getGameStartedReplyBuilder().setUser(player2.getUser()).build();

        lobbyManager.getRequestManager().sendReply(player1.getChannel(), reply2);
        lobbyManager.getRequestManager().sendReply(player2.getChannel(), reply1);

    }


    protected void processTurn(int turnNumber) {
        lock.lock();
        try {
            if (turn.get() != turnNumber) {
                return;
            }

            //регистрируем ход на то чтобы он тикнул через нужное количество секунд, на случай если один из игроков не отвечает
            lobbyManager.addGameTicker(new ticker(this, turn.get()), turnSize);
            player1Complete = false;
            player2Complete = false;
            TurnReplyBuilder turnReplyBuilder = ReplyBuilder.getTurnReplyBuilder();
            turnReplyBuilder.setTurnNumber(turn.get());
            turnReplyBuilder.setAction(TurnReply.actionEnum.endturn.ordinal());
            int spendHpUser1=0;
            int spendHpUser2=0;
            for (TurnAction action : currTurnAction) {
                Card c = lobbyManager.getCards().get(action.getCardId());
                TableSide side = null;
                if (turn.get() % 2 == 1) {
                    if (CardTypeEnum.getValue(c.getCardType()).equals(CardTypeEnum.damageSpell)) {
                        side = table.getPlayer2Side();
                    }
                    else{
                        side = table.getPlayer1Side();
                    }
                } else {
                    if (CardTypeEnum.getValue(c.getCardType()).equals(CardTypeEnum.damageSpell)){
                        side = table.getPlayer1Side();
                    }
                    else {
                        side = table.getPlayer2Side();
                    }
                }
                SideCell cell = side.getCell(action.getPosition());
                switch (CardTypeEnum.getValue(c.getCardType())){
                    case  buffSpell: break;
                    case  damageSpell: break;
                    case  globalSpell: break;
                    default: addCreature(cell,c,turnReplyBuilder); break;
                }

                for(int i=0;i<TableSide.CELL_COUNT;i++){
                    SideCell p1cell=table.getPlayer1Side().getCell(i);
                    SideCell p2cell=table.getPlayer1Side().getCell(i);
                    spendHpUser2 = processCell(turnReplyBuilder, p1cell, p2cell);
                    spendHpUser1 = processCell(turnReplyBuilder, p2cell, p1cell);
                }

                deleteCasualties(turnReplyBuilder);


            }

            currTurnAction.clear();
            turn.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }

    private void deleteCasualties(TurnReplyBuilder turnReplyBuilder){
        for(int i=0;i<TableSide.CELL_COUNT;i++){
            SideCell p1cell=table.getPlayer1Side().getCell(i);
            SideCell p2cell=table.getPlayer1Side().getCell(i);
            for(CardWrapper c:p1cell.getCards()){
                if(c.getHp()==0){
                    turnReplyBuilder.addActionInfo(c.getWrapperId(),String.format("%s dead",c.getCard().getName()));
                    if(player2.getUser().getSide()==0){
                        player1Info.setSurvivalsKilled(player1Info.getSurvivalsKilled()+1);
                    } else{
                        player1Info.setZombyKilled(player1Info.getZombyKilled()+1);
                    }
                }
            }

            for(CardWrapper c:p2cell.getCards()){
                if(c.getHp()==0){
                    turnReplyBuilder.addActionInfo(c.getWrapperId(),String.format("%s dead",c.getCard().getName()));
                    if(player1.getUser().getSide()==0){
                        player2Info.setSurvivalsKilled(player2Info.getSurvivalsKilled()+1);
                    } else{
                        player2Info.setZombyKilled(player2Info.getZombyKilled()+1);
                    }
                }
            }
            p1cell.clearDead();
            p2cell.clearDead();
        }

    }

    private int processCell(TurnReplyBuilder turnReplyBuilder, SideCell p1cell, SideCell p2cell) {
        int sumDmg=0;
        for(CardWrapper cr:p1cell.getCards()){
           int damage=cr.getStrength();
           int spend=0;
           CardWrapper cw=null;
           while((cw=p2cell.getTopCard())!=null && (spend=p2cell.hit(damage,cw))>0){
               turnReplyBuilder.addActionInfo(cr.getWrapperId(),String.format("%s hit %s on %s damage",cr.getCard().getName(),cw.getCard().getName(),Integer.toString(spend)));
               damage=damage-spend;
           }
            sumDmg+=damage;
         }
        return sumDmg;
    }

    private void addCreature(SideCell cell,Card card,TurnReplyBuilder builder){
        CardWrapper cr= cell.addCard(card);
        builder.addActionInfo(cr.getWrapperId(),String.format("%s enter the battle",card.getName()));
    }

    public void addOrder(UserInfo player, Action order) throws IOException {
        int localTurn = turn.get();
        lock.lock();
        try {
            TurnAction ta = order.getTurnAction();

            if (!checkTurnNumberCorrect(player, ta)) {
                return;
            }

            if (!checkCorrectTurn(player, localTurn)) {
                return;
            }

            if (ta.getAction() == TurnAction.actionEnum.turn.ordinal()) {
                currTurnAction.add(ta);
                if (player1Name.equals(player.getUser().getName())) {
                    Reply reply = ReplyBuilder.getTurnReplyBuilder().setAction(ta.getAction()).setCardId(ta.getCardId()).setPosition(ta.getPosition()).setTurnNumber(turn.get()).build();
                    lobbyManager.getRequestManager().sendReply(player2.getChannel(), reply);
                } else {
                    Reply reply = ReplyBuilder.getTurnReplyBuilder().setAction(ta.getAction()).setCardId(ta.getCardId()).setPosition(ta.getPosition()).setTurnNumber(turn.get()).build();
                    lobbyManager.getRequestManager().sendReply(player1.getChannel(), reply);
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
        if (player1Complete && localTurn % 2 == 1) {
            processTurn(localTurn);
        }
        if (player2Complete && localTurn % 2 == 0) {
            processTurn(localTurn);
        }
    }

    /**
     * Проверка что это не устаревшее сообщение
     *
     * @param player
     * @param ta
     * @return
     * @throws IOException
     */
    private boolean checkTurnNumberCorrect(UserInfo player, TurnAction ta) throws IOException {
        if (ta.getTurnNumber() != turn.get()) {
            if (player1Name.equals(player.getUser().getName())) {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.missing_turn.getId()).setErrorText("miss turn").build();
                lobbyManager.getRequestManager().sendReply(player1.getChannel(), reply);
                return false;
            } else {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.missing_turn.getId()).setErrorText("miss turn").build();
                lobbyManager.getRequestManager().sendReply(player2.getChannel(), reply);
                return false;
            }
        }
        return true;
    }

    public int getCardWrapperNum() {
        return cardWrapperNum;
    }

    public int incrementAndGetCardWrapperNum() {
        return cardWrapperNum++;
    }


    /**
     * Проверка что игрок ходит в свой ход
     *
     * @param player
     * @param localTurn
     * @return
     * @throws IOException
     */
    private boolean checkCorrectTurn(UserInfo player, int localTurn) throws IOException {
        if (player1Name.equals(player.getUser().getName())) {
            if (localTurn % 2 == 0) {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.not_you_turn.getId()).setErrorText("not you turn").build();
                lobbyManager.getRequestManager().sendReply(player1.getChannel(), reply);
                return false;
            }
        } else {
            if (localTurn % 2 == 1) {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.not_you_turn.getId()).setErrorText("not you turn").build();
                lobbyManager.getRequestManager().sendReply(player2.getChannel(), reply);
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
