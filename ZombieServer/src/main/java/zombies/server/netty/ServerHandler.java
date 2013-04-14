package zombies.server.netty;

import com.google.inject.Inject;
import org.jboss.netty.channel.*;
import org.slf4j.LoggerFactory;
import zombies.server.game.LobbyManager;

import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class ServerHandler  extends SimpleChannelUpstreamHandler{
    org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());
    @Inject
    LobbyManager manager;


    @Override
    public void handleUpstream(
            ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent &&
                ((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
           // System.out.println(e.toString());
          //  System.out.println(((ChannelStateEvent) e).getValue());
        }
        System.out.println(e.toString());
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) {
        // Echo back the received object to the client.

       // logger.debug("received+" + e.toString());
        System.out.println("onnmessage "+e.toString());
//        if(e.toString().contains("turnAction")) {
//            logger.info(String.format("parse %s, %s, %s", e.toString(),new Date().toString(),Long.toString(new Date().getTime())));
//        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            logger.error("error",ex);
//        }
        manager.parseRequest(e.getChannel(),e.getMessage().toString());
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        if(!e.getCause().getClass().equals(IOException.class)) {
             logger.error("error", e.getCause());
        }
        e.getChannel().close();
    }
}
