package com.lk.qos.dome;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class QosProcessHandler extends ByteToMessageDecoder {

    private String logo;

    public static String prompt = "你的命令>";

    public QosProcessHandler(String logo) {
        this.logo = logo;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        if (logo != null) {
            ctx.write(Unpooled.wrappedBuffer(logo.getBytes()));
            ctx.writeAndFlush(Unpooled.wrappedBuffer(prompt.getBytes()));
        }

    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 1) {
            return;
        }

        final int magic = byteBuf.getByte(byteBuf.readerIndex());

        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        pipeline.remove(this);
    }
}