package server;

import game.Card;
import game.Deck;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import support.DeckInfo;
import support.GameInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 24.12.12
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "UserPlayer")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String pass;
    private Long side;
    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Deck> decks=new ArrayList<>();

    @Transient
    private List<DeckInfo> decksIds;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Card> availableCards =new ArrayList<>();

    @Transient
    private List<Long> availableCardsIds;

    @ManyToOne
    @JoinColumn(name="activedeck_id")
    private Deck activeDeck;

    @OneToOne(cascade = CascadeType.ALL,optional = true)
    private FriendList friendList;

    @Transient
    private DeckInfo activeDeckIds;
    private int level=0;
    private int xp=0;
    private int gold=0;
    private int payed=0;

    private int zombieKilled=0;
    private int survivalsKilled=0;
    @Basic(fetch= FetchType.LAZY)
    @Lob
    private byte[] avatar;

    public User() {
    }

    //1-zzmby 0-surv
    public Long getSide() {
        return side;
    }

    public void setSide(Long side) {
        this.side = side;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<Card> availableCards) {
        this.availableCards = availableCards;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    public int getZombieKilled() {
        return zombieKilled;
    }

    public void setZombieKilled(int zombieKilled) {
        this.zombieKilled = zombieKilled;
    }

    public int getSurvivalsKilled() {
        return survivalsKilled;
    }

    public void setSurvivalsKilled(int survivalsKilled) {
        this.survivalsKilled = survivalsKilled;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<DeckInfo> getDecksIds() {
        return decksIds;
    }

    public void setDecksIds(List<DeckInfo> decksIds) {
        this.decksIds = decksIds;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<Long> getAvailableCardsIds() {
        return availableCardsIds;
    }

    public void setAvailableCardsIds(List<Long> availableCardsIds) {
        this.availableCardsIds = availableCardsIds;
    }
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public DeckInfo getActiveDeckIds() {
        return activeDeckIds;
    }

    public void setActiveDeckIds(DeckInfo activeDeckIds) {
        this.activeDeckIds = activeDeckIds;
    }

    public FriendList getFriendList() {
        return friendList;
    }

    public void setFriendList(FriendList friendList) {
        this.friendList = friendList;
    }

    public User CopyUser(boolean withInfo,boolean privateInfo,boolean full, HashMap<Long,Card> cardMap){
        User u=new User();
        u.setName(this.getName());
        u.setLevel(this.getLevel());
        u.setAvatar(this.getAvatar());
        u.setSide(this.getSide());
        u.setZombieKilled(this.getZombieKilled());
        u.setSurvivalsKilled(this.getSurvivalsKilled());
        if(privateInfo){
         u.setXp(this.getXp());
         u.setGold(this.getGold());
            if(withInfo){
                if(!this.getDecks().isEmpty()) {
                    u.setDecksIds(new ArrayList<DeckInfo>(this.getDecks().size()));
                }
                for(Deck d:this.getDecks()){
                     u.getDecksIds().add(new DeckInfo(d.getId(),d.getDeck()));
                }
                u.setAvailableCardsIds(new ArrayList<Long>(this.getAvailableCards().size()));
                for(Card c:this.getAvailableCards()) {
                    u.getAvailableCardsIds().add(c.getId());
                }
                u.setActiveDeckIds(new DeckInfo(this.getActiveDeck().getId(),this.getActiveDeck().getDeck()));
            }
        }
        if(full){
            u.setXp(this.getXp());
            u.setGold(this.getGold());
            u.setPayed(this.getPayed());
            u.setPass(this.getPass());
            if(!this.getDecks().isEmpty()) {
                u.setDecks(new ArrayList<Deck>());
                for(Deck d:this.getDecks()){
                    Deck dCopy=new Deck();
                    dCopy.setId(d.getId());
                    dCopy.setName(d.getName());
                    dCopy.setDescription(d.getDescription());
                    dCopy.setDeck(new ArrayList<Card>());
                    for(Card c:d.getDeck()){
                       dCopy.getDeck().add(cardMap.get(c.getId()));
                    }
                    u.getDecks().add(dCopy);
                }
            }
            Deck dCopy=new Deck();
            dCopy.setId(this.getActiveDeck().getId());
            dCopy.setName(this.getActiveDeck().getName());
            dCopy.setDescription(this.getActiveDeck().getDescription());
            dCopy.setDeck(new ArrayList<Card>());
            for(Card c:this.getActiveDeck().getDeck()){
                dCopy.getDeck().add(cardMap.get(c.getId()));
            }
            u.setActiveDeck(dCopy);
            u.setAvailableCards(new ArrayList<Card>());
            for(Card c:u.getAvailableCards()){
                u.getAvailableCards().add(cardMap.get(c.getId()));
            }
        }

        return u;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", deck=" + decks +
                ", level=" + level +
                ", xp=" + xp +
                '}';
    }
}
