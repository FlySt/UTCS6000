package com.ncjk.utcs.modules.resources.resources.action;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsCrossParam;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ICrossParamService;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 路口Action
 * Created by swl on 2016/9/19.
 */
@Scope("prototype")
@Controller("crossParamAction")
public class CrossParamAction extends ActionSupport{
    @Resource
    ICrossParamService crossParamService;
    @Resource
    ILogService logService;
    private Integer crossId;
    private String crossName;
    private String crossNum;
    private Integer[] ids;
    private String names;
    UtcsCrossParam crossParam;
    /**
     * 查找路口信息
     */
    public void queryCrossParams(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        JSONObject js = crossParamService.findCrossParams(crossId,crossName);
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加或修改路口
     * @return
     */
    public String modifyCross(){
        if(crossId!=null){
            crossParam = crossParamService.findCrossParamById(crossId);
        }
        return "modifyCross";
    }

    /**
     * 保存路口
     */
    public void saveCross(){
        System.out.println("saveCross");
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        Boolean isSuccess = crossParamService.saveOrUpdateCrossParam(crossParam);
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
        if (crossParam.getCrossId() == null) {
            logService.saveOrUpdateLog("新增路口【" + crossParam.getCrossName() + "】信息" + logMsg, "路口管理");
        }
        else {
            logService.saveOrUpdateLog("修改路口【" + crossParam.getCrossName()  + "】信息" + logMsg, "路口管理");
        }
    }

    /**
     * s删除路口
     */
    public void delCrosss(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        Boolean isSuccess = crossParamService.delCrossParams(ids);
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
        logService.saveOrUpdateLog("删除路口信息【" + names + "】" + logMsg, "路口管理");
    }
    /**
     * 检查区域名称是否存在
     */
    public void validatorCrossName(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        Boolean isExist = crossParamService.isExistCrossName(crossId,crossName);
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
    public void validatorCrossNum(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        Boolean isExist = crossParamService.isExistCrossNum(crossId,crossNum);
        try {
            response.getWriter().write(isExist.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getCrossId() {
        return crossId;
    }

    public void setCrossId(Integer crossId) {
        this.crossId = crossId;
    }

    public String getCrossName() {
        return crossName;
    }

    public void setCrossName(String crossName) {
        this.crossName = crossName;
    }

    public String getCrossNum() {
        return crossNum;
    }

    public void setCrossNum(String crossNum) {
        this.crossNum = crossNum;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public UtcsCrossParam getCrossParam() {
        return crossParam;
    }

    public void setCrossParam(UtcsCrossParam crossParam) {
        this.crossParam = crossParam;
    }
}
