package zombies.server.netty;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class Server {

    private final int port;

    @Inject
    public Server(@Named(value = "port")int port) {
        this.port = port;
    }

    @Inject
    ServerHandler handler;

    private Channel workChannel;

    public void stop(){
        workChannel.close();
    }

    public void run() {
        // Configure the zombies.entity.server.
        ExecutorService bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS);
        ExecutorService ioExec = new OrderedMemoryAwareThreadPoolExecutor(4 /* число рабочих потоков */, 400000000, 2000000000, 60, TimeUnit.SECONDS);

        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        bossExec,
                        ioExec,4));

        // Set up the pipeline factory.
        final FramePacketDecoder decoder = new FramePacketDecoder();
        final FramePacketEncoder encoder = new FramePacketEncoder();

    //    org.jboss.netty.handler.codec.frame. enc=new  DelimiterBasedFrameEncoder();
       // h.c=c;
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        decoder,
                        encoder,
                        handler);
            }
        });

        // Bind and start to accept incoming connections.
        workChannel=bootstrap.bind(new InetSocketAddress(port));

    }

//    public static void main(String[] args) throws Exception {
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8080;
//        }
//        c=new SendClass();
//        new Thread(){
//            @Override
//            public void run(){
//                while(true){
//                    c.send();
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                    }
//                }
//            }
//        } .start();
//        new ObjectEchoServer(port).run();
//    }
}
