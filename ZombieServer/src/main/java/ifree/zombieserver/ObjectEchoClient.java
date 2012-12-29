package ifree.zombieserver;
  
  import java.net.InetSocketAddress;
  import java.util.concurrent.Executors;
  
  import org.jboss.netty.bootstrap.ClientBootstrap;
  import org.jboss.netty.channel.ChannelPipeline;
  import org.jboss.netty.channel.ChannelPipelineFactory;
  import org.jboss.netty.channel.Channels;
  import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
  import org.jboss.netty.handler.codec.serialization.ClassResolvers;
  import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
  import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
  
  /**
   * Modification of {@link EchoClient} which utilizes Java object serialization.
   */
  public class ObjectEchoClient {
  
      private final String host;
      private final int port;
      private final int firstMessageSize;
  
      public ObjectEchoClient(String host, int port, int firstMessageSize) {
          this.host = host;
          this.port = port;
          this.firstMessageSize = firstMessageSize;
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
                          new ObjectEncoder(),
                          new ObjectDecoder(
                                 ClassResolvers.cacheDisabled(getClass().getClassLoader())),
                          new ObjectEchoClientHandler(firstMessageSize));
              }
          });
  
          // Start the connection attempt.
          bootstrap.connect(new InetSocketAddress(host, port));
      }
  
      public static void main(String[] args) throws Exception {
          // Print usage if no argument is specified.
      
         // Parse options.
         final String host = "localhost";
         final int port = 8080;
          final int firstMessageSize;
 
        if (args.length == 3) {
          firstMessageSize = Integer.parseInt(args[2]);
        } else {
              firstMessageSize = 256;
          }
 
          new ObjectEchoClient(host, port, firstMessageSize).run();
      }
  }
