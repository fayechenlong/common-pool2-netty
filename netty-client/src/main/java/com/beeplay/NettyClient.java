package com.beeplay;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NettyClient {
    private static final String HOST = System.getProperty("host", "127.0.0.1");
    private static final int PORT = Integer.parseInt(System.getProperty("port", "8000"));

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            System.out.println("Enter message (quit to end)");

            GenericObjectPoolConfig config=new GenericObjectPoolConfig();

            config.setMaxIdle(10);//最大活跃数
            config.setMinIdle(1);//最小活跃数
            config.setMaxTotal(100);//最大总数

            ChannelPool channelPool=new ChannelPool(config,HOST,PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader((System.in)));
            for (;;) {
                NettyChannel nettyChannel=channelPool.getResource();
                final String input = in.readLine();
                final String line = input != null? input.trim() : null;
                if (line == null || "quit".equalsIgnoreCase(line)) {
                    channelPool.returnResource(nettyChannel);
                    break;
                }else if (line.isEmpty()) {
                    continue;
                }
                nettyChannel.getCh().writeAndFlush(line);
                channelPool.returnResource(nettyChannel);
            }
        }finally {
            group.shutdownGracefully();
        }
    }
}
