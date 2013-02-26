package reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class ErrorReply {
    public static enum errors{
        missing_turn(10),
        not_you_turn(11);
        int id;

        public int getId() {
            return id;
        }

        errors(int id){
            this.id=id;
        }
    }
    private String errorText;
    private Integer errorCode;

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorReply() {
    }

    public ErrorReply(String errorText) {
        this.errorText = errorText;
    }

    public ErrorReply(String errorText, Integer errorCode) {
        this.errorText = errorText;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ErrorReply{" +
                "errorText='" + errorText + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
