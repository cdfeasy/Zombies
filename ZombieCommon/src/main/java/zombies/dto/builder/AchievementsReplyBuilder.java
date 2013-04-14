package zombies.dto.builder;

import zombies.dto.reply.AchievementsReply;
import zombies.dto.reply.UserReply;
import zombies.dto.reply.ReplyTypeEnum;

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
    public UserReply build() {
        UserReply userReply =new UserReply(ReplyTypeEnum.ACHIEVEMENT.getId());
        AchievementsReply gsr= new AchievementsReply();
        gsr.setType(type);
        gsr.setValue(value);
        gsr.setDescription(description);
        gsr.setDescriptionEng(descriptionEng);
        userReply.setAchievementsReply(gsr);
        return userReply;
    }
}