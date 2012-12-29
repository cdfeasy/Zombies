package server;

import com.google.inject.Inject;
import ifree.zombieserver.SendClass;
import org.jboss.netty.channel.*;
import server.game.LobbyManager;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class ServerHandler  extends SimpleChannelUpstreamHandler{

    @Inject
    LobbyManager manager;

    private static final Logger logger = Logger.getLogger(ServerHandler.class.getName());

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
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        manager.parseRequest(e.getChannel(),e.getMessage().toString());
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
