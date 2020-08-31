package com.sfp.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-31 10:12
 **/
public class MapperByteBufferTest {
    public static void main(String[] args) throws Exception {
        //MappedByteBuffer可以让文件直接在内存中（堆外内存）修改，操作系统不需要拷贝一次
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");

        //获取对应的通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 使用只读模式
         * 参数2: 0   可以直接修改的起始位置
         * 参数3： 5 是映射内存的大小（不是索引未知），
         * 可以直接修改的范围是0-5
         * */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
