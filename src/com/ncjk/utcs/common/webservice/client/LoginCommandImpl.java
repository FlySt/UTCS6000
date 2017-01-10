package com.ncjk.utcs.common.webservice.client;

import com.ncjk.ticp.ticp.wgb.GbTICPJK;
import com.ncjk.ticp.ticp.wgb.IGBTICPJK;
import com.ncjk.utcs.common.webservice.ServiceParams;
import com.ncjk.utcs.common.webservice.client.interfaces.CommonCommand;
import com.ncjk.utcs.common.webservice.client.interfaces.LoginCommand;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/29.
 */
@Service("loginCommand")
public class LoginCommandImpl implements LoginCommand {

    private Logger logger = Logger.getLogger(LoginCommandImpl.class);
    @Resource
    CommonCommand commonCommand;

    @Override
    public void Login() {
        logger.info("登录");
       LoginThread loginThread = new LoginThread();
        new Thread(loginThread).start();
    }

    /**
     * 登录线程
     */
    class LoginThread implements Runnable{
        private QName SERVICE_NAME = new QName("http://wgb.ticp.ticp.ncjk.com/", "gbTICPJK");
        private Integer port = 8080;
        private String ipAddr = "192.168.2.52";
        private String url = "http://"+ ipAddr +":"+port+"/TICPCentral/gbTICPJK?wsdl";
        @Override
        public void run() {
           while (!ServiceParams.isLogined){
               try {
                   URL wsdlURL = new URL(url);
                   logger.info("发送登录命令...");
                   GbTICPJK ss = new GbTICPJK(wsdlURL, SERVICE_NAME);
                   IGBTICPJK implPort = ss.getGBTICPJKImplPort();
                   String result = implPort.sonTicpCommon(createLogin());
                   if(result!=null && result.length()>0){
                       logger.info("登录应答:"+result);
                       ServiceParams.isLogined = true;
                   }
                   Thread.sleep(10);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
        }
    }

    /**
     * 创建登录命令
     */
    @Override
    public String createLogin() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String seqText = dateFormat.format(new Date()) + "000001";
            Document doc = DocumentHelper.parseText(commonCommand.createHeader());
            Element message = doc.getRootElement();
            message.element("Seq").setText(seqText);
            Element body = message.element("Body");
            Element operation = body.addElement("Operation");
            operation.addAttribute("order", "1");
            operation.addAttribute("name", "Login");
            Element sdo_user = operation.addElement("SDO_User");
            sdo_user.addElement("UserName").setText("Admin");
            sdo_user.addElement("Pwd").setText("12345");
            return commonCommand.outPut(doc);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
