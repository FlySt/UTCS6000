package com.ncjk.utcs.common.netty.client.xml;

import io.netty.channel.ChannelId;
import org.dom4j.Document;

/**
 * XML接口
 * Created by swl on 2016/9/28.
 */
public interface XmlClientInterface {

    /**
     * 创建xml头
     * @param isServer
     * @param isRequest
     * @return
     */
    String createHeaderXml(boolean isServer, boolean isRequest);

    /**
     * 创建连接请求xml
     * @return
     */
    String createLoginXml();

    /**
     * 创建心跳包Xml
     * @return
     */
    String createPingXml(String token);

    /**
     * 创建退出包Xml
     * @return
     */
    String createGatLogoutXml(String token);

    /**
     * 创建1049Gat协议包Xml
     * @param id
     * @param identify
     * @return
     */
    String createGatXml(ChannelId id, String identify);

    /**
     * 格式化输出
     * @param doc
     * @return
     */
    String outPut(Document doc);
}
