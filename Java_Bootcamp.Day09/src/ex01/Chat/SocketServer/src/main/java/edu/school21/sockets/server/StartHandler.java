package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class StartHandler extends ChannelInboundHandlerAdapter {
    private final UsersService usersService;

    public StartHandler(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Hello from server!\n");
        System.out.println("Client: " + incoming.remoteAddress() + " has joined");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        String in = (String) msg;
        System.out.println("Client sent: " + in);
        Channel incoming = ctx.channel();
        if (!in.equals("signUp")) {
            incoming.writeAndFlush("Unknown command!\n");
        } else {
            incoming.writeAndFlush("Enter login:\n");
            ctx.pipeline().addLast(new SignUpHandler(usersService));
            ctx.pipeline().remove(this);

        }
    }


}
