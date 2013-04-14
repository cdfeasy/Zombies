package ifree.zombieserver; 
import java.net.InetSocketAddress;
  import java.util.concurrent.Executors;
  
 import org.jboss.netty.bootstrap.ServerBootstrap;
  import org.jboss.netty.channel.ChannelPipeline;
  import org.jboss.netty.channel.ChannelPipelineFactory;
  import org.jboss.netty.channel.Channels;
  import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
  import org.jboss.netty.handler.codec.serialization.ClassResolvers;
  import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
  import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

  public class ObjectEchoServer {
      static SendClass c;
      private final int port;
  
      public ObjectEchoServer(int port) {
         this.port = port;
      }
  
      public void run() {
          // Configure the zombies.entity.server.
          ServerBootstrap bootstrap = new ServerBootstrap(
                  new NioServerSocketChannelFactory(
                          Executors.newCachedThreadPool(),
                          Executors.newCachedThreadPool()));
  
          // Set up the pipeline factory.

          final ObjectEchoServerHandler h=new ObjectEchoServerHandler() ;
          h.c=c;
          bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
              public ChannelPipeline getPipeline() throws Exception {
                  return Channels.pipeline(
                          new ObjectEncoder(),
                          new ObjectDecoder(
                                  ClassResolvers.cacheDisabled(getClass().getClassLoader())),
                          h);
              }
          });
  
          // Bind and start to accept incoming connections.
          bootstrap.bind(new InetSocketAddress(port));
      }
  
      public static void main(String[] args) throws Exception {
          int port;
          if (args.length > 0) {
              port = Integer.parseInt(args[0]);
          } else {
              port = 8080;
          }
          c=new SendClass();
          new Thread(){
             @Override
             public void run(){
                  while(true){
                     c.send();
                      try {
                          Thread.sleep(1000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                      }
                  }
             }
          } .start();
          new ObjectEchoServer(port).run();
      }
}