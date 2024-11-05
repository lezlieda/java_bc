package edu.school21.sockets.server;

import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.services.UsersService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
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
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("Hello from server!\n");
        channels.add(incoming);
        logger.info("Client: " + incoming.remoteAddress() + " has joined");
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
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            logger.info("Client: " + incoming.remoteAddress() + " sent message: \"" + msg.toString() + "\"");
            if (channel == incoming) {
                switch (msg.toString()) {
                    case "signUp":
                        signUp(incoming);
                    case "Exit":
                        incoming.writeAndFlush("Goodbye\n");
                        channels.remove(incoming);
                        break;
                    default:
                        incoming.writeAndFlush("Unknown command\n");
                        break;

                }
            } else {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]: " + msg + "\n");
            }
        }
    }

    private void signUp(Channel incoming) {
        String login, password;
        incoming.writeAndFlush("Enter login:\n");
        login = incoming.remoteAddress().toString();
        incoming.writeAndFlush("Enter password:\n");
        password = incoming.remoteAddress().toString();
        logger.info("Client: " + incoming.remoteAddress() + " signed up with login: " + login + " and password: " + password);
    }
}
