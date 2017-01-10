package com.ncjk.utcs.common.netty.server;


import com.ncjk.utcs.common.netty.server.xml.XmlImp;
import com.ncjk.utcs.common.netty.server.xml.XmlInterface;
import com.ncjk.utcs.common.servlet.SpringUtil;
import com.ncjk.utcs.modules.resources.resources.services.SignalControlerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by swl on 2016/9/24.
 */
@Service("nettyService")
public class NettyService {
    private static Logger logger = Logger.getLogger(NettyService.class);


    public void sendToInside(String server, String cmdbuffer){
        Map<ChannelId, Channel> map = NettyChannelMap.getChannels();
        Iterator<ChannelId> it = map.keySet().iterator();
        while (it.hasNext()) {
            ChannelId key = it.next();
            Channel channel = map.get(key);
            int port = new Integer(channel.localAddress().toString().split(":")[1]).intValue();
            if(channel.isWritable() && port == NettyChannelParam.getPort(1) && NettyChannelMap.getChannelState(key)==0
                    && "PSV".equals(NettyChannelMap.getServerMode(key))){
                synchronized (this){
                    NettyChannelParam.insideResponse.setLength(0);
                }
                String writeContent = NettyChannelParam.buildInsideContent(key,server,cmdbuffer);
                if(writeContent!=null){
                    //修改通道状态
                    NettyChannelMap.removeChannelState(key);
                    NettyChannelMap.addChannelState(key,1);
                    channel.writeAndFlush(writeContent);
                }
                break;
            }
        }
    }


    /**
     * 命令下发
     * @param ocxChannel
     * @param content
     */
    public void sendToGat(Channel ocxChannel, String content){
    //    SignalControlerService signalControlerService = new SignalControlerService();
        XmlInterface xmlInterface = new XmlImp();
        Map<ChannelId, Channel> map = NettyChannelMap.getChannels();
        Iterator<ChannelId> it = map.keySet().iterator();
        //信号机编号
        String signalControlerNum = content.substring(NettyChannelParam.ocxPackageLength+NettyChannelParam.ocxCmdLength,
                NettyChannelParam.ocxPackageLength+NettyChannelParam.ocxCmdLength+NettyChannelParam.signalControlerNumLength);
        System.out.println("signalControlerNum:"+signalControlerNum);
        String cmd  = content.substring(NettyChannelParam.ocxPackageLength, NettyChannelParam.ocxPackageLength + NettyChannelParam.ocxCmdLength);
        //没有可用通道则直接给插件响应错误信息
        if(map.size() == 0){
            String writeContent = xmlInterface.getErrorGatResponse(cmd,signalControlerNum,"14");
            ocxChannel.writeAndFlush(writeContent);
        }
   //     ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SignalControlerService signalControlerService = (SignalControlerService) SpringUtil.getApplicationContext().getBean("signalControlerService",SignalControlerService.class);
        String deviceIp = signalControlerService.findSignalControlerIpByNum(signalControlerNum);
        if(deviceIp==null || deviceIp.length()==0){
            //信号机ip地址为空
            String writeContent = xmlInterface.getErrorGatResponse(cmd,signalControlerNum,"16");
            ocxChannel.writeAndFlush(writeContent);
        }
        while (it.hasNext()) {
            ChannelId key = it.next();
            Channel channel = map.get(key);
            int port = new Integer(channel.localAddress().toString().split(":")[1]).intValue();
           if(channel.isWritable() && port == NettyChannelParam.getPort(0) && "PSV".equals(NettyChannelMap.getServerMode(key))){
    //        if(channel.isWritable() && port == 7000 ){
               if(NettyChannelMap.getChannelState(key)==0 || (NettyChannelMap.getChannelState(key)==1 && NettyChannelMap.getOcxChannel(key).id() == ocxChannel.id())){
                   //通道空闲
                   // ||如果被占用的Channel绑定的OcxChannel与当前OcxChannel一致,则继续使用该Channel,否则寻找下一个空闲通道
                   String writeContent = xmlInterface.createGatoXml(channel,content,deviceIp);
                   //ocxChannelId
                   NettyChannelMap.removeOcxChannel(key);
                   NettyChannelMap.addOcxChannel(key,ocxChannel);

                   //修改通道状态
                   NettyChannelMap.removeChannelState(key);
                   NettyChannelMap.addChannelState(key,1);
                   channel.writeAndFlush(writeContent);
                   break;
               }
            }
            if(!it.hasNext()){
                //没有可用空闲通道直接给插件响应错误信息
                String writeContent = xmlInterface.getErrorGatResponse(cmd,signalControlerNum,"15");
                ocxChannel.writeAndFlush(writeContent);
              //  getResult(ocxChannel,content);
            }
        }
    }
    public String  getResult(){
        final ExecutorService exec = Executors.newFixedThreadPool(1);
   /*     Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                //开始执行耗时操作
                while(true){
                    Thread.sleep(10);
                    if(NettyChannelParam.insideResponse!=null && NettyChannelParam.insideResponse.length()>0){
                        break;
                    }
                }
                return "线程执行完成.接收到了内部协议返回的原始信号机信息";
            }
        };*/
        FutureTask<String> future =
                new FutureTask<String>(new Callable<String>() {//使用Callable接口作为构造参数
                    public String call() throws InterruptedException {
                        //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型
                        while(true){
                            Thread.sleep(10);
                            if(NettyChannelParam.insideResponse!=null && NettyChannelParam.insideResponse.length()>0){
                                break;
                            }
                        }
                        return "线程执行完成.接收到了内部协议返回的原始信号机信息";
                    }});
        exec.execute(future);
        try {
      //      Future<String> future = exec.submit(call);
            String obj = future.get(NettyChannelParam.timeOut, TimeUnit.MILLISECONDS); //任务处理超时时间设为 5 秒
      //      logger.info("任务成功返回:"+obj);
        } catch (TimeoutException ex) {
            logger.info("等待接入系统返回原始信号机信息超时....");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result",false);
            jsonObject.put("content","超时");
            future.cancel(true);
            return jsonObject.toString();
            //ex.printStackTrace();
        } catch (Exception e) {
            future.cancel(true);
            logger.info("处理失败.");
            //   e.printStackTrace();
        }finally {
            exec.shutdown();
        }
        return NettyChannelParam.insideResponse.toString();
    }

    /**
     * 响应消息给插件
     * @param msg
     * @param id
     */
    public void responseToOcx(String msg,ChannelId id){
        Channel ocxChannel = NettyChannelMap.getOcxChannel(id);
        logger.info("回应消息给插件，通道Id为:"+ocxChannel.id()+"消息为:"+msg);
        ocxChannel.writeAndFlush(msg);
    }
}
