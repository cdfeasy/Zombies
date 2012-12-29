package ifree.zombieserver;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 18.12.12
 * Time: 21:48
 * To change this template use File | Settings | File Templates.
 */
public class SendClass {
    org.jboss.netty.channel.Channel c;

    public void send(){
        if(c!=null)
            c.write("BlaBlaBla")  ;
    }
}
