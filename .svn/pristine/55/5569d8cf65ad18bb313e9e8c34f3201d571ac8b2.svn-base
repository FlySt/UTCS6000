package com.ncjk.utcs.common.netty.client;


import com.ncjk.utcs.common.netty.client.xml.XmlClientInterface;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 主要用来测试，实际运行不需要
 * Created by swl on 2016/9/29.
 */
@Service("nettyClientService")
public class NettyClientService {
    @Resource
    XmlClientInterface xmlClientImp;
    public void changePingState(Integer state){
        NettyClientChannelParam.pingState = state;
        if(NettyClientChannelParam.pingState == 0){
            System.out.println("中断了心跳包的发送");
        }else{
            System.out.println("开启了心跳包的发送");
        }
    }

    /**
     * 发送校时功能
     */
    public String timer(){
        Map<ChannelId,Channel> map = NettyClientChannelMap.getClientChannels();
        Iterator<ChannelId> it = map.keySet().iterator();
        System.out.println(map.size());
        while(it.hasNext()){
            ChannelId key = it.next();
            Channel channel = map.get(key);
            int port = new Integer(channel.remoteAddress().toString().split(":")[1]).intValue();
            System.out.println("channel: " + channel.isWritable()+",port:"+port);
            if(channel.isWritable() && port == 6000){
                String content = NettyClientChannelParam.getPackeg(0,channel.id(), NettyClientChannelParam.MsgType.TIMER);
                channel.writeAndFlush(content);
                break;
            }
        }
        return getResult();
    }
    /**
     * 发送系统断开
     */
    public void logout(){
        Map<ChannelId,Channel> map = NettyClientChannelMap.getClientChannels();
        Iterator<ChannelId> it = map.keySet().iterator();
        System.out.println(map.size());
        while(it.hasNext()){
            ChannelId key = it.next();
            Channel channel = map.get(key);
            int port = new Integer(channel.remoteAddress().toString().split(":")[1]).intValue();
            System.out.println("channel: " + channel.isWritable()+",port:"+port);
            if(channel.isWritable() && port == 7000){
                String token = NettyClientChannelMap.getClientToken(channel.id());
                String content = xmlClientImp.createGatLogoutXml(token);
                channel.writeAndFlush(content);
                break;
            }
        }
    }
    /**
     * 发送系统断开
     */
    public void send(String msg){
        Map<ChannelId,Channel> map = NettyClientChannelMap.getClientChannels();
        Iterator<ChannelId> it = map.keySet().iterator();
        System.out.println(map.size());
        while(it.hasNext()){
            ChannelId key = it.next();
            Channel channel = map.get(key);
            int port = new Integer(channel.remoteAddress().toString().split(":")[1]).intValue();
            System.out.println("channel: " + channel.isWritable()+",port:"+port);
            if(channel.isWritable() && port == 5000){
                channel.writeAndFlush(msg);
                break;
            }
        }
    }
    private String getResult(){
        String result ;
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                //开始执行耗时操作
                while(true){
                    Thread.sleep(10);
                    if(NettyClientChannelParam.response.length()!=0 && NettyClientChannelParam.response!=null){
                        break;
                    }
                }
                return "线程执行完成.";
            }
        };
        try {
            Future<String> future = exec.submit(call);
            String obj = future.get(NettyClientChannelParam.timeOut, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            System.out.println("任务成功返回:" + obj);
            result = NettyClientChannelParam.response.toString();
        } catch (TimeoutException ex) {
            result = "通信超时";
            System.out.println("处理超时啦....");
            //      ex.printStackTrace();
        } catch (Exception e) {
            result = "通信失败";
            System.out.println("处理失败.");
            //   e.printStackTrace();
        }
        // 关闭线程池
        NettyClientChannelParam.response.setLength(0);
        exec.shutdown();
        return result;
    }

}
