package com.luo.nettyDemo.server.handler;


import com.luo.nettyDemo.protocol.request.LoginRequestPacket;
import com.luo.nettyDemo.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 继承 SimpleChannelInboundHandler 替代之前的 ChannelInboundHandlerAdapter
 * 1、在 channelRead 中，不需要先进行消息类型转换（(ByteBuf)msg），再解码（decode -> Packet）,判断属于哪种类型的消息(Packet -> _Packet)
 *    通过继承时指定泛型类型<_Packet>能够直接实现，利于扩展。
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + " 收到客户端登录请求");

        /* 构建响应信息 */
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + " 客户端登录成功！");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号或者密码错误");
            System.out.println(new Date() + " 客户端登录失败！");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    /**
     *
     * @param loginRequestPacket
     * @return true 为可以登陆 ，false 为不能登陆
     */
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
