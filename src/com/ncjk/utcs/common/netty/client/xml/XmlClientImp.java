package com.ncjk.utcs.common.netty.client.xml;


import com.ncjk.utcs.common.netty.client.NettyClientChannelMap;
import io.netty.channel.ChannelId;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * xml实现类
 * Created by swl on 2016/9/28.
 */
@Service("xmlClientImp")
public class XmlClientImp implements XmlClientInterface {
    private String ipAddr = "192.168.200.244";
    /**
     * 创建xml格式字符串
     */
    public  String createHeaderXml(boolean isServer,boolean isRequest) {
        Document document = DocumentHelper.createDocument();
        Element message = document.addElement("Message");
        message.addElement("Version").setText("1.1");
        message.addElement("Token");
        Element from = message.addElement("From");
        Element fromAddress = from.addElement("Address");
        if(isServer){
            fromAddress.addElement("Sys").setText("UTCS");
        }else{
            fromAddress.addElement("Sys").setText(ipAddr);
        }
        fromAddress.addElement("SubSys");
        fromAddress.addElement("Instance");
        Element to = message.addElement("To");
        Element toAddress = to.addElement("Address");
        if(isServer){
            toAddress.addElement("Sys").setText(ipAddr);
        }else {
            toAddress.addElement("Sys").setText("UTCS");
        }
        toAddress.addElement("SubSys");
        toAddress.addElement("Instance");
        if(isRequest){
            message.addElement("Type").setText("REQUEST");
        }else {
            message.addElement("Type").setText("RESPONSE");
        }
        message.addElement("Seq");
        message.addElement("Body");

        return outPut(document);
    }


    /**
     * 创建连接请求xml
     *
     * @return
     */
    @Override
    public String createLoginXml() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String seqText = dateFormat.format(new Date())+"000001";
            Document doc = DocumentHelper.parseText(createHeaderXml(false,true));
            Element message = doc.getRootElement();
            message.element("Seq").setText(seqText);
            Element body = message.element("Body");
            Element operation = body.addElement("Operation");
            operation.addAttribute("order","1");
            operation.addAttribute("name","Login");
            Element sdo_user = operation.addElement("SDO_User");
            sdo_user.addElement("UserName").setText("admin");
            sdo_user.addElement("Pwd").setText("123456");
            sdo_user.addElement("SelfDef_Mode").setText("ACT");
            return outPut(doc);
        } catch (DocumentException e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 创建心跳包Xml
     * @return
     */
    @Override
    public String createPingXml(String token) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String seqText = dateFormat.format(new Date())+"000005";
        try {
            Document doc = DocumentHelper.parseText(createHeaderXml(false,true));
            Element message = doc.getRootElement();
            message.element("Token").setText(token);
            message.element("Type").setText("PUSH");
            message.element("Seq").setText(seqText);
            Element body = message.element("Body");
            Element operation = body.addElement("Operation");
            operation.addAttribute("order","1");
            operation.addAttribute("name","Notify");
            operation.addElement("SDO_HeartBeat");
            return  outPut(doc);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建GAT协议退出包Xml
     * @param token
     * @return
     */
    @Override
    public String createGatLogoutXml(String token) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String seqText = dateFormat.format(new Date())+"000003";
            Document doc = DocumentHelper.parseText(createHeaderXml(false,true));
            Element message = doc.getRootElement();
            message.element("Token").setText(token);
            message.element("Seq").setText(seqText);
            Element body = message.element("Body");
            Element operation = body.addElement("Operation");
            operation.addAttribute("order","1");
            operation.addAttribute("name","Logout");
            Element sdo_user = operation.addElement("SDO_USER");
            sdo_user.addElement("UserName").setText("username");
            sdo_user.addElement("Pwd").setText("pwd");
            return outPut(doc);
        } catch (DocumentException e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 创建1049Gat协议包Xml
     * @param id
     * @param identify
     * @return
     */
    @Override
    public String createGatXml(ChannelId id, String identify) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(identify);
        try {
            Document document = DocumentHelper.parseText(createHeaderXml(true,true));
            String token = NettyClientChannelMap.getClientToken(id);
            String seqText = dateFormat.format(new Date())+"000082";
            if("0x82".equals(identify)){
                Element message = document.getRootElement();
                message.element("Token").setText(token);
                message.element("Seq").setText(seqText);
                Element body = message.element("Body");
                Element operation = body.addElement("Operation");
                operation.addAttribute("order","1");
                operation.addAttribute("name","Set");
                operation.addElement("SelDef_CMMaxLine").setText("16");
                return outPut(document);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 格式化输出
     * @param doc
     * @return
     */
    public String outPut(Document doc){
        OutputFormat format = OutputFormat.createCompactFormat();
       /* format.setIndent(true); //设置是否缩进
        format.setIndent("   "); //以空格方式实现缩进
        format.setNewlines(true); //设置是否换行*/
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer,format);
        try {
            output.write(doc);
            writer.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
