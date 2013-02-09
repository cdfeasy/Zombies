package server.game.play;

import server.game.UserInfo;
import support.GameInfo;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.01.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
public class GameTable {
    private TableSide player1Side;
    private TableSide player2Side;
    private GameInfo player1Info;
    private GameInfo player2Info;

    public GameTable(UserInfo player1,UserInfo player2) {
        player1Side=new TableSide(player1.getUser().getActiveDeck());
        player2Side=new TableSide(player2.getUser().getActiveDeck());
        player1Info=new GameInfo();
        player1Info.setEnemyName(player2.getUser().getName());
        player2Info=new GameInfo();
        player2Info.setEnemyName(player1.getUser().getName());
    }

    public TableSide getPlayer1Side() {
        return player1Side;
    }

    public void setPlayer1Side(TableSide player1Side) {
        this.player1Side = player1Side;
    }

    public TableSide getPlayer2Side() {
        return player2Side;
    }

    public void setPlayer2Side(TableSide player2Side) {
        this.player2Side = player2Side;
    }
}
