package edu.school21.sockets.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

// Simple Echo server handler
@ChannelHandler.Sharable
public class TestServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        System.out.println("Connected user: " + ctx.channel().remoteAddress());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("Hello from server!");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        Channel incoming = ctx.channel();
        incoming.writeAndFlush(msg);
        ChannelFuture future = incoming.read().closeFuture();
        future.addListener((ChannelFutureListener) f -> {
            if (!f.isSuccess()) {
                f.cause().printStackTrace();
                f.channel().close();
            }
        });
        if (future.isSuccess())
            incoming.writeAndFlush("dddddddddddddddddddddddddddddddddd");

    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        Channel channel = ctx.channel();
//        ByteBuf buf = Unpooled.EMPTY_BUFFER;
//        ChannelFuture cf = channel.writeAndFlush(buf);
//        cf.addListener((ChannelFutureListener) channelFuture -> {
//            if (channelFuture.isSuccess()) {
//                logger.info("write successful!");
//            } else {
//                logger.error("write error!");
//                channelFuture.cause().printStackTrace();
//            }
//        });
////        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
////                .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//        logger.info("channelReadComplete done");
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable err) {
        err.printStackTrace();
        ctx.close();
    }


}

