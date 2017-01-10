package com.ncjk.utcs.common.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * 内部通信协议客户端，主要用来测试
 * Created by swl on 2016/9/25.
 */
public class NettyOcxClient {

    public static String host = "127.0.0.1";
    public static int port = 5000;
    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) {
        System.out.println("\n请输入要建立客户端的个数:");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String text = in.readLine();
            int num = new Integer(text).intValue();
            for(int i=0;i<num;i++){
                CreateThread myThread = new CreateThread();
                new Thread(myThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NettyOcxClient() {
        CreateThread myThread = new CreateThread();
        new Thread(myThread).start();
    }

    static class CreateThread implements Runnable{
        @Override
        public void run() {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new ClientInitializer());
                // 连接服务端
                Channel ch = b.connect(host, port).sync().channel();
                // 控制台输入
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                for (;;) {
                    String line = in.readLine();
                    if (line == null) {
                        continue;
                    }
                    ch.writeAndFlush(line + "\r\n");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // The connection is closed automatically on shutdown.
              //  group.shutdownGracefully();
            }
        }
    }
    private static class ClientInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
        /*
         * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
         *
         * 解码和编码
         *
         * */
       //     pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            pipeline.addLast("idleStateHandler", new IdleStateHandler(30, 30, 30, TimeUnit.SECONDS));
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            // 客户端的逻辑
            pipeline.addLast("handler", new ClientHandler());
        }
    }
    private static class ClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            NettyClientChannelMap.removeClientChannel(ctx.channel().id());
            ctx.close();
            super.exceptionCaught(ctx, cause);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("服务器说: " + msg+",通道Id为:"+ctx.channel().id());
        }
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client active ");
            NettyClientChannelMap.addClientChannel(ctx.channel().id(),ctx.channel());
            super.channelActive(ctx);
        }
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client close ");
            NettyClientChannelMap.removeClientChannel(ctx.channel().id());
            super.channelInactive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state() == IdleState.WRITER_IDLE && NettyClientChannelParam.pingState == 1){
            }
            super.userEventTriggered(ctx, evt);
        }
    }
}
