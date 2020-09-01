package com.sfp.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-31 10:33
 **/
public class ScatteringAndGatheringTest {
    /**
     * Scattering：将数据写入Buffer时，可以采用buffer数组，依次写入（分散）
     * Gathering：从buffer读取数据时，可以采用buffer数组，依次读
     */
    public static void main(String[] args) throws Exception {
        //使用ServerSocketChannel和SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接（telnet）
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;
        //循环的读取
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                socketChannel.read(byteBuffers);
                byteRead += 1;//累计读取的字节数
                System.out.printf("byteRead =" + byteRead);
                //使用流打印，看看这个buffer的position和limit
            }
        }

    }
}
