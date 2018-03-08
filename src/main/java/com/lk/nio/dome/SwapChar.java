package com.lk.nio.dome;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * NIO Buffer 坐标观察
 *
 * @author lkj41110
 * @version time：2017年1月20日 下午2:43:28
 */
public class SwapChar {
    public static void main(String[] args) {
        //获取一段char数组  
        char[] c = "abcdefg1234".toCharArray();
        //初始化一段缓冲区  
        ByteBuffer buf = ByteBuffer.allocate(c.length * 2);
        //把bytebuffer转换成charBuffer，方便操作char  
        CharBuffer charBuf = buf.asCharBuffer();
        print(charBuf);
        charBuf.put(c);
        print(charBuf);

        /**
         * buffer
         */
        showBuffer(charBuf);
        charBuf.rewind();
        print(charBuf);

        charBuf.limit(4);
        print(charBuf);

        charBuf.position(2);
        print(charBuf);

        charBuf.limit(11).position(0);
        print(charBuf);
        System.out.println("************交换位置********************");

        char[] c2 = "abcd1234".toCharArray();
        ByteBuffer buf2 = ByteBuffer.allocate(c2.length * 2);
        CharBuffer charBuf2 = buf2.asCharBuffer();
        charBuf2.put(c2);
        print(charBuf2.rewind());
        swap(charBuf2);
        print(charBuf2.rewind());
        System.out.println("********************************");
        swap(charBuf2);
        print(charBuf2.rewind());
    }

    /**
     * 交换相邻的顺序S
     *
     * @param charbuf
     */
    private static void swap(CharBuffer charbuf) {
        while (charbuf.hasRemaining()) {
            charbuf.mark();
            showBuffer(charbuf);
            char a = charbuf.get();
            showBuffer(charbuf);
            char b = charbuf.get();
            showBuffer(charbuf);
            charbuf.reset();
            showBuffer(charbuf);
            charbuf.put(b).put(a);
            showBuffer(charbuf);
            System.out.println();
        }
    }

    /**
     * 显示buffer的细节
     */
    private static void showBuffer(Buffer buffer) {
        System.out.println("position =" + buffer.position() + "  limit =" + buffer.limit() + "  capacity=" + buffer.capacity() );
    }

    private static void print(Buffer buffer) {
        showBuffer(buffer);
        System.out.println(buffer);
    }
}  