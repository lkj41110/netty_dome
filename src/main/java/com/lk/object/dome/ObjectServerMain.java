package com.lk.object.dome;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * 传输对象例子
 * 
 * @author lkj41110
 * @version time：2017年1月16日 下午9:54:55
 */
public class ObjectServerMain {

	private int port;

	public ObjectServerMain(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		new ObjectServerMain(2000).run();
	}

	public void run() {
		EventLoopGroup acceptor = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024).group(acceptor, worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() { // 匿名内部类的方式
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new ObjectDecoder(1024 * 1024,
								ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
						pipeline.addLast(new ObjectEncoder());
						pipeline.addLast(new ObjectServerHandler());
					}

				});

		try {
			// 服务器绑定端口监听
			Channel channel = bootstrap.bind(port).sync().channel();
			// 监听服务器关闭监听
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 退出
			acceptor.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
}
