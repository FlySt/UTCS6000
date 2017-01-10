package com.ncjk.utcs.common.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主要用来客户端需要的测试，实际运行不需要
 * Created by swl on 2016/9/29.
 */
public class NettyClientChannelMap {

    public static Map<ChannelId,Channel> clientChannelMap = new ConcurrentHashMap<>();
    public static Map<ChannelId,String> clientTokenMap = new ConcurrentHashMap<>();
    //客户端Channel
    public static void addClientChannel(ChannelId id, Channel channel){
        clientChannelMap.put(id, channel);
    }
    public static Map<ChannelId, Channel> getClientChannels(){
        return clientChannelMap;
    }

    public static Channel getClientChannel(ChannelId id){
        return clientChannelMap.get(id);
    }

    public static void removeClientChannel(ChannelId id){
        clientChannelMap.remove(id);
    }
    //客户端Token
    public static void addClientTokenMap(ChannelId id, String token){
        clientTokenMap.put(id, token);
    }
    public static Map<ChannelId, String> getClientTokens(){
        return clientTokenMap;
    }

    public static String getClientToken(ChannelId id){
        return clientTokenMap.get(id);
    }

    public static void removeClientToken(ChannelId id){
        clientTokenMap.remove(id);
    }
}
