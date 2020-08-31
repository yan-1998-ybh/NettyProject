package com.sfp.nio;

import java.nio.ByteBuffer;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-31 10:01
 **/
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i =0;i<64;i++){
            buffer.put((byte) i);
        }
        //读取
        buffer.flip();
        //得到一个只读buffer
        ByteBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(asReadOnlyBuffer.getClass());
        while (asReadOnlyBuffer .hasRemaining()){
            System.out.println(asReadOnlyBuffer.get());
        }
        asReadOnlyBuffer.put((byte) 100);//ReadOnlyBufferException
    }

}
