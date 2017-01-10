package com.ncjk.utcs.modules.system.action;

import com.ncjk.utcs.modules.system.pojo.UtcsNetWorkParam;
import com.ncjk.utcs.modules.system.services.interfaces.INetWorkParamService;
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
 * 网络参数Action
 */
@Scope("prototype")
@Controller("netWorkParamAction")
public class NetWorkParamAction  extends ActionSupport{

    @Resource
    private INetWorkParamService netWorkParamService;

    private UtcsNetWorkParam utcsNetWorkParam = new UtcsNetWorkParam();
    public void saveParam(){
        JSONObject jsonObject = new JSONObject();
        HttpServletResponse response = ServletActionContext.getResponse();
        boolean isSave = netWorkParamService.saveParam(utcsNetWorkParam);
        response.setContentType("text/json;charset=utf-8");
        String msg = "";
        if(isSave){
            msg = "ok";
        }else {
        }
        jsonObject.put("msg", msg);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UtcsNetWorkParam getUtcsNetWorkParam() {
        return utcsNetWorkParam;
    }

    public void setUtcsNetWorkParam(UtcsNetWorkParam utcsNetWorkParam) {
        this.utcsNetWorkParam = utcsNetWorkParam;
    }
}
