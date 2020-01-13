package org.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class UpdClient {
    public void run(int port){
        EventLoopGroup eventLoopGroup = new EpollEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpClientHandler());
            Channel channel = bootstrap.bind(7070).sync().channel();
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8), new InetSocketAddress("localhost", port))).sync();
            if(!channel.closeFuture().await(15000)){
                System.out.println("time out....");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
