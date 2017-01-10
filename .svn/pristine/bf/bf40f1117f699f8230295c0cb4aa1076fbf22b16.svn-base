package com.ncjk.utcs.modules.common.action;

import com.ncjk.utcs.common.netty.client.NettyClientService;
import com.ncjk.utcs.common.netty.server.NettyService;
import com.ncjk.utcs.common.util.FileUtil;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by swl on 2016/9/22.
 */
@Scope("prototype")
@Controller("jtpServerAction")
public class JtpServerAction extends ActionSupport{
    @Resource
    NettyService nettyService;
    private String  content;
    private Integer state;
    private boolean isOut;
    private String xml;
    private String flag;
    private String msg;
    //测试所需要的
    @Resource
    NettyClientService nettyClientService;

    public void changePingState(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        //   JtpServer jtp = JtpServer.getInstance();
        //  String result = jtp.Send("0.0.0.0", cmdContent);
        nettyClientService.changePingState(state);
        JSONObject js = new JSONObject();
        js.put("result","ok");
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void timer(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        //   JtpServer jtp = JtpServer.getInstance();
        //  String result = jtp.Send("0.0.0.0", cmdContent);
        String result = nettyClientService.timer();
        JSONObject js = new JSONObject();
        js.put("result",result);
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logout(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        nettyClientService.logout();
        JSONObject js = new JSONObject();
        js.put("result","ok");
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changeSysout(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        //   JtpServer jtp = JtpServer.getInstance();
        //  String result = jtp.Send("0.0.0.0", cmdContent);
        JSONObject js = new JSONObject();
        js.put("result","ok");
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ocxSend(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        //   JtpServer jtp = JtpServer.getInstance();
        //  String result = jtp.Send("0.0.0.0", cmdContent);
        nettyClientService.send(msg);
        JSONObject js = new JSONObject();
        js.put("result","ok");
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void down(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        FileUtil.fileDownload("E://demo");
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
