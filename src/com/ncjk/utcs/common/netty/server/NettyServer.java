package com.ncjk.utcs.common.netty.server;


import com.ncjk.utcs.common.netty.server.xml.XmlImp;
import com.ncjk.utcs.common.netty.server.xml.XmlInterface;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;
import java.util.concurrent.TimeUnit;

/**
 * Created by swl on 2016/9/23.
 * 通信服务端
 */

public class NettyServer {

    /**
     * 服务端监听的端口地址
     */
    private static Logger logger = Logger.getLogger(NettyServer.class);
    public static void main(String[] args){
         new NettyServer();
    }
    public NettyServer() {
        ServerStart serverInsideStart = new  ServerStart(NettyChannelParam.getPort(1));//内部协议6000端口
        ServerStart serverGatStart = new  ServerStart(NettyChannelParam.getPort(0));//1046协议7000端口
        ServerStart webSocketServerStart = new  ServerStart(NettyChannelParam.getPort(2));//插件通信5000端口
        new Thread(serverInsideStart).start();
        new Thread(serverGatStart).start();
        new Thread(webSocketServerStart).start();

    }

    private class ServerStart implements Runnable{
        private  Logger logger = Logger.getLogger(ServerStart.class);
        private int port;
        public ServerStart(int port) {
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
                b.childHandler(new NettyServerInitializer());
                /*b.option(ChannelOption.SO_BACKLOG, 1024)
                        .option(ChannelOption.SO_RCVBUF, 10*1024*1024)
                        .option(ChannelOption.SO_SNDBUF, 1024*1024)
                        .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                        .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);*/
                b.childOption(ChannelOption.SO_KEEPALIVE,true);
                b.option(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(64, 1024, 65536));
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
                System.out.println("netty退出");
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
            System.out.println("线程退出");
        }
    }

    private static class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            // 以("\n")为结尾分割的 解码器
          // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            // 字符串解码 和 编码
         //   pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        //    pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
            pipeline.addLast("decoder", new ByteToStringDecoder());
            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
            pipeline.addLast("idleStateHandler", new IdleStateHandler(30, 10, 0, TimeUnit.SECONDS));
            // 自己的逻辑Handler
            pipeline.addLast("handler", new NettyServerHandler());

       //     pipeline.addLast("myHandler", new MyHandler());
        }
    }
    private static class NettyServerHandler extends SimpleChannelInboundHandler<String> {
        Integer pingCount = 0;
        StringBuffer tempBuffer = new StringBuffer("");
        StringBuffer ocxBuffer = new StringBuffer("");
        Integer msgLength = 0;
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            NettyChannelMap.removeChannel(ctx.channel().id());
            NettyChannelMap.removeServerToken(ctx.channel().id());
            NettyChannelMap.removeServerMode(ctx.channel().id());
            NettyChannelMap.removeOcxChannel(ctx.channel().id());
            NettyChannelMap.removeChannelState(ctx.channel().id());
            ctx.close();
            super.exceptionCaught(ctx, cause);
         //   System.out.println("msg:"+cause.toString());
        }


        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //    msg = new String(msg.getBytes("UTF-8"));
            // 收到消息直接打印输出
       //     logger.info(ctx.channel().remoteAddress() + ":" +msg);
            String localAddress = ctx.channel().localAddress().toString();
            int port = new Integer(localAddress.split(":")[1]).intValue();
            /**内部协议**/
            if(port == NettyChannelParam.getPort(1)){
                    NettyChannelParam.MsgType type = NettyChannelParam.getMsgType(msg);
                    Integer logTokenStartIndex = NettyChannelParam.packageLength;
                    Integer logTokenEndIndex =  NettyChannelParam.packageLength + NettyChannelParam.tokenLength;
                    String token = msg.substring(logTokenStartIndex,logTokenEndIndex);
                    if(type == NettyChannelParam.MsgType.LOGIN){
                        String content = NettyChannelParam.getPackeg(ctx.channel().id(), type,token);
                        String mode = NettyChannelParam.getServerMode(msg,0);
                        logger.info("客户端发送了登录验证 id:"+ctx.channel().id());
                        //保存通道
                        NettyChannelMap.addChannel(ctx.channel().id(),ctx.channel());
                        //保存Mode
                        NettyChannelMap.addServerModeMap(ctx.channel().id(),mode);
                        //初始话通道状态
                        NettyChannelMap.addChannelState(ctx.channel().id(),0);
                        //将随机生成的32位Token保存
                        NettyChannelMap.addServerTokenMap(ctx.channel().id(),NettyChannelParam.getToken(content));
                        logger.info("返回内部协议登录验证的信息:"+content);
                        ctx.writeAndFlush(content);
                    }else if(type == NettyChannelParam.MsgType.PING){
                        //验证Token
                        if(NettyChannelMap.getServerToken(ctx.channel().id()).equals(NettyChannelParam.getMsgToken(msg))){
                            pingCount = 0;
                            //响应客户端主动模式发来的心跳包
                            if("ACT".equals(NettyChannelMap.getServerMode(ctx.channel().id()))){
                                logger.info("客户端发送了心跳包 id："+ctx.channel().id());
                                String content = NettyChannelParam.getPackeg(ctx.channel().id(), type,token);
                                ctx.writeAndFlush(content);
                            }
                        }
                    }else if(type == NettyChannelParam.MsgType.TIMER){
                        if(NettyChannelMap.getServerToken(ctx.channel().id()).equals(NettyChannelParam.getMsgToken(msg))){
                            String content = NettyChannelParam.getPackeg(ctx.channel().id(), type,token);
                            //          System.out.println("客户端校时信息 id："+ctx.channel().id());
                            pingCount = 0;
                            ctx.writeAndFlush(content);
                        }
                    }else if(type == NettyChannelParam.MsgType.OLDSIGNAL){
        //                logger.info("接收到原始信号机协议:"+msg);
                        //改变通道状态
                        NettyChannelMap.removeChannelState(ctx.channel().id());
                        NettyChannelMap.addChannelState(ctx.channel().id(),0);
                        /**验证Token*/
                        if(NettyChannelMap.getServerToken(ctx.channel().id()).equals(NettyChannelParam.getMsgToken(msg))){
                            String content = NettyChannelParam.getInsideContent(msg);
                            synchronized (this){
                                NettyChannelParam.insideResponse.append(content);
                            }
                        }
                    }
             //   }
            }else if(port == NettyChannelParam.getPort(0)){/**GAT1049协议**/
                tempBuffer.append(msg);
            //    logger.info("接收数据:"+tempBuffer);
                Integer startIndex = tempBuffer.indexOf("<?xml");
                Integer endIndex = tempBuffer.indexOf("</Message>");
                while(tempBuffer.length()!=0 && startIndex!=-1 && endIndex!=-1){
                    String packeg = tempBuffer.substring(startIndex,endIndex+"</Message>".length());
                    tempBuffer.delete(0,endIndex+"</Message>".length());
                    if(tempBuffer.length()!=0){
                        startIndex = tempBuffer.indexOf("<?xml");
                        endIndex = tempBuffer.indexOf("</Message>");
                    }
                    msg = packeg;
                  //  logger.info("mag:"+msg);
                    msgLength = msgLength + msg.length();
                  //  logger.info("收到数据的长度："+msgLength);
                    NettyChannelParam.MsgType type = NettyChannelParam.getGatMsgType(msg);
                    if(type == NettyChannelParam.MsgType.ERROR){
                        logger.info("异常数据:"+msg);
                    }
                    else if(type == NettyChannelParam.MsgType.LOGIN){
                        String mode = NettyChannelParam.getServerMode(msg,1);
                        if(mode!=null && mode.length()!=0){
                            String content = NettyChannelParam.getGatPackeg(msg,type,ctx.channel().id());
                            logger.info("客户端发送了登录验证 id:"+ctx.channel().id());
                            //保存通道
                            NettyChannelMap.addChannel(ctx.channel().id(),ctx.channel());
                            //保存Mode
                            NettyChannelMap.addServerModeMap(ctx.channel().id(),mode);
                            //初始话通道状态
                            NettyChannelMap.addChannelState(ctx.channel().id(),0);
                            //将随机生成的32位Token保存
                            NettyChannelMap.addServerTokenMap(ctx.channel().id(),NettyChannelParam.getGatToken(content));
                            ctx.writeAndFlush(content);
                        }
                        logger.info("总连接个数:"+NettyChannelMap.channelMap.size());
                    }else if(type == NettyChannelParam.MsgType.PING){
              //          NettyChannelParam.setSignalControlerState(msg,0);
                        //验证Token
                        if(NettyChannelMap.getServerToken(ctx.channel().id()).equals(NettyChannelParam.getGatToken(msg))){
                            logger.info("1049协议客户端发送了心跳包 id："+ctx.channel().id());
                            pingCount = 0;
                        }
                    }else if(type == NettyChannelParam.MsgType.LOGOUT){
                        String content = NettyChannelParam.getGatPackeg(msg,type,ctx.channel().id());
                        if(NettyChannelMap.getServerToken(ctx.channel().id()).equals(NettyChannelParam.getGatToken(msg))){
                            logger.info("GAT协议发送断开请求 id:"+ctx.channel().id());
                            ctx.writeAndFlush(content);
                            pingCount = 0;
                            ctx.close();
                        }
                    }else if(type == NettyChannelParam.MsgType.REPLY){
                        //改变通道状态
                        NettyChannelMap.removeChannelState(ctx.channel().id());
                        NettyChannelMap.addChannelState(ctx.channel().id(),0);
                        if(NettyChannelMap.getServerToken(ctx.channel().id()).equals(NettyChannelParam.getGatToken(msg))){
                            logger.info("1049客户端回应了消息，通道ID为："+ctx.channel().id()+",消息为:"+msg);
                            XmlInterface xmlimp = new XmlImp();
                            String responseContent = xmlimp.analyzeGatResponse(ctx.channel().id(),msg);
                            //删除Flag
                            NettyChannelMap.removeChannelFlag(ctx.channel().id());
                            pingCount = 0;
                            if(responseContent!=null && responseContent.length()!=0){
                                NettyService nettyService = new NettyService();
                                logger.info("回应消息给插件,");
                                nettyService.responseToOcx(responseContent,ctx.channel().id());
                            }else{
                                logger.info("解析获得的命令字不对;"+msg);
                            }
                        }
                    }else if(type == NettyChannelParam.MsgType.PUSH){
                        logger.info("1049客户端主动推送了消息:"+msg);
                        String content = NettyChannelParam.getGatPackeg(msg,type,ctx.channel().id());
                        logger.info("响应主动推送的消息:"+content);
                        ctx.writeAndFlush(content);
                    }
                }
            }else if(port == NettyChannelParam.getPort(2)){
                ocxBuffer.append(msg);
                Integer startIndex = ocxBuffer.indexOf("<Operation");
                Integer endIndex = ocxBuffer.indexOf("</Operation>");
                String packegLength = msg.substring(0,NettyChannelParam.ocxPackageLength);
                while(ocxBuffer.length()!=0 && startIndex!=-1 && endIndex!=-1) {
                    String packeg = ocxBuffer.substring(0, endIndex + "</Operation>".length());
                    logger.info("ocxPackeg:" + packeg);
                    ocxBuffer.delete(0, endIndex + "</Operation>".length());
                    if (ocxBuffer.length() != 0) {
                        startIndex = ocxBuffer.indexOf("<Operation");
                        endIndex = ocxBuffer.indexOf("</Operation>");
                    }
                    msg = packeg;
                    if(msg!=null && msg.length()!=0){
                        NettyService nettyService = new NettyService();
                        try {
                            nettyService.sendToGat(ctx.channel(),msg);
                        }catch (RuntimeException e){//捕获到异常时返回失败
                            e.printStackTrace();
                            String fCmdToResponseCode = msg.substring(NettyChannelParam.ocxPackageLength,NettyChannelParam.ocxPackageLength+NettyChannelParam.ocxCmdLength+
                                    NettyChannelParam.ocxFlagLength)+"11";
                            int packLength = fCmdToResponseCode.length()+NettyChannelParam.ocxPackageLength;
                            String sPackegLength = String.format("%04d",packLength);
                            ctx.writeAndFlush(sPackegLength+fCmdToResponseCode);//"11代表失败"
                        }
                    }
                }
            }
        }

        /*
         * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
         * */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("有一个客户端连接上来了:"+ ctx.channel().remoteAddress());
            super.channelActive(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            logger.info("有一个客户端断开了,id为"+ctx.channel().id()+",地址为:"+ctx.channel().remoteAddress());
            if("PSV".equals(NettyChannelMap.getServerMode(ctx.channel().id()))){
                logger.info("服务器主动模式");
            }else if("ACT".equals(NettyChannelMap.getServerMode(ctx.channel().id()))){
                logger.info("服务器被动模式");
            }
            NettyChannelMap.removeChannel(ctx.channel().id());
            NettyChannelMap.removeServerToken(ctx.channel().id());
            NettyChannelMap.removeServerMode(ctx.channel().id());
            NettyChannelMap.removeOcxChannel(ctx.channel().id());
            NettyChannelMap.removeChannelState(ctx.channel().id());
            ctx.channel().close();
            super.channelUnregistered(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
            if (evt instanceof IdleStateEvent) {
                String localAddress = ctx.channel().localAddress().toString();
                int port = new Integer(localAddress.split(":")[1]).intValue();
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.READER_IDLE ) {
                    //空闲则改变通道状态
                    NettyChannelMap.removeChannelState(ctx.channel().id());
                    NettyChannelMap.addChannelState(ctx.channel().id(),0);
                    if(port!=NettyChannelParam.getPort(2) && !"PSV".equals(NettyChannelMap.getServerMode(ctx.channel().id()))){//OCX协议不需要心跳检测
                        pingCount++;
                    }
                    tempBuffer.setLength(0);
                    ocxBuffer.setLength(0);
                    if( pingCount==3){
                        pingCount = 0;
                        ctx.close();
                    }
                }else if(e.state() == IdleState.WRITER_IDLE){
                    //1049协议服务端主动模式
                    if(port==NettyChannelParam.getPort(0) && "PSV".equals(NettyChannelMap.getServerMode(ctx.channel().id()))){
                        XmlInterface xmlImp = new XmlImp();
                        String content = xmlImp.createPingXml(ctx.channel().id());
                        logger.info("1049协议服务器主动模式发送心跳包");
                        ctx.writeAndFlush(content);
                    }if(port==NettyChannelParam.getPort(1) && "PSV".equals(NettyChannelMap.getServerMode(ctx.channel().id()))){
                       String token = NettyChannelMap.getServerToken(ctx.channel().id());
                        String cmd = "HJ_S";
                        String sign = "0";//标志
                        String pack = token+sign+cmd;
                        /**整型转十六进制字符串**/
                        String length = Integer.toHexString(pack.length()+5).toUpperCase();
                        length = String.format("%5s",length);
                        length= length.replaceAll(" ","0");
                  //      String length =  String.format("%05d",pack.length()+5);
                        String content = length+pack;
                        logger.info("内部协议服务器主动模式发送心跳包");
                        ctx.writeAndFlush(content);
                    }
                }
            }
        }
    }


}
