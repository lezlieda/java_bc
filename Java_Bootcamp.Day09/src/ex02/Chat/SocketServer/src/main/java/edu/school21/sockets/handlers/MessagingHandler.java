package edu.school21.sockets.handlers;

import edu.school21.sockets.managers.ChatroomsManager;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.managers.MessagesManager;
import edu.school21.sockets.managers.UsersManager;
import edu.school21.sockets.utils.SocketLogger;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

@ChannelHandler.Sharable
public class MessagingHandler extends ChannelInboundHandlerAdapter {
    private final UsersManager usersManager = UsersManager.getInstance();
    private final MessagesManager messagesManager = MessagesManager.getInstance();
    private final ChatroomsManager chatroomsManager = ChatroomsManager.getInstance();
    private static final SocketLogger logger = SocketLogger.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel incoming = ctx.channel();
        if (msg.toString().equals("Exit")) {
            logger.info("Client: " + incoming.remoteAddress() + " left the chatroom " + chatroomsManager.getChatroomById(usersManager.getUser(incoming).getId()));
            chatroomsManager.leaveChatroom(usersManager.getUser(incoming));
            incoming.writeAndFlush("You have left the chat.\n1. Create room\n2. Choose room\n3. Exit\n");
            ctx.pipeline().addLast(new MenuHandler());
            ctx.pipeline().remove(this);
        } else {
            logger.info("Client: " + incoming.remoteAddress() + " sent: " + msg);
            String message = usersManager.getUsername(incoming) + ": " + msg + "\n";
            for (Channel c : getRecipients(incoming)) {
                c.writeAndFlush(message);
            }
            messagesManager.sendMessage(new Message(null, usersManager.getUser(incoming),
                    chatroomsManager.getChatroomById(chatroomsManager.getChatroomIdByUserId(usersManager.getUser(incoming).getId())),
                    (String) msg, null));
        }
    }

    public ChannelGroup getRecipients(Channel sender) {
        ChannelGroup res = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup allChannels = usersManager.getAllChannels();
        Long chatroomId = chatroomsManager.getChatroomIdByUserId(usersManager.getUser(sender).getId());
        for (Channel c : allChannels) {
            if (chatroomsManager.getUsersInChatroom(chatroomId).contains(usersManager.getUser(c).getId())) {
                res.add(c);
            }
        }
        return res;

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }

}
