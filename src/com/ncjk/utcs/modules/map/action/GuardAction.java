package com.ncjk.utcs.modules.map.action;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.map.pojo.UtcsGuard;
import com.ncjk.utcs.modules.map.pojo.UtcsGuardSignal;
import com.ncjk.utcs.modules.map.services.interfaces.IGuardService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalControler;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalControlerService;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swl on 2016/12/13.
 * 警卫任务Action
 */
@Scope("prototype")
@Controller("guardAction")
public class GuardAction extends ActionSupport{

    @Resource
    IGuardService guardService;
    @Resource
    ILogService logService;
    @Resource
    ISignalControlerService signalControlerService;
    private UtcsGuard utcsGuard= new UtcsGuard();
    //描点集合
    private String pointArrays;
    //方案名称
    private String guardName;
    private Integer guardId;
    private Integer guardStatus;
    private Integer signalControlerId;
    private Integer guardIndex;
    private Integer guardSignalId;
    private Integer lastToTime;
    private Integer passTime;
    private Integer direction;
    private Integer[] ids;
    private Integer[] signalControlerIds;
    private Integer[] guardIndexs;
    /**
     * 查找方案
     */
    public void queryGuards(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject jsonObject = guardService.queryGuards(guardId,guardName,guardStatus);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加方案
     */
    public void  addGuard(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        utcsGuard.setPoints(pointArrays);
        utcsGuard.setGuardName(guardName);
        boolean isSuccess = guardService.saveOrUpdateGuard(utcsGuard);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",isSuccess);
        String logMsg = "失败";
        if(isSuccess){
            logMsg = "成功";
        }
        logService.saveOrUpdateLog("新增方案【" + guardName + "】信息" + logMsg, "预案管理");
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加方案信号机信息
     */
    public void addGuardSignal(){
        boolean isExist = false;
        List<UtcsGuardSignal> utcsGuardSignalList = new ArrayList<UtcsGuardSignal>();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        for(int i=0;i<signalControlerIds.length;i++){
            UtcsGuardSignal utcsGuardSignal = guardService.findGuardSignalBySignalControlerId(signalControlerIds[i]);
            if(utcsGuardSignal==null){
                utcsGuardSignal = new UtcsGuardSignal();
                utcsGuardSignal.setSignalControlerId(signalControlerIds[i]);
                utcsGuardSignal.setGuardIndex(guardIndexs[i]);
                utcsGuardSignal.setDirection(0);
                utcsGuardSignal.setLastToTime(0);
                utcsGuardSignal.setPassTime(0);
                utcsGuardSignalList.add(utcsGuardSignal);
            }else{//信号机已存在与别的方案中
                isExist = true;
                UtcsSignalControler utcsSignalControler = signalControlerService.findSignalControlerById(signalControlerIds[i]);
                jsonObject.put("guardName",utcsGuardSignal.getUtcsGuard().getGuardName());
                jsonObject.put("signalControlerName",utcsSignalControler.getSignalControlerName());
            }
        }
        boolean isSuccess = false;
        if(!isExist){
            isSuccess = guardService.saveOrUpdateGuardSignal(utcsGuardSignalList,guardId);
            jsonObject.put("result",isSuccess);
        }else{
            jsonObject.put("result",false);
        }
        String logMsg = "失败";
        if(isSuccess){
            logMsg = "成功";
        }
        logService.saveOrUpdateLog("新增信号机到方案" + logMsg, "预案审核");
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取方案信号机信息
     */
    public void getGuardSignal(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject jsonObject = guardService.getGuardSignal(signalControlerId,guardId);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据方案ID获取方案信号机信息
     */
    public void getGuardSignalByGuardId(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject jsonObject = guardService.getGuardSignalByGuardId(guardId);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据方案ID获取信号机信息
     */
    public void getSignalControlers(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONArray array = guardService.getSignalControlers(guardId);
        try {
            response.getWriter().write(array.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**修改*/
    public void modifyGuardSignal(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        UtcsGuardSignal utcsGuardSignal = guardService.findGuardSignalById(guardSignalId);
        if(utcsGuardSignal!=null){
            utcsGuardSignal.setPassTime(passTime);
            utcsGuardSignal.setLastToTime(lastToTime);
            utcsGuardSignal.setDirection(direction);
            boolean isSuccess = guardService.saveGuardSignal(utcsGuardSignal);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result",isSuccess);
            try {
                response.getWriter().write(jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**删除方案信号机信息**/
    public void deleteGuardSiganl(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        boolean isSuccess = guardService.deleteGuardSignal(guardSignalId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",isSuccess);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除方案
     */
    public void deleteGuard(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        boolean isSuccess = guardService.deleteGuard(ids);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",isSuccess);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getPointArrays() {
        return pointArrays;
    }

    public void setPointArrays(String pointArrays) {
        this.pointArrays = pointArrays;
    }

    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public Integer getGuardId() {
        return guardId;
    }

    public void setGuardId(Integer guardId) {
        this.guardId = guardId;
    }

    public Integer getGuardStatus() {
        return guardStatus;
    }

    public void setGuardStatus(Integer guardStatus) {
        this.guardStatus = guardStatus;
    }

    public Integer getSignalControlerId() {
        return signalControlerId;
    }

    public void setSignalControlerId(Integer signalControlerId) {
        this.signalControlerId = signalControlerId;
    }

    public Integer getGuardIndex() {
        return guardIndex;
    }

    public void setGuardIndex(Integer guardIndex) {
        this.guardIndex = guardIndex;
    }

    public Integer getGuardSignalId() {
        return guardSignalId;
    }

    public void setGuardSignalId(Integer guardSignalId) {
        this.guardSignalId = guardSignalId;
    }

    public Integer getLastToTime() {
        return lastToTime;
    }

    public void setLastToTime(Integer lastToTime) {
        this.lastToTime = lastToTime;
    }

    public Integer getPassTime() {
        return passTime;
    }

    public void setPassTime(Integer passTime) {
        this.passTime = passTime;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public Integer[] getSignalControlerIds() {
        return signalControlerIds;
    }

    public void setSignalControlerIds(Integer[] signalControlerIds) {
        this.signalControlerIds = signalControlerIds;
    }

    public Integer[] getGuardIndexs() {
        return guardIndexs;
    }

    public void setGuardIndexs(Integer[] guardIndexs) {
        this.guardIndexs = guardIndexs;
    }
}
