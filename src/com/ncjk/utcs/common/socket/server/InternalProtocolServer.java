package com.ncjk.utcs.common.socket.server;

import com.ncjk.utcs.common.netty.server.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

/**
 * Created by swl on 2017/1/10.
 * 内部协议通信服务端
 */
public class InternalProtocolServer implements Runnable{

    private Logger logger = Logger.getLogger(InternalProtocolServer.class);
    private int port;
    public InternalProtocolServer(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new InternalProtocolServerInitializer());
            b.childOption(ChannelOption.SO_KEEPALIVE,true);//保持长连接
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(this.port).sync();
            logger.info("Server已经启动，监听端口: " + f.channel().localAddress()+ "， 等待客户端注册。。。");
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
            logger.info("Server关闭");
            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
