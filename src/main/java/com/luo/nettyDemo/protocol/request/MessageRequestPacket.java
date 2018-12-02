package com.luo.nettyDemo.protocol.request;

import com.luo.nettyDemo.protocol.Packet;

import static com.luo.nettyDemo.protocol.command.Command.MESSAGE_REQUEST;

import com.luo.nettyDemo.protocol.response.MessageResponsePacket;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
