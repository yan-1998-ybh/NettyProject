package com.sfp.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @program: NettyProject
 * @description: 1
 * @author: ybh
 * @create: 2020-09-01 16:51
 **/

/**
 * 1.我们自定义一个Handler。需要继承netty规定好的某个HandlerAdapter
 * 2.这是我们自定义的Handler，才能称为一个handler
 * */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据实际（这里我们可以读取客户端发送的消息）
    /*
    1.ChannelHandlerContext ctx：上下文对象，含有管道 Pipeline,通道channel，地址
    2.Object msg 就是客户端发送的数据 默认Object
    * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("server ctx = "+ctx);
        //将msg转成一个ByteBuf
        //ByteBuf是netty提供的，不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ctx.channel().remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存并刷新
        //一般讲，我们对这个发送数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端:~o( =∩ω∩= )m喵",CharsetUtil.UTF_8));
    }

    //处理异常，一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
