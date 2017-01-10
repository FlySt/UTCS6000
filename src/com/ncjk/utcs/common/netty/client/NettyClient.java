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
import io.netty.util.CharsetUtil;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * 内部通信协议客户端，主要用来测试
 * Created by swl on 2016/9/25.
 */
public class NettyClient {

    public static String host = "127.0.0.1";
    public static int port = 6000;
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

    public NettyClient() {
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
            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

            // 客户端的逻辑
            pipeline.addLast("handler", new ClientHandler());
        }
    }
    private static class ClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            NettyClientChannelMap.removeClientChannel(ctx.channel().id());
            NettyClientChannelMap.removeClientToken(ctx.channel().id());
            ctx.close();
            super.exceptionCaught(ctx, cause);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("服务器说: " + msg+",通道Id为:"+ctx.channel().id());
            NettyClientChannelParam.MsgType type = NettyClientChannelParam.getMsgType(msg);
            switch (type){
                case LOGIN:
                    NettyClientChannelMap.addClientChannel(ctx.channel().id(),ctx.channel());
                    NettyClientChannelMap.addClientTokenMap(ctx.channel().id(),NettyClientChannelParam.getToken(msg));
                    break;
                case TIMER:
                    String content = msg.substring(msg.length()-14,msg.length());
                    NettyClientChannelParam.response.append(content);
                    break;
                case OLDSIGNAL:
                    String json = msg.substring(msg.indexOf("{"));
                    JSONObject jsonObject = JSONObject.fromObject(json);
                    Integer cmdid = jsonObject.getInt("cmdid");
                    System.out.println("cmdid:"+cmdid);
                    String oldContent = NettyClientChannelParam.getPackeg(cmdid,ctx.channel().id(),type);
                    ctx.writeAndFlush(oldContent);
                    break;
            }
        }
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client active ");
           String content = NettyClientChannelParam.getPackeg(0,ctx.channel().id(), NettyClientChannelParam.MsgType.LOGIN);
       //    String content = "石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来石望来";
  /*          byte[] bytes = content.getBytes("UTF-8");
            for(byte b:bytes){
                System.out.println(Integer.toHexString(b));
            }
            content = new String(bytes,"UTF-8");
            System.out.println("content:"+content);*/
            ctx.writeAndFlush(content);
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
       //     System.out.println("pingState:"+NettyClientChannelParam.pingState);
            if(event.state() == IdleState.WRITER_IDLE && NettyClientChannelParam.pingState == 1){
      //          System.out.println("写空闲，发送心跳包");
             //   String content = NettyClientChannelParam.getPackeg(0,ctx.channel().id(), NettyClientChannelParam.MsgType.PING);
        //        System.out.println("心跳包内容:"+content);
            //    content = content + "|石望来";
          //      ctx.writeAndFlush(content);
            }
            super.userEventTriggered(ctx, evt);
        }
    }
}
