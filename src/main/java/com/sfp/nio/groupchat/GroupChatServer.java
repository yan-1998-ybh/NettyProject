package com.sfp.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT=6667;


    public GroupChatServer(){
        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //监听
    public void listen(){
        try {
            //循环处理
            while (true){
                int count = selector.select();
                if (count>0){
                    //遍历得到的selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        //监听到accept
                        if (key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            //设置非阻塞
                            sc.configureBlocking(false);
                            //将该sc注册到selector
                            sc.register(selector,SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress()+"上线");

                        }
                        if (key.isReadable()){//通道发送read事件，既通道是可读状态
                            //处理读（专门写方法）
                            readData(key);

                        }
                        //d当前key删除，防止重复处理
                        iterator.remove();
                    }
                }else {
                    System.out.println("等待....");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //发生异常处理
        }
    }
    //读取k客户端消息
    private void readData(SelectionKey key){
        //定义一个socketChannel，取到关联的channel
        SocketChannel channel =null;
        try{
            //得到channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            //根据count值做处理
            if (count>0){
                //把缓冲区的数据转换成字符串
                String msg = new String(byteBuffer.array());
                System.out.println("form客户端："+msg);

                //向其他的客户端转发消息
                sendInfoTOOtherClients(msg,channel);
            }
        }catch (Exception e){
            try {
                System.out.println(channel.getRemoteAddress()+"离线了");
                //取消注册
                key.channel();
                //关闭通道
                channel.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    //转发消息给其他客户
    private void sendInfoTOOtherClients(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        //遍历，所有注册到selector上的socketChannel，并排除self
        for (SelectionKey key:selector.keys()){
            //通过key取出对应的SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel !=self){
                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg存入buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer写入通道
                dest.write(buffer);
            }
        }

    }
    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();

    }
}
