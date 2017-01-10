package com.ncjk.utcs.common.netty.server;


import com.ncjk.utcs.common.netty.server.xml.XmlImp;
import com.ncjk.utcs.common.netty.server.xml.XmlInterface;
import com.ncjk.utcs.common.servlet.SpringUtil;
import com.ncjk.utcs.modules.resources.resources.services.SignalControlerService;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalControlerService;
import com.ncjk.utcs.modules.system.services.NetWorkParamService;
import com.ncjk.utcs.modules.system.services.PlugParamService;
import com.ncjk.utcs.modules.system.services.interfaces.INetWorkParamService;
import com.ncjk.utcs.modules.system.services.interfaces.IPluginParamService;
import io.netty.channel.ChannelId;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by swl on 2016/9/24.
 */
public class NettyChannelParam {
    private static Logger logger = Logger.getLogger(NettyChannelParam.class);
    /*内部协议
    包格式：包长+令牌+标志+命令字+数据内容
    包长：【0-99999】，固定5位，不足前补0,包括本身。
    令牌格式：【随机数】，32字节可见字符。
    初始令牌(登录指令有效):JK_UTCS_INITTOKEN_WANHUI219_KANG

    标志：1:有后续包，0:无后续包
    返回码（4位）:0000-成功 其它-失败,响应包返回码为0000时，才有数据内容。
    * */
    //包长  5位
    public final static Integer packageLength = 5;
    //令牌长度 32位
    public final static Integer tokenLength = 32;
    //标志位长度 1位
    public final static Integer signLength = 1;
    //返回码长度 4 位
    public final static Integer responseCodeLength = 4;
    //登录初始令牌
    public final static String  logToken = "JK_UTCS_INSIDETOKEN_WH0219_KANG_";
    //连接登录时的命令字
    public final static String  logCmd = "LOGS";
    //连接登录时返回的命令字
    public final static String  logRturnCmd = "LOGR";
    //原信号机命令字
    public final static String  oldSignalCmd = "AOXS";
    //原信号机返回命令字
    public final static String  oldSignalRturnCmd = "AOXR";
    //心跳命令字
    public final static String  pingCmd = "HJ_S";
    //心跳返回命令字
    public final static String  pingReturnCmd = "HJ_R";
    //校时命令字
    public final static String  timerCmd = "TIMS";
    //校时返回命令字
    public final static String  timerReturnCmd = "TIMR";

    public final static Integer timeOut = 5000;
    //收到回应的内容
    public static StringBuffer response = new StringBuffer("");
    public static StringBuffer insideResponse = new StringBuffer("");
    //客户端回应的内容
    private static  String result = "";
    //序列号
    public static long seq = 0;

    //1049登录初始令牌
    public final static String  gatLogToken = "JK_UTCS_1049TOKEN_WHY0308_DKANG_";
    public  static String  gatUsername = "admin";
    public  static String  gatPwd = "123456";
    /*OCX协议*
    5.通讯格式（通讯超时10秒）
    包头格式：
    长度(4位整型)，不足前补0，包括自己
    命令字(1位BYTE)，如0x8D
    通讯标识(1位整型)，1：查询 0：设置
    返回码(2位字符串，发送包无意义)，00：成功，其它：失败
    包体格式：
    以‘|’进行字段区分，如“1|2|3|4|0”代表“<No>1</No><Month>2</Month> <Week>3</Week><Day>4</Day><TINo>0</TINo>”
    eg:
    设置【时基调度表】指令
    发送：0017\x8D0001|2|3|4|0
    响应：0008\x8D011（此例为失败）
    查询【时基调度表】指令
    发送：0008\x8D100
    响应：0017\x8D1001|2|3|4|0
     */
    //包长  4位
    public final static Integer ocxPackageLength = 4;
    public final static Integer ocxCmdLength = 2;
    public final static Integer ocxFlagLength = 1;
    public final static Integer ocxResponseCodeLength = 2;
    public final static Integer signalControlerNumLength = 17;

    /**信号机在线状态*/
    public static HashMap<String,Integer> signalStatus = new HashMap<>();
    //命令数用来记录查询信号机实时状态的次数 5次一个循环
    public static Integer cmdCount = 0;

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        NettyChannelParam.result = result;
    }
    public enum  MsgType {
        LOGIN,PING,ASK,REPLY,LOGOUT,TIMER,PUSH,OLDSIGNAL,ERROR
    }
    public static MsgType getMsgType(String msg){
        Integer logCmdStartIndex = NettyChannelParam.packageLength + NettyChannelParam.tokenLength + NettyChannelParam.signLength;
        Integer logCmdEndIndex = logCmdStartIndex + NettyChannelParam.logCmd.length();
        Integer logTokenStartIndex = NettyChannelParam.packageLength;
        Integer logTokenEndIndex =  NettyChannelParam.packageLength + NettyChannelParam.tokenLength;
        String token = msg.substring(logTokenStartIndex,logTokenEndIndex);
        String cmd = msg.substring(logCmdStartIndex,logCmdEndIndex);

        if(logToken.equals(token) && logCmd.equals(cmd)){
            return MsgType.LOGIN;
        }else{
            switch (cmd){
                case pingCmd:
                    return MsgType.PING;
                case oldSignalRturnCmd:
                    return MsgType.OLDSIGNAL;
                case timerCmd:
                    return MsgType.TIMER;
                default:
                    return MsgType.REPLY;
            }
        }

    }
    public static String getPackeg( ChannelId id,MsgType type,String clientToken){
        String cmd = "";
        String content = "";
        String token = "";
        switch (type){
            case LOGIN:
                cmd = logRturnCmd;
                content = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                token = "JK_UTCS_INSIDETOKEN_WH0219_KANG_";
                break;
            case PING:
                cmd = pingReturnCmd;
                token = NettyChannelMap.getServerToken(id);
                break;
            case TIMER:
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                cmd = timerReturnCmd;
                token = NettyChannelMap.getServerToken(id);
                content = dateFormat.format(new Date());
                break;
            default:
                break;
        }
        String sign = "0";//标志
        String pack ;
        if(clientToken.equals(token)){//验证token
            pack = token+sign+cmd+"0000"+content;
        }else{
            pack = token+sign+cmd+"1111";
        }
        /**整型转十六进制字符串**/
        String length = Integer.toHexString(pack.length()+5).toUpperCase();
        length = String.format("%5s",length);
        length= length.replaceAll(" ","0");
     //   String length =  String.format("%05d",pack.length()+5);
        pack = length+pack;
        return pack;
    }
    //获取返回信息的Token用来连接验证之后保存在Map里
    public static String getToken(String msg){
        String token = msg.substring(msg.length()-32,msg.length());
        return token;
    }
    //获取接收到信息的Token,用来与Map里保存的Token对比是否一致
    public static String getMsgToken(String msg){
        String token = msg.substring(packageLength,packageLength+32);
        return token;
    }

    /**
     * 获取1049协议命令类型
     * @param msg
     * @return
     */
    public static MsgType getGatMsgType(String msg){
        try {
            Document doc = DocumentHelper.parseText(msg);
            Element message = doc.getRootElement();
            Element body = message.element("Body");
            Element operation = body.element("Operation");
            String name = operation.attributeValue("name");
            if("Login".equals(name)){
                return MsgType.LOGIN;
            }else if("Notify".equals(name)){
                if(operation.element("SDO_HeartBeat")!=null){
                    return MsgType.PING;
                }else if("PUSH".equals(message.elementText("Type"))){
                    return MsgType.PUSH;
                }
            }else if("Logout".equals(name)){
                return MsgType.LOGOUT;
            } else{
                if("REQUEST".equals(message.elementText("Type"))){
                    return MsgType.ASK;
                }else if("RESPONSE".equals(message.elementText("Type"))){
                    return MsgType.REPLY;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            return MsgType.ERROR;
        }
        return MsgType.ERROR;
    }

    private static String getGatFromIp(String msg){
        try {
            Document doc = DocumentHelper.parseText(msg);
            String clientIp = doc.getRootElement().element("From").element("Address").element("Sys").getText();
            return clientIp;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取服务端模式 ACT被动模式 ，PSV主动模式
     * @param msg
     * @param type 0:内部协议， 1:1049协议
     * @return
     */
    public static String getServerMode(String msg,Integer type){
        if(type == 1){
            try {
                Document doc = DocumentHelper.parseText(msg);
                String mode = doc.getRootElement().element("Body").element("Operation").element("SDO_User").element("SelfDef_Mode").getText();
                return mode;
            } catch (DocumentException e) {
                logger.error("getServerMode",e);
                return null;
            }
        }else{
            Integer modeStartIndex = packageLength+tokenLength+signLength+responseCodeLength;
            String mode = msg.substring(modeStartIndex,modeStartIndex+3);
            return mode;
        }
    }
    public static String getGatPackeg(String msg,MsgType type,ChannelId id){
        XmlInterface xmlImp = new XmlImp();
        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        try {
            Document doc = DocumentHelper.parseText(msg);
            String version =  doc.getRootElement().elementText("Version");
            String seq = doc.getRootElement().element("Seq").getText();
            String clientToken = doc.getRootElement().element("Token").getText();
            String clientIp = doc.getRootElement().element("From").element("Address").element("Sys").getText();
            if(type == MsgType.LOGIN){
                String username = doc.getRootElement().element("Body").element("Operation").element("SDO_User").element("UserName").getText();
                String pwd = doc.getRootElement().element("Body").element("Operation").element("SDO_User").element("Pwd").getText();
                String mode = doc.getRootElement().element("Body").element("Operation").element("SDO_User").element("SelfDef_Mode").getText();
                Document document = DocumentHelper.parseText(xmlImp.createHeaderXml(true,false));
                Element message = document.getRootElement();
                message.element("Version").setText(version);
                message.element("Token").setText(token);
                message.element("To").element("Address").element("Sys").setText(clientIp);
                message.element("Seq").setText(seq);
                Element body = message.element("Body");
                Element operation = body.addElement("Operation");
                operation.addAttribute("order","1");
                operation.addAttribute("name","Login");
                if(!gatUsername.equals(username) || !gatPwd.equals(pwd) || !gatLogToken.equals(clientToken)){
                    Element sdo_error = operation.addElement("SDO_Error");
                    sdo_error.addElement("ErrObj").setText("SDO_User");
                    if(!gatUsername.equals(username)){
                        sdo_error.addElement("ErrType").setText("SDE_UserName");
                    }else if(!gatPwd.equals(pwd)){
                        sdo_error.addElement("ErrType").setText("SDE_Pwd");
                    }else if(!gatLogToken.equals(clientToken)){
                        sdo_error.addElement("ErrType").setText("SDE_Token");
                    }
                    sdo_error.addElement("ErrDesc").setText("");
                }else{
                    Element sdo_user = operation.addElement("SDO_User");
                    sdo_user.addElement("UserName").setText(username);
                    sdo_user.addElement("Pwd").setText("");
                    sdo_user.addElement("SelfDef_Mode").setText(mode);
                }
                return xmlImp.outPut(document);
            }else if(type == MsgType.LOGOUT){
                String username = doc.getRootElement().element("Body").element("Operation").element("SDO_User").element("UserName").getText();
                Document document = DocumentHelper.parseText(xmlImp.createHeaderXml(true,false));
                Element message = document.getRootElement();
                message.element("Version").setText(version);
                message.element("Token").setText(token);
                message.element("To").element("Address").element("Sys").setText(clientIp);
                message.element("Seq").setText(seq);
                Element body = message.element("Body");
                Element operation = body.addElement("Operation");
                operation.addAttribute("order","1");
                operation.addAttribute("name","Logout");
                Element sdo_user = operation.addElement("SDO_User");
                sdo_user.addElement("Username").setText(username);
                return xmlImp.outPut(document);
            } else if(type == MsgType.PUSH){
                if(!NettyChannelMap.getServerToken(id).equals(clientToken)){//token验证失败
                    System.out.println("Token验证失败");
                    Document document = DocumentHelper.parseText(xmlImp.createHeaderXml(true,false));
                    Element message = document.getRootElement();
                    message.element("Version").setText(version);
                    message.element("Token").setText(token);
                    message.element("To").element("Address").element("Sys").setText(clientIp);
                    message.element("Seq").setText(seq);
                    Element body = message.element("Body");
                    Element operation = body.addElement("Operation");
                    operation.addAttribute("order","1");
                    operation.addAttribute("name","Notify");
                    Element sdo_error = operation.addElement("SDO_Error");
                    return xmlImp.outPut(document);
                }else{//token验证成功
                    System.out.println("Token验证成功");
                    doc.getRootElement().element("From").element("Address").element("Sys").setText("UTCS");
                    doc.getRootElement().element("To").element("Address").element("Sys").setText(clientIp);
                    doc.getRootElement().element("Type").setText("RESPONSE");
                    return xmlImp.outPut(doc);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    //1049协议获取信息内容里的token
    public static String getGatToken(String msg){
        try {
            Document doc = DocumentHelper.parseText(msg);
            String token = doc.getRootElement().elementText("Token");
            return token;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 生成需要发送的内部协议的原始信号机协议
     * @param id
     * @param server
     * @param cmdbuffer
     * @return
     */
    public static String buildInsideContent(ChannelId id,String server,String cmdbuffer){
        String token = NettyChannelMap.getServerToken(id);
        String cmd = oldSignalCmd;
        String data = server+"|"+cmdbuffer;
        String content = token+"0"+cmd+data;
        /**整型转十六进制字符串**/
        String length = Integer.toHexString(content.length()+5).toUpperCase();
        length = String.format("%5s",length);
        length= length.replaceAll(" ","0");
        content = length+content;
        return content;
    }


    /**
     * 获取内部协议发来的原始信号机协议内容
     * @return
     */
    public static String getInsideContent(String msg){
   //     Integer startIndex = packageLength+tokenLength+signLength+oldSignalRturnCmd.length()+1;
        Integer startIndex = msg.indexOf("{");
        String content = msg.substring(startIndex);
        if(cmdCount >= 5){
            cmdCount = 0;
            changeSignalStatus(content);
        }
        return content;
    }
    private static void changeSignalStatus(String content){
        JSONObject jsonObject = JSONObject.fromObject(content);
      //  logger.info(jsonObject.toString());
        String contentS = jsonObject.getString("content");
    //    logger.info("contentS:"+contentS);
        if(contentS!=null && contentS.length()>0){
            JSONObject contentJs = JSONObject.fromObject(contentS);
            if(contentJs!=null){
                JSONArray statusJs = contentJs.getJSONArray("status");
                if(statusJs!=null){
                    for(int i=0;i<statusJs.size();i++){
                        JSONObject object = statusJs.getJSONObject(i);
                        String ip = object.getString("ip");
                        if(ip!=null && ip.length()>0){
                            signalStatus.put(ip,0);//在线状态
                        }
                    }
                }
            }
        }
    }
    /**
     * 获取协议端口号
     * @param type  0-1049协议  1-内部协议 2-插件通讯协议端口号
     * @return
     */
    public static Integer getPort(int type){
        if(type==2){
            IPluginParamService pluginParamService = (PlugParamService) SpringUtil.getApplicationContext().getBean("plugParamService",PlugParamService.class);
            return pluginParamService.getTcpPort();
        }else{
            INetWorkParamService netWorkParamService = (NetWorkParamService) SpringUtil.getApplicationContext().getBean("netWorkParamService",NetWorkParamService.class);
            return  netWorkParamService.getPort(type);
        }
    }

    /**
     * 获取有效心跳次数
     * @return
     */
    public static Integer getHJNum(){
        INetWorkParamService netWorkParamService = (NetWorkParamService) SpringUtil.getApplicationContext().getBean("netWorkParamService",NetWorkParamService.class);
        return  netWorkParamService.getHjNum();
    }

    /**
     * 获取心跳步长
     * @return
     */
    public static Integer getHjStep(){
        INetWorkParamService netWorkParamService = (NetWorkParamService) SpringUtil.getApplicationContext().getBean("netWorkParamService",NetWorkParamService.class);
        return  netWorkParamService.getHjStep();
    }

    /**
     * 获取内部协议最大连接数
     * @return
     */
    public static Integer getInsideConNum(){
        INetWorkParamService netWorkParamService = (NetWorkParamService) SpringUtil.getApplicationContext().getBean("netWorkParamService",NetWorkParamService.class);
        return  netWorkParamService.getInsideConNum();
    }

    /**
     * 获取1049协议最大连接数
     */
    public static Integer getGat1049ConNum(){
        INetWorkParamService netWorkParamService = (NetWorkParamService) SpringUtil.getApplicationContext().getBean("netWorkParamService",NetWorkParamService.class);
        return  netWorkParamService.getGat1049ConNum();
    }

    /**
     * 改变信号机状态
     * @param state
     */
    public static void setSignalControlerState(String msg,Integer state){
        String deviceIp = getGatFromIp(msg);
        ISignalControlerService signalControlerService = (SignalControlerService) SpringUtil.getApplicationContext().getBean("signalControlerService",SignalControlerService.class);
        signalControlerService.setSignalControlerState(deviceIp,state);
    }
}
