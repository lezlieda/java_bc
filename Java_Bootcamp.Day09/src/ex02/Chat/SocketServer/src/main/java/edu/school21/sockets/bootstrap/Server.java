package edu.school21.sockets.bootstrap;

import edu.school21.sockets.utils.SocketLogger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("server")
public class Server {
    private static final SocketLogger logger = SocketLogger.getInstance();
    public void run(int port) {
        logger.info("Starting server on port {}", port);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(io.netty.channel.socket.nio.NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());
            bootstrap.bind(port).sync().channel().closeFuture().sync();
            logger.info("Server started successfully on port {}", port);
        } catch (Exception e) {
            logger.error("Error occurred while starting the server: {}", e);
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            logger.info("Server stopped successfully");
        }
    }
}
