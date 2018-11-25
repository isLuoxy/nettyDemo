package com.luo.nettyDemo.protocol.command;


public interface Command {
    /**
     * 登录指令
     */
    Byte LOGIN_REQUEST = 1;

    /**
     * 登录返回指令
     */
    Byte LOGIN_RESPONSE = 2;
}
