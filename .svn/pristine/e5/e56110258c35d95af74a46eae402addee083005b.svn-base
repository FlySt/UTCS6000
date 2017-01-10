package com.ncjk.utcs.modules.system.action;

import com.ncjk.utcs.modules.system.services.interfaces.IPluginParamService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by swl on 2016/11/30.
 */
@Scope("prototype")
@Controller("pluginParamAction")
public class PluginParamAction {

    @Resource
    private IPluginParamService pluginParamService;
    /**配置文件名，一个信号机对应一个唯一配置文件**/
    private String fileName;

    public void getPluginParam(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset/utf8");
        JSONObject jsonObject = pluginParamService.getPluginParam(fileName);
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
