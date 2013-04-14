package zombies.entity.server;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 24.12.12
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class History {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(optional=false)
    @JoinColumn(name="player1", nullable=false, updatable=false)
    private User player1;
    @ManyToOne(optional=false)
    @JoinColumn(name="player2", nullable=false, updatable=false)
    private User player2;
    private byte result;
    private byte gameType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public byte getResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }

    public byte getGameType() {
        return gameType;
    }

    public void setGameType(byte gameType) {
        this.gameType = gameType;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", result=" + result +
                ", gameType=" + gameType +
                '}';
    }
}
