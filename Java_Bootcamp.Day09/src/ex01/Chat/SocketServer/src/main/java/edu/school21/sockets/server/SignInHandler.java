package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SignInHandler extends ChannelInboundHandlerAdapter {
    private String login = null;
    private String password = null;
    private final UsersService usersService;
    private final UsersManager usersManager = UsersManager.getInstance();
    public SignInHandler(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel incoming = ctx.channel();
        if (login == null && password == null) {
            incoming.writeAndFlush("Enter password:\n");
            login = (String) msg;
        } else if (login != null && password == null) {
            password = (String) msg;
            User user = usersService.signIn(login, password);
            if (user != null) {
                if (usersManager.isUserLoggedIn(user)) {
                    incoming.writeAndFlush("User already logged in!!\n");
                    ctx.close();
                }
                incoming.writeAndFlush("Success!\n");
                usersManager.logIn(incoming, user);
                ctx.pipeline().addLast(new MessagingHandler(usersManager));
                ctx.pipeline().remove(this);
            } else {
                incoming.writeAndFlush("Incorrect login/password!\n");
                ctx.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }


}
