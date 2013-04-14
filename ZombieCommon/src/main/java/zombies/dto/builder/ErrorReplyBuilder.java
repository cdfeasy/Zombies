package zombies.dto.builder;

import zombies.dto.reply.ErrorReply;
import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 27.01.13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class ErrorReplyBuilder  implements Builder{
    String errorText;
    Integer errorCode=0;
    public ErrorReplyBuilder setErrorText(String errorText) {
        this.errorText = errorText;
        return this;
    }
    public ErrorReplyBuilder setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    @Override
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.ERROR.getId());
        userReply.setErrorReply(new ErrorReply(errorText,errorCode));
        return userReply;
    }
}