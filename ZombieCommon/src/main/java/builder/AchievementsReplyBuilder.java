package builder;

import game.Card;
import reply.AchievementsReply;
import reply.GameStartedReply;
import reply.Reply;
import reply.ReplyTypeEnum;
import server.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 09.03.13
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
public class AchievementsReplyBuilder implements Builder{
    private byte type;
    private int value;
    private String descriptionEng;
    private String description;




    public AchievementsReplyBuilder setType(byte type) {
        this.type = type;
        return this;
    }
    public AchievementsReplyBuilder setValue(int value) {
        this.value = value;
        return this;
    }
    public AchievementsReplyBuilder setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
        return this;
    }
    public AchievementsReplyBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

       @Override
    public Reply build() {
        Reply reply =new Reply(ReplyTypeEnum.ACHIEVEMENT.getId());
        AchievementsReply gsr= new AchievementsReply();
        gsr.setType(type);
        gsr.setValue(value);
        gsr.setDescription(description);
        gsr.setDescriptionEng(descriptionEng);
        reply.setAchievementsReply(gsr);
        return reply;
    }
}