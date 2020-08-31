package com.sfp.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-31 13:57
 **/
public class NIOClient {
    public static void main(String[] args) throws Exception {
        //的到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务端的IP和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }
        //如果连接成功，就发送数据
        String str = "我都快我到点击啊";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());//将一个字节数组包装到缓冲区中。使用平台的默认字符集将此 String编码为字节序列，将结果存储到新的字节数组中。
        //发送数据，将buffer数据写入channel
        socketChannel.write(buffer);
        //远程主机强迫关闭了一个现有的连接。
        System.in.read();
    }
}
