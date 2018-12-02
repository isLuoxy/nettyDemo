package com.luo.nettyDemo.server.handler;


import com.luo.nettyDemo.protocol.request.MessageRequestPacket;
import com.luo.nettyDemo.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 构建消息响应
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setVersion(messageRequestPacket.getVersion());
        messageResponsePacket.setMessage("服务端回复：【 qaq 】");
        System.out.println(new Date() + " 收到客户端消息:" + messageRequestPacket.getMessage());

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
