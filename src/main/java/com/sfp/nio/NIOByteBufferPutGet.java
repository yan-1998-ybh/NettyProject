package com.sfp.nio;

import java.nio.ByteBuffer;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-31 09:51
 **/
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        //创建一个Buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
         //类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('闫');
        buffer.putShort((short) 4);

        //取出
        buffer.flip();
        System.out.println();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

    }
}
