<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>非国标信号机管理</title>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyleEx.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/modules/main.js"></script>
<script type="text/javascript">
var type = "deviceManager"
var path = "<%=path%>";
	$(function() {
		$("#tabs").tabs()
		$(".signal").on("click",function(){
			type = $(this).attr('id');
			var treeObj = $.fn.zTree.getZTreeObj("crossTree");
			var nodes = treeObj.getSelectedNodes();
			if(nodes[0].id.indexOf("cross_")==0){
				signalDeviceList(0,nodes[0].id.split("_")[1],0,type);
			}else if(nodes[0].id.indexOf("region_")==0){
				signalDeviceList(nodes[0].id.split("_")[1],0,0,type);
			}
		})
		var cross_setting =
		{
			selectFirstNode : true,
			data :
			{
				simpleData :
				{
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : -1
				}
			},
			async :
			{
				enable : true,
				url:path+"/treeResource_getCrossParamTrees.action"
			},
			callback :
			{
				// zTree单击事件
				onClick : function(event, treeId, treeNode)
				{
					if(treeNode.id.indexOf("cross_")==0){
						signalDeviceList(0,treeNode.id.split("_")[1],0,type);
					}else if(treeNode.id.indexOf("region_")==0){
						signalDeviceList(treeNode.id.split("_")[1],0,0,type);
					}
				},
				// zTree加载完成事件
				onAsyncSuccess : function()
				{
					var treeObj = $.fn.zTree.getZTreeObj("crossTree");
					var nodes = treeObj.getNodes();
					if (nodes.length>0) {
						treeObj.selectNode(nodes[0]);
						if(nodes[0].id.indexOf("region_")==0){
							var regionId = nodes[0].id.split("_")[1]
							signalDeviceList(regionId,0,0,type);
						}
					}
				}
			}
		};
		cross_tree = $.fn.zTree.init($("#crossTree"), cross_setting);

	});
	function signalDeviceList(regionId,crossId,signalId,type){
		$.ajax( {
			url:path+'/signal_queryAllSignalDevices.action',
			type:'get',
			cache:false,
			data:{regionId:regionId,crossId:crossId,signalId:signalId},
			dataType:'text',
			success:function(data) {
				var width =$("#gridContainer").width();
				var height =$("#gridContainer").height();
				var v=document.getElementById("showTable");
				var result=v.OpenUI(type,data,width,height);
			},
			error : function() {
				dialog.message("异常！");
			}
		});
	}
</script>
</head>
<body>
	<div class="place">
		<input id="powerModuleId" value="${param.powerModuleId }" type="hidden" />
		<input id="level" value="${param.level }" type="hidden" />
		<span>位置：</span>
		<ul class="placeul">
			<li>资源管理</li>
			<li>资源管理</li>
			<li>非国标信号机管理</li>
		</ul>
	</div>
	<div id="rightinfo" class="rightinfo" style="height: 100%">

		<div style="width: 15%;float: left;height: 100%;border: 1px solid royalblue">
			<div >
				<ul id="crossTree" class="ztree"></ul>
				<input id="crossId" style="display: none">
			</div>
		</div>

		<div style="float: left;height: 100%;width: 83%" >
			<div id="tabs" style="padding: 0px;width:100%;height: 100%" >
				<ul>
					<li id="deviceManager" class="signal"><a href="#gridContainer"><span>信号机管理</span></a></li>
					<li id="realTime" class="signal"><a href="#gridContainer"><span>信号机实时信息</span></a></li>
					<li id="signalLog" class="signal"><a href="#gridContainer"><span>信号机日志查询</span></a></li>
					<li id="schemeSet" class="signal"><a href="#gridContainer"><span>方案配置</span></a></li>
					<li id="greenConflick" class="signal"><a href="#gridContainer"><span>绿冲突配置</span></a></li>
					<li id="specialLightSet" class="signal"><a href="#gridContainer"><span>特殊灯色</span></a></li>
					<li id="downLevelSet" class="signal"><a href="#gridContainer"><span>降级配置</span></a></li>
					<li id="carDetectionSet" class="signal"><a href="#gridContainer"><span>车检器配置</span></a></li>
					<li id="lightPanelSet" class="signal"><a href="#gridContainer"><span>灯驱板配置</span></a></li>
					<li id="followSet" class="signal"><a href="#gridContainer"><span>跟随表配置</span></a></li>
					<li id="checkTime" class="signal"><a href="#gridContainer"><span>人工校时</span></a></li>
				</ul>
				<div id="gridContainer" style="width:100%;height: 90%;">
					<div>
						<object id="showTable" classid="clsid:4AF1D819-B51B-4B4C-B3EA-333B733ABB4E" style="height: 90%"> </object>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>