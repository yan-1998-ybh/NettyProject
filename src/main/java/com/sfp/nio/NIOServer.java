package com.sfp.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-31 11:41
 **/
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //创建serverSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector
        Selector selector = Selector.open();

        //绑定端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到 selector 关心事件 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//操作集位用于插座接受操作。接受另一个连接
        System.out.println("注册后的selectionKeys的数量=" + selector.keys().size());

        //循环等待客户端连接
        while (true) {
            //这里等待一秒，如果没有事件发生，返回
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待一秒，无连接");
                continue;
            }
            //如果返回的>0，就获取到相关的selectionKey集合
            //1.如果返回的>0,表示已近获取到关注的事件
            //2.selector.selectedKeys() 返回关住事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys的数量=" + selectionKeys.size());

            //遍历Set<SelectionKey>，使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();
                //根据key对应的通道发生的事件相应处理
                if (key.isAcceptable()) {//如果是OP_ACCEPT，有新的客户连接
                    //该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成一个 socketChannel" + socketChannel.hashCode());
                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册到selector,关注事件为OP_READ  同时给SocketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));//
                    System.out.println("客户端连接后注册的selectionKeys的数量=" + selector.keys().size());

                }
                if (key.isReadable()) {//发生OP_READ
                    //通过key 反向获取到对应channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form 客户端" + new String(buffer.array()));//返回支持此缓冲区的字节数组

                }
                //手动从集合中的移动当前的selectionKey，防止重复操作
                keyIterator.remove();
            }

        }
    }
}
