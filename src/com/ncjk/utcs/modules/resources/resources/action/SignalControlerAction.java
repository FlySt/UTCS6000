package com.ncjk.utcs.modules.resources.resources.action;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalControler;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalControlerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 信号机Action
 * Created by shiwanglai on 2016/9/7.
 */
@Scope("prototype")
@Controller("signalControlerAction")
public class SignalControlerAction {
    @Resource
    ISignalControlerService signalControlerService;
    @Resource
    ILogService logService;
    private Integer signalControlerId;
    private String signalControlerName;
    private String signalControlerNum;
    private String longitude;
    private String latitude;
    private UtcsSignalControler signalControler;
    private Integer[] ids;
    private String names;
    private Integer crossId;
    /** *
     *查找所有信号机
     */
    public void queryAllSignalControlers(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject js = signalControlerService.findSignalControlers(signalControlerId,signalControlerName);
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加或修改信号机
     * @return
     */
    public String modifySignalControler(){
        if(signalControlerId!=null){
            signalControler = signalControlerService.findSignalControlerById(signalControlerId);
            signalControler.setType(signalControler.getType()-Integer.valueOf(signalControler.getProtocolNum()));
        }
        return "modifySignalControler";
    }
    /**
     * 保存信号机信息
     */
    public void saveSignalControler(){
        Boolean isSuccess;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("json/text;charset=utf-8");
        isSuccess = signalControlerService.saveOrUpdateSignalControler(signalControler);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",isSuccess);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String logMsg = "失败";
        if(isSuccess){
            logMsg = "成功";
        }
        if (signalControler.getSignalControlerId() == null) {
            logService.saveOrUpdateLog("新增信号机【" + signalControler.getSignalControlerName() + "】信息" + logMsg, "信号机管理");
        }
        else {
            logService.saveOrUpdateLog("修改信号机【" + signalControler.getSignalControlerName()  + "】信息" + logMsg, "信号机管理");
        }
    }

    /**
     * 删除信号机
     */
    public void delSignalControlers(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        Boolean isSuccess = signalControlerService.delSignalControlers(ids);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",isSuccess);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String logMsg = "失败";
        if (isSuccess) {
            logMsg = "成功";
        }
        logService.saveOrUpdateLog("删除信号机信息【" + names + "】" + logMsg, "信号机管理");
    }
    /**
     * 检查该路口下是否存在信号机
     */
    public void isCrossExistSignalControler(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        Boolean isExist = signalControlerService.isCrossExistSignalControler(crossId,signalControlerId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",isExist);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 检查信号机名称是否存在
     */
    public void validatorSignalControlerName(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        Boolean isExist = signalControlerService.isExistSignalControlerName(signalControlerId,signalControlerName);
        try {
            response.getWriter().write(isExist.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 检查区域编号是否存在
     */
    public void validatorSignalControlerNum(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        Boolean isExist = signalControlerService.isExistSignalControlerNum(signalControlerId,signalControlerNum);
        try {
            response.getWriter().write(isExist.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Integer getSignalControlerId() {
        return signalControlerId;
    }

    public void setSignalControlerId(Integer signalControlerId) {
        this.signalControlerId = signalControlerId;
    }

    public String getSignalControlerName() {
        return signalControlerName;
    }

    public void setSignalControlerName(String signalControlerName) {
        this.signalControlerName = signalControlerName;
    }

    public String getSignalControlerNum() {
        return signalControlerNum;
    }

    public void setSignalControlerNum(String signalControlerNum) {
        this.signalControlerNum = signalControlerNum;
    }

    public UtcsSignalControler getSignalControler() {
        return signalControler;
    }

    public void setSignalControler(UtcsSignalControler signalControler) {
        this.signalControler = signalControler;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getCrossId() {
        return crossId;
    }

    public void setCrossId(Integer crossId) {
        this.crossId = crossId;
    }
}
