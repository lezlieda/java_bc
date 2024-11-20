package edu.school21.sockets.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessagingHandler extends ChannelInboundHandlerAdapter {
    private final UsersManager usersManager;

    public MessagingHandler(UsersManager usersManager) {
        this.usersManager = usersManager;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel incoming = ctx.channel();
        if (msg.toString().equals("Exit")) {
            usersManager.logOut(incoming);
            incoming.writeAndFlush("You have left the chat.\n");
            ctx.close();
        } else {
            String message = usersManager.getUsername(incoming) + ": " + msg + "\n";
            for (Channel c : usersManager.getRecipients(incoming)) {
                c.writeAndFlush(message);
            }
        }
    }

}
