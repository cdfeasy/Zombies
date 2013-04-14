/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifree.zombieserver;
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
   * Handles both client-side and zombies.entity.server-side handler depending on which
   * constructor was called.
   */
  public class ObjectEchoServerHandler extends SimpleChannelUpstreamHandler {
      SendClass c;
      private static final Logger logger = Logger.getLogger(
              ObjectEchoServerHandler.class.getName());
  
      private final AtomicLong transferredMessages = new AtomicLong();
  
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
      public void messageReceived(
              ChannelHandlerContext ctx, MessageEvent e) {
          // Echo back the received object to the client.
          transferredMessages.incrementAndGet();
          System.out.println("received+"+e.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ObjectEchoServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        // String res=e.getMessage().toString()+transferredMessages.get();
          c.c=e.getChannel();
         // e.getChannel().write(res);
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