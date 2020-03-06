package com.beeplay;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf)msg;
        String re=in.toString(CharsetUtil.UTF_8);
        System.out.println("Server received: "+re);
        /**
         * 价值一个亿的人工智能代码
         */
        re=re.replace("吗","");
        re=re.replace("？","！");
        re=re.replace("？","！");
        ctx.writeAndFlush(Unpooled.copiedBuffer(re, CharsetUtil.UTF_8));
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String channelid=ctx.channel().id().toString();
        System.out.println("client connect;address:" + ctx.channel().remoteAddress()+" id:"+channelid);
        NettyClientPool.nettyClients.put(channelid,ctx);
        System.out.println("client count:"+ NettyClientPool.nettyClients.size());
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        ctx.close();
        String channelid=ctx.channel().id().toString();
        NettyClientPool.nettyClients.remove(channelid);
        System.out.println("client count:"+ NettyClientPool.nettyClients.size());
    }
    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise){
        ctx.disconnect(promise);
        String channelid=ctx.channel().id().toString();
        NettyClientPool.nettyClients.remove(channelid);
        System.out.println("client count:"+ NettyClientPool.nettyClients.size());
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise)  {
        ctx.close(promise);
        String channelid=ctx.channel().id().toString();
        NettyClientPool.nettyClients.remove(channelid);
        System.out.println("client count:"+ NettyClientPool.nettyClients.size());
    }
}
