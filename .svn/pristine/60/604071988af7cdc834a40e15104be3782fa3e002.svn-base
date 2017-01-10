package com.ncjk.utcs.common.netty.client;

import com.ncjk.utcs.common.netty.client.xml.XmlClientImp;
import com.ncjk.utcs.common.netty.client.xml.XmlClientInterface;
import com.ncjk.utcs.common.netty.server.NettyChannelParam;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
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
 * GAT1049协议客户端，主要用来测试，实际运行不需要
 * Created by swl on 2016/9/27.
 */
public class NettyGatClient {
    public static String host = "127.0.0.1";
    public static int port = 7000;
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
                CreateGatThread myThread = new CreateGatThread();
                new Thread(myThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public NettyGatClient() {
        CreateGatThread myThread = new CreateGatThread();
        new Thread(myThread).start();
    }

    static class CreateGatThread implements Runnable{
        @Override
        public void run() {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new ClientGatInitializer());
                b.option(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(64, 1024, 65536));
                // 连接服务端0
                Channel ch = b.connect(host, port).sync().channel();
     /*           // 控制台输入
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                for (;;) {
                    String line = in.readLine();
                    if (line == null) {
                        continue;
                    }
                *//*
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
                 * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 * *//*
                    ch.writeAndFlush(line + "\r\n");
                }*/

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // The connection is closed automatically on shutdown.
                //  group.shutdownGracefully();
            }
        }
    }
    private static class ClientGatInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
        /*
         * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
         * 解码和编码
         *
         * */
            // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
          //  pipeline.addFirst("decoder1", new LengthFieldBasedFrameDecoder(100000000,0,4,0,4));
            pipeline.addLast("idleStateHandler", new IdleStateHandler(30, 30, 30, TimeUnit.SECONDS));
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            // 客户端的逻辑
            pipeline.addLast("handler", new ClientGatHandler());
        }
    }
    private static class ClientGatHandler extends SimpleChannelInboundHandler<String> {
        StringBuffer tempBuffer = new StringBuffer("");
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            NettyClientChannelMap.removeClientChannel(ctx.channel().id());
            NettyClientChannelMap.removeClientToken(ctx.channel().id());
            ctx.close();
            super.exceptionCaught(ctx, cause);
        }
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("收到消息，通道Id为:"+ctx.channel().id()+",:"+msg);
            tempBuffer.append(msg);
            //         System.out.println("tempBuffer:"+tempBuffer);
            Integer startIndex = tempBuffer.indexOf("<?xml");
            Integer endIndex = tempBuffer.indexOf("</Message>");
            //        System.out.println("startIndex:"+startIndex);
            //        System.out.println("endIndex:"+endIndex);
            while(tempBuffer.length()!=0 && startIndex!=-1 && endIndex!=-1) {
                //             System.out.println("进入循环");
                String packeg = tempBuffer.substring(startIndex, endIndex + "</Message>".length());
                System.out.println("packeg:" + packeg);
                tempBuffer.delete(0, endIndex + "</Message>".length());
                //         System.out.println("处理后的tempBuffer的长度:"+tempBuffer.length());
                //          System.out.println("处理后的tempBuffer:"+tempBuffer);
                //          logger.info("处理后的tempBuffer的长度:"+tempBuffer.length());
                //          logger.info("处理后的tempBuffer:"+tempBuffer);
                if (tempBuffer.length() != 0) {
                    startIndex = tempBuffer.indexOf("<?xml");
                    endIndex = tempBuffer.indexOf("</Message>");
                    System.out.println("startIndex:" + startIndex);
                    System.out.println("endIndex:" + endIndex);
                }
                //            System.out.println("数据完整,继续后续处理");
                msg = packeg;
                NettyClientChannelParam.MsgType type = NettyClientChannelParam.getGatMsgType(msg);
                switch (type){
                    case LOGIN:
                        System.out.println(NettyClientChannelParam.getGatToken(msg));
                        NettyClientChannelMap.addClientTokenMap(ctx.channel().id(),NettyClientChannelParam.getGatToken(msg));
                        NettyClientChannelMap.addClientChannel(ctx.channel().id(),ctx.channel());
                        System.out.println("客户端Channel数:"+ NettyClientChannelMap.getClientChannels());
                        break;
                    case ASK:
                        Thread.sleep(2000);
                        String content = NettyClientChannelParam.getGatPackeg(msg,type);
                        System.out.println("发送消息，通道Id为:"+ctx.channel().id()+",:"+content);
                        ctx.writeAndFlush(content);
                        break;
                }
            }

        }
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client active ");
            XmlClientInterface xmlImp = new XmlClientImp();
            String loginContent = xmlImp.createLoginXml();
            ctx.writeAndFlush(loginContent);
            super.channelActive(ctx);
        }
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client close ");
            NettyClientChannelMap.removeClientChannel(ctx.channel().id());
            NettyClientChannelMap.removeClientToken(ctx.channel().id());
            super.channelInactive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state() == IdleState.WRITER_IDLE ){
                String token = NettyClientChannelMap.getClientToken(ctx.channel().id());
                String content = new XmlClientImp().createPingXml(token);
                ctx.writeAndFlush(content);
            }
            super.userEventTriggered(ctx, evt);
        }
    }
}
