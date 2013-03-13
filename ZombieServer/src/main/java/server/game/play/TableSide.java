package server.game.play;

import game.Deck;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.01.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
public class TableSide {
    public final static int CELL_COUNT=6;
    private SideCell[] cells=new SideCell[CELL_COUNT];
    private SideCell structures;
    private PlayerCards cards;
    private  int res1Income;
    private  int res2Income;
    private  int res3Income;

    private  int res1;
    private  int res2;
    private  int res3;

    private GameManager manager;
    public TableSide(Deck userDeck,GameManager manager){
        this.manager=manager;
       cards=new PlayerCards(userDeck.getDeck());
       for(int i=0;i<CELL_COUNT;i++)
           cells[i]=new SideCell(manager);
        structures=new SideCell(manager);
        res1Income+=cards.getBaseRes1();
        res2Income+=cards.getBaseRes2();
        res3Income+=cards.getBaseRes3();

    }

    public SideCell getCell(int index){
        return cells[index];
    }

    public SideCell[] getCells() {
        return cells;
    }

    public void setCells(SideCell[] cells) {
        this.cells = cells;
    }

    public SideCell getStructures() {
        return structures;
    }

    public void setStructures(SideCell structures) {
        this.structures = structures;
    }

    public PlayerCards getCards() {
        return cards;
    }

    public void setCards(PlayerCards cards) {
        this.cards = cards;
    }

    public int getRes1Income() {
        return res1Income;
    }

    public void setRes1Income(int res1Income) {
        this.res1Income = res1Income;
    }

    public int getRes2Income() {
        return res2Income;
    }

    public void setRes2Income(int res2Income) {
        this.res2Income = res2Income;
    }

    public int getRes3Income() {
        return res3Income;
    }

    public void setRes3Income(int res3Income) {
        this.res3Income = res3Income;
    }

    public int getRes1() {
        return res1;
    }

    public void setRes1(int res1) {
        this.res1 = res1;
    }

    public int getRes2() {
        return res2;
    }

    public void setRes2(int res2) {
        this.res2 = res2;
    }

    public int getRes3() {
        return res3;
    }

    public void setRes3(int res3) {
        this.res3 = res3;
    }
}
