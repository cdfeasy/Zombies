package server.game.play;

import actions.Action;
import actions.TurnAction;
import builder.GameStartedReplyBuilder;
import builder.ReplyBuilder;
import builder.TurnReplyBuilder;
import game.Card;
import game.CardTypeEnum;
import org.slf4j.LoggerFactory;
import reply.ErrorReply;
import reply.Reply;
import reply.TurnReply;
import server.game.LobbyManager;
import server.game.UserInfo;
import support.CardWrapper;
import support.GameInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private  static org.slf4j.Logger logger= LoggerFactory.getLogger(GameManager.class);
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
    private int user1Hp = 100;
    private int user2Hp = 100;
    private GameInfo player1Info;
    private GameInfo player2Info;
    Date d1;
    Date d2;


    public GameManager(UserInfo player1, UserInfo player2, LobbyManager lobbyManager) throws IOException {
        this.lobbyManager = lobbyManager;
        table = new GameTable(player1, player2, this);
        player1Name = player1.getUser().getName();
        player2Name = player2.getUser().getName();
        player1Info = new GameInfo();
        player1Info.setEnemyName(player2Name);
        player2Info = new GameInfo();
        player2Info.setEnemyName(player1Name);
        sendGameStartedMessage(player1, player2);
        player1.setManager(this);
        player2.setManager(this);
        this.player1 = player1;
        this.player2 = player2;
        lobbyManager.addGameTicker(new ticker(this, turn.get()), turnSize);
    }

    public void sendGameStartedMessage(UserInfo player1, UserInfo player2) throws IOException {
        logger.info("new game started {} {}",player1Name,player2Name);
        GameStartedReplyBuilder replyBuilder1 = ReplyBuilder.getGameStartedReplyBuilder().setUser(player1.getUser()).setCards(table.getPlayer2Side().getCards().getPlayerHand()).setPosition(1);
        replyBuilder1.setRes(table.getPlayer2Side().getRes1Income(),table.getPlayer2Side().getRes2Income(),table.getPlayer2Side().getRes3Income()) ;
        GameStartedReplyBuilder replyBuilder2 = ReplyBuilder.getGameStartedReplyBuilder().setUser(player2.getUser()).setCards(table.getPlayer1Side().getCards().getPlayerHand()).setPosition(0);
        replyBuilder2.setRes(table.getPlayer1Side().getRes1Income(),table.getPlayer1Side().getRes2Income(),table.getPlayer1Side().getRes3Income()) ;
        lobbyManager.getRequestManager().sendReply(player1.getChannel(), replyBuilder2.build());
        lobbyManager.getRequestManager().sendReply(player2.getChannel(), replyBuilder1.build());

    }

    protected void processTurn(int turnNumber) {
        lock.lock();
        try {
            //Если это тикнул таймер после того как ход уже был отработан, пропускаем обработку
            if (turn.get() != turnNumber) {
                return;
            }
            //регистрируем ход на то чтобы он тикнул через нужное количество секунд, на случай если один из игроков не отвечает
            lobbyManager.addGameTicker(new ticker(this, turn.get()+1), turnSize);

            //Заполняем общие поля
            player1Complete = false;
            player2Complete = false;
            TurnReplyBuilder turnReplyBuilder = ReplyBuilder.getTurnReplyBuilder();
            turnReplyBuilder.setTurnNumber(turn.get());
            turnReplyBuilder.setAction(TurnReply.actionEnum.endturn.ordinal());
            if (turn.get() % 2 == 1) {
                turnReplyBuilder.setNextTurnUser(1);
            } else {
                turnReplyBuilder.setNextTurnUser(0);
            }
            //Заполняем общие поля

            processPlayerActivity(turnReplyBuilder);

            processCardDamage(turnReplyBuilder);

            processCardAbilities(turnReplyBuilder);

            deleteCasualties(turnReplyBuilder);

            sendReply(turnReplyBuilder);


            currTurnAction.clear();

            turn.incrementAndGet();
//            d1=new Date();
//            if(d1!=null && d2!=null)
//            logger.info("turn time {} turn {}",Long.toString(d1.getTime()-d2.getTime()),player1Name+"/"+player2Name+"/"+Integer.toString(turn.get()));
//            d2=new Date();
        }catch (Throwable th){
            th.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void sendReply(TurnReplyBuilder turnReplyBuilder) {
        try {
            if(user1Hp<=0 || player1Surrender){
                turnReplyBuilder.setAction(TurnReply.actionEnum.uwin.ordinal());
                turnReplyBuilder.setInfo(player2Info);
                lobbyManager.getRequestManager().sendReply(player2.getChannel(), turnReplyBuilder.build());
                turnReplyBuilder.setAction(TurnReply.actionEnum.ulose.ordinal());
                turnReplyBuilder.setInfo(player1Info);
                lobbyManager.getRequestManager().sendReply(player1.getChannel(), turnReplyBuilder.build());
                return;
            }

            if(user2Hp<=0 || player2Surrender){
                turnReplyBuilder.setAction(TurnReply.actionEnum.uwin.ordinal());
                turnReplyBuilder.setInfo(player1Info);
                lobbyManager.getRequestManager().sendReply(player1.getChannel(), turnReplyBuilder.build());
                turnReplyBuilder.setAction(TurnReply.actionEnum.ulose.ordinal());
                turnReplyBuilder.setInfo(player2Info);
                lobbyManager.getRequestManager().sendReply(player2.getChannel(), turnReplyBuilder.build());
                return;
            }


            for (int i = 0; i < TableSide.CELL_COUNT; i++) {
                turnReplyBuilder.setPlayer1Cards(i, table.getPlayer1Side().getCell(i).getCards());
                turnReplyBuilder.setPlayer2Cards(i, table.getPlayer2Side().getCell(i).getCards());
            }
            turnReplyBuilder.setCards(table.getPlayer1Side().getCards().getPlayerHand());
            if (turn.get() % 2 == 1) {
                processRes(turnReplyBuilder, table.getPlayer1Side());
            }
            Reply rep1 = turnReplyBuilder.build();
            lobbyManager.getRequestManager().sendReply(player1.getChannel(), rep1);

            turnReplyBuilder.setRes(0,0,0);

            if (turn.get() % 2 == 0) {
                processRes(turnReplyBuilder,table.getPlayer2Side());
            }
            turnReplyBuilder.setCards(table.getPlayer2Side().getCards().getPlayerHand());
            Reply rep2 = turnReplyBuilder.build();
            lobbyManager.getRequestManager().sendReply(player2.getChannel(), rep2);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    private void processCardAbilities(TurnReplyBuilder turnReplyBuilder) {

    }

    private void processCardDamage(TurnReplyBuilder turnReplyBuilder) {
        int spendHpUser1 = 0;
        int spendHpUser2 = 0;
        for (int i = 0; i < TableSide.CELL_COUNT; i++) {
            SideCell p1cell = table.getPlayer1Side().getCell(i);
            SideCell p2cell = table.getPlayer2Side().getCell(i);
            spendHpUser2+= processCell(turnReplyBuilder, p1cell, p2cell,player1Info);
            spendHpUser1+= processCell(turnReplyBuilder, p2cell, p1cell,player2Info);
        }
        if (spendHpUser1 > 0) {
            user1Hp -= spendHpUser1;
        }
        if (spendHpUser2 > 0) {
            user2Hp -= spendHpUser2;
        }
    }

    private void processPlayerActivity(TurnReplyBuilder turnReplyBuilder) {
        for (TurnAction action : currTurnAction) {
            Card c = lobbyManager.getCards().get(action.getCardId());
            TableSide side = null;
            if (turn.get() % 2 == 1) {
                if (CardTypeEnum.getValue(c.getCardType()).equals(CardTypeEnum.damageSpell)) {
                    side = table.getPlayer2Side();
                } else {
                    side = table.getPlayer1Side();
                }
            } else {
                if (CardTypeEnum.getValue(c.getCardType()).equals(CardTypeEnum.damageSpell)) {
                    side = table.getPlayer1Side();
                } else {
                    side = table.getPlayer2Side();
                }
            }
            SideCell cell = side.getCell(action.getPosition());
            switch (CardTypeEnum.getValue(c.getCardType())) {
                case buffSpell:
                    break;
                case damageSpell:
                    break;
                case globalSpell:
                    break;
                default:
                    addCreature(cell, c, turnReplyBuilder);
                    break;
            }
        }
    }

    private void processRes(TurnReplyBuilder turnReplyBuilder,TableSide side){
        turnReplyBuilder.setRes(side.getRes1Income(),side.getRes2Income(),side.getRes3Income());

    }

    private void deleteCasualties(TurnReplyBuilder turnReplyBuilder) {
        for (int i = 0; i < TableSide.CELL_COUNT; i++) {
            SideCell p1cell = table.getPlayer1Side().getCell(i);
            SideCell p2cell = table.getPlayer1Side().getCell(i);
            for (CardWrapper c : p1cell.getCards()) {
                if (c.getHp() == 0) {
                    turnReplyBuilder.addActionInfo(c.getWrapperId(), String.format("%s dead", c.getCard().getName()));
                    if (player2.getUser().getSide() == 0) {
                        player1Info.incSurvivalsKilled();
                    } else {
                        player1Info.incZombieKilled();
                    }
                    player1Info.incDead(c.getCard().getId());
                }
            }

            for (CardWrapper c : p2cell.getCards()) {
                if (c.getHp() == 0) {
                    turnReplyBuilder.addActionInfo(c.getWrapperId(), String.format("%s dead", c.getCard().getName()));
                    if (player1.getUser().getSide() == 0) {
                        player2Info.incSurvivalsKilled();
                    } else {
                        player2Info.incZombieKilled();
                    }
                    player2Info.incDead(c.getCard().getId());
                }
            }
            p1cell.clearDead();

        }

    }

    private int processCell(TurnReplyBuilder turnReplyBuilder, SideCell p1cell, SideCell p2cell,GameInfo info) {
        int sumDmg = 0;
        for (CardWrapper cr : p1cell.getCards()) {
//            if(!cr.isActive()){
//                continue;
//            }
            int damage = cr.getStrength();
            int spend = 0;
            CardWrapper cw = null;
            while ((cw = p2cell.getTopCard()) != null && (spend = p2cell.hit(damage, cw)) > 0) {
                turnReplyBuilder.addActionInfo(cr.getWrapperId(), String.format("%s hit %s on %s damage", cr.getCard().getName(), cw.getCard().getName(), Integer.toString(spend)));
                damage = damage - spend;
                info.incDead(cr.getCard().getId());
            }
            sumDmg += damage;
            if(damage>0){
                turnReplyBuilder.addActionInfo(cr.getWrapperId(), String.format("%s hit player on %s damage", cr.getCard().getName(), Integer.toString(damage)));
            }
        }
        return sumDmg;
    }

    private void addCreature(SideCell cell, Card card, TurnReplyBuilder builder) {
        CardWrapper cr = cell.addCard(card);
        builder.addActionInfo(cr.getWrapperId(), String.format("%s enter the battle", card.getName()));
        if (turn.get() % 2 == 1) {
            player1Info.incUsed(card.getId());
        } else {
            player2Info.incUsed(card.getId());
        }

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
                if (player1Name.equals(player.getUser().getName())) {
                    player1Surrender = true;
                } else {
                    player2Surrender = true;
                }

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

        if (player1Surrender && localTurn % 2 == 1) {
            processTurn(localTurn);
        }
        if (player2Surrender && localTurn % 2 == 0) {
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
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.missing_turn.getId()).setErrorText(String.format("miss turn sended [%d] but current [%d]", ta.getTurnNumber(), turn.get())).build();
                lobbyManager.getRequestManager().sendReply(player1.getChannel(), reply);
                return false;
            } else {
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.missing_turn.getId()).setErrorText(String.format("miss turn sended [%d] but current [%d]",ta.getTurnNumber(),turn.get())).build();
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
         //   logger.info("tick turn started "+Integer.toString(turn)+"   ");
            this.manager = manager;
            this.turn = turn;
        }

        @Override
        public void run() {
           // logger.info("tick turn "+Integer.toString(turn)+"   ");
            manager.processTurn(turn);
        }
    }


}
