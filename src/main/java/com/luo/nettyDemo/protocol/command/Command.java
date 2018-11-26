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

    /**
     *  客户端发送消息
     */
    Byte MESSAGE_REQUEST = 3;

    /**
     *  服务端发送消息
     */
    Byte MESSAGE_RESPONSE = 4;
}
