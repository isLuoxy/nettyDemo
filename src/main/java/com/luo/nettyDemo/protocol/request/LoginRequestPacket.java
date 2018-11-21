package com.luo.nettyDemo.protocol.request;

import com.luo.nettyDemo.protocol.Packet;
import lombok.Data;

import static com.luo.nettyDemo.protocol.command.Command.LOGIN_REQUEST;

/**
 *  定义登陆对象
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
