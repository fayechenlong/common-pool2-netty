package com.beeplay;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty 客户端连接对象类
 */
public class NettyChannel {
    private String host;
    private int port;
    private NettyClientHandler nettyClientHandler= new NettyClientHandler();

    NettyChannel(String host, int port){
        try {
            this.host=host;
            this.port=port;
            connect();
        }catch (Exception e){
        }
    }
    private Channel ch;

    public Channel getCh() {
        return ch;
    }

    public void connect() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(nettyClientHandler);
                    }
                });
        ch = b.connect(host, port).sync().channel();
    }
}
