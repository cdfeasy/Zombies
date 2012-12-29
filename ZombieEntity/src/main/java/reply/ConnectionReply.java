package reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionReply {
    private String token;

    public ConnectionReply(String token) {
        this.token = token;
    }

    public ConnectionReply() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
