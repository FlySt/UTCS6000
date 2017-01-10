<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ncjk.utcs.common.jtp.*"%>
<%@ page import="java.io.File" %>
<%
	String path = request.getContextPath();
    String url = request.getRequestURI();
    String a = application.getRealPath("//");
    String b = new File(application.getRealPath(request.getRequestURI())).getParent();
    String c = application.getRealPath(request.getRequestURI());
    String e = System.getProperty("catalina.home");
%>
<!DOCTYPE html>
<html>
<head>
<title>JinKe'TCP Communication Test.</title>
<script src="common/js/jquery.js"></script>
<script type="text/javascript" >
    var url = "<%=e%>";
    function Lunch()
    {
        alert(url);
        var DlgWidth = UTCSOcx.OcxGetDlgWidth();
        var DlgHeight = UTCSOcx.OcxGetDlgHeight();

        document.all("UTCSOcx").style["width"]= DlgWidth ;
        document.all("UTCSOcx").style["height"]= DlgHeight;

        UTCSOcx.OcxSetTcpParam("192.168.200.244",5000);
        var res1 = UTCSOcx.OcxSetCfgParam("11111111111111111","文件大道与工业四路口",
                "配置.KANG","配置.why");
        if(res1 == 0)
            UTCSOcx.OcxLunch();
        else
            alert("设置参数失败");
    }
    function Test()
    {
        alert("测试方法");
    }
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td valign="top" width="150">

            <input type="button" id="Init" name="Init" value="启动" onClick="Lunch()" >
            <br />
            <input type="button" id="Test" name="Test" value="测试" onClick="Test()" >
            <br />
            <DIV id="wh" style="FONT-SIZE: 28pt; LINE-HEIGHT: 16px; FONT-FAMILY: 宋体;COLOR:Red"></DIV>
        </td>

        <td>
            <OBJECT ID="UTCSOcx" CLASSID="CLSID:641EAB80-11CE-41DB-A11C-6823980DF786" style="width:900px; height:750px; background-color: #FFFFFF;"></OBJECT>
        </td>
    </tr>
</table>
</body>
</html>