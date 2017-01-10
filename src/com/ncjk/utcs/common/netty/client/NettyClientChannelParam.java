package com.ncjk.utcs.common.netty.client;



import com.ncjk.utcs.common.netty.client.xml.XmlClientImp;
import io.netty.channel.ChannelId;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by swl on 2016/9/24.
 */
public class NettyClientChannelParam {
    /*内部协议
    包格式：包长+令牌+标志+命令字+数据内容
    包长：【0-99999】，固定5位，不足前补0,包括本身。
    令牌格式：【随机数】，32字节可见字符。
    初始令牌(登录指令有效):JK_UTCS_INITTOKEN_WANHUI219_KANG

    标志：1:有后续包，0:无后续包
    返回码（4位）:0000-成功 其它-失败,响应包返回码为0000时，才有数据内容。
    * */
    //1发送心跳包 0 不发送
    public static Integer pingState = 1;
    //包长  5位
    public final static Integer packageLength = 5;
    //令牌长度 32位
    public final static Integer tokenLength = 32;
    //标志位长度 1位
    public final static Integer signLength = 1;
    //登录初始令牌
    public final static String  logToken = "JK_UTCS_INSIDETOKEN_WH0219_KANG_";
    //连接登录时的命令字
    public final static String  logCmd = "LOGS";
    //连接登录时返回的命令字
    public final static String  logRturnCmd = "LOGR";
    //心跳命令字
    public final static String  pingCmd = "HJ_S";
    //心跳返回命令字
    public final static String  pingReturnCmd = "HJ_R";

    public final static String oldSignaCmd = "AOXS";
    public final static String oldSignarReturnCmd = "AOXR";
    //心校时返回命令字
    public final static String  timerReturnCmd = "TIMR";
    public final static Integer timeOut = 1000;
    //收到回应的内容
    public static StringBuffer response = new StringBuffer("");
    //客户端回应的内容
    private static  String result = "";

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        NettyClientChannelParam.result = result;
    }
    public enum  MsgType {
        LOGIN,PING,ASK,REPLY,LOGOUT,TIMER,OLDSIGNAL
    }
    public static String getPackeg(Integer cmdid, ChannelId id,MsgType type){
        String cmd = "";
        String content = "";
        String token = "";
        switch (type){
            case LOGIN:
                cmd = logCmd;
                content = "PSV";
                token = logToken;
                break;
            case PING:
                cmd = pingCmd;
                token = NettyClientChannelMap.getClientToken(id);
                break;
            case TIMER:
                cmd = "TIMS";
                token = NettyClientChannelMap.getClientToken(id);
                break;
            case OLDSIGNAL:
                cmd = oldSignarReturnCmd;
                token = NettyClientChannelMap.getClientToken(id);
                String status ="";
                if(cmdid==16) {
                 //    status = "{\"status\":[{\"ip\":\"192.168.200.29\",\"type\":80,\"context\":\"{\\\"year\\\":16,\\\"month\\\":12,\\\"day\\\":7,\\\"hour\\\":14,\\\"minute\\\":21,\\\"second\\\":12,\\\"control_mode\\\":0,\\\"PhasePeriod\\\":0,\\\"status1\\\":56310,\\\"status2\\\":62430,\\\"status3\\\":56310,\\\"status4\\\":62430,\\\"RemainTime\\\":3}\"},{\"ip\":\"192.168.200.25\",\"type\":82,\"context\":\"{\\\"year\\\":16,\\\"month\\\":12,\\\"day\\\":7,\\\"hour\\\":14,\\\"minute\\\":21,\\\"second\\\":13,\\\"control_mode\\\":33,\\\"dailystatus\\\":0,\\\"PeriodNum\\\":1,\\\"current_step\\\":2,\\\"PhaseCycleNum\\\":1,\\\"PhaseStepNum\\\":3,\\\"status1\\\":4385,\\\"status2\\\":4617,\\\"status3\\\":4385,\\\"status4\\\":4617,\\\"PhasePeriod\\\":0,\\\"TimeSpan\\\":10,\\\"RemainTime\\\":8,\\\"TimeSum\\\":64}\"}]}\"}";
                    status = "{\"status\":[{\"ip\":\"192.168.200.100\",\"type\":4,\"context\":\"{\\\"year\\\":16,\\\"month\\\":12,\\\"day\\\":8,\\\"hour\\\":10,\\\"minute\\\":8,\\\"second\\\":18,\\\"run_status\\\":2,\\\"day_TableNo\\\":1,\\\"day_step\\\":2,\\\"hope_day\\\":1,\\\"run_way\\\":1,\\\"PhaseNo\\\":1,\\\"status\\\":\\\"1,3,1,1,1,1,1,3,1,3,1,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0\\\",\\\"CycleSum\\\":72,\\\"SecondNo\\\":30,\\\"CountDown\\\":\\\"42,3,0,0,6,24,0,0,42,3,0,0,6,24,0,0\\\"}\"},{\"ip\":\"192.168.200.29\",\"type\":80,\"context\":\"{\\\"year\\\":16,\\\"month\\\":12,\\\"day\\\":8,\\\"hour\\\":13,\\\"minute\\\":22,\\\"second\\\":18,\\\"control_mode\\\":0,\\\"PhasePeriod\\\":0,\\\"status1\\\":62451,\\\"status2\\\":62454,\\\"status3\\\":62451,\\\"status4\\\":62454,\\\"RemainTime\\\":2}\"}]}";
                }else if(cmdid == 17){
                    status ="{\"s\":34,\"n\":59,\"h\":13,\"w\":3,\"d\":7,\"m\":12,\"y\":16}";
                }else if(cmdid == 173){
                    status = "{\"log\":\"[20161204 星期7 09:19:00]:上位机发来新校时信息, 系统校时完成.\\r\\n[20161204 星期7 09:19:00]:没有接收到GPS信息.\\r\\n[20161204 星期7 09:19:00]:系统启动成功.\\r\\n[20161204 星期7 09:19:00]:降级配置表获取成功.\\r\\n[20161204 星期7 09:19:00]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161204 星期7 09:19:00]:特殊灯色表获取成功.\\r\\n[20161204 星期7 09:19:00]:特殊节假日设置获取成功.\\r\\n[20161128 星期1 14:19:14]:上位机发来新校时信息, 系统校时完成.\\r\\n[20161128 星期1 14:19:14]:没有接收到GPS信息.\\r\\n[20161128 星期1 14:19:14]:系统启动成功.\\r\\n[20161128 星期1 14:19:14]:降级配置表获取成功.\\r\\n[20161128 星期1 14:19:14]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161128 星期1 14:19:14]:特殊灯色表获取成功.\\r\\n[20161128 星期1 14:19:14]:特殊节假日设置获取成功.\\r\\n[20161128 星期1 14:18:15]:系统启动成功.\\r\\n[20161128 星期1 14:18:15]:降级配置表获取成功.\\r\\n[20161128 星期1 14:18:15]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161128 星期1 14:18:15]:特殊灯色表获取成功.\\r\\n[20161128 星期1 14:18:15]:特殊节假日设置获取成功.\\r\\n[20161122 星期2 11:31:02]:没有接收到GPS信息.\\r\\n[20161122 星期2 11:31:02]:系统启动成功.\\r\\n[20161122 星期2 11:31:02]:降级配置表获取成功.\\r\\n[20161122 星期2 11:31:02]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161122 星期2 11:31:02]:特殊灯色表获取成功.\\r\\n[20161122 星期2 11:31:02]:特殊节假日设置获取成功.\\r\\n[20161111 星期5 14:43:10]:上位机发来新校时信息, 系统校时完成.\\r\\n[20161111 星期5 14:43:10]:没有接收到GPS信息.\\r\\n[20161111 星期5 14:43:10]:系统启动成功.\\r\\n[20161111 星期5 14:43:10]:降级配置表获取成功.\\r\\n[20161111 星期5 14:43:10]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161111 星期5 14:43:10]:特殊灯色表获取成功.\\r\\n[20161111 星期5 14:43:10]:特殊节假日设置获取成功.\\r\\n[20161104 星期5 08:10:51]:没有接收到GPS信息.\\r\\n[20161104 星期5 08:10:51]:系统启动成功.\\r\\n[20161104 星期5 08:10:51]:降级配置表获取成功.\\r\\n[20161104 星期5 08:10:51]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161104 星期5 08:10:51]:特殊灯色表获取成功.\\r\\n[20161104 星期5 08:10:51]:特殊节假日设置获取成功.\\r\\n[20161025 星期2 14:52:54]:没有接收到GPS信息.\\r\\n[20161025 星期2 14:52:54]:系统启动成功.\\r\\n[20161025 星期2 14:52:54]:降级配置表获取成功.\\r\\n[20161025 星期2 14:52:54]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161025 星期2 14:52:54]:特殊灯色表获取成功.\\r\\n[20161025 星期2 14:52:54]:特殊节假日设置获取成功.\\r\\n[20161025 星期2 14:40:14]:上位机发来新校时信息, 系统校时完成.\\r\\n[20161025 星期2 14:40:14]:没有接收到GPS信息.\\r\\n[20161025 星期2 14:40:14]:系统启动成功.\\r\\n[20161025 星期2 14:40:14]:降级配置表获取成功.\\r\\n[20161025 星期2 14:40:14]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161025 星期2 14:40:14]:特殊灯色表获取成功.\\r\\n[20161025 星期2 14:40:14]:特殊节假日设置获取成功.\\r\\n[20161025 星期2 14:30:37]:没有接收到GPS信息.\\r\\n[20161025 星期2 14:30:37]:系统启动成功.\\r\\n[20161025 星期2 14:30:37]:降级配置表获取成功.\\r\\n[20161025 星期2 14:30:37]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161025 星期2 14:30:37]:特殊灯色表获取成功.\\r\\n[20161025 星期2 14:30:37]:特殊节假日设置获取成功.\\r\\n[20161025 星期2 08:57:30]:没有接收到GPS信息.\\r\\n[20161025 星期2 08:57:30]:系统启动成功.\\r\\n[20161025 星期2 08:57:30]:降级配置表获取成功.\\r\\n[20161025 星期2 08:57:30]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161025 星期2 08:57:30]:特殊灯色表获取成功.\\r\\n[20161025 星期2 08:57:30]:特殊节假日设置获取成功.\\r\\n[20161025 星期2 08:56:45]:系统启动成功.\\r\\n[20161025 星期2 08:56:45]:降级配置表获取成功.\\r\\n[20161025 星期2 08:56:45]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161025 星期2 08:56:45]:特殊灯色表获取成功.\\r\\n[20161025 星期2 08:56:45]:特殊节假日设置获取成功.\\r\\n[20161020 星期4 09:19:56]:上位机发来新校时信息, 系统校时完成.\\r\\n[20161020 星期4 09:18:46]:没有接收到GPS信息.\\r\\n[20161020 星期4 09:18:46]:系统启动成功.\\r\\n[20161020 星期4 09:18:46]:降级配置表获取成功.\\r\\n[20161020 星期4 09:18:46]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161020 星期4 09:18:46]:特殊灯色表获取成功.\\r\\n[20161020 星期4 09:18:46]:特殊节假日设置获取成功.\\r\\n[20161017 星期1 10:07:42]:没有接收到GPS信息.\\r\\n[20161017 星期1 10:07:42]:系统启动成功.\\r\\n[20161017 星期1 10:07:42]:降级配置表获取成功.\\r\\n[20161017 星期1 10:07:42]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161017 星期1 10:07:42]:特殊灯色表获取成功.\\r\\n[20161017 星期1 10:07:42]:特殊节假日设置获取成功.\\r\\n[20161010 星期1 14:49:22]:没有接收到GPS信息.\\r\\n[20161010 星期1 14:49:22]:系统启动成功.\\r\\n[20161010 星期1 14:49:22]:降级配置表获取成功.\\r\\n[20161010 星期1 14:49:22]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161010 星期1 14:49:22]:特殊灯色表获取成功.\\r\\n[20161010 星期1 14:49:22]:特殊节假日设置获取成功.\\r\\n[20161009 星期7 15:59:06]:没有接收到GPS信息.\\r\\n[20161009 星期7 15:59:06]:系统启动成功.\\r\\n[20161009 星期7 15:59:06]:降级配置表获取成功.\\r\\n[20161009 星期7 15:59:06]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161009 星期7 15:59:06]:特殊灯色表获取成功.\\r\\n[20161009 星期7 15:59:06]:特殊节假日设置获取成功.\\r\\n[20161008 星期6 08:22:13]:没有接收到GPS信息.\\r\\n[20161008 星期6 08:22:13]:系统启动成功.\\r\\n[20161008 星期6 08:22:13]:降级配置表获取成功.\\r\\n[20161008 星期6 08:22:13]:成功获取绿冲突检测设置, 在故障检测使能情况下, 将对绿冲突进行检测.\\r\\n[20161008 星期6 08:22:13]:特殊灯色表获取成功.\\r\\n[20161008 星期6 08:22:13]:特殊节假日设置获取成功.\\r\\n[20160929 星期4 07:54:34]:没有接收到GPS信息.\\r\\n\"}";
                }
                JSONObject js = new JSONObject();
                js.put("result",true);
                js.put("content","\""+status+"\"");
                content = "192.168.200.100|"+js.toString();
                break;
            default:
                break;
        }
        String sign = "0";//标志
        String pack = token+sign+cmd+content;
        /**整型转十六进制字符串**/
        String length = Integer.toHexString(pack.length()+5).toUpperCase();
        length = String.format("%5s",length);
        length= length.replaceAll(" ","0");
        if(cmdid==173){
            length = "01CE2";
        }
      //  String length =  String.format("%05d",pack.length()+5);
        pack = length+pack;
        return pack;
    }
    public static MsgType getMsgType(String msg){
        Integer logCmdStartIndex = NettyClientChannelParam.packageLength + NettyClientChannelParam.tokenLength + NettyClientChannelParam.signLength;
        Integer logCmdEndIndex = logCmdStartIndex +4;
        String cmd = msg.substring(logCmdStartIndex,logCmdEndIndex);
        switch (cmd){
            case logRturnCmd:
                return MsgType.LOGIN;
            case pingReturnCmd:
                return MsgType.PING;
            case timerReturnCmd:
                return MsgType.TIMER;
            case oldSignaCmd:
                return MsgType.OLDSIGNAL;
            default:
                return MsgType.REPLY;
        }
    }
    //获取返回信息的Token用来连接验证之后保存在Map里
    public static String getToken(String msg){
        String token = msg.substring(msg.length()-32,msg.length());
        return token;
    }
    //GAT1049协议
    public static MsgType getGatMsgType(String msg){
        try {
            Document doc = DocumentHelper.parseText(msg);
            Element message = doc.getRootElement();
            Element body = message.element("Body");
            List<Element> operations = body.elements("Operation");
            if(operations.size() ==1){
                Element operation = operations.get(0);
                String name = operation.attributeValue("name");
                if("Login".equals(name)){
                    return MsgType.LOGIN;
                }else if("notify".equals(name)){
                    if(operation.element("SDO_HeartBeat")!=null){
                        return MsgType.PING;
                    }
                }else{
                    if("REQUEST".equals(message.elementText("Type"))){
                        return MsgType.ASK;
                    }else if("RESPONSE".equals(message.elementText("Type"))){
                        return MsgType.REPLY;
                    }
                }
            }else{
                if("REQUEST".equals(message.elementText("Type"))){
                    return MsgType.ASK;
                }else if("RESPONSE".equals(message.elementText("Type"))){
                    return MsgType.REPLY;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return MsgType.REPLY;
    }

    //获取包信息，用来回应REQUEST请求
    public static String getGatPackeg(String msg,MsgType type){
        try {
            if(type == MsgType.ASK) {
                Document document = DocumentHelper.parseText(msg);
                Element message = document.getRootElement();
                message.element("Type").setText("RESPONSE");
                String deviceIp = message.element("To").element("Address").element("Sys").getText();
                message.element("From").element("Address").element("Sys").setText(deviceIp);
                message.element("To").element("Address").element("Sys").setText("UTCS");
                return  new XmlClientImp().outPut(document);
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
}
