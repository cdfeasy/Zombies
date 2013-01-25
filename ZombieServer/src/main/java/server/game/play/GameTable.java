package server.game.play;

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

    public GameTable() {
        player1Side=new TableSide();
        player2Side=new TableSide();
    }
}
