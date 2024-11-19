package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Qualifier;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Qualifier("usersService")
    private final UsersService usersService;

    public ServerHandler(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Hello from server!\n");
        channels.add(incoming);
        System.out.println("Client: " + incoming.remoteAddress() + " has joined");
        startMenu(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String in = (String) msg;
        try {
            System.out.println("Message received: " + in);
            Channel incoming = ctx.channel();

        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    private void startMenu(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        String str = incoming.read().toString();
        if (str.equals("Exit")) {
            incoming.writeAndFlush("Goodbye!\n");
            channels.remove(incoming);
            ctx.close();
        } else if (str.equals("signUp")) {
            signUp(ctx, str);
        } else {
            incoming.writeAndFlush("Unknown command!\n");
        }
    }

    private void signUp(ChannelHandlerContext ctx, String str) {
        String login, password;
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Enter login:\n");
        login = incoming.read().toString();
        System.out.println("Client: " + incoming.remoteAddress() + " signed up with login: " + login);
//        incoming.remoteAddress().notify();
        System.out.println("login = " + login);
        incoming.writeAndFlush("Enter password:\n");
        password = incoming.read().toString();
        if (usersService.signUp(login, password)) {
            System.out.println("Client: " + incoming.remoteAddress() + " signed up with login: " + login + " and password: " + password);
            incoming.writeAndFlush("Goodbye\n");
            ctx.close();
        } else {
            System.out.println("An error occurred!");
        }
    }
}

