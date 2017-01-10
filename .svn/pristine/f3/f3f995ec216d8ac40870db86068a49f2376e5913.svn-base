package com.ncjk.utcs.common.webservice.server;

import com.ncjk.utcs.common.webservice.server.interfaces.CommonCommand;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Administrator on 2016/12/30.
 */
@Service("serverCommonCommand")
public class CommonCommandImpl implements CommonCommand {

    private Logger logger = Logger.getLogger(CommonCommandImpl.class);
    /**
     * 创建错误命令
     * @param errObj
     * @param errType
     * @param message
     * @return
     */
    @Override
    public String createError(String errObj,String errType,Element message){
        try {
            Document document = setHeaderValue(DocumentHelper.parseText(createHeader("ERROR")),message);
            Element operation = document.getRootElement().element("Body").element("Operation");
            Element sdoError = operation.addElement("SDO_Error");
            sdoError.addElement("ErrObj").setText(errObj);
            sdoError.addElement("ErrType").setText(errType);
            sdoError.addElement("ErrDesc");
            return outPut(document);
        } catch (Exception e) {
            // e.printStackTrace();
            logger.info("创建错误应答失败:"+e.getMessage());
            return null;
        }
    }

    /**
     * 创建命令头部
     * @param type
     * @return
     */
    @Override
    public String createHeader(String type) {
        Document document = DocumentHelper.createDocument();
        Element message = document.addElement("Message");
        message.addElement("Version").setText("1.0");
        message.addElement("Token");
        Element from = message.addElement("From");
        Element fromAddress = from.addElement("Address");
        fromAddress.addElement("Sys").setText("UTCS");
        fromAddress.addElement("SubSys");
        fromAddress.addElement("Instance");
        Element to = message.addElement("To");
        Element toAddress = to.addElement("Address");
        toAddress.addElement("Sys").setText("TICP");
        toAddress.addElement("SubSys");
        toAddress.addElement("Instance");
        if("RESPONSE".equals(type)){
            message.addElement("Type").setText("RESPONSE");
        }else if("ERROR".equals(type)){
            message.addElement("Type").setText("ERROR");
        }
        message.addElement("Seq");
        message.addElement("Body");
        return outPut(document);
    }

    /**
     * 设置命令头部值
     * @param document
     * @return
     */
    @Override
    public Document setHeaderValue(Document document,Element message) {
        String version = message.element("Version").getText();
        String token = message.element("Token").getText();
        String seq = message.element("Seq").getText();
        String name = message.element("Body").element("Operation").attributeValue("name");
        Element root = document.getRootElement();
        root.element("Version").setText(version);
        root.element("Token").setText(token);
        root.element("Seq").setText(seq);
        Element body = root.element("Body");
        Element operation = body.addElement("Operation");
        operation.addAttribute("order","1");
        operation.addAttribute("name",name);
        return document;
    }

    /**
     * 格式化输出
     * @param document
     * @return
     */
    @Override
    public String outPut(Document document) {
        OutputFormat format = OutputFormat.createCompactFormat();
       /* format.setIndent(true); //设置是否缩进
        format.setIndent("   "); //以空格方式实现缩进
        format.setNewlines(true); //设置是否换行*/
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer,format);
        try {
            output.write(document);
            writer.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
