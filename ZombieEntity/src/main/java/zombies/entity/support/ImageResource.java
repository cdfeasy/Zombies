package zombies.entity.support;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 02.05.13
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
public class ImageResource {
    private int id;
    private String description;
    private byte[] image;

    public ImageResource(int id, String description, byte[] image) {
        this.id = id;
        this.description = description;
        this.image = image;
    }

    public ImageResource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
