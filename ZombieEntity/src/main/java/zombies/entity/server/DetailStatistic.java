package zombies.entity.server;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.02.13
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DetailStatistic {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(optional=false)
    @JoinColumn(name="userId", nullable=false, updatable=false)
    private User user;
    private Long cardId;
    private Integer killing;
    private Integer dead;
    private Integer used;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Integer getKilling() {
        return killing;
    }

    public void setKilling(Integer killing) {
        this.killing = killing;
    }

    public Integer getDead() {
        return dead;
    }

    public void setDead(Integer dead) {
        this.dead = dead;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "DetailStatistic{" +
                "id=" + id +
                ", user=" + user +
                ", cardId=" + cardId +
                ", killing=" + killing +
                ", dead=" + dead +
                ", used=" + used +
                '}';
    }
}
