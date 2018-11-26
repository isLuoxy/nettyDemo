package com.luo.nettyDemo.message;

import com.luo.nettyDemo.protocol.Packet;
import com.luo.nettyDemo.protocol.PacketCodec;
import com.luo.nettyDemo.protocol.request.LoginRequestPacket;
import com.luo.nettyDemo.protocol.request.MessageRequestPacket;
import com.luo.nettyDemo.protocol.response.LoginResponsePacket;
import com.luo.nettyDemo.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import sun.plugin2.message.Message;

import java.util.Date;


public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket) {
            // 判断是否为登录对象
            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;

            // 创建返回对象
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(requestPacket.getVersion());

            // 判断这个登录对象包含的信息是否正确
            if (valid(requestPacket)) {
                // 如果验证成功，构建返回对象
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + " 用户登陆成功");
            } else {
                loginResponsePacket.setReason("账号或者密码错误");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + " 登录不成功");
            }

            // 把登录对象发送给客户端
            ByteBuf responseBuffer = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseBuffer);
        } else if (packet instanceof MessageRequestPacket) {
            // 如果是客户端发送的消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            // 建立一个服务端发送消息对象
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(new Date() + " 服务器收到客户端发来的信息：" + messageRequestPacket.getMessage());
            messageResponsePacket.setMessage("【服务器已收到消息:" + messageRequestPacket.getMessage() + " 】");
            ByteBuf messageResponseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(messageResponseByteBuf);
        }
    }

    public boolean valid(LoginRequestPacket packet) {
        // 一般是从将数据对比数据库的信息，正确的话返回 true，否则返回 false ，这里为了演示直接返回 true
        return true;
    }
}
