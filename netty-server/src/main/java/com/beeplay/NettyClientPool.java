package com.beeplay;

import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class NettyClientPool {
    public static ConcurrentMap<String,ChannelHandlerContext> nettyClients=new ConcurrentHashMap<String,ChannelHandlerContext>();
}
