package reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 09.03.13
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class AchievementsReply {
    private byte type;
    private int value;
    private String descriptionEng;
    private String description;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
