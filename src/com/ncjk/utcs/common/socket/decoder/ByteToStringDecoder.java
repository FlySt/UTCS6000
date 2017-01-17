package com.ncjk.utcs.common.socket.decoder;

import com.ncjk.utcs.common.netty.server.NettyChannelParam;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by swl on 2016/12/8.
 * 自定义编码器
 */
public class ByteToStringDecoder extends ByteToMessageDecoder{
    int HEAD_LENGTH = 5;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String localAddress = ctx.channel().localAddress().toString();
        int port = new Integer(localAddress.split(":")[1]).intValue();

    //    System.out.println("msg:"+msg);
        //内部协议
        if(port == NettyChannelParam.getPort(1)){
            //String dataLength = msg.substring(0,HEAD_LENGTH);
            in.markReaderIndex(); //标记一下当前的readIndex的位置
            //获取包长度
            String length = in.readBytes(HEAD_LENGTH).toString(Charset.forName("UTF-8"));
            int dataLength = Integer.valueOf(length,16).intValue();
            if(dataLength<0){
                ctx.close();
            }
            int len = in.readableBytes();
            if ((in.readableBytes()+HEAD_LENGTH) < dataLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
                in.resetReaderIndex();
                return;
            }
            byte[] body = new byte[dataLength];
            in.resetReaderIndex();//置位，从头开始读
            in.readBytes(body);  //
            String content = new String(body,"UTF-8");  //将byte数据转化为我们需要的对象。
            out.add(content);
        }else if(port == NettyChannelParam.getPort(0)){//1049协议
            in.markReaderIndex(); //标记一下当前的readIndex的位置
            int len = in.readableBytes();
            byte[] body = new byte[len];  //
            in.resetReaderIndex();//置位，从头开始读
            in.readBytes(body);  //
            String content = new String(body,"UTF-8");  //将byte数据转化为我们需要的对象。
            out.add(content);
        }else if(port == NettyChannelParam.getPort(2)){//插件协议
            in.markReaderIndex(); //标记一下当前的readIndex的位置
            int len = in.readableBytes();
            byte[] body = new byte[len];
            in.resetReaderIndex();//置位，从头开始读
            in.readBytes(body);  //
            String content = new String(body,"UTF-8");
            out.add(content);
        }
    }
}
