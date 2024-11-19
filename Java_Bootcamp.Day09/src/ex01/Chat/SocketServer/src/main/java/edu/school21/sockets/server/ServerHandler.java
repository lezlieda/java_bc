package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.services.UsersService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final ChannelGroup authorizedChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private final Logger logger = LogManager.getLogger();

    @Qualifier("usersService")
    private final UsersService usersService;

    @Qualifier("messagesRepository")
    private final MessagesRepository messagesRepository;


    public ServerHandler(UsersService usersService, MessagesRepository messagesRepository) {
        this.usersService = usersService;
        this.messagesRepository = messagesRepository;
        logger.info("ServerHandler created");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Hello from server!\n");

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
//        Channel incoming = ctx.channel();
////        incoming.writeAndFlush("Hello from server!\n");
//        channels.add(incoming);
//        logger.info("Client: " + incoming.remoteAddress() + " has joined");
    }

    private void startMenu(ChannelHandlerContext ctx, String str) {
        Channel incoming = ctx.channel();
        if (str.equals("Exit")) {
            closeConnection(ctx);
        } else if (str.equals("signUp")) {
            logger.info(str);
            signUp(ctx, str);
        } else if (str.equals("signIn")) {
            logger.info(str);
            incoming.writeAndFlush("sign In\n");
        } else {
            incoming.writeAndFlush("Unknown command!\n");
        }
    }

    private void signUp(ChannelHandlerContext ctx, String str) {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Enter username:\n");
        ByteBuf buf = incoming.read().alloc().buffer();
        System.out.println(buf);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[" + incoming.remoteAddress() + "] has left\n");
        }
        channels.remove(incoming);
        logger.info("Client: " + incoming.remoteAddress() + " has left");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String in = (String) msg;
        try {
            logger.info(in);
            Channel incoming = ctx.channel();
            ByteBuf buf = incoming.alloc().buffer();
            System.out.println(buf);
            startMenu(ctx, in);
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    private void closeConnection(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Goodbye!\n");
        channels.remove(incoming);
        logger.info("Client: " + incoming.remoteAddress() + " was disconnected");
        ctx.close();
    }

    private void signUp(Channel incoming) {
//        String login, password;
//        ChannelFuture future = incoming.remoteAddress().
//        incoming.writeAndFlush("Enter login:\n");
//        login = incoming.remoteAddress().toString();
//        logger.info("Client: " + incoming.remoteAddress() + " signed up with login: " + login);
//        incoming.remoteAddress().notify();
//        System.out.println("login = " + login);
//        incoming.writeAndFlush("Enter password:\n");
//        password = incoming.remoteAddress().toString();
//        logger.info("Client: " + incoming.remoteAddress() + " signed up with login: " + login + " and password: " + password);
    }
}
