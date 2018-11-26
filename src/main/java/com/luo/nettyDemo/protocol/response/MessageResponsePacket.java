package com.luo.nettyDemo.protocol.response;

import com.luo.nettyDemo.protocol.Packet;
import lombok.Data;

import static com.luo.nettyDemo.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
