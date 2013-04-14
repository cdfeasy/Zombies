package zombies.dto.reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.01.13
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class SuccessReply {
    private String successText;

    public String getSuccessText() {
        return successText;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }

    public SuccessReply() {
    }

    public SuccessReply(String successText) {
        this.successText = successText;
    }
}
