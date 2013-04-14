/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifree.zombieserver;


  
  import java.util.ArrayList;
  import java.util.List;
  import java.util.concurrent.atomic.AtomicLong;
  import java.util.logging.Level;
  import java.util.logging.Logger;
  
  import org.jboss.netty.channel.ChannelEvent;
  import org.jboss.netty.channel.ChannelHandlerContext;
  import org.jboss.netty.channel.ChannelState;
  import org.jboss.netty.channel.ChannelStateEvent;
  import org.jboss.netty.channel.ExceptionEvent;
  import org.jboss.netty.channel.MessageEvent;
  import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
  
  /**
33   * Handler implementation for the object echo client.  It initiates the
34   * ping-pong traffic between the object echo client and zombies.entity.server by sending the
35   * first message to the zombies.entity.server.
36   */
  public class ObjectEchoClientHandler extends SimpleChannelUpstreamHandler {
  
      private static final Logger logger = Logger.getLogger(
              ObjectEchoClientHandler.class.getName());
  
      private final String firstMessage;
      private final AtomicLong transferredMessages = new AtomicLong();
  
      /**
       * Creates a client-side handler.
       */
      public ObjectEchoClientHandler(int firstMessageSize) {
          if (firstMessageSize <= 0) {
              throw new IllegalArgumentException(
                      "firstMessageSize: " + firstMessageSize);
          }
          firstMessage = "babla"+transferredMessages.get();
         
      }
  
      public long getTransferredMessages() {
          return transferredMessages.get();
      }
  
      @Override
      public void handleUpstream(
              ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
          if (e instanceof ChannelStateEvent &&
             ((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
              System.out.println(e.toString());
          }
          super.handleUpstream(ctx, e);
      }
  
      @Override
      public void channelConnected(
              ChannelHandlerContext ctx, ChannelStateEvent e) {
          // Send the first message if this handler is a client-side handler.
          System.out.println("send "+ firstMessage);
              e.getChannel().write(firstMessage);
      }
  
      @Override
      public void messageReceived(
              ChannelHandlerContext ctx, MessageEvent e) {
          // Echo back the received object to the zombies.entity.server.
          transferredMessages.incrementAndGet();
           System.out.println("received"+e.toString());
         // e.getChannel().write(e.getMessage());
      }
  
      @Override
      public void exceptionCaught(
              ChannelHandlerContext ctx, ExceptionEvent e) {
          logger.log(
                  Level.WARNING,
                  "Unexpected exception from downstream.",
                  e.getCause());
          e.getChannel().close();
      }
}
 