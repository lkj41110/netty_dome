package com.lk.netty.mulchat.dome;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 多人聊天客户端（后开启）
 * @author lkj41110
 * @version time：2017年1月16日 下午9:55:41
 */
public class ClientMain {
	private String host;
	private int port;
	private boolean stop = false;

	public ClientMain(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws IOException {
		new ClientMain("127.0.0.1", 2000).run();
	}

	public void run() throws IOException {
	    //设置一个worker线程，使用
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(worker);
		//指定所使用的 NIO 传输 Channel
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ClientIniterHandler());

		try {
		    //使用指定的 端口设置套 接字地址
			Channel channel = bootstrap.connect(host, port).sync().channel();
			while (true) {
			    //向服务端发送内容
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				String input = reader.readLine();
				if (input != null) {
					if ("quit".equals(input)) {
						System.exit(1);
					}
					channel.writeAndFlush(input);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
