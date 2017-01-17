package com.ncjk.utcs.common.socket.server;


import com.ncjk.utcs.common.socket.decoder.ByteToStringDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/1/10.
 */
public class InternalProtocolServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 以("\n")为结尾分割的 解码器
        // 字符串解码 和 编码
        pipeline.addLast("decoder", new ByteToStringDecoder());
        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast("idleStateHandler", new IdleStateHandler(30, 10, 0, TimeUnit.SECONDS));
        // 自己的逻辑Handler
        pipeline.addLast("handler", new InternalProtocolServerHandler());
    }
}
