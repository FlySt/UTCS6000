package com.ncjk.utcs.modules.resources.resources.action;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsRegionParam;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.IRegionParamService;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 区域ACTION
 * Created by swl on 2016/9/19.
 */
@Scope("prototype")
@Controller("regionParamAction")
public class RegionParamAction extends ActionSupport {
    @Resource
    IRegionParamService regionParamService;
    @Resource
    ILogService logService;
    private Integer regionId;
    private String regionName;
    private String fatherRegionName;
    private Integer[] ids;
    private String names;
    private UtcsRegionParam regionParam;
    /**
     * 查找区域信息
     */
    public void queryRegionParams(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject js = regionParamService.findRegionParams(regionId,regionName);
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加或修改区域信息
     * @return
     */
    public String modifyRegion(){
        if(regionId!=null){
            regionParam = regionParamService.findRegionParamById(regionId);
            if(regionParam.getFatherRegionId()!=-1){
                UtcsRegionParam fatherRegion = regionParamService.findRegionParamById(regionParam.getFatherRegionId());
                fatherRegionName = fatherRegion.getRegionName();
            }
        }
        return "modifyRegion";
    }

    /**
     * 保存区域信息
     */
    public void saveRegion(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        Boolean isSuccess = regionParamService.saveOrUpdateReginParam(regionParam);
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
        if (regionParam.getRegionId() == null) {
            logService.saveOrUpdateLog("新增区域【" + regionParam.getRegionName() + "】信息" + logMsg, "区域管理");
        }
        else {
            logService.saveOrUpdateLog("修改区域【" + regionParam.getRegionName() + "】信息" + logMsg, "区域管理");
        }
    }
    public void delRegions(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        Boolean isSuccess = regionParamService.delRegionParams(ids);
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
        logService.saveOrUpdateLog("删除区域信息【" + names + "】" + logMsg, "区域管理");
    }
    /**
     * 检查区域名称是否存在
     */
    public void validatorRegionName(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json; charset=utf-8");
        Boolean isExist = regionParamService.isExistRegionName(regionId,regionName);
        try {
            response.getWriter().write(isExist.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UtcsRegionParam getRegionParam() {
        return regionParam;
    }

    public void setRegionParam(UtcsRegionParam regionParam) {
        this.regionParam = regionParam;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getFatherRegionName() {
        return fatherRegionName;
    }

    public void setFatherRegionName(String fatherRegionName) {
        this.fatherRegionName = fatherRegionName;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
