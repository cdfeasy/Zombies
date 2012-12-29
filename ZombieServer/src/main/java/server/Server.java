package server;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import ifree.zombieserver.ObjectEchoServerHandler;
import ifree.zombieserver.SendClass;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

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

    public void run() {
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the pipeline factory.


       // h.c=c;
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        new ObjectEncoder(),
                        new ObjectDecoder(
                                ClassResolvers.cacheDisabled(getClass().getClassLoader())),
                        handler);
            }
        });

        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(port));
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
