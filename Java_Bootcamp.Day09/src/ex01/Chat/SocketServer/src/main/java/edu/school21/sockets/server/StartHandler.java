package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class StartHandler extends ChannelInboundHandlerAdapter {
    private final UsersService usersService;
    private final  UsersManager usersManager;

    public StartHandler(UsersService usersService, UsersManager usersManager) {
        this.usersService = usersService;
        this.usersManager = usersManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Hello from server!\n");
        System.out.println("Client: " + incoming.remoteAddress() + " has joined");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String in = (String) msg;
        System.out.println("Client sent: " + in);
        Channel incoming = ctx.channel();
        switch (in) {
            case "signUp":
                incoming.writeAndFlush("Enter login:\n");
                ctx.pipeline().addLast(new SignUpHandler(usersService));
                ctx.pipeline().remove(this);
                break;
            case "signIn":
                incoming.writeAndFlush("Enter login:\n");
                ctx.pipeline().addLast(new SignInHandler(usersService));
                ctx.pipeline().remove(this);
                break;
            case "Exit":
                incoming.writeAndFlush("Goodbye!\n");
                ctx.close();
                break;
            default:
                incoming.writeAndFlush("Unknown command!\n");
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }


}