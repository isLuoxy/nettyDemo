package com.luo.nettyDemo.codec;


import com.luo.nettyDemo.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 *  基于长度域拆包器
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    /**
     *  这里的7是4+1+1+1，魔数字节+版本号+序列化算法+指令
     */
    private static final int LENGTH_FIELD_OFFSET = 7;

    /**
     *  数据长度为4个字节
     */
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            // 这里通过自定义的拆包器能够保证完整性，每次读取的数据都是完整的，in.getInt()能保证读取最开头数字
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }

}
