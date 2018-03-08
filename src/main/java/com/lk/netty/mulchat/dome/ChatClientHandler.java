package com.lk.netty.mulchat.dome;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String arg1)
			throws Exception {
	    //客户端主要用来接收服务器发送的消息
		System.out.println(arg1);
	}

}
