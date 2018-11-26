package com.luo.nettyDemo.client;

import com.luo.nettyDemo.message.ClientHandler;
import com.luo.nettyDemo.protocol.PacketCodec;
import com.luo.nettyDemo.protocol.request.MessageRequestPacket;
import com.luo.nettyDemo.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class NettyClient {
    static final int MAX_RETRY = 10;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 指定线程模型
                .group(workerGroup)
                // 指定 io 模型
                .channel(NioSocketChannel.class)
                // io 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);

    }

    public static void connect(Bootstrap bootstrap, String host, int port, int retry) {

        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
                Channel channel = ((ChannelFuture) future).channel();
                /* 这里启动控制台线程 读取控制台信息 */
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.out.println("连接次数已经用完，放弃连接");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.out.println(new Date() + "第" + order + "次重连");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel))
                {
                    System.out.println("请输入发送到服务器的信息：");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                    messageRequestPacket.setMessage(line);

                    ByteBuf byteBuf = PacketCodec.INSTANCE.encode(channel.alloc(), messageRequestPacket);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
