package com.lk.qos.dome;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class TelnetProcessHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("你收到的消息：" + s);

        channelHandlerContext.writeAndFlush( "\r\n" + QosProcessHandler.prompt);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;
            System.out.println(idleStateEvent.state());
            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                System.out.println("已经 10 秒没有发送信息！");
                //向服务端发送消息
                //ctx.writeAndFlush("心跳包").addListener(ChannelFutureListener.CLOSE_ON_FAILURE) ;
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}