package ifree.zombieserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
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
 34   * ping-pong traffic between the object echo client and server by sending the
 35   * first message to the server.
 36   */
public class ClientHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = Logger.getLogger(
            ObjectEchoClientHandler.class.getName());

    private String message;
    private List<String> receive;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getReceive() {
        return receive;
    }

    public void setReceive(List<String> receive) {
        this.receive = receive;
    }

    /**
     * Creates a client-side handler.
     */
    public ClientHandler() {
    }

    @Override
    public void handleUpstream(
            ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent &&
                ((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
           // System.out.println(e.toString());
        }
        super.handleUpstream(ctx, e);
    }
    org.jboss.netty.channel.Channel c;
    private AtomicBoolean isInited=new AtomicBoolean(false);
    @Override
    public void channelConnected(
            ChannelHandlerContext ctx, ChannelStateEvent e) {
        // Send the first message if this handler is a client-side handler.
         c=e.getChannel();
        isInited.set(true);
    }

    public void send(){

            while (!isInited.get()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        System.out.println("send "+ message);
        c.write(message+'\n');
    }

    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) {
        // Echo back the received object to the server.
        receive.add(e.getMessage().toString());
        System.out.println("received"+e.toString());
        // e.getChannel().write(e.getMessage());
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.",
                e.getCause());
        e.getChannel().close();
    }
}
