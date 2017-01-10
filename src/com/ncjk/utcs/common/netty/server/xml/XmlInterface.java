package com.ncjk.utcs.common.netty.server.xml;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.dom4j.Document;

/**
 * XML接口
 * Created by swl on 2016/9/28.
 */
public interface XmlInterface  {

    /**
     * 创建xml头
     * @param isServer
     * @param isRequest
     * @return
     */
    String createHeaderXml(boolean isServer,boolean isRequest);

    /**
     * 创建心跳包
     * @param
     * @return
     */
    String createPingXml(ChannelId id);

    /**
     * 创建1049Gat协议包Xml
     * @param channel
     * @param content
     * @param deviceIp 信号机Ip地址
     * @return
     */
    String createGatoXml(Channel channel, String content,String deviceIp);

    /**
     * 解析内容
     * @param id
     * @param msg
     * @return
     */
    String analyzeGatResponse(ChannelId id,String msg);
    /**
     * 获取错误响应内容 11:信号机返回SDO_USER  10:GET时没有返回包体信息
     * 12:信号机没有返回SET或者GET属性 13:信号机返回的XML格式不对 14:没有连接的通道 15:没有可用空闲通道
     * @param cmd
     * @param responseCode
     * @return
     */
    String getErrorGatResponse(String cmd,String siganelControlerNum,String responseCode);
    /**
     * 创建自定义内容
     * @param id
     * @param xml
     * @return
     */
    String createCustomXml(ChannelId id,String xml);

    /**
     * 解析内容
     * @param id
     * @param msg
     * @return
     */
    String analyzeResponse(ChannelId id,String msg);
    /**
     * 格式化输出
     * @param doc
     * @return
     */
    String outPut(Document doc);
}
