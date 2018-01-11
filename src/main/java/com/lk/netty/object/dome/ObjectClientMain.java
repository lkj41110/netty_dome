package com.lk.netty.object.dome;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * 传输对象例子 客户端
 * 
 * @author lkj41110
 * @version time：2017年1月16日 下午9:54:55
 */
public class ObjectClientMain {

	private String host;
	private int port;

	public ObjectClientMain(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) {
		new ObjectClientMain("127.0.0.1", 2000).run();
	}

	public void run() {
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(worker).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new ObjectDecoder(1024 * 1024,
								ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
						pipeline.addLast(new ObjectEncoder());
						pipeline.addLast(new ObjectClientHandler());
					}
				});
		try {
			bootstrap.connect(host, port).sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("connect fail");
			System.exit(1);
		}
	}
}