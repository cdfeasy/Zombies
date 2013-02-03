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
    public final static int CELL_COUNT=8;
    private SideCell[] cells=new SideCell[CELL_COUNT];
    private SideCell structures;
    private PlayerCards cards;
    private  int res1Income;
    private  int res2Income;
    private  int res3Income;
    public TableSide(Deck userDeck){
       cards=new PlayerCards(userDeck.getDeck());
       for(int i=0;i<CELL_COUNT;i++)
           cells[i]=new SideCell();
        structures=new SideCell();
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
}
