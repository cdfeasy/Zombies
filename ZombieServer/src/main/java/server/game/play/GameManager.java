package server.game.play;

import actions.Action;
import game.Card;
import game.Deck;
import server.User;
import server.game.UserInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class GameManager {
    private GameTable table;

    public GameManager(UserInfo player1,UserInfo player2)  {
        table=new GameTable(player1, player2);
    }

    public void addOrder(UserInfo player1,Action order){

    }


}
