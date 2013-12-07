package zombies.server.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import zombies.dto.reply.UserReply;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry
 * Date: 20.10.13
 * Time: 21:05
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable
public class FramePacketEncoder extends OneToOneEncoder {
    public static ChannelBuffer encodeMessage(String message)
            throws IllegalArgumentException {
        // verify that no fields are set to null

        // version(1b) + type(1b) + payload length(4b) + payload(nb)
        byte[] msg=null;
        try {
            msg=message.getBytes("utf-8");
        } catch (Exception e) {
            msg=message.getBytes();
        }

        int size = 4 + msg.length;

        ChannelBuffer buffer = ChannelBuffers.buffer(size);
        buffer.writeInt(msg.length);
        buffer.writeBytes(msg);
        return buffer;
    }
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        return encodeMessage((String)msg);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
