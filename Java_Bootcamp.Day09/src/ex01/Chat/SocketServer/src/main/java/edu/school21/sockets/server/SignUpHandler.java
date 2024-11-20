package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class SignUpHandler extends ChannelInboundHandlerAdapter {
    private final UsersService usersService;
    private String login = null;
    private String password = null;
    private final UsersManager usersManager = UsersManager.getInstance();

    public SignUpHandler(UsersService usersService) {
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
            if (usersService.signUp(login, password)) {
                incoming.writeAndFlush("Success!\n");
                ctx.close();
            } else {
                incoming.writeAndFlush("Something went wrong!!\nEnter login:\n");
                login = null;
                password = null;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }
}
