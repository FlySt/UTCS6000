<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<%--<html style="overflow: scroll">--%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信号机管理</title>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=path%>/modules/main.js"></script>
<script type="text/javascript">
var path = "<%=path%>";
	$(function() {
		tableHeight = document.documentElement.clientHeight - 145 + "px";//table高度适应不同屏幕分辨率
		tableInit();//表格初始化
		toolbarStatus();//按钮状态初始化
		readyDialog("dialog", 450, 400);//弹出框初始化  jquery ui dialog插件
		readyDialog("map_dialog", 600, 400);
		/*把单击行时，如果行处于没选中状态，则把样式设为选中状态
		 ，当中的复选框设为选中状态；否则，移除选中状态，取消选中状态 排除表头*/
		$("table ").on("click", "tr:gt(0)", function () {
			if ($(this).hasClass("selected")) {
				$(this).removeClass("selected")
						.find(":checkbox").removeAttr("checked");
				toolbarStatus();
			}
			else {
				$(this).addClass("selected")
						.find(":checkbox").prop("checked", true);
				toolbarStatus();
			}

		});
		//全选
		$("#checkAll").on("click", function () {
			if (this.checked) {
				$("tbody :checkbox").prop("checked", true);
				$("tbody tr").addClass("selected");
			} else {
				$("tbody :checkbox").removeAttr("checked");
				$("tbody tr").removeClass("selected");
			}
			toolbarStatus();

		});
		function toolbarStatus() {
			var num = $("input:checkbox:checked").size()
			if (num == 0) {
				$("#watch").addClass("gray").unbind("click", watch);
				$("#modify").addClass("gray").unbind("click", modify);
				$("#del").addClass("gray").unbind("click", del);
				$("#mapEdit").addClass("gray").unbind("click", mapEdit);
				$("#config").addClass("gray").unbind("click", config);

			} else if (num == 1) {
				$("#modify").removeClass("gray").bind("click", modify);
				$("#watch").removeClass("gray").bind("click", watch);
				$("#del").removeClass("gray").bind("click", del);
				$("#mapEdit").removeClass("gray").bind("click", mapEdit);
				$("#config").removeClass("gray").bind("click", config);
			} else {
				$("#watch").addClass("gray").unbind("click", watch);
				$("#del").removeClass("gray").bind("click", del);
				$("#modify").addClass("gray").unbind("click", modify);
				$("#mapEdit").addClass("gray").unbind("click", mapEdit);
				$("#config").addClass("gray").unbind("click", config);
			}

		/*	if(num==1){
				var signalControlerNum = table.row('.selected').data().signalControlerId;
				var crossName = table.row('.selected').data().crossName;
				$("#config1").attr("href","plugin.jsp?signalControlerNum="+signalControlerNum+"&crossName="+crossName);
			}else{
				console.log(num);
				$("#config1").attr("href","javascript:void(0)");
			}*/
		}

		//DataTables插件 具体参数意义可查看DataTables中文网
		function tableInit() {
			table = $('#dept').DataTable({
				"dom": 'rt<"bottom"iflp><"clear">',
				"pagingType": "full_numbers",
				//	"pageLength":5,
				"lengthChange": true,
				"lengthMenu": [10, 20, 50, 100],
				"searching": false,
				"scrollY": tableHeight,//"500px",
				"scrollCollapse": "true",
				"language": {
					"url": "../language.json",
				},
				"ajax": path + '/signalControler_queryAllSignalControlers.action',
				"columns": [
					{
						"data": "signalControlerId",
						"render": function (data, type, row) {
							return "<input type='checkbox' name='checklist' value='" + data + "'/>";
						},
						"orderable": false,
						"width": "5%"
					},
					{
						"data": "signalControlerName",
						"orderable": false,
						"width": "15%"
					},
					{
						"data": "signalControlerNum",
						"orderable": false,
						"width": "10%"
					},	{
						"data": "serverNo",
						"orderable": false,
						"width": "10%"
					},
					{
						"data": "crossName",
						"orderable": false,
                        "width": "15%"
					},
					{
						"data": "crossId",
						"orderable": false,
						"visible": false
					},
					{
						"data": "supplier",
						"orderable": false,
                        "visible": false
					},
					{
						"data": "type",
						"orderable": false,
						"width": "10%"
					},
					{
						"data": "protocolNum",
						"orderable": false,
                        "visible": false
					},
					{
						"data": "deviceIp",
						"orderable": false,
						"width": "10%"
					},
					{
						"data": "signalState",
						"render": function (data, type, row) {
							if(data=="异常故障"){
								return "<input style='color: red;background-color: transparent' value='"+data+"'/>";
							}else if(data=="脱机/断线"){
								return "<input style='color: blue;background-color: transparent' value='"+data+"'/>";
							}else{
								return "<input style='background-color: transparent' value='"+data+"'/>";
							}
						},
						"orderable": false,
						"visible": false
					},
					{
						"data": "error",
						"render": function (data, type, row) {
							if(data=="无错误"){
								return "<input style='background-color: transparent' value='"+data+"'/>";;
							}else{
								return "<input style='color: red;background-color: transparent' value='"+data+"'/>";
							}
						},
						"orderable": false,
						"visible": false
					},
                    {
                        "data": "longitude",
                        "orderable": false,
                        "visible": false
                    },
                    {
                        "data": "latitude",
                        "orderable": false,
                        "visible": false
                    },
					{
						"data": "mapSign",
						"orderable": false,
						"visible": false
					},
					{
						"data": "signalType",
						"render": function (data, type, row) {
							if(data==0){
								return "<input style='background-color: transparent' value='非国标信号机'/>";;
							}else{
								return "<input style='background-color: transparent' value='国标信号机'/>";
							}
						},
						"width": "20%",
						"orderable": false,
					}
				]
			});
		}
		$.fn.dataTable.ext.errMode = function(s,h,m){};
		$('#dept').on('page.dt', function () { //翻页后清除checkAll
			$("#checkAll").removeAttr("checked");
		});
		//查看
		function watch(){
			isWatch = true;
			var signalControlerId = table.row('.selected').data().signalControlerId;
			var config = {
				id:"dialog",
				title:"查看信号机",
				disabled:true,
				src:path+'/signalControler_modifySignalControler.action?signalControlerId='+signalControlerId,
				iframeId:"iframe_dialog"
			}
			showDialog(config);
		}
		//添加
		$("#add").click(function() {
			var config = {
				id:"dialog",
				title:"添加信号机",
				disabled:false,
				src:path+'/signalControler_modifySignalControler.action',
				iframeId:"iframe_dialog"
			};
			showDialog(config);
		});
		//修改编辑
		function modify(){
			var signalControlerId = table.row('.selected').data().signalControlerId;
			var config = {
				id:"dialog",
				title:"编辑信号机",
				disabled:false,
				src:path+'/signalControler_modifySignalControler.action?signalControlerId='+signalControlerId,
				iframeId:"iframe_dialog"
			};
			showDialog(config);
		}
		//删除
		function del(){
			var ids = [];
			ids = getIds();
			var names = getNames();
			dialog.confirm("确认删除选中的信号机？","信号机删除",function(){
				$.ajax( {
					url:path+'/signalControler_delSignalControlers.action',
					type:'post',
					cache:false,
					dataType:'json',
					traditional : true,
					data:{
						ids:ids,
						names:names
					},
					success:function(response) {
						if(response.result){
							table.ajax.reload(null,false);
							$("#checkAll").removeAttr("checked");
							dialog.message("删除成功");
						}else{
							dialog.message("删除失败");
						}
					},
					error : function() {
						dialog.message("删除失败");
					}
				});
			});
		}
		//搜索
		$("#search").click(function(){
			var signalControlerName = $("#searchinput").val();
			var url = path+'/signalControler_queryAllSignalControlers.action?signalControlerName='+signalControlerName;
            url = encodeURI(url);
			table.ajax.url(url).load();
		});
		function getIds(){
			var ids = [];
			table.rows('.selected').every(function(){//遍历被选中的所有的行
				var signalControlerId = this.data().signalControlerId;
				ids.push(signalControlerId);
			});
			return ids;
		}
		function getNames(){
			var names = "";
			table.rows('.selected').every(function(){//遍历被选中的所有的行
				var name = this.data().signalControlerName;
				names += name;
			});
			return names;
		}
        function mapEdit(){
            var signalControlerId = table.row('.selected').data().signalControlerId;
			var signalControlerName = table.row('.selected').data().signalControlerName;
            var longitude = table.row('.selected').data().longitude;
            var latitude = table.row('.selected').data().latitude;
			var mapSign = table.row('.selected').data().mapSign;
            var param = "?id=signal_"+signalControlerId+"&name="+signalControlerName+"&longitude="+longitude+"&latitude="+latitude+"&mapSign="+mapSign;
            var config = {
                id:"map_dialog",
                title:"电子地图编辑",
                disabled:false,
                src:path+'/modules/gis/map.jsp'+param,
                iframeId:"iframe_map"
            };
            showDialog(config);
			var help = document.getElementById("map_help");
			console.log(help);
			if(help==null){
				var $tip = $("<span id='map_help' class='help' style='position: absolute;color:red;margin:10px;'>*单击鼠标右键添加或删除信号机</span>");
				$('.ui-dialog-buttonpane').append( $tip);
			}else{
				$('.ui-dialog-buttonpane .help').html("*单击鼠标右键添加或删除信号机");
			}

        }
		function config(){
			var signalType = table.row('.selected').data().signalType;
			if(signalType==0){//非国标信号机
			//	dialog.message("请先安装非国标信号机控件");
				var crossId = table.row('.selected').data().crossId;
				var signalControlerName = table.row('.selected').data().signalControlerName;
				var v=document.getElementById("showTable");
				var result=v.OpenBasicConfigUI(signalControlerName,crossId);
			}else{//国标信号机
				var signalControlerNum = table.row('.selected').data().signalControlerNum;
				var crossName = table.row('.selected').data().crossName;
				try{
					UTCSOcx.OcxGetDlgWidth();
				}catch (e){
					dialog.message("请先安装国标信号机控件");
					return;
				}
				getPluginParam(signalControlerNum);
				UTCSOcx.OcxSetFuncParam(1);
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
			}
		}
	});
	function mapDialogClose(){
		$("#map_dialog").dialog("close");
		table.ajax.reload(null,false);
	}
	function dialogClose(){
		$("#dialog").dialog("close");
		table.ajax.reload(null,false);
		dialog.message("保存成功");
		getSignalDevice()();
	}
function getSignalDevice(){
	$.ajax( {
		url:path+'/signal_initSignalActiveX.action',
		type:'post',
		cache:false,
		dataType:'text',
		success:function(data) {
			var v=document.getElementById("JKCommon");
			if(v){
				var result=v.Start(data);
			}
		},
		error : function() {
			alert("异常！");
		}
	});
}
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
<%--<body style="overflow: scroll">--%>
<body>
	<div class="place">
		<input id="powerModuleId" value="${param.powerModuleId }" type="hidden" />
		<input id="level" value="${param.level }" type="hidden" />
		<span>位置：</span>
		<ul class="placeul">
			<li>资源管理</li>
			<li>资源管理</li>
			<li>信号机管理</li>
		</ul>
	</div>
	<div id="rightinfo" class="rightinfo">
		<div class="tools">
			<ul class="toolbar">
				<li id="watch" ><span><img src="../../common/images/t00.png" /></span>查看</li>
				<li id="add" ><span><img src="../../common/images/t01.png" /></span>添加</li>
				<li id="modify" ><span><img src="../../common/images/t02.png" /></span>修改</li>
				<li id="del"> <span><img src="../../common/images/t03.png" /></span>删除</li>
                <li id="mapEdit" ><span><img src="../../common/images/t02.png" /></span>电子地图编辑</li>

			</ul>

			<ul class="toolbar1">
				<li style="padding-right: 0px;"><input style="width: 320px; margin-left: 0px;" id="searchinput" name="searchinput" type="text" class="searchinput" placeholder="请输入信号机名称进行查询" /></li>
				<li id="search"><span><img src="../../common/images/t05.png" /></span>搜索</li>
			</ul>
			<ul class="toolbar1">
				<li id="config" ><span><img src="../../common/images/t02.png" /></span>信号机配置</li>
			</ul>
			<%--<div>
				<a href="plugin.jsp">配置</a>
			</div>--%>
		</div>
		<table  id="dept" class="tablelist">
			<thead>
			<tr>
				<th><input id="checkAll" type="checkbox"></th>
				<th>信号机名称</th>
				<th>信号机编号</th>
				<th>所属服务器</th>
				<th>路口</th>
				<th style="display: none">路口ID</th>
				<th style="display: none">供应商</th>
				<th>规格型号</th>
				<th style="display: none">协议号</th>
				<th>IP地址</th>
				<th>信号机状态</th>
				<th>错误值</th>
				<th>经度</th>
				<th>纬度</th>
				<th>地图标注状态</th>
				<th>信号机种类</th>
			</tr>
			</thead>
		</table>
	</div>
	<div id="dialog">
		<iframe id="iframe_dialog" name="iframe_dialog"  style="position:absolute; scrolling:no;overflow: hidden; height: 100%;width:100%" class="dialog-iframe" src="" ></iframe>
	</div>
	<div id="map_dialog" >
		<iframe  src="" id="iframe_map" style="position:absolute; scrolling:no;overflow: hidden; height: 100%;width:100%"  ></iframe>
	</div>
	<div id="gridContainer" style="width:100%;height: 90%;">
		<div>
			<object id="showTable" classid="clsid:96004938-def8-498d-a1fc-6a8c4328a191" style="height: 90%"> </object>
		</div>
	</div>
	<div style="display: none">
		<object id="JKCommon" classid="clsid:78A94A2B-CAEE-4419-B2B4-6E9E1571244A"> </object>
	</div>
	<div style="width:800px; height:750px;">
		<OBJECT ID="UTCSOcx" CLASSID="CLSID:641EAB80-11CE-41DB-A11C-6823980DF786" style="width:800px; height:750px; background-color: #FFFFFF;"></OBJECT>
	</div>
</body>
</html>