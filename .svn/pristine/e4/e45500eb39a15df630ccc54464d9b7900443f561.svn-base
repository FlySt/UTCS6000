<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<%--<!DOCTYPE html>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信号机配置</title>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript">
var path = "<%=path%>";
var serverIp;
var ftpUser;
var ftpPwd;
var ftpPort;
var tcpPort;
    $(function(){
        try{
            UTCSOcx.OcxGetDlgWidth();
        }catch (e){
            dialog.message("请先安装国标信号机控件");
            return;
        }
        var crossName = "${param.crossName}";
        var signalControlerNum = "${param.signalControlerNum}";
        var flag = "${param.flag}";
        getPluginParam(signalControlerNum);
        UTCSOcx.OcxSetFuncParam(flag);
        var DlgWidth = UTCSOcx.OcxGetDlgWidth();
        var DlgHeight = UTCSOcx.OcxGetDlgHeight();
        document.all("UTCSOcx").style["width"]= DlgWidth ;
        document.all("UTCSOcx").style["height"]= DlgHeight;
        UTCSOcx.OcxSetFtpParam(serverIp,ftpPort,ftpUser,ftpPwd);
        UTCSOcx.OcxSetTcpParam(serverIp,tcpPort);
        var res1 = UTCSOcx.OcxSetCfgParam(signalControlerNum,crossName,signalControlerNum+".KANG","配置.why");
        if(res1 == 0){
            UTCSOcx.OcxLunch();
        }
        else
            alert("设置参数失败");
    });
function getPluginParam(signalControlerNum){
    $.ajax({
        url:path+'/pluginParam_getPluginParam.action',
        dataType:'json',
        type:'post',
        async:false,
        data:{
            "fileName":signalControlerNum+".KANG",
        },
        success:function(response){
            serverIp = response.serverIp;
            ftpUser = response.ftpUser;
            ftpPwd = response.ftpPwd;
            ftpPort = response.ftpPort;
            tcpPort = response.tcpPort;
        }
    })
}
</script>
</head>
<body>
<div style="width:800px; height:750px;">
    <OBJECT ID="UTCSOcx" CLASSID="CLSID:641EAB80-11CE-41DB-A11C-6823980DF786" style="width:800px; height:750px; background-color: #FFFFFF;"></OBJECT>
</div>
</body>
</html>
