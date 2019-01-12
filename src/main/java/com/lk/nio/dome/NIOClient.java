package com.lk.nio.dome;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO客户端
 *
 * @author lkj41110
 * @version time：2017年1月19日 下午9:43:23
 */
public class NIOClient {
    private static int BLOCK = 4096;
    private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
    private static ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
    private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress("localhost", 9000);

    public static void main(String[] args) throws IOException {
        // 打开套接字的信道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置成非堵塞
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        // 注册在socket信道上注册我们感兴趣的事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(SERVER_ADDRESS);

        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;
        SocketChannel client;
        String receiveText;
        int count, index = 0;

        while (true && index < 10) {
            selector.select();
            selectionKeys = selector.selectedKeys();
            // System.out.println(selectionKeys.size());
            iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                selectionKey = iterator.next();
                // 连接事件
                if (selectionKey.isConnectable()) {
                    System.out.println("client connect");
                    client = (SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()) {
                        client.finishConnect();
                        sendbuffer.clear();
                        sendbuffer.put("Hello,Server".getBytes());
                        sendbuffer.flip();
                        client.write(sendbuffer);
                    }
                    client.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) { // 读事件
                    client = (SocketChannel) selectionKey.channel();
                    receivebuffer.clear();
                    count = client.read(receivebuffer);
                    if (count > 0) {
                        receiveText = new String(receivebuffer.array(), 0, count);
                        System.out.println("接收:" + receiveText);
                        client.register(selector, SelectionKey.OP_WRITE);
                    }
                } else if (selectionKey.isWritable()) {        // 给客户端注册写事件
                    sendbuffer.clear();
                    client = (SocketChannel) selectionKey.channel();
                    String sendText = "你好！我试用来测试装包拆包的，哈哈哈哈或。哈哈哈哈或或" + (index++);
                    sendbuffer.put(sendText.getBytes());
                    sendbuffer.flip();
                    client.write(sendbuffer);
                    System.out.println("发送：" + sendText);
                    client.register(selector, SelectionKey.OP_READ);
                }
            }
            selectionKeys.clear();
        }
        //交互10次后关闭连接
        socketChannel.close();
    }
}