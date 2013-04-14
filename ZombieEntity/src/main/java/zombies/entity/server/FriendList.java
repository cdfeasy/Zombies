package zombies.entity.server;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 03.03.13
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class FriendList {
    @Id
    @GeneratedValue
    private Long id;

//    @OneToOne(mappedBy="friendList")
//    @PrimaryKeyJoinColumn
//    public User person;
    @ElementCollection
    private List<Long> friends=new ArrayList<>();
    @ElementCollection
    private List<Long> sendedRequest=new ArrayList<>();
    @ElementCollection
    private List<Long> receivedRequest=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getFriends() {
        return friends;
    }

    public void setFriends(List<Long> friends) {
        this.friends = friends;
    }

    public List<Long> getSendedRequest() {
        return sendedRequest;
    }

    public void setSendedRequest(List<Long> sendedRequest) {
        this.sendedRequest = sendedRequest;
    }

    public List<Long> getReceivedRequest() {
        return receivedRequest;
    }

    public void setReceivedRequest(List<Long> receivedRequest) {
        this.receivedRequest = receivedRequest;
    }
}
