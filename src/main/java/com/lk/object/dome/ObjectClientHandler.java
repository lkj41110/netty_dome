package com.lk.object.dome;

import java.util.Random;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ObjectClientHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Object obj)
			throws Exception {
		MyObject myObject=(MyObject)obj;
		System.out.println("client receive" + myObject);
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//channel连接成功时发送
		Random random=new Random();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<10;i++){
			MyObject myObject=new MyObject();
			sb.append("a");
			myObject.setId(random.nextInt(100));
			myObject.setMessage(sb.toString());
			ctx.write(myObject);
		}
		ctx.flush();
		System.out.println("send seccess");
	}

}
