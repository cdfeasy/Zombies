package zombies.testclient;

import org.jboss.netty.channel.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 33   * Handler implementation for the object echo client.  It initiates the
 * 34   * ping-pong traffic between the object echo client and zombies.entity.server by sending the
 * 35   * first message to the zombies.entity.server.
 * 36
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = Logger.getLogger(
            ClientHandler.class.getName());
    org.jboss.netty.channel.Channel c;
    private String message;
    private List<String> receive;
    private AtomicBoolean isInited = new AtomicBoolean(false);
    private Client.listener listener;
    public void setListener(Client.listener listener){
        this.listener=listener;
    }
    /**
     * Creates a client-side handler.
     */
    public ClientHandler() {
    }
    public void close(){
        c.disconnect();
       c.close();
    }

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

    @Override
    public void handleUpstream(
            ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent &&
                ((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
            // System.out.println(e.toString());
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(
            ChannelHandlerContext ctx, ChannelStateEvent e) {
        // Send the first message if this handler is a client-side handler.
        c = e.getChannel();
        isInited.set(true);
    }

    public void send() {

        while (!isInited.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        System.out.println("send " + message);
        c.write(message + '\n');
    }

    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) {
        // Echo back the received object to the zombies.entity.server.
        if(listener!=null){
            try {
                listener.onMessageReceived(e.getMessage().toString());
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        receive.add(e.getMessage().toString());
        System.out.println("received" + e.toString());
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
