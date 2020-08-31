package com.sfp.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-31 09:30
 **/
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {
        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("f:\\a.png");
        FileOutputStream fileOutputStream = new FileOutputStream("f:\\b.png");
        //获取各个流对应的fileChannel
        FileChannel sourcech = fileInputStream.getChannel();
        FileChannel destch = fileOutputStream.getChannel();

        //使用transferForm完成拷贝
        destch.transferFrom(sourcech,0,sourcech.size());

        //关闭相关的通道和流
        sourcech.close();
        destch.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
