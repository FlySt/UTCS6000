package com.ncjk.utcs.common.webservice.server;

import com.ncjk.utcs.common.webservice.ServiceParams;
import com.ncjk.utcs.common.webservice.server.interfaces.CommonCommand;
import com.ncjk.utcs.common.webservice.server.interfaces.SysInfoCommand;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.IRegionParamService;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalControlerService;
import com.ncjk.utcs.modules.system.pojo.UtcsSystemParam;
import com.ncjk.utcs.modules.system.services.interfaces.ISystemParaService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by swl on 2016/12/30.
 * 系统参数
 */
@Service("sysInfoCommand")
public class SysInfoCommandImpl implements SysInfoCommand {

    @Resource
    CommonCommand serverCommonCommand;
    @Resource
    ISystemParaService systemParaService;
    @Resource
    IRegionParamService regionParamService;
    @Resource
    ISignalControlerService signalControlerService;
    /**
     * 系统参数解析
     * @param message
     * @return
     */
    @Override
    public String sysInfoAnalysis(Element message) {
        String token = message.element("Token").getText();
        if(ServiceParams.token == null){
            return serverCommonCommand.createError("SysInfo","SDE_NotAllow",message);
        }else if(!ServiceParams.token.equals(token)){
            return serverCommonCommand.createError("SysInfo","SDE_Token",message);
        }else{
            return sysInfoResposne(message);
        }
    }

    private String sysInfoResposne(Element message){
        try {
            Document document = serverCommonCommand.setHeaderValue(DocumentHelper.parseText(serverCommonCommand.createHeader("RESPONSE")),message);
            Element operation = document.getRootElement().element("Body").element("Operation");
            Element sysInfo = operation.addElement("SysInfo");
            UtcsSystemParam systemParam = systemParaService.findSystemParam();
            sysInfo.addElement("SysName").setText(systemParam.getSystemName());
            sysInfo.addElement("SysVersion").setText(systemParam.getSystemVersion());
            sysInfo.addElement("Supplier").setText(systemParam.getSupplier());
            Element regionIdList = sysInfo.addElement("RegionIDList");
            JSONObject object = regionParamService.findRegionParams(null,null);
            if(object!=null){
                JSONArray array = object.getJSONArray("data");
                System.out.println(array.size());
                if(array!=null){
                    for(int i=0;i<array.size();i++){
                        regionIdList.addElement("RegionID").setText(array.getJSONObject(i).getString("regionNum"));
                    }
                }
            }
            Element signalContolerIDList = sysInfo.addElement("SignalControlerIDList");
            JSONObject signalObject = signalControlerService.findSignalControlers(null,null);
            if(signalObject!=null){
                JSONArray array = signalObject.getJSONArray("data");
                System.out.println(array.size());
                if(array!=null){
                    for(int i=0;i<array.size();i++){
                        signalContolerIDList.addElement("SignalControlerID").setText(array.getJSONObject(i).getString("signalControlerNum"));
                    }
                }
            }
            return serverCommonCommand.outPut(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
