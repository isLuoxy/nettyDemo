package com.luo.nettyDemo.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        ByteBuf byteBuf = (ByteBuf) obj;
        System.out.println(new Date() + "服务端读到的数据" + byteBuf.toString(Charset.forName("utf-8")));

        // 可以回复信息给客户端
        System.out.println(new Date() + "服务端发出数据");
        ByteBuf response = getByteBuf(ctx);
        ctx.channel().writeAndFlush(response);
    }

    public ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        String message = "欢迎访问服务端";
        byte[] bytes = message.getBytes(Charset.forName("utf-8"));
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
