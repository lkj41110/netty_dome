package com.lk.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author luokai
 * @description:
 * @date: 2019/1/12
 * @version: 1.0
 */
public class ToIntegerDecoderTest {

    @Test
    public void decode() {
        ByteBuf buf = Unpooled.buffer();

        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        ByteBuf input = buf.duplicate(); //复制一个完全一样的ByteBuf

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ToIntegerDecoder());
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        assertTrue(embeddedChannel.finish());

        Integer read = embeddedChannel.readInbound();
        System.out.println(read);
        assertEquals((Integer) buf.readInt(), read);

        read = embeddedChannel.readInbound();
        System.out.println(read);
        assertEquals((Integer) buf.readInt(), read);

        read = embeddedChannel.readInbound();
        System.out.println(read);
        assertEquals((Integer) buf.readInt(), read);
    }

    @Test
    public void decode2() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(1);
        buf.writeByte(0);

        ByteBuf input = buf.duplicate(); //复制一个完全一样的ByteBuf

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ToIntegerDecoder());
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        assertTrue(embeddedChannel.finish());

        Integer read = embeddedChannel.readInbound();
        System.out.println(read);
        assertEquals((Integer) buf.readInt(), read);

    }
}