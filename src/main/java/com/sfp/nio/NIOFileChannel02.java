package com.sfp.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-28 17:05
 **/
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {
        //创建文件为输入流

        File file = new File("f:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过fileInputStream获取对应的channel——》实际类型为FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();

        //创建缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将通道数据读入byteBuffer
        fileChannel.read(byteBuffer);

        //将byteBuffer的字节数据转成String
        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }
}
