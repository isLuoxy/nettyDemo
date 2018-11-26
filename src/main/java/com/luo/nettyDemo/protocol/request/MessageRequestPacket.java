package com.luo.nettyDemo.protocol.request;

import com.luo.nettyDemo.protocol.Packet;

import static com.luo.nettyDemo.protocol.command.Command.MESSAGE_REQUEST;

import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
