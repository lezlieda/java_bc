package edu.school21.sockets.server;

import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.services.UsersService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private final MessagesRepository messagesRepository;
    private final UsersService usersService;

    public ServerInitializer(MessagesRepository messagesRepository, UsersService usersService) {
        this.messagesRepository = messagesRepository;
        this.usersService = usersService;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new StartHandler(usersService));

    }
}
