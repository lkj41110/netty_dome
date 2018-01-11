package com.lk.io.dome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 用io来操作，实现堵塞的network客户端
 *
 * @author lkj41110
 * @version time：2017年1月19日 下午2:06:04
 */
public class IOChatClient {
    // 端口号
    private int port;
    private String host;

    public IOChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new IOChatClient("127.0.0.1", 9000).run();
    }

    public void run() throws Exception {
        Socket socket = null;
        BufferedReader br;
        PrintWriter bw;
        try {
            socket = new Socket(host, port);
            // 用于接收客户端发来的请求
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 用于发送返回信息
            bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            bw.println("i am coming ");
            bw.flush();
            String str = br.readLine();
            System.out.println(str);

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String inStr = in.readLine();
                System.out.println("发送消息 " + inStr);
                bw.println(inStr);
                bw.flush();
                if (inStr.equalsIgnoreCase("exit")) {
                    in.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
