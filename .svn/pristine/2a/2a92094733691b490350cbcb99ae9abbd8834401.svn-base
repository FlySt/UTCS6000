package com.ncjk.utcs.common.netty.server.xml;


import com.ncjk.utcs.common.netty.server.NettyChannelMap;
import com.ncjk.utcs.common.netty.server.NettyChannelParam;
import com.ncjk.utcs.common.netty.server.NettyServer;
import com.ncjk.utcs.common.servlet.SpringUtil;
import com.ncjk.utcs.modules.resources.resources.services.SignalControlerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * xml实现类
 * Created by swl on 2016/9/28.
 */

public class XmlImp implements XmlInterface{
    private SignalControlerService signalControlerService = (SignalControlerService) SpringUtil.getApplicationContext().
            getBean("signalControlerService",SignalControlerService.class);
    private static Logger logger = Logger.getLogger(XmlImp.class);
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
            fromAddress.addElement("Sys").setText("");
        }
        fromAddress.addElement("SubSys");
        fromAddress.addElement("Instance");
        Element to = message.addElement("To");
        Element toAddress = to.addElement("Address");
        if(isServer){
            toAddress.addElement("Sys").setText("");
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
     * 创建心跳包Xml
     * @return
     */
    @Override
    public String createPingXml(ChannelId id) {
        if(NettyChannelParam.seq == 999999){
            NettyChannelParam.seq = 0;
        }
        NettyChannelParam.seq++;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String seqText = dateFormat.format(new Date())+String.format("%06d",NettyChannelParam.seq);
        String token = NettyChannelMap.getServerToken(id);
        String clientIp = NettyChannelMap.getChannel(id).remoteAddress().toString().split(":")[0];
        clientIp = clientIp.substring(1,clientIp.length());
        try {
            Document doc = DocumentHelper.parseText(createHeaderXml(true,true));
            Element message = doc.getRootElement();
            message.element("Token").setText(token);
            message.element("Type").setText("PUSH");
            message.element("Seq").setText(seqText);
            message.element("To").element("Address").element("Sys").setText(clientIp);
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
     * 创建1049Gat协议包Xml
     *
     * @param channel
     * @param content
     * @param deviceIp 信号机Ip地址
     * @return
     */
    @Override
    public String createGatoXml(Channel channel, String content,String deviceIp) {
        if(NettyChannelParam.seq == 999999){
            NettyChannelParam.seq = 0;
        }
        NettyChannelParam.seq++;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("createGatoXml:"+content);
    //    String clientIp = channel.remoteAddress().toString().split(":")[0];
       // clientIp = clientIp.substring(1,clientIp.length());
        String clientIp = deviceIp;
        String token = NettyChannelMap.getServerToken(channel.id());
        String seqText = dateFormat.format(new Date())+String.format("%06d",NettyChannelParam.seq);
        try {
            content = content.substring(content.indexOf("<Operation"),content.length());
            Document document = DocumentHelper.parseText(content);
            Element operation = document.getRootElement();
            operation.attribute("Order").setValue("1");
            Document doc = DocumentHelper.parseText(createHeaderXml(true,true));
            Element message = doc.getRootElement();
            message.element("Token").setText(token);
            message.element("Seq").setText(seqText);
            message.element("To").element("Address").element("Sys").setText(clientIp);
         //   Element operation = document.getRootElement().element("Body").addElement("Operation");
            Element body = message.element("Body");
         //   body.remove(operation);
            body.add(document.getRootElement());
            return outPut(doc);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解析内容
     * @param id
     * @param msg
     * @return
     */
    public  String analyzeGatResponse(ChannelId id,String msg) {
    //    String cmd = NettyChannelMap.getChannelFlag(id);
        String cmd = getGatResponseCmd(msg);
        String signalControlerNum = getSiganleContolerNum(msg);
   //     String signalControlerNum = "11111111111111111";
        if(cmd!=null && cmd.length()!=0){
            try {
                Document doc = DocumentHelper.parseText(msg);
                Element message = doc.getRootElement();
                Element opreation = message.element("Body").element("Operation");
                String name = opreation.attributeValue("name");
                if (opreation.element("SDO_Error") != null) {
                    return getErrorGatResponse(cmd,signalControlerNum,"11");//11错误
                } else {
                    Integer length = NettyChannelParam.ocxCmdLength + NettyChannelParam.ocxResponseCodeLength + NettyChannelParam.signalControlerNumLength;
                    if ("Set".equals(name)) {//设置
                        String packegLength = String.format("%04d",length+NettyChannelParam.ocxPackageLength);
                        String response = packegLength+cmd+signalControlerNum+"00";
                        return response;
                    } else if ("Get".equals(name)) {
                        Integer oprStartIndex = msg.indexOf("<Operation");
                        Integer oprEndIndex = msg.indexOf("</Operation>")+"</Operation>".length();
                        if(oprStartIndex!=-1 && oprEndIndex!=-1){
                            String content = msg.substring(oprStartIndex,oprEndIndex);
                            String packegLength = String.format("%04d",length+NettyChannelParam.ocxPackageLength+content.length());
                            String response = packegLength+cmd+signalControlerNum+"00"+content;
                            return response;
                        }else {
                            return getErrorGatResponse(cmd,signalControlerNum,"10");
                        }
                    }
                }
            } catch (DocumentException e) {
                e.printStackTrace();
                return getErrorGatResponse(cmd,signalControlerNum,"13");
            }
            return getErrorGatResponse(cmd,signalControlerNum,"12");
        }else{
            return null;
        }
    }

    /**
     *获取信号机编号
     * @param msg
     * @return
     */
    private String getSiganleContolerNum(String msg){
        try {
            Document doc = DocumentHelper.parseText(msg);
            String deviceIp = doc.getRootElement().element("From").element("Address").element("Sys").getText();
            String signalControlerNum = signalControlerService.findSignalControlerNumByIp(deviceIp);
            return signalControlerNum;
        } catch (DocumentException e) {
            logger.error("getSiganleContolerNum",e);
            return null;
        }
    }

    /**
     * 获取响应的命令字
     * @param msg
     * @return
     */
    private String getGatResponseCmd(String msg){
        if(msg.indexOf("<SelfDef_CMMaxLine>")!=-1){
            return "82";
        }else if(msg.indexOf("<SelfDef_GlobalSynTime>")!=-1){
            return "83";
        }else if(msg.indexOf("<SelfDef_GlobalSynFlag>")!=-1){
            return "84";
        }else if(msg.indexOf("<SysInfo>")!=-1){
            return "85";
        }else if(msg.indexOf("<SelfDef_CommTime>")!=-1){
            return "86";
        }else if(msg.indexOf("<SelfDef_StandTimeZone>")!=-1){
            return "87";
        }else if(msg.indexOf("<SelfDef_LocalTime>")!=-1){
            return "88";
        }else if(msg.indexOf("<SelfDef_TBDMaxLine>")!=-1){
            return "89";
        }else if(msg.indexOf("<SelfDef_TIMaxLine>")!=-1){
            return "8A";
        }else if(msg.indexOf("<SelfDef_TIMaxInter>")!=-1){
            return "8B";
        }else if(msg.indexOf("<SelfDef_ATINo>")!=-1){
            return "8C";
        }else if(msg.indexOf("<SelfDef_TBDLine>")!=-1){
            return "8D";
        }else if(msg.indexOf("<SelfDef_TILine>")!=-1){
             return "8E";
         }else if(msg.indexOf("<SelfDef_EventTypeMaxLine>")!=-1){
             return "8F";
         }else if(msg.indexOf("<SelfDef_EventLogMaxLine>")!=-1){
             return "90";
         }else if(msg.indexOf("<SelfDef_EventTypeLine>")!=-1){
             return "91";
         }else if(msg.indexOf("<SignalControlerError>")!=-1){//事件日志表参数列表
             return "92";
         }else if(msg.indexOf("<SelfDef_PhaseMaxLine>")!=-1){//相位表最大的行数
             return "93";
         }else if(msg.indexOf("<SelfDef_PSGMaxNum>")!=-1){//相位状态组最大数据
             return "94";
         }else if(msg.indexOf("<SelfDef_PhaseAttribute>")!=-1){//相位属性
             return "95";
         }else if(msg.indexOf("<CrossPhaseLampStatus>")!=-1){//相位输出状态表行数
             return "96";
         }else if(msg.indexOf("<SelfDef_PCLine>")!=-1){//相位冲突表行数
             return "97";
         }else if(msg.indexOf("<SelfDef_DetMaxNum>")!=-1){//车辆检测器最大数量
             return "98";
         }else if(msg.indexOf("<SelfDef_DSGMaxNum>")!=-1){//检测器状态组的最大数量
             return "99";
         }else if(msg.indexOf("<SelfDef_DetDataSeq>")!=-1){//检测数据流水号
             return "9A";
         }else if(msg.indexOf("<SelfDef_DetCollectCycle>")!=-1){//数据采集周期
             return "9B";
         }else if(msg.indexOf("<SelfDef_ActDetTotal>")!=-1){//活动检测器总数
             return "9C";
         }else if(msg.indexOf("<SelfDef_PulseDataSeq>")!=-1){//脉冲数据流水号
             return "9D";
         }else if(msg.indexOf("<SelfDef_PulseDataCycle>")!=-1){//脉冲数据采集周期
             return "9E";
         }else if(msg.indexOf("<DetParam>")!=-1){//车检测器参数定义表行数
             return "9F";
         }else if(msg.indexOf("<SelfDef_DetStateParam>")!=-1){//车辆检测器状态表行数
             return "A0";
         }else if(msg.indexOf("<CrossTrafficData>")!=-1){//交通检测数据表参数列表
             return "A1";
         }else if(msg.indexOf("<SelfDef_DetAlarmParam>")!=-1){//检测器告警参数列表
             return "A2";
         }else if(msg.indexOf("<SelfDef_RunFlashSec>")!=-1){//启动时的闪光控制时间
             return "A3";
         }else if(msg.indexOf("<SelfDef_RunRedSec>")!=-1){//启动时的全红控制时间
             return "A4";
         }else if(msg.indexOf("<SelfDef_SignalContState>")!=-1){//当前信号机控制状态
             return "A5";
         }else if(msg.indexOf("<SelfDef_FlashContMode>")!=-1){//当前的闪光控制模式
             return "A6";
         }else if(msg.indexOf("<SelfDef_SignalAlarm2>")!=-1){//信号机报警2
             return "A7";
         }else if(msg.indexOf("<SelfDef_SignalAlarm1>")!=-1){//信号机报警1
             return "A8";
         }else if(msg.indexOf("<SelfDef_SignalAbstract>")!=-1){//信号机报摘要
             return "A9";
         }else if(msg.indexOf("<SelfDef_RemoteActFunc>")!=-1){//允许远程控制实体激活信号机的某些功能
             return "AA";
         }else if(msg.indexOf("<SelfDef_FlashRate>")!=-1){//闪光频率
             return "AB";
         }else if(msg.indexOf("<SelfDef_BrightStartTime>")!=-1){//辉度控制开启时间
             return "AC";
         }else if(msg.indexOf("<SelfDef_BrightEndTime>")!=-1){//辉度控制关闭时间
             return "AD";
         }else if(msg.indexOf("<SelfDef_SignalMaxRoute>")!=-1){//信号机支持的最大通道数量
             return "AE";
         }else if(msg.indexOf("<SelfDef_RouteStateGroup>")!=-1){//通道状态组数
             return "AF";
         }else if(msg.indexOf("<PhaseParam>")!=-1){//通道参数表行数
             return "B0";
         }else if(msg.indexOf("<SelfDef_RouteStateParam>")!=-1){//通道输出状态参数列表
             return "B1";
         }else if(msg.indexOf("<SelfDef_PlanNum>")!=-1){//配时方案数
             return "B2";
         }else if(msg.indexOf("<SelfDef_MaxStagePlanNum>")!=-1){//最大阶段配时表数
             return "B3";
         }else if(msg.indexOf("<SelfDef_MaxStageNum>")!=-1){//最大阶段数
             return "B4";
         }else if(msg.indexOf("<CrossPlan>")!=-1){//系统控制方案
             return "B6";
         }else if(msg.indexOf("<CrossControlMode>")!=-1){//控制方式
             return "B7";
         }else if(msg.indexOf("<SelfDef_CommCycleDur>")!=-1){//公共周期时长
             return "B8";
         }else if(msg.indexOf("<SelfDef_PlanNum>")!=-1){//阶段状态
             return "BA";
         }else if(msg.indexOf("<SelfDef_Step>")!=-1){//步进指令
             return "BB";
         }else if(msg.indexOf("<SelfDef_DegradeMode>")!=-1){//降级模式
             return "BC";
         }else if(msg.indexOf("<SelfDef_DegradeBasePlan>")!=-1){//降级基准方案表
             return "BD";
         }else if(msg.indexOf("<SelfDef_PlanStageDur>")!=-1){//当前方案各阶段时长
             return "BE";
         }else if(msg.indexOf("<SelfDef_KeyPhaseGreen>")!=-1){//当前方案各关键相位绿灯时长
             return "BF";
         }else if(msg.indexOf("<PlanParam>")!=-1){//配置方案表行数
             return "C0";
         }else if(msg.indexOf("<StageParam>")!=-1){//阶段配时表参数列表
             return "C1";
         }else if(msg.indexOf("<SelfDef_DownLoadFlag>")!=-1){//下载标志
             return "C2";
         }else if(msg.indexOf("<SelfDef_HostOptionParam>")!=-1){//控制主机选项参数
             return "C3";
         }else if(msg.indexOf("<SelfDef_SignalBaseAddr>")!=-1){//信号机基地址
             return "C4";
         }else if(msg.indexOf("<SelfDef_CrossNum>")!=-1){//路口数量
             return "C5";
         }else if(msg.indexOf("<SelfDef_FolPhaseMaxLine>")!=-1){//跟随相位表最大行数
             return "C6";
         }else if(msg.indexOf("<SelfDef_FolPhaseStateLine>")!=-1){//跟随相位状态表行数
             return "C7";
         }else if(msg.indexOf("<SelfDef_FolPhaseParam>")!=-1){//跟随相位表参数列表
             return "C8";
         }else if(msg.indexOf("<SelfDef_FolPhaseState>")!=-1){//跟随相位状态组表行数
             return "C9";
         }
         return null;
    }
    /**
     * 获取错误响应内容 11:信号机返回SDO_USER  10:GET时没有返回包体信息
     * 12:信号机没有返回SET或者GET属性 13:信号机返回的XML格式不对 14:没有连接的通道 15:没有可用空闲通道
     * @param cmd
     * @param responseCode
     * @return
     */
    public String getErrorGatResponse(String cmd,String siganelControlerNum,String responseCode){
        Integer length = NettyChannelParam.ocxCmdLength + NettyChannelParam.ocxResponseCodeLength+NettyChannelParam.signalControlerNumLength;
        String packegLength = String.format("%04d",length+NettyChannelParam.ocxPackageLength);
        String response = packegLength+cmd+siganelControlerNum+responseCode;
        return response;
    }

    private String createCMMaxLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelDef_CMMaxLine").setText(contents[0]);
        return outPut(document);
    }
    private String createGlobalSynTime(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_GlobalSynTime").setText(contents[0]);
        return outPut(document);
    }
    private String createGlobalSynchFlag(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_GlobalSynchFlag").setText(contents[0]);
        return outPut(document);
    }
    //模块表行数参数
    private String createSysInfo(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element sysInfo = operation.addElement("SysInfo");
        sysInfo.addElement("SelfDef_No").setText(contents[0]);
        sysInfo.addElement("SysName").setText(contents[1]);
        sysInfo.addElement("SysVersion").setText(contents[2]);
        sysInfo.addElement("Supplier").setText(contents[3]);
        sysInfo.addElement("SelfDef_Model").setText(contents[4]);
        sysInfo.addElement("SelfDef_Type").setText(contents[5]);
        return outPut(document);
    }
    //公共时间
    private String createCommTime(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_CommTime").setText(contents[0]);
        return outPut(document);
    }
    //标准时区
    private String createStandTimeZone(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_StandTimeZone").setText(contents[0]);
        return outPut(document);
    }
    //本地时间
    private String createLocalTime(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_LocalTime").setText(contents[0]);
        return outPut(document);
    }
    //时基调度表最大行数
    private String createTBDMaxLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_TBDMaxLine").setText(contents[0]);
        return outPut(document);
    }
    //时段表最大行数
    private String createTIMaxLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_TIMaxLine").setText(contents[0]);
        return outPut(document);
    }
    //时段表所包含的最大时段数
    private String createTIMaxInter(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_TIMaxInter").setText(contents[0]);
        return outPut(document);
    }
    //活动时段表的编号
    private String createATINo(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_ATINo").setText(contents[0]);
        return outPut(document);
    }
    //时基调度表行数
    private String createTBDLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element tbdLine = operation.addElement("SelfDef_TBDLine");
        tbdLine.addElement("No").setText(contents[0]);
        tbdLine.addElement("Month").setText(contents[1]);
        tbdLine.addElement("Week").setText(contents[2]);
        tbdLine.addElement("Day").setText(contents[3]);
        tbdLine.addElement("TINo").setText(contents[4]);
        return outPut(document);
    }
    //时段表行数
    private String createTILine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element tILine = operation.addElement("SelfDef_TILine");
        tILine.addElement("No").setText(contents[0]);
        tILine.addElement("EventNo").setText(contents[1]);
        tILine.addElement("Hour").setText(contents[2]);
        tILine.addElement("Min").setText(contents[3]);
        tILine.addElement("ContMode").setText(contents[4]);
        tILine.addElement("PlanNo").setText(contents[5]);
        tILine.addElement("Assist").setText(contents[6]);
        tILine.addElement("Special").setText(contents[7]);
        return outPut(document);
    }
    //事件类型最大行数
    private String createEventTypeMaxLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_EventTypeMaxLine").setText(contents[0]);
        return outPut(document);
    }
    //事件日志最大行数
    private String createEventLogMaxLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_EventLogMaxLine").setText(contents[0]);
        return outPut(document);
    }
    //时间类型表行数
    private String createEventTypeLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element eventTypeLine = operation.addElement("SelfDef_EventTypeLine");
        eventTypeLine.addElement("No").setText(contents[0]);
        eventTypeLine.addElement("ClearTime").setText(contents[1]);
        eventTypeLine.addElement("Desc").setText(contents[2]);
        eventTypeLine.addElement("TablePos").setText(contents[3]);
        return outPut(document);
    }
    //事件日志表参数列表
    private String createSignalControlerError(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element signalControlerError = operation.addElement("SignalControlerError");
        signalControlerError.addElement("SignalID").setText(contents[0]);
        signalControlerError.addElement("SelfDef_Seq").setText(contents[1]);
        signalControlerError.addElement("ErrorType").setText(contents[2]);
        signalControlerError.addElement("ErrorDesc").setText(contents[3]);
        signalControlerError.addElement("OccerTime").setText(contents[4]);
        return outPut(document);
    }
    //相位表最大的行数
    private String createPhaseMaxLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_PhaseMaxLine").setText(contents[0]);
        return outPut(document);
    }
    //相位状态组最大数据
    private String createPSGMaxNum (Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_PSGMaxNum").setText(contents[0]);
        return outPut(document);
    }
    //相位属性
    private String createPhaseAttribute(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element phaseAttribute = operation.addElement("SelfDef_PhaseAttribute");
        phaseAttribute.addElement("No").setText(contents[0]);
        phaseAttribute.addElement("PassGreenSec").setText(contents[1]);
        phaseAttribute.addElement("ClearSec").setText(contents[2]);
        phaseAttribute.addElement("MinGreenSec").setText(contents[3]);
        phaseAttribute.addElement("DelayGreenSec").setText(contents[4]);
        phaseAttribute.addElement("MaxGreenSec1").setText(contents[5]);
        phaseAttribute.addElement("MaxGreenSec2").setText(contents[6]);
        phaseAttribute.addElement("FixedGreenSec").setText(contents[7]);
        phaseAttribute.addElement("FlashGreenSec").setText(contents[8]);
        phaseAttribute.addElement("PhaseType").setText(contents[9]);
        phaseAttribute.addElement("PhaseOption").setText(contents[10]);
        phaseAttribute.addElement("Reserve").setText(contents[11]);
        return outPut(document);
    }
    //相位输出表行数
    private String createCrossPhaseLampStatus(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element crossPhaseLampStatus = operation.addElement("CrossPhaseLampStatus");
        crossPhaseLampStatus.addElement("CrossID").setText(contents[0]);
        Element phaseLampStatusList = crossPhaseLampStatus.addElement("PhaseLampStatusList");
        Element phaseLampStatus =  phaseLampStatusList.addElement("PhaseLampStatus");
        phaseLampStatus.addElement("PhaseNo").setText(contents[1]);
        phaseLampStatus.addElement("LampStatus").setText(contents[2]);
        return outPut(document);
    }
    //相位冲突表行数
    private String createPCLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element tbdLine = operation.addElement("SelfDef_PCLine");
        tbdLine.addElement("No ").setText(contents[0]);
        tbdLine.addElement("Conflict").setText(contents[1]);
        return outPut(document);
    }
    //车辆检测其最大数量
    private String createDetMaxNum(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_DetMaxNum").setText(contents[0]);
        return outPut(document);
    }
    //检测器状态组最大数量
    private String createDSGMaxNum(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_DSGMaxNum").setText(contents[0]);
        return outPut(document);
    }
    //检测数据流水号
    private String createDetDataSeq(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_DetDataSeq").setText(contents[0]);
        return outPut(document);
    }
    //数据采集周期
    private String createDetCollectCycle(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_DetCollectCycle").setText(contents[0]);
        return outPut(document);
    }
    //活动检测器总数
    private String createActDetTotal(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_ActDetTotal").setText(contents[0]);
        return outPut(document);
    }
    //脉冲数据流水号
    private String createPulseDataSeq(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_PulseDataSeq").setText(contents[0]);
        return outPut(document);
    }
    //脉冲数据采集周期
    private String createPulseDataCycle(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_PulseDataCycle").setText(contents[0]);
        return outPut(document);
    }
    //车检测器参数定义表行数
    private String createDetParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element detParam = operation.addElement("DetParam");
        detParam.addElement("DetID").setText(contents[0]);
        detParam.addElement("Distance").setText(contents[1]);
        detParam.addElement("CrossID").setText(contents[2]);
        detParam.addElement("SelfDef_RequPhase").setText(contents[3]);
        detParam.addElement("SelfDef_Type").setText(contents[4]);
        detParam.addElement("SelfDef_Direction").setText(contents[5]);
        detParam.addElement("SelfDef_RequValidSec").setText(contents[6]);
        detParam.addElement("SelfDef_Option").setText(contents[7]);
        detParam.addElement("SelfDef_KeySatuFlow").setText(contents[8]);
        detParam.addElement("SelfDef_KeySatuRate").setText(contents[9]);
        return outPut(document);
    }
    //车辆检测器状态表行数
    private String createDetStateParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element detStateParam = operation.addElement("SelfDef_DetStateParam");
        detStateParam.addElement("No").setText(contents[0]);
        detStateParam.addElement("State").setText(contents[1]);
        detStateParam.addElement("Alarm").setText(contents[2]);
        return outPut(document);
    }
    //交通检测数据表参数列表
    private String createCrossTrafficData(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element crossTrafficData = operation.addElement("CrossTrafficData");
        crossTrafficData.addElement("CrossID").setText(contents[0]);
        crossTrafficData.addElement("EndTime").setText(contents[1]);
        crossTrafficData.addElement("Interval").setText(contents[2]);
        Element dataList = crossTrafficData.addElement("DataList");
        Element data = dataList.addElement("Data");
        data.addElement("LaneNo").setText(contents[3]);
        data.addElement("Volume").setText(contents[4]);
        data.addElement("SelfDef_BigVolume").setText(contents[5]);
        data.addElement("SelfDef_SmallVolume").setText(contents[6]);
        data.addElement("AvgVehLen").setText(contents[7]);
        data.addElement("Pcu").setText(contents[8]);
        data.addElement("HeadDistance").setText(contents[9]);
        data.addElement("HeadTime").setText(contents[10]);
        data.addElement("Speed").setText(contents[11]);
        data.addElement("Saturation").setText(contents[12]);
        data.addElement("Density").setText(contents[13]);
        data.addElement("QueueLength").setText(contents[14]);
        data.addElement("Occupancy").setText(contents[15]);
        return outPut(document);
    }
    //检测器告警参数列表
    private String createDetAlarmParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element detAlarmParam = operation.addElement("SelfDef_DetAlarmParam");
        detAlarmParam.addElement("No").setText(contents[0]);
        detAlarmParam.addElement("AlarmState").setText(contents[1]);
        detAlarmParam.addElement("CoilState").setText(contents[2]);
        return outPut(document);
    }
    //启动时的闪光控制时间
    private String createRunFlashSec(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_RunFlashSec").setText(contents[0]);
        return outPut(document);
    }
    //启动时的全红控制时间
    private String createRunRedSec(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_RunRedSec").setText(contents[0]);
        return outPut(document);
    }
    //当前信号机控制状态
    private String createSignalContState(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_SignalContState").setText(contents[0]);
        return outPut(document);
    }
    //当前的闪光控制模式
    private String createFlashContMode(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_FlashContMode").setText(contents[0]);
        return outPut(document);
    }
    //信号机报警2
    private String createSignalAlarm2(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_SignalAlarm2").setText(contents[0]);
        return outPut(document);
    }
    //信号机报警1
    private String createSignalAlarm1(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_SignalAlarm1").setText(contents[0]);
        return outPut(document);
    }
    //信号机摘要
    private String createSignalAbstract(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_SignalAbstract").setText(contents[0]);
        return outPut(document);
    }
    //允许远程控制实体激活信号机的某些功能
    private String createRemoteActFunc(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_RemoteActFunc").setText(contents[0]);
        return outPut(document);
    }
    //闪光频率
    private String createFlashRate(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_FlashRate").setText(contents[0]);
        return outPut(document);
    }
    //辉度控制开启时间
    private String createBrightStartTime(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_BrightStartTime").setText(contents[0]);
        return outPut(document);
    }
    //辉度控制关闭时间
    private String createBrightEndTime(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_BrightEndTime").setText(contents[0]);
        return outPut(document);
    }
    //信号机支持的最大通道数量
    private String createSignalMaxRoute(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_SignalMaxRoute").setText(contents[0]);
        return outPut(document);
    }
    //通道状态组数
    private String createRouteStateGroup(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_RouteStateGroup").setText(contents[0]);
        return outPut(document);
    }
    //通道参数表行数
    private String createPhaseParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element phaseParam = operation.addElement("PhaseParam");
        phaseParam.addElement("CrossID").setText(contents[0]);
        phaseParam.addElement("No").setText(contents[1]);
        phaseParam.addElement("PhaseNo").setText(contents[2]);
        phaseParam.addElement("PhaseName").setText(contents[3]);
        phaseParam.addElement("Attribute").setText(contents[4]);
        phaseParam.addElement("SelfDef_FlashState").setText(contents[5]);
        phaseParam.addElement("SelfDef_ContMode").setText(contents[6]);
        Element laneNoList = phaseParam.addElement("LaneNoList");
        laneNoList.addElement("LaneNo").setText(contents[7]);
        Element pedDirList = phaseParam.addElement("PedDirList");
        pedDirList.addElement("Direction").setText(contents[8]);
        return outPut(document);
    }
    //通道输出状态参数列表
    private String createRouteStateParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element phaseParam = operation.addElement("SelfDef_RouteStateParam");
        phaseParam.addElement("No").setText(contents[0]);
        phaseParam.addElement("Red").setText(contents[1]);
        phaseParam.addElement("Yellow").setText(contents[2]);
        phaseParam.addElement("Green").setText(contents[3]);
        return outPut(document);
    }
    //配时方案数
    private String createPlanNum(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_PlanNum").setText(contents[0]);
        return outPut(document);
    }
    //最大阶段配时表数
    private String createMaxStagePlanNum(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_MaxStagePlanNum").setText(contents[0]);
        return outPut(document);
    }
    //最大阶段数
    private String createMaxStageNum(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_MaxStageNum").setText(contents[0]);
        return outPut(document);
    }
    //系统控制方案
    private String createCrossPlan(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element phaseParam = operation.addElement("CrossPlan");
        phaseParam.addElement("CrossID").setText(contents[0]);
        phaseParam.addElement("SelfDef_Type ").setText(contents[1]);
        phaseParam.addElement("Plan ").setText(contents[2]);
        return outPut(document);
    }
    //控制方式
    private String createCrossControlMode(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element phaseParam = operation.addElement("CrossControlMode");
        phaseParam.addElement("CrossID").setText(contents[0]);
        phaseParam.addElement("Value  ").setText(contents[1]);
        return outPut(document);
    }
    //公共周期时长
    private String createCommCycleDur(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_CommCycleDur").setText(contents[0]);
        return outPut(document);
    }
    //阶段状态
    private String createStageState(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_StageState").setText(contents[0]);
        return outPut(document);
    }
    //步进指令
    private String createStep(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_Step").setText(contents[0]);
        return outPut(document);
    }
    //降级模式
    private String createDegradeMode(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_DegradeMode").setText(contents[0]);
        return outPut(document);
    }
    //降级基准方案表
    private String createDegradeBasePlan(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_DegradeBasePlan").setText(contents[0]);
        return outPut(document);
    }
    //当前方案各阶段时长
    private String createPlanStageDur(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_PlanStageDur").setText(contents[0]);
        return outPut(document);
    }
    //当前方案各关键相位绿灯时长
    private String createKeyPhaseGreen(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_KeyPhaseGreen").setText(contents[0]);
        return outPut(document);
    }
    //配置方案表行数
    private String createPlanParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element planParam = operation.addElement("PlanParam");
        planParam.addElement("CrossID").setText(contents[0]);
        planParam.addElement("PlanNo").setText(contents[1]);
        planParam.addElement("CycleLen").setText(contents[2]);
        planParam.addElement("CoordPhaseNo").setText(contents[3]);
        planParam.addElement("OffSet").setText(contents[4]);
        Element stageNoList = planParam.addElement("StageNoList");
        stageNoList.addElement("StageNo").setText(contents[5]);
        return outPut(document);
    }
    //阶段配时表参数列表
    private String createStageParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element stageParam = operation.addElement("StageParam");
        stageParam.addElement("CrossID").setText(contents[0]);
        stageParam.addElement("StageNo").setText(contents[1]);
        stageParam.addElement("StageName").setText(contents[2]);
        stageParam.addElement("Attribute").setText(contents[3]);
        stageParam.addElement("SelfDef_PassPhase").setText(contents[4]);
        stageParam.addElement("Green").setText(contents[5]);
        stageParam.addElement("RedYellow").setText(contents[6]);
        stageParam.addElement("Yellow").setText(contents[7]);
        stageParam.addElement("AllRed").setText(contents[8]);
        Element phaseNoList = stageParam.addElement("PhaseNoList");
        phaseNoList.addElement("PhaseNo").setText(contents[9]);
        return outPut(document);
    }
    //下载标志
    private String createDownLoadFlag(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_DownLoadFlag").setText(contents[0]);
        return outPut(document);
    }
    //控制主机选项参数
    private String createHostOptionParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_HostOptionParam").setText(contents[0]);
        return outPut(document);
    }
    //信号机基地址
    private String createSignalBaseAddr(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_SignalBaseAddr").setText(contents[0]);
        return outPut(document);
    }
    //路口数量
    private String createCrossNum(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_CrossNum").setText(contents[0]);
        return outPut(document);
    }
    //跟随相位表最大行数
    private String createFolPhaseMaxLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_FolPhaseMaxLine").setText(contents[0]);
        return outPut(document);
    }
    //跟随相位状态表行数
    private String createFolPhaseStateLine(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        operation.addElement("SelfDef_FolPhaseStateLine").setText(contents[0]);
        return outPut(document);
    }
    //跟随相位表参数列表
    private String createFolPhaseParam(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element stageParam = operation.addElement("SelfDef_FolPhaseParam");
        stageParam.addElement("No").setText(contents[0]);
        stageParam.addElement("OperType").setText(contents[1]);
        stageParam.addElement("IncludePhase").setText(contents[2]);
        stageParam.addElement("CorrectPhase").setText(contents[3]);
        stageParam.addElement("TailGreen").setText(contents[4]);
        stageParam.addElement("TailYellow").setText(contents[5]);
        stageParam.addElement("TailRed").setText(contents[6]);
        return outPut(document);
    }
    //跟随相位状态组表行数
    private String createFolPhaseState(Document document, String[] contents){
        Element operation = document.getRootElement().element("Body").element("Operation");
        Element stageParam = operation.addElement("SelfDef_FolPhaseState");
        stageParam.addElement("No").setText(contents[0]);
        stageParam.addElement("RedState").setText(contents[1]);
        stageParam.addElement("YellowState").setText(contents[2]);
        stageParam.addElement("GreenState").setText(contents[3]);
        return outPut(document);
    }
    @Override
    public String createCustomXml(ChannelId id,String xml) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String token = NettyChannelMap.getServerToken(id);
            String seqText = dateFormat.format(new Date())+"000009";
            Document document = DocumentHelper.parseText(xml);
            Document doc = DocumentHelper.parseText(createHeaderXml(true,true));
            Element message = doc.getRootElement();
            message.element("Token").setText(token);
            message.element("Seq").setText(seqText);
            Element body = message.element("Body");
            message.remove(body);
            message.add(document.getRootElement());
            return outPut(doc);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解析内容
     * @param id
     * @param msg
     * @return
     */
    public  String analyzeResponse(ChannelId id,String msg){
        try {
            Document doc = DocumentHelper.parseText(msg);
            Element message = doc.getRootElement();
            Element opreation = message.element("Body").element("Operation");
            String name = opreation.attributeValue("name");
            String flag = "0";
            if("Set".equals(name)){
                flag = "0";
            }else if("Get".equals(name)){
                flag = "1";
            }
            if(opreation.element("SDO_Error")!=null){
                String cmd = NettyChannelMap.getChannelFlag(id);
                return getErrorResponse(flag,cmd);
            }else{
                String cmd = NettyChannelMap.getChannelFlag(id);
                String content ;
                try{
                    switch (cmd){
                        case "82":
                            content = analyzeCMMaxLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "83":
                            content = analyzeGlobalSynTime(opreation);
                            return getResponse(flag,cmd,content);
                        case "84":
                            content = analyzeGlobalSynchFlag(opreation);
                            return getResponse(flag,cmd,content);
                        case "85":
                            content = analyzeSysInfo(opreation);
                            return getResponse(flag,cmd,content);
                        case "86":
                            content = analyzeCommTime(opreation);
                            return getResponse(flag,cmd,content);
                        case "87":
                            content = analyzeStandTimeZone(opreation);
                            return getResponse(flag,cmd,content);
                        case "88"://本地时间
                            content = analyzeLocalTime(opreation);
                            return getResponse(flag,cmd,content);
                        case "89"://时基调度表最大行数
                            content = analyzeTBDMaxLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "8A"://时段表最大行数
                            content = analyzeTIMaxLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "8B"://时段表所包含的最大时段数
                            content = analyzeTIMaxInter(opreation);
                            return getResponse(flag,cmd,content);
                        case "8C"://活动时段表的编号
                            content = analyzeATINo(opreation);
                            return getResponse(flag,cmd,content);
                        case "8D"://时基调度表行数
                            content = analyzeTBDLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "8E"://时段表行数
                            content = analyzeTILine(opreation);
                            return getResponse(flag,cmd,content);
                        case "8F"://事件类型最大行数
                            content = analyzeEventTypeMaxLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "90"://事件日志表最大行数
                            content = analyzeEventLogMaxLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "91"://事件类型表行数
                            content = analyzeEventTypeLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "92"://事件日志表参数列表
                            content = analyzeSignalControlerError(opreation);
                            return getResponse(flag,cmd,content);
                        case "93"://相位表最大的行数
                            content = analyzePhaseMaxLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "94"://相位状态组最大数据
                            content = analyzePSGMaxNum(opreation);
                            return getResponse(flag,cmd,content);
                        case "95"://相位属性
                            content = analyzePhaseAttribute(opreation);
                            return getResponse(flag,cmd,content);
                        case "96"://相位输出状态表行数
                            content = analyzeCrossPhaseLampStatus(opreation);
                            return getResponse(flag,cmd,content);
                        case "97"://相位冲突表行数
                            content = analyzePCLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "98"://车辆检测器最大数量
                            content = analyzeDetMaxNum(opreation);
                            return getResponse(flag,cmd,content);
                        case "99"://检测器状态组的最大数量
                            content = analyzeDSGMaxNum(opreation);
                            return getResponse(flag,cmd,content);
                        case "9A"://检测数据流水号
                            content = analyzeDetDataSeq(opreation);
                            return getResponse(flag,cmd,content);
                        case "9B"://数据采集周期
                            content = analyzeDetCollectCycle(opreation);
                            return getResponse(flag,cmd,content);
                        case "9C"://活动检测器总数
                            content = analyzeActDetTotal(opreation);
                            return getResponse(flag,cmd,content);
                        case "9D"://脉冲数据流水号
                            content = analyzePulseDataSeq(opreation);
                            return getResponse(flag,cmd,content);
                        case "9E"://脉冲数据采集周期
                            content = analyzePulseDataCycle(opreation);
                            return getResponse(flag,cmd,content);
                        case "9F"://车检测器参数定义表行数
                            content = analyzeDetParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "A0"://车辆检测器状态表行数
                            content = analyzeDetStateParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "A1"://交通检测数据表参数列表
                            content = analyzeCrossTrafficData(opreation);
                            return getResponse(flag,cmd,content);
                        case "A2"://检测器告警参数列表
                            content = analyzeDetAlarmParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "A3"://启动时的闪光控制时间
                            content = analyzeRunFlashSec(opreation);
                            return getResponse(flag,cmd,content);
                        case "A4"://启动时的全红控制时间
                            content = analyzeRunRedSec(opreation);
                            return getResponse(flag,cmd,content);
                        case "A5"://当前信号机控制状态
                            content = analyzeSignalContState(opreation);
                            return getResponse(flag,cmd,content);
                        case "A6"://当前的闪光控制模式
                            content = analyzeFlashContMode(opreation);
                            return getResponse(flag,cmd,content);
                        case "A7"://信号机报警2
                            content = analyzeSignalAlarm2(opreation);
                            return getResponse(flag,cmd,content);
                        case "A8"://信号机报警1
                            content = analyzeSignalAlarm1(opreation);
                            return getResponse(flag,cmd,content);
                        case "A9"://信号机报摘要
                            content = analyzeSignalAbstract(opreation);
                            return getResponse(flag,cmd,content);
                        case "AA"://允许远程控制实体激活信号机的某些功能
                            content = analyzeRemoteActFunc(opreation);
                            return getResponse(flag,cmd,content);
                        case "AB"://闪光频率
                            content = analyzeFlashRate(opreation);
                            return getResponse(flag,cmd,content);
                        case "AC"://辉度控制开启时间
                            content = analyzeBrightStartTime(opreation);
                            return getResponse(flag,cmd,content);
                        case "AD"://辉度控制关闭时间
                            content = analyzeBrightEndTime(opreation);
                            return getResponse(flag,cmd,content);
                        case "AE"://信号机支持的最大通道数量
                            content = analyzeSignalMaxRoute(opreation);
                            return getResponse(flag,cmd,content);
                        case "AF"://通道状态组数
                            content = analyzeRouteStateGroup(opreation);
                            return getResponse(flag,cmd,content);
                        case "B0"://通道参数表行数
                            content = analyzePhaseParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "B1"://通道输出状态参数列表
                            content = analyzeRouteStateParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "B2"://配时方案数
                            content = analyzePlanNum(opreation);
                            return getResponse(flag,cmd,content);
                        case "B3"://通道参数表行数
                            content = analyzeMaxStagePlanNum(opreation);
                            return getResponse(flag,cmd,content);
                        case "B4"://最大阶段数
                            content = analyzeMaxStageNum(opreation);
                            return getResponse(flag,cmd,content);
                        case "B6"://系统控制方案
                            content = analyzeCrossPlan(opreation);
                            return getResponse(flag,cmd,content);
                        case "B7"://控制方式
                            content = analyzeCrossControlMode(opreation);
                            return getResponse(flag,cmd,content);
                        case "B8"://公共周期时长
                            content = analyzeCommCycleDur(opreation);
                            return getResponse(flag,cmd,content);
                        case "BA"://阶段状态
                            content = analyzeStageState(opreation);
                            return getResponse(flag,cmd,content);
                        case "BB"://步进指令
                            content = analyzeStep(opreation);
                            return getResponse(flag,cmd,content);
                        case "BC"://降级模式
                            content = analyzeDegradeMode(opreation);
                            return getResponse(flag,cmd,content);
                        case "BD"://降级基准方案表
                            content = analyzeDegradeBasePlan(opreation);
                            return getResponse(flag,cmd,content);
                        case "BE"://当前方案各阶段时长
                            content = analyzePlanStageDur(opreation);
                            return getResponse(flag,cmd,content);
                        case "BF"://当前方案各关键相位绿灯时长
                            content = analyzeKeyPhaseGreen(opreation);
                            return getResponse(flag,cmd,content);
                        case "C0"://配置方案表行数
                            content = analyzePlanParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "C1"://阶段配时表参数列表
                            content = analyzeStageParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "C2"://下载标志
                            content = analyzeDownLoadFlag(opreation);
                            return getResponse(flag,cmd,content);
                        case "C3"://控制主机选项参数
                            content = analyzeHostOptionParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "C4"://信号机基地址
                            content = analyzeSignalBaseAddr(opreation);
                            return getResponse(flag,cmd,content);
                        case "C5"://路口数量
                            content = analyzeCrossNum(opreation);
                            return getResponse(flag,cmd,content);
                        case "C6"://跟随相位表最大行数
                            content = analyzeFolPhaseMaxLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "C7"://跟随相位状态表行数
                            content = analyzeFolPhaseStateLine(opreation);
                            return getResponse(flag,cmd,content);
                        case "C8"://跟随相位表参数列表
                            content = analyzeFolPhaseParam(opreation);
                            return getResponse(flag,cmd,content);
                        case "C9"://跟随相位状态组表行数
                            content = analyzeFolPhaseState(opreation);
                            return getResponse(flag,cmd,content);
                    }
                }catch (RuntimeException e){
                    System.out.println("设置时接收包体异常，响应内容包体为空");
                    e.printStackTrace();
                    return getResponse(flag,cmd,"");
                }

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getErrorResponse(String flag,String cmd){
        Integer length = NettyChannelParam.ocxCmdLength+NettyChannelParam.ocxFlagLength+
                NettyChannelParam.ocxResponseCodeLength;
        String packegLength = String.format("%04d",length+NettyChannelParam.ocxPackageLength);
        String response = packegLength+cmd+flag+"11";
        return response;
    }
    private String getResponse(String flag,String cmd,String content){
        Integer length = NettyChannelParam.ocxCmdLength+NettyChannelParam.ocxFlagLength+
                NettyChannelParam.ocxResponseCodeLength+content.length();
        String packegLength = String.format("%04d",length+NettyChannelParam.ocxPackageLength);
        if("1".equals(flag) && (content == null || "".equals(content))){//如果为查询命令且返回内容为空则报错
            return getErrorResponse(flag,cmd);
        }
        String response;
        if(content==null  || "".equals(content)){
            response = packegLength+cmd+flag+"00";
        }else{
            response = packegLength+cmd+flag+"00"+content;
        }
        return response;
    }
    private  String analyzeCMMaxLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelDef_CMMaxLine").getText());
        return content.toString();
    }
    private  String analyzeGlobalSynTime(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_GlobalSynTime").getText());
        return content.toString();
    }
    private  String analyzeGlobalSynchFlag(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_GlobalSynchFlag").getText());
        return content.toString();
    }
    //公共时间
    private  String analyzeCommTime(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_CommTime").getText());
        return content.toString();
    }
    //标准时区
    private  String analyzeStandTimeZone(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_StandTimeZone").getText());
        return content.toString();
    }
    //本地时间
    private  String analyzeLocalTime(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_LocalTime").getText());
        return content.toString();
    }
    private  String analyzeSysInfo(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element sysInfo = opreation.element("SysInfo");
        content.append(sysInfo.element("SelfDef_No").getText());
        content.append("|"+sysInfo.element("SysName").getText());
        content.append("|"+sysInfo.element("SysVersion").getText());
        content.append("|"+sysInfo.element("SelfDef_Model").getText());
        content.append("|"+sysInfo.element("SelfDef_Type").getText());
        return content.toString();
    }
    //时基调度表最大行数
    private  String analyzeTBDMaxLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_TBDMaxLine").getText());
        return content.toString();
    }
    //时段表最大行数
    private  String analyzeTIMaxLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_TIMaxLine").getText());
        return content.toString();
    }
    //时段表所包含的最大时段数
    private  String analyzeTIMaxInter(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_TIMaxInter").getText());
        return content.toString();
    }
    //活动时段表的编号
    private  String analyzeATINo(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_ATINo").getText());
        return content.toString();
    }
    //时基调度表行数
    private  String analyzeTBDLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element tbdLine = opreation.element("SelfDef_TBDLine");
        content.append(tbdLine.element("No").getText());
        content.append("|"+tbdLine.element("Month").getText());
        content.append("|"+tbdLine.element("Week").getText());
        content.append("|"+tbdLine.element("Day").getText());
        content.append("|"+tbdLine.element("TINo").getText());
        return content.toString();
    }
    //时段表行数
    private  String analyzeTILine(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element tILine = opreation.element("SelfDef_TILine");
        content.append(tILine.element("No").getText());
        content.append("|"+tILine.element("EventNo").getText());
        content.append("|"+tILine.element("Hour").getText());
        content.append("|"+tILine.element("Min").getText());
        content.append("|"+tILine.element("ContMode").getText());
        content.append("|"+tILine.element("PlanNo").getText());
        content.append("|"+tILine.element("Assist").getText());
        content.append("|"+tILine.element("Special").getText());
        return content.toString();
    }
    //事件类型最大行数
    private  String analyzeEventTypeMaxLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_EventTypeMaxLine").getText());
        return content.toString();
    }
    //事件日志表最大行数
    private  String analyzeEventLogMaxLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_EventLogMaxLine").getText());
        return content.toString();
    }
    //事件类型表行数
    private  String analyzeEventTypeLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element eventTypeLine = opreation.element("SelfDef_EventTypeLine");
        content.append(eventTypeLine.element("No").getText());
        content.append("|"+eventTypeLine.element("ClearTime").getText());
        content.append("|"+eventTypeLine.element("Desc").getText());
        content.append("|"+eventTypeLine.element("TablePos").getText());
        return content.toString();
    }
    //事件日志表参数列表
    private  String analyzeSignalControlerError(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element signalControlerError = opreation.element("SignalControlerError");
        content.append(signalControlerError.element("SignalID").getText());
        content.append("|"+signalControlerError.element("SelfDef_Seq").getText());
        content.append("|"+signalControlerError.element("ErrorType").getText());
        content.append("|"+signalControlerError.element("ErrorDesc").getText());
        content.append("|"+signalControlerError.element("OccerTime").getText());
        return content.toString();
    }
    //相位表最大的行数
    private  String analyzePhaseMaxLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_PhaseMaxLine").getText());
        return content.toString();
    }
    //相位状态组最大数据
    private  String analyzePSGMaxNum(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_PSGMaxNum").getText());
        return content.toString();
    }
    //相位属性
    private  String analyzePhaseAttribute(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element phaseAttribute = opreation.element("SelfDef_PhaseAttribute");
        content.append(phaseAttribute.element("No").getText());
        content.append("|"+phaseAttribute.element("PassGreenSec").getText());
        content.append("|"+phaseAttribute.element("ClearSec").getText());
        content.append("|"+phaseAttribute.element("MinGreenSec").getText());
        content.append("|"+phaseAttribute.element("DelayGreenSec").getText());
        content.append("|"+phaseAttribute.element("MaxGreenSec1").getText());
        content.append("|"+phaseAttribute.element("MaxGreenSec2").getText());
        content.append("|"+phaseAttribute.element("FixedGreenSec").getText());
        content.append("|"+phaseAttribute.element("FlashGreenSec").getText());
        content.append("|"+phaseAttribute.element("PhaseType").getText());
        content.append("|"+phaseAttribute.element("PhaseOption").getText());
        content.append("|"+phaseAttribute.element("Reserve").getText());
        return content.toString();
    }
    //相位输出状态表行数
    private  String analyzeCrossPhaseLampStatus(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element crossPhaseLampStatus = opreation.element("CrossPhaseLampStatus");
        content.append(crossPhaseLampStatus.element("CrossID").getText());
        Element phaseLampStatus = crossPhaseLampStatus.element("PhaseLampStatusList").element("PhaseLampStatus");
        content.append("|"+phaseLampStatus.element("PhaseNo").getText());
        content.append("|"+phaseLampStatus.element("LampStatus").getText());
        return content.toString();
    }
    //相位冲突表行数
    private  String analyzePCLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element pCLine  = opreation.element("SelfDef_PCLine");
        content.append(pCLine.element("No").getText());
        content.append("|"+pCLine.element("Conflict").getText());
        return content.toString();
    }
    //车辆检测器最大数量
    private  String analyzeDetMaxNum(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_DetMaxNum").getText());
        return content.toString();
    }
    //检测器状态组的最大数量
    private  String analyzeDSGMaxNum(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_DSGMaxNum").getText());
        return content.toString();
    }
    //检测数据流水号
    private  String analyzeDetDataSeq(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_DetDataSeq").getText());
        return content.toString();
    }
    //数据采集周期
    private  String analyzeDetCollectCycle(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_DetCollectCycle").getText());
        return content.toString();
    }
    //活动检测器总数
    private  String analyzeActDetTotal(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_ActDetTotal").getText());
        return content.toString();
    }
    //脉冲数据流水号
    private  String analyzePulseDataSeq(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_PulseDataSeq").getText());
        return content.toString();
    }
    //脉冲数据采集周期
    private  String analyzePulseDataCycle(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_PulseDataCycle").getText());
        return content.toString();
    }
    //车检测器参数定义表行数
    private  String analyzeDetParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element phaseAttribute = opreation.element("DetParam");
        content.append(phaseAttribute.element("DetID").getText());
        content.append("|"+phaseAttribute.element("Distance").getText());
        content.append("|"+phaseAttribute.element("CrossID").getText());
        content.append("|"+phaseAttribute.element("SelfDef_RequPhase").getText());
        content.append("|"+phaseAttribute.element("SelfDef_Type").getText());
        content.append("|"+phaseAttribute.element("SelfDef_Direction").getText());
        content.append("|"+phaseAttribute.element("SelfDef_RequValidSec").getText());
        content.append("|"+phaseAttribute.element("SelfDef_Option").getText());
        content.append("|"+phaseAttribute.element("SelfDef_KeySatuFlow").getText());
        content.append("|"+phaseAttribute.element("SelfDef_KeySatuRate").getText());
        return content.toString();
    }
    //车检测器参数定义表行数
    private  String analyzeDetStateParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element phaseAttribute = opreation.element("SelfDef_DetStateParam");
        content.append(phaseAttribute.element("No").getText());
        content.append("|"+phaseAttribute.element("State").getText());
        content.append("|"+phaseAttribute.element("Alarm").getText());
        return content.toString();
    }
    //交通检测数据表参数列表
    private  String analyzeCrossTrafficData(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element crossTrafficData = opreation.element("CrossTrafficData");
        content.append(crossTrafficData.element("CrossID").getText());
        content.append("|"+crossTrafficData.element("EndTime").getText());
        content.append("|"+crossTrafficData.element("Interval").getText());
        Element data = crossTrafficData.element("DataList").element("Data");
        content.append("|"+data.element("LaneNo").getText());
        content.append("|"+data.element("Volume").getText());
        content.append("|"+data.element("SelfDef_BigVolume").getText());
        content.append("|"+data.element("SelfDef_SmallVolume").getText());
        content.append("|"+data.element("AvgVehLen").getText());
        content.append("|"+data.element("Pcu").getText());
        content.append("|"+data.element("HeadDistance").getText());
        content.append("|"+data.element("HeadTime").getText());
        content.append("|"+data.element("Speed").getText());
        content.append("|"+data.element("Saturation").getText());
        content.append("|"+data.element("Density").getText());
        content.append("|"+data.element("QueueLength").getText());
        content.append("|"+data.element("Occupancy").getText());
        return content.toString();
    }
    //检测器告警参数列表
    private  String analyzeDetAlarmParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element phaseAttribute = opreation.element("SelfDef_DetAlarmParam");
        content.append(phaseAttribute.element("No").getText());
        content.append("|"+phaseAttribute.element("AlarmState").getText());
        content.append("|"+phaseAttribute.element("CoilState").getText());
        return content.toString();
    }
    //启动时的闪光控制时间
    private  String analyzeRunFlashSec(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_RunFlashSec").getText());
        return content.toString();
    }
    //启动时的全红控制时间
    private  String analyzeRunRedSec(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_RunRedSec").getText());
        return content.toString();
    }
    //当前信号机控制状态
    private  String analyzeSignalContState(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_SignalContState").getText());
        return content.toString();
    }
    //当前的闪光控制模式
    private  String analyzeFlashContMode(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_FlashContMode").getText());
        return content.toString();
    }
    //信号机报警2
    private  String analyzeSignalAlarm2(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_SignalAlarm2").getText());
        return content.toString();
    }
    //信号机报警1
    private  String analyzeSignalAlarm1(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_SignalAlarm1").getText());
        return content.toString();
    }
    //信号机报摘要
    private  String analyzeSignalAbstract(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_SignalAbstract").getText());
        return content.toString();
    }
    //允许远程控制实体激活信号机的某些功能
    private  String analyzeRemoteActFunc(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_RemoteActFunc").getText());
        return content.toString();
    }
    //闪光频率
    private  String analyzeFlashRate(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_FlashRate").getText());
        return content.toString();
    }
    //辉度控制开启时间
    private  String analyzeBrightStartTime(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_BrightStartTime").getText());
        return content.toString();
    }
    //辉度控制关闭时间
    private  String analyzeBrightEndTime(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_BrightEndTime").getText());
        return content.toString();
    }
    //信号机支持的最大通道数量
    private  String analyzeSignalMaxRoute(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_SignalMaxRoute").getText());
        return content.toString();
    }
    //通道状态组数
    private  String analyzeRouteStateGroup(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_RouteStateGroup").getText());
        return content.toString();
    }
    //通道参数表行数
    private  String analyzePhaseParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element phaseParam = opreation.element("PhaseParam");
        content.append(phaseParam.element("CrossID").getText());
        content.append("|"+phaseParam.element("No").getText());
        content.append("|"+phaseParam.element("PhaseNo").getText());
        content.append("|"+phaseParam.element("PhaseName").getText());
        content.append("|"+phaseParam.element("Attribute").getText());
        content.append("|"+phaseParam.element("SelfDef_FlashState").getText());
        content.append("|"+phaseParam.element("SelfDef_ContMode").getText());
        Element laneNoList = phaseParam.element("LaneNoList");
        content.append("|"+laneNoList.element("LaneNo").getText());
        Element pedDirList = phaseParam.element("PedDirList");
        content.append("|"+pedDirList.element("Direction").getText());
        return content.toString();
    }
    //通道输出状态参数列表
    private  String analyzeRouteStateParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element routeStateParam = opreation.element("SelfDef_RouteStateParam");
        content.append(routeStateParam.element("No").getText());
        content.append("|"+routeStateParam.element("Red").getText());
        content.append("|"+routeStateParam.element("Yellow").getText());
        content.append("|"+routeStateParam.element("Green").getText());
        return content.toString();
    }
    //配时方案数
    private  String analyzePlanNum(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_PlanNum").getText());
        return content.toString();
    }
    //最大阶段配时表数
    private  String analyzeMaxStagePlanNum(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_MaxStagePlanNum").getText());
        return content.toString();
    }
    //最大阶段数
    private  String analyzeMaxStageNum(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_MaxStageNum").getText());
        return content.toString();
    }
    //系统控制方案
    private  String analyzeCrossPlan(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element crossPlan = opreation.element("CrossPlan");
        content.append(crossPlan.element("CrossID").getText());
        content.append("|"+crossPlan.element("SelfDef_Type").getText());
        content.append("|"+crossPlan.element("Plan").getText());
        return content.toString();
    }
    //控制方式
    private  String analyzeCrossControlMode(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element crossPlan = opreation.element("CrossControlMode");
        content.append(crossPlan.element("CrossID").getText());
        content.append("|"+crossPlan.element("Value").getText());
        return content.toString();
    }
    //公共周期时长
    private  String analyzeCommCycleDur(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_CommCycleDur").getText());
        return content.toString();
    }
    //阶段状态
    private  String analyzeStageState(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_StageState").getText());
        return content.toString();
    }
    //步进指令
    private  String analyzeStep(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_Step").getText());
        return content.toString();
    }
    //降级模式
    private  String analyzeDegradeMode(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_DegradeMode").getText());
        return content.toString();
    }
    //降级基准方案表
    private  String analyzeDegradeBasePlan(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_DegradeBasePlan").getText());
        return content.toString();
    }
    //当前方案各阶段时长
    private  String analyzePlanStageDur(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_PlanStageDur").getText());
        return content.toString();
    }
    //当前方案各关键相位绿灯时长
    private  String analyzeKeyPhaseGreen(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_KeyPhaseGreen").getText());
        return content.toString();
    }
    //配置方案表行数
    private  String analyzePlanParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element planParam = opreation.element("PlanParam");
        content.append(planParam.element("CrossID").getText());
        content.append("|"+planParam.element("PlanNo").getText());
        content.append("|"+planParam.element("CycleLen").getText());
        content.append("|"+planParam.element("CoordPhaseNo").getText());
        content.append("|"+planParam.element("OffSet").getText());
        Element StageNoList = planParam.element("StageNoList");
        content.append("|"+StageNoList.element("StageNo").getText());
        return content.toString();
    }
    //阶段配时表参数列表
    private  String analyzeStageParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element stageParam = opreation.element("StageParam");
        content.append(stageParam.element("CrossID").getText());
        content.append("|"+stageParam.element("StageNo").getText());
        content.append("|"+stageParam.element("StageName").getText());
        content.append("|"+stageParam.element("Attribute").getText());
        content.append("|"+stageParam.element("SelfDef_PassPhase").getText());
        content.append("|"+stageParam.element("Green").getText());
        content.append("|"+stageParam.element("RedYellow").getText());
        content.append("|"+stageParam.element("Yellow").getText());
        content.append("|"+stageParam.element("AllRed").getText());
        Element phaseNoList = stageParam.element("PhaseNoList");
        content.append("|"+phaseNoList.element("PhaseNo").getText());
        return content.toString();
    }
    //下载标志
    private  String analyzeDownLoadFlag(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_DownLoadFlag").getText());
        return content.toString();
    }
    //控制主机选项参数
    private  String analyzeHostOptionParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_HostOptionParam").getText());
        return content.toString();
    }
    //信号机基地址
    private  String analyzeSignalBaseAddr(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_SignalBaseAddr").getText());
        return content.toString();
    }
    //路口数量
    private  String analyzeCrossNum(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_CrossNum").getText());
        return content.toString();
    }
    //跟随相位表最大行数
    private  String analyzeFolPhaseMaxLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_FolPhaseMaxLine").getText());
        return content.toString();
    }
    //跟随相位状态表行数
    private  String analyzeFolPhaseStateLine(Element opreation){
        StringBuffer content = new StringBuffer("");
        content.append(opreation.element("SelfDef_FolPhaseStateLine").getText());
        return content.toString();
    }
    //跟随相位表参数列表
    private  String analyzeFolPhaseParam(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element folPhaseParam = opreation.element("SelfDef_FolPhaseParam");
        content.append(folPhaseParam.element("No").getText());
        content.append("|"+folPhaseParam.element("OperType").getText());
        content.append("|"+folPhaseParam.element("IncludePhase").getText());
        content.append("|"+folPhaseParam.element("CorrectPhase").getText());
        content.append("|"+folPhaseParam.element("TailGreen").getText());
        content.append("|"+folPhaseParam.element("TailYellow").getText());
        content.append("|"+folPhaseParam.element("TailRed").getText());
        return content.toString();
    }
    //跟随相位状态组表行数
    private  String analyzeFolPhaseState(Element opreation){
        StringBuffer content = new StringBuffer("");
        Element folPhaseState = opreation.element("SelfDef_FolPhaseState");
        content.append(folPhaseState.element("No").getText());
        content.append("|"+folPhaseState.element("RedState").getText());
        content.append("|"+folPhaseState.element("YellowState").getText());
        content.append("|"+folPhaseState.element("GreenState").getText());
        return content.toString();
    }
    /**
     * 格式化输出
     * @param doc
     * @return
     */
    public String outPut(Document doc){
        OutputFormat format = OutputFormat.createCompactFormat();
        /*format.setIndent(true); //设置是否缩进
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
