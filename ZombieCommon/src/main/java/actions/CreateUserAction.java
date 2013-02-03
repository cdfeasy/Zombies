package actions;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 31.01.13
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public class CreateUserAction {
    private String name;
    private String pass;
    private Long side;

    public CreateUserAction() {
    }

    public CreateUserAction(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Long getSide() {
        return side;
    }

    public void setSide(Long side) {
        this.side = side;
    }
}
