package com.ncjk.utcs.common.webservice.client;

import com.ncjk.utcs.common.webservice.client.interfaces.CommonCommand;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by swl on 2016/12/29.
 */
@Service("commonCommand")
public class CommonCommandImpl implements CommonCommand {
    /**
     * 创建命令头部
     * @return
     */
    @Override
    public String createHeader() {
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
        message.addElement("Type").setText("REQUEST");
        message.addElement("Seq");
        message.addElement("Body");
        return outPut(document);
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
