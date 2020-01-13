package org.example.Server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.example.netty.UdpServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpServer {

    private final static Logger logger = LoggerFactory.getLogger(UdpServer.class);

    public void run(int port){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpServerHandler());

            bootstrap.bind(port).sync().channel().closeFuture().sync();
        }catch(Exception e){
            logger.error(e.getMessage());
        }finally{
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void runUpdServer(){
        UdpServer server = new UdpServer();
        server.run(8000);
    }
}
