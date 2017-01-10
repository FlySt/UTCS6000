package com.ncjk.utcs.common.netty.server;




import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.EventLoopGroup;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * Created by SWL on 2016/9/24.
 */
public class NettyChannelMap {

    public static Map<ChannelId,Channel> channelMap = new ConcurrentHashMap<>();
    public static Map<ChannelId,String> serverTokenMap = new ConcurrentHashMap<>();
    public static Map<ChannelId,String> serverModeMap = new ConcurrentHashMap<>();
    public static Map<ChannelId,Integer> channelStateMap = new ConcurrentHashMap<>();
    public static Map<ChannelId,String> channelFlagMap = new ConcurrentHashMap<>();
    public static Map<ChannelId,Channel> ocxChannelIdMap = new ConcurrentHashMap<>();

    public static void addChannel(ChannelId id, Channel channel){
        channelMap.put(id, channel);
    }

    public static Map<ChannelId, Channel> getChannels(){
        return channelMap;
    }

    public static Channel getChannel(ChannelId id){
        return channelMap.get(id);
    }

    public static void removeChannel(ChannelId id){
        channelMap.remove(id);
    }
    //服务端Token
    public static void addServerTokenMap(ChannelId id, String token){
        serverTokenMap.put(id, token);
    }
    public static Map<ChannelId, String> getServerTokens(){
        return serverTokenMap;
    }

    public static String getServerToken(ChannelId id){
        return serverTokenMap.get(id);
    }
    public static void removeServerToken(ChannelId id){
        serverTokenMap.remove(id);
    }

    //服务端Mode ACT客户端主动模式，服务端被动模式 PSV客户端被动模式，服务端主动模式
    public static void addServerModeMap(ChannelId id, String mode){
        serverModeMap.put(id, mode);
    }
    public static Map<ChannelId, String> getServerModes(){
        return serverModeMap;
    }

    public static String getServerMode(ChannelId id){
        return serverModeMap.get(id);
    }
    public static void removeServerMode(ChannelId id){
        serverModeMap.remove(id);
    }

    //通道状态
    public static void addChannelState(ChannelId id, Integer state){
        channelStateMap.put(id, state);
    }

    public static Map<ChannelId, Integer> getChannelStates(){
        return channelStateMap;
    }

    public static Integer getChannelState(ChannelId id){
        return channelStateMap.get(id);
    }

    public static void removeChannelState(ChannelId id){
        channelStateMap.remove(id);
    }

    //对象标识
    public static void addChannelFlag(ChannelId id, String flag){
        channelFlagMap.put(id, flag);
    }
    public static Map<ChannelId, String> getChannelFlags(){
        return channelFlagMap;
    }
    public static String getChannelFlag(ChannelId id){
        return channelFlagMap.get(id);
    }

    public static void removeChannelFlag(ChannelId id){
        channelFlagMap.remove(id);
    }

    //OCX的channelId
    public static void addOcxChannel(ChannelId id, Channel ocxChannel){
        ocxChannelIdMap.put(id, ocxChannel);
    }
    public static Map<ChannelId, Channel> OcxChannels(){
        return ocxChannelIdMap;
    }
    public static Channel getOcxChannel(ChannelId id){
        return ocxChannelIdMap.get(id);
    }
    public static void removeOcxChannel(ChannelId id){
        ocxChannelIdMap.remove(id);
    }
}
