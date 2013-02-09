package reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 09.01.13
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class SearchReply {
    private String message;

    public SearchReply() {
    }

    public SearchReply(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
