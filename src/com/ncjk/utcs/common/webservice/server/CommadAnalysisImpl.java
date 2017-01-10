package com.ncjk.utcs.common.webservice.server;

import com.ncjk.utcs.common.webservice.server.interfaces.CommadAnalysis;
import com.ncjk.utcs.common.webservice.server.interfaces.SysInfoCommand;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

/**
 * Created by swl on 2016/12/30.
 * 命令解析
 */
@Service("commadAnalysis")
public class CommadAnalysisImpl implements CommadAnalysis {

    private Logger logger = Logger.getLogger(CommadAnalysisImpl.class);
    @Resource
    SysInfoCommand sysInfoCommand;
    @Override
    public String annlysis(String command) {

        try {
            Document document = DocumentHelper.parseText(command);
            Element message = document.getRootElement();
            Element body = message.element("Body");
            Element operation = body.element("Operation");
            if(operation.element("SysInfo")!=null){
                return sysInfoCommand.sysInfoAnalysis(message);
            }
        } catch (Exception e) {
         //   e.printStackTrace();
            logger.info("解析的命令格式错误:"+e.getMessage());
            return e.getMessage();
        }
        return null;
    }






}
