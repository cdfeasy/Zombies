package reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class ErrorReply {
    private String errorText;

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public ErrorReply() {
    }

    public ErrorReply(String errorText) {
        this.errorText = errorText;
    }
}
