package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.services.UsersService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@Qualifier("server")
public class Server {
    private final UsersService usersService;
    private final MessagesRepository messagesRepository;
    public Server(UsersService usersService, MessagesRepository messagesRepository) {
        this.usersService = usersService;
        this.messagesRepository = messagesRepository;
    }

    public void run(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventExecutorGroup eventExecutorGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(io.netty.channel.socket.nio.NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer(usersService, messagesRepository));
            bootstrap.bind(port).sync().channel().closeFuture().sync();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }




}
