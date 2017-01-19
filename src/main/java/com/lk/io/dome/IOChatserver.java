package com.lk.io.dome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 用io来操作，实现堵塞的network服务端
 * 发送接收到的字符串转化为大写返回到客户端
 * @author lkj41110
 * @version time：2017年1月19日 下午2:06:04
 */
public class IOChatserver {
	//端口号
	private int port;
	
	public IOChatserver(int port) {
		this.port=port;
	}
	
	public static void main(String[] args) throws Exception {
		new IOChatserver(8888).run();
	}
	
	public void run() throws Exception{
		ServerSocket serverSocket=new ServerSocket(port);
		System.out.println("server strart running in port:" + port);
		try{
			while(true){
				//堵塞方法
				final Socket socket=serverSocket.accept();
				System.out.println(socket.getLocalAddress());
				//创建一个线程，防止堵塞
				new Thread(new Runnable() {
					public void run() {
						BufferedReader br=null;
						PrintWriter bw=null;
						try{
							//用于接收客户端发来的请求  
							br = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
				            //用于发送返回信息
							bw  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));  
				            while(true){  
				                String str = br.readLine();  
				                if(str.equals("EXIT")){  
				                    break;
				                }  
				                System.out.println("Client Socket Message:"+str);  
				                bw.println(str.toUpperCase()); 
				                bw.flush();  
				            }  
						}catch(Exception e){
							e.printStackTrace();
						}finally {
							try{
								br.close();
								bw.close();
								socket.close();
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						
					}
				}).start();
			}
		}catch(Exception e){
			e.printStackTrace();
			serverSocket.close();
		}
		
	}
}
