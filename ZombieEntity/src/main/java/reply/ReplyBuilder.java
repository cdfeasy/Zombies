package reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class ReplyBuilder {
    public static interface Builder{
        Reply build();
    }
    public static class ConnectionReplyBuilder implements Builder{
        String token;
        private String getToken() {
            return token;
        }
        public ConnectionReplyBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        @Override
        public Reply build() {
            Reply reply =new Reply(ReplyTypeEnum.CONNECTION.getId());
            reply.setConnectionReply(new ConnectionReply(getToken()));
            return reply;
        }
    }

    public static class ErrorReplyBuilder implements Builder{
        String errorText;
        public ErrorReplyBuilder setErrorText(String errorText) {
            this.errorText = errorText;
            return this;
        }

        @Override
        public Reply build() {
            Reply reply =new Reply(ReplyTypeEnum.ERROR.getId());
            reply.setErrorReply(new ErrorReply(errorText));
            return reply;
        }
    }




    public static ConnectionReplyBuilder getConnectionReplyBuilder(){
        return new ConnectionReplyBuilder();
    }
    public static ErrorReplyBuilder getErrorReplyBuilderBuilder(){
        return new ErrorReplyBuilder();
    }
}
