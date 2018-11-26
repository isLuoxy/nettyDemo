package com.luo.nettyDemo.message;

import com.luo.nettyDemo.protocol.Packet;
import com.luo.nettyDemo.protocol.PacketCodec;
import com.luo.nettyDemo.protocol.request.LoginRequestPacket;
import com.luo.nettyDemo.protocol.response.LoginResponsePacket;
import com.luo.nettyDemo.protocol.response.MessageResponsePacket;
import com.luo.nettyDemo.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import sun.plugin2.message.Message;

import java.util.Date;
import java.util.UUID;


public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "客户端开始登陆");

        // 创建登陆对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUsername("luoxy");
        loginRequestPacket.setPassword("123456");
        loginRequestPacket.setUserId(UUID.randomUUID().toString());

        // 编码
        ByteBuf requestBuffer = PacketCodec.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 发送给服务端
        ctx.channel().writeAndFlush(requestBuffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        // 判断是否为登录响应对象
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                /* 设置登录标志 */
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + " 客户端登录成功");
            } else {
                System.out.println(new Date() + " 客户端登录失败，原因是：" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + " 收到服务端发送的消息：" + messageResponsePacket.getMessage());
        }
    }
}
