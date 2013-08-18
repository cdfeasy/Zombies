package zombies.testclient;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 07.01.13
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    private String message;
    private List<String> receive= Collections.synchronizedList(new ArrayList());
    final ClientHandler ch=new ClientHandler();
    org.jboss.netty.channel.ChannelFuture ftr;
        private final String host;
        private final int port;

        public Client(String host, int port) {
            this.host = host;
            this.port = port;
            ch.setReceive(receive);
        }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        ch.setMessage(message);
    }

    public List<String> getReceive() {
        return receive;
    }

    public void setReceive(List<String> receive) {
        this.receive = receive;
    }

    public void send(){
       ch.send();
    }

    public void run() {

        // Configure the client.
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the pipeline factory.


        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        new DelimiterBasedFrameDecoder(
                                20000, Delimiters.lineDelimiter()),
                        new StringEncoder(),
                        new StringDecoder(),
                        ch);
            }
        });

        // Start the connection attempt.
        ftr= bootstrap.connect(new InetSocketAddress(host, port));
    }

}


