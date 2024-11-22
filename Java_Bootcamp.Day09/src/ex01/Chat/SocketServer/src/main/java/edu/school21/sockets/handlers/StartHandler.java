package edu.school21.sockets.handlers;

import edu.school21.sockets.utils.SocketLogger;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class StartHandler extends ChannelInboundHandlerAdapter {
    private final static SocketLogger logger = SocketLogger.getInstance();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Hello from server!\n");
        logger.info("Client: " + incoming.remoteAddress() + " has connected");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel incoming = ctx.channel();
        switch ((String) msg) {
            case "signUp":
                logger.info("Client " + incoming.remoteAddress() + " trying to sign up.");
                incoming.writeAndFlush("Enter login:\n");
                ctx.pipeline().addLast(new SignUpHandler());
                ctx.pipeline().remove(this);
                break;
            case "signIn":
                logger.info("Client " + incoming.remoteAddress() + " trying to sign in...");
                incoming.writeAndFlush("Enter login:\n");
                ctx.pipeline().addLast(new SignInHandler());
                ctx.pipeline().remove(this);
                break;
            case "Exit":
                incoming.writeAndFlush("Goodbye!\n");
                logger.info("Client: " + incoming.remoteAddress() + " has left.");
                ctx.close();
                break;
            default:
                incoming.writeAndFlush("Unknown command!\n");
                logger.info("Client: " + incoming.remoteAddress() + "sent unknown command: " + msg);
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }
}
