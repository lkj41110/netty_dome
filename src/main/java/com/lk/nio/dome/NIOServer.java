package com.lk.nio.dome;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 * NIO服务端
 * @author lkj41110
 * @version time：2017年1月20日 下午2:43:28
 */
public class NIOServer {
	
	private  int flag = 0;
	private  int BLOCK = 2048;
	private  ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	private  ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	private  Selector selector;

	public NIOServer(int port) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(port));
		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server Start----"+port+":");
	}

	private void listen() throws IOException {
		//轮询  事件驱动模式
		while (true) {
			// select()阻塞，等待有事件发生唤醒 
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				//处理事件后，要移除
				iterator.remove();
				handleKey(selectionKey);
			}
		}
	}

	/**
	 * 处理不同事件
	 */
	private void handleKey(SelectionKey selectionKey) throws IOException {
		ServerSocketChannel server = null;
		SocketChannel client = null;
		String receiveText;
		String sendText;
		int count=0;
		//连接事件
		if (selectionKey.isAcceptable()) {
			server = (ServerSocketChannel) selectionKey.channel();
			client = server.accept();
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);
			//读取模式
		} else if (selectionKey.isReadable()) {
			client = (SocketChannel) selectionKey.channel();
			receivebuffer.clear();
			count = client.read(receivebuffer);	
			if (count > 0) {
				receiveText = new String( receivebuffer.array(),0,count);
				System.out.println("读取到:"+receiveText);
				//注册对写的事件感兴趣
				client.register(selector, SelectionKey.OP_WRITE);
			}
			//写模式
		} else if (selectionKey.isWritable()) {
			sendbuffer.clear();
			client = (SocketChannel) selectionKey.channel();
			sendText="message from server--" + flag++;
			sendbuffer.put(sendText.getBytes());
			sendbuffer.flip();
			client.write(sendbuffer);
			System.out.println("向客户端发送："+sendText);
			client.register(selector, SelectionKey.OP_READ);
		}
	}

	public static void main(String[] args) throws IOException {
		NIOServer server = new NIOServer(9000);
		server.listen();
	}
}