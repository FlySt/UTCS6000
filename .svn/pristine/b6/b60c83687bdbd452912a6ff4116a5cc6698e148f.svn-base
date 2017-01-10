package com.ncjk.utcs.modules.resources.resources.action;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsServerParam;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.IServerParamService;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by swl on 2016/11/29.
 * 服务器参数ACTION
 */
@Scope("prototype")
@Controller("serverParamAction")
public class ServerParamAction extends ActionSupport{

    @Resource
    IServerParamService serverParamService;
    @Resource
    ILogService logService;
    private Integer serverId;
    private String serverNo;
    private UtcsServerParam serverParam;
    private Integer[] ids;
    private String nos;
    private Integer isCenter;
    /**
     * 查找服务器信息
     */
    public void findServerParams(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject jsonObject = serverParamService.findServerParams(serverId,serverNo);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加或更改服务器
     * @return
     */
    public String modifyServer(){
        if(serverId!=null){
            serverParam = serverParamService.findServerParamById(serverId);
        }
        return "modifyServer";
    }

    /**
     * 保存服务器信息
     */
    public void saveServer(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        Boolean isSuccess = serverParamService.saveOrUpdateServerParam(serverParam);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",isSuccess);
        String logMsg = "失败";
        if(isSuccess){
            logMsg = "成功";
        }
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (serverParam.getServerId() == null) {
            logService.saveOrUpdateLog("新增服务器【" + serverParam.getServerNo() + "】信息" + logMsg, "服务器配置");
        }
        else {
            logService.saveOrUpdateLog("修改服务器【" + serverParam.getServerNo() + "】信息" + logMsg, "服务器配置");
        }
    }

    /**
     * 删除服务器
     */
    public void delServers(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        Boolean isSuccess = serverParamService.delServers(ids);
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
        logService.saveOrUpdateLog("删除服务器信息【" + nos + "】" + logMsg, "服务器配置");
    }
    /**
     * 验证服务器编号是否存在
     */
    public void validatorServerNo(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        Boolean isExist = serverParamService.isExistServerNo(serverId,serverNo);
        try {
            response.getWriter().write(isExist.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查中心服务器是否存在
     * @return
     */
    public void validatorCenterServer(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        Boolean isExist = true;
        if(isCenter==0){
            isExist = serverParamService.isExistCenterServer(serverId);
        }
        try {
            response.getWriter().write(isExist.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerNo() {
        return serverNo;
    }

    public void setServerNo(String serverNo) {
        this.serverNo = serverNo;
    }

    public UtcsServerParam getServerParam() {
        return serverParam;
    }

    public void setServerParam(UtcsServerParam serverParam) {
        this.serverParam = serverParam;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public String getNos() {
        return nos;
    }

    public void setNos(String nos) {
        this.nos = nos;
    }

    public Integer getIsCenter() {
        return isCenter;
    }

    public void setIsCenter(Integer isCenter) {
        this.isCenter = isCenter;
    }
}
