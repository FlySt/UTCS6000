package com.ncjk.utcs.common.service;

import com.ncjk.utcs.common.netty.server.NettyChannelParam;
import com.ncjk.utcs.common.netty.server.NettyService;
import com.ncjk.utcs.common.service.interfaces.UtcsWebService;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalDeviceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.driver.DatabaseError;
import org.apache.log4j.Logger;
import sun.util.resources.CalendarData_ar;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by swl on 2016/12/1.
 */
@WebService(endpointInterface="com.ncjk.utcs.common.service.interfaces.UtcsWebService",serviceName="UtcsWebService",targetNamespace = "urn:signal")
public class UtcsWebServiceImpl implements UtcsWebService {
    Logger logger = Logger.getLogger(UtcsWebServiceImpl.class);
    @Resource
    NettyService nettyService;
    @Resource
    ISignalDeviceService signalDeviceService;
    @Override
    public String Command(@WebParam(name="cmdid",targetNamespace="urn:signal") int cmdid,
                          @WebParam(name="server",targetNamespace="urn:signal")String server,
                          @WebParam(name="cmdbuffer",targetNamespace="urn:signal")String cmdbuffer) {
    //    logger.info("server:"+server+",cmdid:"+cmdid+",cmdbuffer:"+cmdbuffer);
        synchronized (this){
            if("".equals(server)){
                server = signalDeviceService.getIpAddr();
            }
        //    logger.info("NettyChannelParam.cmdCount:"+NettyChannelParam.cmdCount);
            if(cmdid==16){
                NettyChannelParam.cmdCount++;
                if(NettyChannelParam.cmdCount>=5){
                    NettyChannelParam.signalStatus.clear();
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cmdid",cmdid);
            if(cmdbuffer==""){
                jsonObject.put("content",cmdbuffer);
            }else{
                jsonObject.put("content","\""+cmdbuffer+"\"");
            }
            nettyService.sendToInside(server,jsonObject.toString());
            String result = nettyService.getResult();
            if(cmdid!=16){
                logger.info("server:"+server+",cmdid:"+cmdid+",cmdbuffer:"+cmdbuffer);
                logger.info("收到内部协议原始信号机信息:"+result);
            }
            NettyChannelParam.insideResponse.setLength(0);
            String gbkResult = null;
            try {
               gbkResult = new String(result.getBytes(),"ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return gbkResult;
        }
    }

    public static void main(String[] args) {
        System.out.println("web service start");
        UtcsWebServiceImpl implementor = new UtcsWebServiceImpl();
        String address = "http://localhost:7080/utcsWebService";
        Endpoint.publish(address, implementor);
        System.out.println("web service started");
    }
}
