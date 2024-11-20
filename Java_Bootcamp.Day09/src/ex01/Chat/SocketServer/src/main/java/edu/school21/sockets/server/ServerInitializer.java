package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Qualifier;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Qualifier("usersService")
    private final UsersService usersService;

    private final UsersManager usersManager;

    public ServerInitializer(UsersService usersService, UsersManager usersManager) {
        this.usersService = usersService;
        this.usersManager = usersManager;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new StartHandler(usersService, usersManager));

    }
}
