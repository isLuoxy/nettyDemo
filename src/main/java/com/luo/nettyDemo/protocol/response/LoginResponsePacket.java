package com.luo.nettyDemo.protocol.response;

import com.luo.nettyDemo.protocol.Packet;
import lombok.Data;

import static com.luo.nettyDemo.protocol.command.Command.LOGIN_RESPONSE;

/**
 * 登录响应对象
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }

}
