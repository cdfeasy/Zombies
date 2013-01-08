package ifree.zombieserver;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 07.01.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class ClientTest {

    @Test
    public void ConnectionTest() throws InterruptedException {
        Client c=new Client("localhost",18080);
        c.setMessage("{\"name\":\"User1\",\"token\":null,\"action\":0,\"connectAction\":{\"pass\":12345}}");
        c.run();
        Thread.sleep(3000);

     //   c.setMessage("bla");
     //   c.run();

    }
}
