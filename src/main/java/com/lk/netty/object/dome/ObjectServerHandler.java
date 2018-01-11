package com.lk.netty.object.dome;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ObjectServerHandler extends SimpleChannelInboundHandler<Object> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj)
			throws Exception {
		MyObject myObject=(MyObject)obj;
		System.out.println("rece: "+myObject);
		MyObject newObjext=new MyObject();
		newObjext.setId(myObject.getId()+1);
		newObjext.setMessage("return"+newObjext.getId());
		ctx.writeAndFlush(newObjext);
	}
	
	//链接上
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println("[" + channel.remoteAddress() + "] " + "online");
		MyObject newObjext=new MyObject();
		newObjext.setId(1);
		newObjext.setMessage("return"+newObjext.getId());
		ctx.writeAndFlush(newObjext);
	}

}
