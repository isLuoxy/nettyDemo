package com.luo.nettyDemo.command;

import com.luo.nettyDemo.protocol.Packet;
import com.luo.nettyDemo.protocol.PacketCodec;
import com.luo.nettyDemo.protocol.request.LoginRequestPacket;
import com.luo.nettyDemo.serialize.Serializer;
import com.luo.nettyDemo.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * 测试类
 */

public class PacketCodeTest {

    @Test
    public void encode(){
        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion((byte)1);
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("luoxy");
        loginRequestPacket.setPassword("password");

        PacketCodec packetCodec = new PacketCodec();
        ByteBuf byteBuf = packetCodec.encode(ByteBufAllocator.DEFAULT,loginRequestPacket);
        Packet packet = packetCodec.decode(byteBuf);
        Assert.assertArrayEquals(serializer.serializer(loginRequestPacket),serializer.serializer(packet));

    }
}
