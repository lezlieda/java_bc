package edu.school21.sockets.handlers;

import edu.school21.sockets.models.User;
import edu.school21.sockets.managers.UsersManager;
import edu.school21.sockets.utils.SocketLogger;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class SignInHandler extends ChannelInboundHandlerAdapter {
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
            User user = usersManager.signIn(login, password);
            if (user != null) {
                if (usersManager.isUserLoggedIn(user)) {
                    incoming.writeAndFlush("User already logged in!!\n");
                    logger.info("Client: " + incoming.remoteAddress() + " tried to sign in but is already logged in. Disconnecting.");
                    ctx.close();
                } else {
                    incoming.writeAndFlush("Success!\n");
                    logger.info("Client: " + incoming.remoteAddress() + " signed in successfully as " + user);
                    usersManager.logIn(incoming, user);
                    incoming.writeAndFlush("1. Create room\n2. Choose room\n3. Exit\n");
                    ctx.pipeline().addLast(new MenuHandler());
                    ctx.pipeline().remove(this);
                }
            } else {
                incoming.writeAndFlush("Incorrect login/password!\n1. signIn\n2. signUp\n3. Exit\n");
                login = null;
                password = null;
                logger.info("Client: " + incoming.remoteAddress() + " tried to sign in but failed.");
                ctx.pipeline().addLast(new StartHandler());
                ctx.pipeline().remove(this);

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }


}
