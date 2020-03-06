package com.beeplay;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;


public class NettyClientHandler extends ChannelDuplexHandler {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg.toString(), CharsetUtil.UTF_8));
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
       ctx.writeAndFlush(Unpooled.copiedBuffer("hi,i am netty client", CharsetUtil.UTF_8));
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf)msg;
        System.out.println("server: "+in.toString(CharsetUtil.UTF_8));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
