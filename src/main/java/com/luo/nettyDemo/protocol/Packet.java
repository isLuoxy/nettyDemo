package com.luo.nettyDemo.protocol;


import lombok.Data;

/**
 *  通信过程的对象
 */
@Data
public abstract class Packet {

    /**
     *  协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return Byte
     */
    public abstract Byte getCommand();
}
