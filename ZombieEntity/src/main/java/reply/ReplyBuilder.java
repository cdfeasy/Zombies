package reply;

import game.Fraction;

import java.util.List;

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

    public static class GetCardInfoReplyBuilder implements Builder{
        List<Fraction> fraction;

        public List<Fraction> getFraction() {
            return fraction;
        }

        public GetCardInfoReplyBuilder setFractions(List<Fraction> fractions) {
            this.fraction = fractions;
            return this;
        }

        @Override
        public Reply build() {
            Reply reply =new Reply(ReplyTypeEnum.GET_CARD_INFO.getId());
            reply.setGetCardInfoReply(new GetCardInfoReply(getFraction()));
            return reply;
        }
    }

    public static class SearchReplyBuilder implements Builder{

        @Override
        public Reply build() {
            Reply reply =new Reply(ReplyTypeEnum.SEARCH.getId());
            reply.setSearchReply(new SearchReply());
            return reply;
        }
    }

    public static class StopSearchReplyBuilder implements Builder{

        @Override
        public Reply build() {
            Reply reply =new Reply(ReplyTypeEnum.STOP_SEARCH.getId());
            reply.setStopSearchReply(new StopSearchReply());
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
    public static ErrorReplyBuilder getErrorReplyBuilder(){
        return new ErrorReplyBuilder();
    }
    public static SearchReplyBuilder getSearchReplyBuilder(){
        return new SearchReplyBuilder();
    }
    public static StopSearchReplyBuilder getStopSearchReplyBuilder(){
        return new StopSearchReplyBuilder();
    }

    public static GetCardInfoReplyBuilder getGetCardInfoReplyBuilder(){
        return new GetCardInfoReplyBuilder();
    }
}
