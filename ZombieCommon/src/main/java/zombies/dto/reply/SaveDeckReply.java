package zombies.dto.reply;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.01.13
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */
public class SaveDeckReply {
    private Integer deckId;

    public SaveDeckReply() {
    }

    public SaveDeckReply(Integer deckId) {
        this.deckId = deckId;
    }

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }
}
