package zombies.server.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry
 * Date: 20.10.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class FramePacketDecoder extends FrameDecoder{
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if(buffer.readableBytes() < 4){
            return null;
        }

        int length = buffer.getInt(buffer.readerIndex());
        if(buffer.readableBytes() < length + 4){
            buffer.resetReaderIndex();
            return null;
        }
        buffer.skipBytes(4);
        return new String(buffer.readBytes(length).array());
    }
}
