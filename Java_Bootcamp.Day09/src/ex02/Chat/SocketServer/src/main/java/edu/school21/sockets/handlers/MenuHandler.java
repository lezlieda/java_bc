package edu.school21.sockets.handlers;

import edu.school21.sockets.managers.ChatroomsManager;
import edu.school21.sockets.managers.UsersManager;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.utils.SocketLogger;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class MenuHandler extends ChannelInboundHandlerAdapter {
    private int choice = 0;
    private final ChatroomsManager chatroomsManager = ChatroomsManager.getInstance();
    private final UsersManager usersManager = UsersManager.getInstance();
    private final static SocketLogger logger = SocketLogger.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel incoming = ctx.channel();
        if (choice == 0) {
            switch ((String) msg) {
                case "1":
                case "Create room":
                case "create":
                    incoming.writeAndFlush("Enter room name:\n");
                    choice = 1;
                    break;
                case "2":
                case "Choose room":
                case "choose":
                    incoming.writeAndFlush(chatroomsManager.getChatroomList());
                    incoming.writeAndFlush(chatroomsManager.getAllChatrooms().size() + 1 + ". Exit\n");
                    choice = 2;
                    break;
                case "3":
                case "Exit":
                    incoming.writeAndFlush("Goodbye!\n");
                    logger.info("Client: " + incoming.remoteAddress() + " has left.");
                    usersManager.logOut(incoming);
                    ctx.close();
                    break;
                default:
                    incoming.writeAndFlush("Unknown command!\n1. Create room\n2. Choose room\n3. Exit\n");
                    logger.info("Client: " + incoming.remoteAddress() + " sent unknown command: " + msg);
                    break;
            }
        } else if (choice == 1) {
            String roomName = (String) msg;
            if (chatroomsManager.createChatroom(roomName, usersManager.getUser(incoming))) {
                incoming.writeAndFlush("Room created successfully!\n1. Create room\n2. Choose room\n3. Exit\n");
                logger.info("Client: " + incoming.remoteAddress() + " created room: " + roomName);
            } else {
                incoming.writeAndFlush("Room creation failed! Try again.\n");
                logger.info("Client: " + incoming.remoteAddress() + " tried to create room but failed. Disconnecting.");
                ctx.close();
            }
            choice = 0;
        } else if (choice == 2) {
            Long roomId = Long.parseLong((String) msg);
            if (roomId < 1 || roomId > chatroomsManager.getAllChatrooms().size() + 1) {
                incoming.writeAndFlush("Invalid room number! Try again.\n");
                logger.info("Client: " + incoming.remoteAddress() + " tried to join room with invalid number: " + roomId);
                incoming.writeAndFlush(chatroomsManager.getChatroomList());
                incoming.writeAndFlush(chatroomsManager.getAllChatrooms().size() + 1 + ". Exit\n");
            } else if (roomId == chatroomsManager.getAllChatrooms().size() + 1) {
                incoming.writeAndFlush("1. Create room\n2. Choose room\n3. Exit\n");
                choice = 0;
            } else {
                Chatroom chatroom = chatroomsManager.getChatroomById(roomId);
                chatroomsManager.enterChatroom(usersManager.getUser(incoming), chatroom);
                incoming.writeAndFlush("\t---\t" + chatroom.getName() + "\t---\n");
                logger.info("Client: " + incoming.remoteAddress() + " joined room: " + chatroom.getName());
                choice = 0;
                ctx.pipeline().addLast(new MessagingHandler());
                ctx.pipeline().remove(this);
            }

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }

}
