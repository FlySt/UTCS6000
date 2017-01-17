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
<script src="<%=path%>/common/js/ol3/ol.js"></script>
<script type="text/javascript" src="<%=path%>/modules/gis/drawicon.js"></script>
<script type="text/javascript" src="gisInit.js"></script>
<script type="text/javascript" src="element.js"></script>
<script type="text/javascript" src="signalApp.js"></script>

<script type="text/javascript">
var type ;
var path = "<%=path%>";
var map;
var layersName = "shanggai";
var ipAddr = "192.168.200.244";
var port = 8080;
var lon = 115.81451;
var lat = 28.69705;

var container ;
var content ;
var title ;
var overlay ;
var closer;

var serverIp;
var ftpUser;
var ftpPwd;
var ftpPort;
var tcpPort;
	$(function() {
		gisInit();
		elementInit();
		popupInit();
		$(".signal").on("click",function(){
			type = $(this).attr('id');
			var treeObj = $.fn.zTree.getZTreeObj("crossTree");
			var nodes = treeObj.getSelectedNodes();
			if(nodes[0].id.indexOf("signal_")==0){
				var v=document.getElementById("showTable");
				var result ;
				if(type=="signalLog"){
					result = v.OpenSignalLogUI(nodes[0].name);
				}else if(type=="greenConflick"){
					result = v.OpenGreenConflickUI(nodes[0].name);
				}else if(type=="specialLightSet"){
					result = v.OpenSpecialLightSetUI(nodes[0].name);
				}else if(type=="downLevelSet"){
					result = v.OpenDownLevelSetUI(nodes[0].name);
				}else if(type=="carDetectionSet"){
					result = v.OpenCarDetectionSetUI(nodes[0].name);
				}else if(type=="lightPanelSet"){
					result = v.OpenLightPanelSetUI(nodes[0].name);
				} else if(type=="followSet"){
					result = v.OpenFollowSetUI(nodes[0].name);
				}else if(type=="checkTime"){
					result = v.OpenCheckTimeUI(nodes[0].name);
				}
			}else{
				dialog.message("请先选择信号机");
			}
		});
		$(".config").on("click",function(){
			var treeObj = $.fn.zTree.getZTreeObj("crossTree");
			var nodes = treeObj.getSelectedNodes();
			if(nodes[0].id.indexOf("signal_")==0){
				try{
					UTCSOcx.OcxGetDlgWidth();
				}catch (e){
					dialog.message("请先安装国标信号机控件");
					return;
				}
				var treeObj = $.fn.zTree.getZTreeObj("crossTree");
				var nodes = treeObj.getSelectedNodes();
				var signalControlerNum = nodes[0].signalControlerNum;
				var crossName = nodes[0].getParentNode().name;
				getPluginParam(signalControlerNum);
				UTCSOcx.OcxSetFuncParam(2);
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
					dialog.message("设置参数失败");
			}else{
				dialog.message("请先选择信号机");
			}
		});
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
				url:path+"/treeResource_getSignalControlerTrees.action"
			},
			callback :
			{
				// zTree单击事件
				onClick : function(event, treeId, treeNode)
				{
					signalInfo(treeNode);
					var lan = parseFloat(treeNode.lon);
					var lat = parseFloat(treeNode.lat);
					if(lan!=null && !isNaN(lan) && lan!=undefined && lat!=null && !isNaN(lat) && lat!=undefined){
						var coordinate = [lan,lat];
						map.getView().setCenter(coordinate);
						popup(coordinate);
					}
				},
				// zTree加载完成事件
				onAsyncSuccess : function()
				{
					var treeObj = $.fn.zTree.getZTreeObj("crossTree");
					treeObj.expandAll(true);
					var nodes = treeObj.getNodes();
					if (nodes.length>0) {
						treeObj.selectNode(nodes[0]);
					}
				}
			}
		};
		cross_tree = $.fn.zTree.init($("#crossTree"), cross_setting);

	});
</script>
</head>
<body>
	<div class="place">
		<input id="powerModuleId" value="${param.powerModuleId }" type="hidden" />
		<input id="level" value="${param.level }" type="hidden" />
		<span>位置：</span>
		<ul class="placeul">
			<li>信号机应用</li>
		</ul>
	</div>
	<div id="rightinfo" class="rightinfo" style="height: 100%">

		<div style="width: 200px;float: left;height: 100%;border: 1px solid royalblue">
			<div >
				<ul id="crossTree" class="ztree"></ul>
				<input id="crossId" style="display: none">
			</div>
		</div>
		<div style="position: absolute;left:200px;top: 40px;right:0;bottom: 0" >
			<div id="tabs" class="ui-tabs ui-corner-all ui-widget ui-widget-content" style="padding: 0;width:100%;height: 40px" >
				<ul class="ui-corner-all ui-helper-reset ui-helper-clearfix ui-widget-header" style="height: 40px;">
					<li id="signalLog" class="signal ui-button"><a class="ui-tabs-anchor" href="#"><span>信号机日志查询</span></a></li>
					<li id="greenConflick" class="signal ui-button"><a class="ui-tabs-anchor" href="#"><span>绿冲突配置</span></a></li>
					<li id="specialLightSet" class="signal ui-button"><a class="ui-tabs-anchor" href="#"><span>特殊灯色</span></a></li>
					<li id="downLevelSet" class="signal ui-button"><a class="ui-tabs-anchor" href="#"><span>降级配置</span></a></li>
					<li id="carDetectionSet" class="signal ui-button " ><a class="ui-tabs-anchor" href="#"><span>车检器配置</span></a></li>
					<li id="lightPanelSet" class="signal ui-button " ><a class="ui-tabs-anchor" href="#"><span>灯驱板配置</span></a></li>
					<li id="followSet" class="signal ui-button"><a class="ui-tabs-anchor" href="#"><span>跟随表配置</span></a></li>
					<li id="checkTime" class="signal ui-button"><a class="ui-tabs-anchor" href="#"><span>人工校时</span></a></li>
					<li style="display: none" id="config" class="config ui-button"><a class="ui-tabs-anchor" href="#"><span>信号机配置</span></a></li>

					<li class="app-li" style="position: absolute;top:15px;width:180px;right: 290px;">
						<label for="signalState">信号机种类:</label>
						<input  id="signalType" value="非国标信号机" readonly="readonly"></li>
					<li class="app-li" style="position: absolute;top:15px;width:150px;right: 140px;">
						<label  for="signalState">规格型号:</label>
						<input  id="type" value="JK-XT-100" readonly="readonly"></li>
					<li class="app-li" style="position: absolute;top:15px;width:100px;right: 30px;">
						<label  for="signalState">信号机状态:</label>
						<input  id="signalState" value="在线" readonly="readonly"></li>

				</ul>
			</div>
			<div style="clear: both" id="appMap" class="appMap">
				<div id="map">
				</div>
			</div>
		</div>
		<div id="popup" class="ol-popup" style="width: 250px">
			<a href="#" id="popup-closer" class="ol-popup-closer"></a>
			<div id="popup-title" class="popup-title"></div>
			<div id="popup-content" class="popup-content">
				<form class="info">
					<div>
						<label for="signalControlerNum">信号机编号:</label>
						<input readonly="readonly" id="signalControlerNum" >
					</div>
					<div>
						<label for="deviceIp">信号机IP地址:</label>
						<input readonly="readonly" id="deviceIp">
					</div>
					<div>
						<label for="crossName">所属路口:</label>
						<input readonly="readonly" id="crossName">
					</div>
					<div>
						<label for="serverNo">所属服务器:</label>
						<input readonly="readonly" id="serverNo">
					</div>
					<div>
						<label for="typeF">规格型号:</label>
						<input readonly="readonly" id="typeF">
					</div>
					<div>
						<label for="signalStateF">信号机状态:</label>
						<input readonly="readonly" id="signalStateF">
					</div>
				</form>
				<input style="display:none" id="featureId"/>
			</div>
		</div>
		<div id="gridContainer" style="width:100%;height: 90%;">
			<div>
				<object id="showTable" classid="clsid:96004938-def8-498d-a1fc-6a8c4328a191" style="height: 90%"> </object>
			</div>
		</div>
		<div style="width:800px; height:750px;">
			<OBJECT ID="UTCSOcx" CLASSID="CLSID:641EAB80-11CE-41DB-A11C-6823980DF786" style="width:800px; height:750px; background-color: #FFFFFF;"></OBJECT>
		</div>
	</div>
</body>
</html>