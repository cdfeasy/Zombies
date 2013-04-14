package zombies.entity.server;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 17.02.13
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class GameVersion {
    @Id
    private Integer id;
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
