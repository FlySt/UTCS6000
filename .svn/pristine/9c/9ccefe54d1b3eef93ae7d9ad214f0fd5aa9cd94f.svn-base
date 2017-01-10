<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
var path = "<%=path%>";
</script>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyleEx.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
var zTreeModule;

$(function(){
	
	function zTreeOnAsyncSuccess(){
			var userIds = $("#ids").val();
			if(userIds){	
				var ids = userIds.split(",");
				for(var i=0;i<ids.length;i++){
					var nodeId = "user_"+ids[i];
					console.log(nodeId);
					var node = zTreeModule.getNodeByParam("id", nodeId, null);
					zTreeModule.checkNode(node, true, true);
				}	
			}
	}
	var module_setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : -1
				}
			},
			async :
			{
				enable : true,
				url:path+"/treeResource_getOrganizationTrees().action?type=-1&deptLevel=10",
			},
			callback: {
				onAsyncSuccess: zTreeOnAsyncSuccess
			}
		};
	
	zTreeModule = $.fn.zTree.init($("#treeModule"), module_setting);
});
</script>
</head>

<body>
  	<input id="ids" value="${param.ids}" type="hidden"/>
	<div style="overflow: auto;position: absolute;width:100%;bottom: 0px;top: 0px;">
		<ul id="treeModule" class="ztree" ></ul>
	</div>

	<script type="text/javascript">
		function clear(){
		}
		var ids = "";
		var accounts = "";
		var names = "";
		function getNodes(){
			var nodes = zTreeModule.getCheckedNodes(true);
			for(var i=0;i<nodes.length;i++){
				if(!nodes[i].isParent){//不是父节点
					id = nodes[i].id;
					name = nodes[i].name;
					
					ids = ids+","+id.substring(id.indexOf("_")+1,id.length);					
					accounts = accounts +","+ name.substring(name.indexOf("[")+1,name.length-1);								
					names = names+","+name;					
				}

			}
			ids = ids.substring(1);
			accounts = accounts.substring(1);
			names = names.substring(1);
		}
		function dialogSubmit(){
			 getNodes() ;
			 window.parent.dialogClose(ids,accounts,names);
		}
	</script>
</body>

</html>