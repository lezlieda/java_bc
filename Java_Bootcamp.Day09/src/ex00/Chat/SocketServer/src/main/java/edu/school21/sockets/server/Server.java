package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.channel.oio.OioEventLoopGroup;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Component
//@Qualifier("server")
//public class Server {
//    private final UsersService usersService;
//    static ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//    @Autowired
//    public Server(@Qualifier("usersService") UsersService usersService) {
//        this.usersService = usersService;
//    }
//
//    public void start(int port) {
//        try {
//            ServerSocket server = new ServerSocket(port);
//            while (!server.isClosed()) {
//                Socket client = server.accept();
//                executorService.execute(new ClientHandler(client, usersService));
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}

@Component
@Qualifier("server")
public class Server {
    private final UsersService usersService;

    @Autowired
    public Server(@Qualifier("usersService")UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(OioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ServerInitializer(usersService));
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }
}