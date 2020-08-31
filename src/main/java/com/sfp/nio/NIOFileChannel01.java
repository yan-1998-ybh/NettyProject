package com.sfp.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-28 16:28
 **/
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "真的";
        //创建一个输出流——》channel
        FileOutputStream fileOutputStream = new FileOutputStream("f:\\file01.txt");

        //通过输出流获取对应的文件channel
        //这个fileChannel的正式真是类型是fileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放入byteBuffer
        byteBuffer.put(str.getBytes());
        //对byteBuffer，进行flip
        byteBuffer.flip();

        //将byteBuffer数据写入fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
