package com.sfp.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-08-31 09:04
 **/
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true){
            //这里有一个重要的操作，一定不要忘了
            byteBuffer.clear();
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read="+read);
            if (read == -1){
                break;
            }
            //将buffer中的数据写入fileChannel02-----2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        fileChannel01.close();
        fileChannel02.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
