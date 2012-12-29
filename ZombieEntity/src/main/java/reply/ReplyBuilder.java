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
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }


    public static ConnectionReplyBuilder getConnectionReplyBuilder(){
        return new ConnectionReplyBuilder();
    }
}
