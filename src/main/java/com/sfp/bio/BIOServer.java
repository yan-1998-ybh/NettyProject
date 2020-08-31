package com.sfp.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-27 16:21
 **/
public class BIOServer {
    public static void main(String[] args) throws IOException {
        //创建一个线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");

        while (true){
            System.out.println("线程信息 id="+Thread.currentThread().getId()+"名字"+Thread.currentThread().getName());
            //监听等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接一个客户端");
            //创建一个线程，与之通讯（单独写一个方法）
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);

                }
            });
        }
    }
    //编写一个handler方法，和客户端通讯
    public static void handler (Socket socket){
        System.out.println("线程信息 id="+Thread.currentThread().getId()+"名字"+Thread.currentThread().getName());
        byte[] bytes =new byte[1024];
        //通过socket获取输入流
        try {
            InputStream inputStream = socket.getInputStream();

            //循环的读取客户端发送的数据
            while (true){
                System.out.println("线程信息 id="+Thread.currentThread().getId()+"名字"+Thread.currentThread().getName());
                int read = inputStream.read();
                if(read !=-1){
                    System.out.println(new String(bytes,0,read));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
