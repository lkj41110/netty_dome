package com.lk.qos.dome;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author luokai
 * @description:
 * @date: 2019/1/3
 * @version: 1.0
 */
public class Server {

    private int port = 22222;

    private AtomicBoolean started = new AtomicBoolean();

    private static final Server INSTANCE = new Server();

    public static final Server getInstance() {
        return INSTANCE;
    }
    public void start() throws Throwable {
        if (!started.compareAndSet(false, true)) {
            return;
        }

        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(acceptor, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new LineBasedFrameDecoder(10));
                ch.pipeline().addLast(new QosProcessHandler(Logo.logo));
                ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                ch.pipeline().addLast(new IdleStateHandler(5, 10, 0));
                ch.pipeline().addLast(new TelnetProcessHandler());
            }
        });
        try {
            serverBootstrap.bind(port).sync();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    public static void main(String[] args) throws Throwable {
        getInstance().start();
    }
}
