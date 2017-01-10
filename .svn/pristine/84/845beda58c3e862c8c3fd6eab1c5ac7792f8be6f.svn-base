<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML >
<html>
  <head>
    <base href="<%=basePath%>">
    <title>session失效</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
	<style type="text/css">
	</style>
	<script type="text/javascript">
	   var path = "<%=path%>";
		window.onload= function(){
			dialog.error("页面失效！请重新登录",function(){
					top.location = path ;
				});
			return false;
		}
	</script>
	</head>
	<body>
	</body>
</html>