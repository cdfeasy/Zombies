package server.game.play;

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
    private  int res1Income;
    private  int res2Income;
    public TableSide(){
       for(int i=0;i<CELL_COUNT;i++)
           cells[i]=new SideCell();
    }

    public SideCell getCell(int index){
        return cells[index];
    }

}
