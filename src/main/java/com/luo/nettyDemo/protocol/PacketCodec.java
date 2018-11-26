package com.luo.nettyDemo.protocol;

import com.luo.nettyDemo.protocol.request.LoginRequestPacket;
import com.luo.nettyDemo.protocol.request.MessageRequestPacket;
import com.luo.nettyDemo.protocol.response.LoginResponsePacket;
import com.luo.nettyDemo.protocol.response.MessageResponsePacket;
import com.luo.nettyDemo.serialize.Serializer;
import com.luo.nettyDemo.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.luo.nettyDemo.protocol.command.Command.*;

/**
 *  编解码器
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    /**
     * 定义魔数
     */
    private static final int MAGIC_NUMBER = 0x12345678;

    /**
     * key 为指令， value 为指令对应的传输对象
     */
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    /**
     * key 为序列化的算法标识， value 为对应的序列化类
     */
    private final Map<Byte, Serializer> serializerMap;

    /**
     * 初始化
     */
    public PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    /**
     * 编码
     * @param byteBufAllocator  netty 的 ByteBuf 分配器
     * @param packet 传输对象
     * @return ByteBuf
     */
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        // 创建 ByteBuf 对象，利用ioBuffer() 方法会返回适配 io 读写相关的内存，它会尽可能创建一个直接内存
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();

        // 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serializer(packet);

        // 编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * 解码
     * @param byteBuf 二进制数据
     * @return Packet
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数 MAGIC_NUMBER
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 获得序列化算法
        Byte serializerAlgorithm = byteBuf.readByte();

        // 获得指令
        Byte command = byteBuf.readByte();

        // 获得数据包长度
        int length = byteBuf.readInt();

        // 创建一个数组用于存放内容
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserializer(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(Byte serializerAlgorithm) {
        return serializerMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(Byte command) {
        return packetTypeMap.get(command);
    }
}
