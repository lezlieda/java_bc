package edu.school21.sockets.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

//    discard server
public class TestServerHandler extends ChannelInboundHandlerAdapter {
    Logger logger = LogManager.getLogger();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable err) {
        logger.error(err);
        err.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
            logger.info(msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

//    public static void main(String[] args) {
//        Logger LOGGER = LogManager.getLogger();
//        LOGGER.info("Hello, World!");
//    }
}

