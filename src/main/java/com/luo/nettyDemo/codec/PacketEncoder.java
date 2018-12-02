package com.luo.nettyDemo.codec;

import com.luo.nettyDemo.protocol.Packet;
import com.luo.nettyDemo.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MessageToByteEncoder 对象转换成二进制
 * 1、自定义编码，不用关心Bytebuf的创建，不用每次向对端写Java对象都要编码
 */
public class PacketEncoder extends MessageToByteEncoder<Packet>{
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        PacketCodec.INSTANCE.encode(out,packet);
    }
}
