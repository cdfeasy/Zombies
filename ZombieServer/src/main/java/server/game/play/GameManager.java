package server.game.play;

import actions.Action;
import actions.TurnAction;
import builder.GameStartedReplyBuilder;
import builder.ReplyBuilder;
import builder.TurnReplyBuilder;
import com.google.inject.Inject;
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
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private UserInfo player1;
    private UserInfo player2;
    private short turnSize = 30;
    private LobbyManager lobbyManager;
    private List<TurnAction> currTurnAction = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private short cardWrapperNum = 0;
    private byte user1Hp = 100;
    private byte user2Hp = 100;
    private GameInfo player1Info;
    private GameInfo player2Info;
    public AtomicBoolean running=new AtomicBoolean(true);

    GameEndProcessor gameEnd;
    AbilitiesProcessor ability;

    Random rand=new Random();


    @Inject
    public GameManager(UserInfo player1, UserInfo player2, LobbyManager lobbyManager,GameEndProcessor gameEnd, AbilitiesProcessor ability) throws IOException {
        this.lobbyManager = lobbyManager;
        this.gameEnd = gameEnd;
        this.ability = ability;
        table = new GameTable(player1, player2, this);
        player1Info = new GameInfo();
        player1Info.setEnemyName(player2.getUser().getName());
        player2Info = new GameInfo();
        player2Info.setEnemyName(player1.getUser().getName());
        sendGameStartedMessage(player1, player2);
        player1.setManager(this);
        player2.setManager(this);
        this.player1 = player1;
        this.player2 = player2;
        lobbyManager.addGameTicker(new ticker(this, turn.get()), turnSize);
    }
    private GameInfo getCurrGameInfo(){
        if (turn.get() % 2 == 1) {
            return player1Info;
        } else {
            return player2Info;
        }
    }

    private UserInfo getCurrPlayerInfo(){
        if (turn.get() % 2 == 1) {
            return player1;
        } else {
            return player2;
        }
    }

    private TableSide getCurrPlayerSide(){
        if (turn.get() % 2 == 1) {
            return table.getPlayer1Side();
        } else {
            return table.getPlayer2Side();
        }
    }

    public void sendGameStartedMessage(UserInfo player1, UserInfo player2) throws IOException {
        logger.info("new game started {} {}",player1.getUser().getName(),player2.getUser().getName());
        GameStartedReplyBuilder replyBuilder1 = ReplyBuilder.getGameStartedReplyBuilder().setUser(player1.getUser()).setCards(table.getPlayer2Side().getCards().getPlayerHand()).setPosition(1);
        replyBuilder1.setRes(table.getPlayer2Side().getRes1Income(),table.getPlayer2Side().getRes2Income(),table.getPlayer2Side().getRes3Income()) ;
        GameStartedReplyBuilder replyBuilder2 = ReplyBuilder.getGameStartedReplyBuilder().setUser(player2.getUser()).setCards(table.getPlayer1Side().getCards().getPlayerHand()).setPosition(0);
        replyBuilder2.setRes(table.getPlayer1Side().getRes1Income(),table.getPlayer1Side().getRes2Income(),table.getPlayer1Side().getRes3Income()) ;
        lobbyManager.getRequestManager().sendReply(player1, replyBuilder2.build());
        lobbyManager.getRequestManager().sendReply(player2, replyBuilder1.build());

    }

    protected void processTurn(int turnNumber) {
        lock.lock();
        try {
            //Если это тикнул таймер после того как ход уже был отработан, пропускаем обработку
            if (turn.get() != turnNumber || !running.get()) {
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
            processPreCardAbilities(turnReplyBuilder);
            processCardDamage(turnReplyBuilder);
            processPostCardAbilities(turnReplyBuilder);
            deleteCasualties(turnReplyBuilder);
            activateCard();

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
                gameEnd.processEnd(player1,player2,player1Info,player2Info,turnReplyBuilder,this);
                return;
            }
            if(user2Hp<=0 || player2Surrender){
                gameEnd.processEnd(player2,player1,player2Info,player1Info,turnReplyBuilder,this);
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
            lobbyManager.getRequestManager().sendReply(player1, rep1);

            turnReplyBuilder.setRes(0,0,0);

            if (turn.get() % 2 == 0) {
                processRes(turnReplyBuilder,table.getPlayer2Side());
            }
            turnReplyBuilder.setCards(table.getPlayer2Side().getCards().getPlayerHand());
            Reply rep2 = turnReplyBuilder.build();
            lobbyManager.getRequestManager().sendReply(player2, rep2);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    private void processPostCardAbilities(TurnReplyBuilder turnReplyBuilder) {
        for (int i = 0; i < TableSide.CELL_COUNT; i++) {
            SideCell p1cell = table.getPlayer1Side().getCell(i);
            SideCell p2cell = table.getPlayer2Side().getCell(i);
            GameManagerSupport.processHeal(turnReplyBuilder, p1cell, player1Info,ability);
            GameManagerSupport.processHeal(turnReplyBuilder, p2cell, player2Info,ability);
        }

    }
    private void processPreCardAbilities(TurnReplyBuilder turnReplyBuilder) {
        for (int i = 0; i < TableSide.CELL_COUNT; i++) {
            SideCell p1cell = table.getPlayer1Side().getCell(i);
            SideCell p2cell = table.getPlayer2Side().getCell(i);
            GameManagerSupport.processSplash(turnReplyBuilder, p1cell, p2cell,player1Info,ability);
            GameManagerSupport.processSplash(turnReplyBuilder, p2cell, p1cell,player2Info,ability);
        }
    }

    private void processCardDamage(TurnReplyBuilder turnReplyBuilder) {
        int spendHpUser1 = 0;
        int spendHpUser2 = 0;
        for (int i = 0; i < TableSide.CELL_COUNT; i++) {
            SideCell p1cell = table.getPlayer1Side().getCell(i);
            SideCell p2cell = table.getPlayer2Side().getCell(i);
            spendHpUser2+= GameManagerSupport.processCell(turnReplyBuilder, p1cell, p2cell,player1Info,ability);
            spendHpUser1+= GameManagerSupport.processCell(turnReplyBuilder, p2cell, p1cell,player2Info,ability);
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
            SideCell p2cell = table.getPlayer2Side().getCell(i);
            for (CardWrapper c : p1cell.getCards()) {
                if (c.getHp() == 0) {
                    turnReplyBuilder.addActionInfo((int)c.getWrapperId(), String.format("%s dead", c.getCard().getName()));
                    if (player1.getUser().getSide() == 0) {
                        player2Info.incSurvivalsKilled();
                        //убитый зараженный мирный житель становится зомби
                        if(c.getVirus()>0){
                           if(rand.nextInt(100)<c.getVirus()){
                               addCreature(p2cell,lobbyManager.getCards().get(0l),turnReplyBuilder);
                               c.setVirus((byte)0);
                           }
                        }

                    } else {
                        player2Info.incZombieKilled();
                    }
                    player1Info.incDead(c.getCard().getId());
                }
            }

            for (CardWrapper c : p2cell.getCards()) {
                if (c.getHp() == 0) {
                    turnReplyBuilder.addActionInfo((int)c.getWrapperId(), String.format("%s dead", c.getCard().getName()));
                    if (player2.getUser().getSide() == 0) {
                        player1Info.incSurvivalsKilled();
                        //убитый зараженный мирный житель становится зомби
                        if(c.getVirus()>0){
                            if(rand.nextInt(100)<c.getVirus()){
                                addCreature(p1cell,lobbyManager.getCards().get(0l),turnReplyBuilder);
                                c.setVirus((byte)0);
                            }
                        }
                    } else {
                        player1Info.incZombieKilled();
                    }
                    player2Info.incDead(c.getCard().getId());
                }
            }


            processZombyfication(player1,player2Info,p2cell,turnReplyBuilder);
            processZombyfication(player2,player1Info,p1cell,turnReplyBuilder);
            p1cell.clearDead();
            p2cell.clearDead();

        }

    }

    private void  processZombyfication(UserInfo player, GameInfo info,SideCell cell,TurnReplyBuilder turnReplyBuilder){
        if (player.getUser().getSide() == 0) {
            for (CardWrapper c : cell.getCards()){
                if(c.getVirus()>0){
                    c.setVirus((byte)(c.getVirus()+5));
                }
                if(c.getVirus()>=100){
                    info.incSurvivalsKilled();
                    //убитый зараженный мирный житель становится зомби
                    addCreature(cell,lobbyManager.getCards().get(0l),turnReplyBuilder);
                }
            }
        }

    }

    private void activateCard() {
        for(SideCell sc:getCurrPlayerSide().getCells()){
           for(CardWrapper cw:sc.getCards()){
               cw.setActive(true);
           }
        }

    }



    private void addCreature(SideCell cell, Card card, TurnReplyBuilder builder) {
        CardWrapper cr = cell.addCard(card);
        builder.addActionInfo((int)cr.getWrapperId(), String.format("%s enter the battle", card.getName()));
        getCurrGameInfo().incUsed(card.getId());
    }

    public void addOrder(UserInfo player, Action order) throws IOException {

        int localTurn = turn.get();
        lock.lock();
        try {
            TurnAction ta = order.getTurnAction();

            if (!checkTurnNumberCorrect(player, ta)) {
                return;
            }

            if (!checkCorrectTurn(player)) {
                return;
            }

            if (ta.getAction() == TurnAction.actionEnum.turn.ordinal()) {
                currTurnAction.add(ta);
                if (turn.get() % 2 == 1) {
                    Reply reply = ReplyBuilder.getTurnReplyBuilder().setAction(ta.getAction()).setCardId(ta.getCardId()).setPosition(ta.getPosition()).setTurnNumber(turn.get()).build();
                    lobbyManager.getRequestManager().sendReply(player2, reply);
                } else {
                    Reply reply = ReplyBuilder.getTurnReplyBuilder().setAction(ta.getAction()).setCardId(ta.getCardId()).setPosition(ta.getPosition()).setTurnNumber(turn.get()).build();
                    lobbyManager.getRequestManager().sendReply(player1, reply);
                }
            }
            if (ta.getAction() == TurnAction.actionEnum.endturn.ordinal()) {
                if (turn.get() % 2 == 1) {
                    player1Complete = true;
                } else {
                    player2Complete = true;
                }
            }
            if (ta.getAction() == TurnAction.actionEnum.surrender.ordinal()) {
                if (turn.get() % 2 == 1) {
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
                Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.missing_turn.getId()).setErrorText(String.format("miss turn sended [%d] but current [%d]", ta.getTurnNumber(), turn.get())).build();
                lobbyManager.getRequestManager().sendReply(player, reply);
                return false;
        }
        return true;
    }

    public int getCardWrapperNum() {
        return cardWrapperNum;
    }

    public short incrementAndGetCardWrapperNum() {
        return cardWrapperNum++;
    }

    /**
     * Проверка что игрок ходит в свой ход
     *
     * @param player
     * @return
     * @throws IOException
     */
    private boolean checkCorrectTurn(UserInfo player) throws IOException {
        boolean flag= !(getCurrPlayerInfo().getId()==player.getId());
        if (flag) {
            Reply reply = ReplyBuilder.getErrorReplyBuilder().setErrorCode(ErrorReply.errors.not_you_turn.getId()).setErrorText("not you turn").build();
            lobbyManager.getRequestManager().sendReply(player, reply);
            return false;
        }
        return !flag;
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
