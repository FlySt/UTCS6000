<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<%--<!DOCTYPE html>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实时信息</title>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript">
var path = "<%=path%>";
$(function() {
    var signalId = "${param.signalId}";
    var signalName = "${param.signalName}";
    var width =$("#gridContainer").width();
    var height =$("#gridContainer").height();
    var v=document.getElementById("showTable");
    var result=v.OpenUI(signalName,width,height);
})
</script>
</head>
<body>
<div id="gridContainer" style="width:100%;height: 90%;">
    <div>
        <object id="showTable" classid="clsid:E8CF7AFE-5F8C-43EE-BA5D-E297B6ED591D" style="height: 90%"> </object>
    </div>
</div>
</body>
</html>
