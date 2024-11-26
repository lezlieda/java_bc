package edu.school21.sockets.handlers;

import edu.school21.sockets.managers.UsersManager;
import edu.school21.sockets.utils.SocketLogger;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class SignUpHandler extends ChannelInboundHandlerAdapter {
    private String login = null;
    private String password = null;
    private final UsersManager usersManager = UsersManager.getInstance();
    private final static SocketLogger logger = SocketLogger.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel incoming = ctx.channel();

        if (login == null && password == null) {
            incoming.writeAndFlush("Enter password:\n");
            login = (String) msg;
        } else if (login != null && password == null) {
            password = (String) msg;
            if (usersManager.signUp(login, password)) {
                login = null;
                password = null;
                incoming.writeAndFlush("Success!\n1. signIn\n2. signUp\n3. Exit\n");
                logger.info("Client: " + incoming.remoteAddress() + " signed up successfully as " + login);
            } else {
                incoming.writeAndFlush("Something went wrong!!\n1. signIn\n2. signUp\n3. Exit\n");
                logger.info("Client: " + incoming.remoteAddress() + " had a problem signing up.");
                login = null;
                password = null;
            }
            ctx.pipeline().addLast(new StartHandler());
            ctx.pipeline().remove(this);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }
}
