package edu.school21.sockets.handlers;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.managers.MessagesManager;
import edu.school21.sockets.managers.UsersManager;
import edu.school21.sockets.utils.SocketLogger;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class MessagingHandler extends ChannelInboundHandlerAdapter {
    private final UsersManager usersManager = UsersManager.getInstance();
    private final MessagesManager messagesManager = MessagesManager.getInstance();
    private static final SocketLogger logger = SocketLogger.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel incoming = ctx.channel();
        if (msg.toString().equals("Exit")) {
            usersManager.logOut(incoming);
            incoming.writeAndFlush("You have left the chat.\n");
            logger.info("Client " + incoming.remoteAddress() + " logged out, disconnecting.");
            ctx.close();
        } else {
            logger.info("Client " + incoming.remoteAddress() + " sent: " + msg);
            String message = usersManager.getUsername(incoming) + ": " + msg + "\n";
            for (Channel c : usersManager.getRecipients(incoming)) {
                c.writeAndFlush(message);
            }
            messagesManager.sendMessage(new Message(usersManager.getUser(incoming), msg.toString()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }

}
