<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务器配置</title>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.validate.min.all.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=path%>/modules/main.js"></script>
<script type="text/javascript">
var path = "<%=path%>";
var table;
var tableHeight;
	$(function() {
		tableHeight = document.documentElement.clientHeight - 145 + "px";//table高度适应不同屏幕分辨率
		tableInit();//表格初始化
		toolbarStatus();//按钮状态初始化
		readyDialog("dialog", 450, 400);//弹出框初始化  jquery ui dialog插件
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
			} else if (num == 1) {
				$("#modify").removeClass("gray").bind("click", modify);
				$("#watch").removeClass("gray").bind("click", watch);
				$("#del").removeClass("gray").bind("click", del);
			} else {
				$("#watch").addClass("gray").unbind("click", watch);
				$("#del").removeClass("gray").bind("click", del);
				$("#modify").addClass("gray").unbind("click", modify);
			}
		}

		//DataTables插件 具体参数意义可查看DataTables中文网
		function tableInit() {
			table = $('#server').DataTable({
				"dom": 'rt<"bottom"iflp><"clear">',
				"pagingType": "full_numbers",
				"lengthChange": true,
				"lengthMenu": [10, 20, 50, 100],
				"searching": false,
				"scrollY": tableHeight,
				"scrollCollapse": "true",
				"language": {
					"url": "../language.json",
				},
				"ajax": path + '/server_findServerParams.action',
				"columns": [
					{
						"data": "serverId",
						"render": function (data, type, row) {
							return "<input type='checkbox' name='checklist' value='" + data + "'/>";
						},
						"orderable": false,
						"width": "20%"
					},
					{
						"data": "serverNo",
						"orderable": false,
						"width": "25%"
					},
					{
						"data": "serverIP",
						"orderable": false,
						"width": "25%"
					},
					{
						"data": "isCenter",
						"render": function (data, type, row) {
							if(data == 0){
								return "<input style='background-color: transparent ' value='中心服务器'/>";
							}else{
								return "<input style='background-color: transparent ' value='区域服务器'/>";
							}
						},
						"orderable": false,
						"width": "30%"
					}
				]
			});
		}

		$.fn.dataTable.ext.errMode = function (s, h, m) {
		};
		$('#dept').on('page.dt', function () { //翻页后清除checkAll
			$("#checkAll").removeAttr("checked");
		});
		//查看
		function watch(){
			isWatch = true;
			var serverId = table.row('.selected').data().serverId;
			var config = {
				id:"dialog",
				title:"查看服务器",
				disabled:true,
				src:path+'/server_modifyServer.action?serverId='+serverId,
				iframeId:"iframe_dialog"
			}
			showDialog(config);
		}
		//添加
		$("#add").click(function() {
			var config = {
				id:"dialog",
				title:"添加服务器",
				disabled:false,
				src:path+'/server_modifyServer.action',
				iframeId:"iframe_dialog"
			}
			showDialog(config);
		});
		//修改编辑
		function modify(){
			var serverId = table.row('.selected').data().serverId;
			var config = {
				id:"dialog",
				title:"编辑服务器",
				disabled:false,
				src:path+'/server_modifyServer.action?serverId='+serverId,
				iframeId:"iframe_dialog"
			}
			showDialog(config);
		}
		//删除
		function del(){
			var ids = [];
			ids = getIds();
			var nos = getNos();
			dialog.confirm("确认删除选中的服务器？","服务器删除",function(){
				$.ajax( {
					url:path+'/server_delServers.action',
					type:'post',
					cache:false,
					dataType:'json',
					traditional : true,
					data:{
						ids:ids,
						nos:nos
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
		function getIds(){
			var ids = [];
			table.rows('.selected').every(function(){//遍历被选中的所有的行
				var serverId = this.data().serverId;
				ids.push(serverId);
			});
			return ids;
		}
		function getNos(){
			var nos = "";
			table.rows('.selected').every(function(){//遍历被选中的所有的行
				var no = this.data().serverNo;
				nos += no;
			});
			return nos;
		}
	})
function dialogClose(){
	$("#dialog").dialog("close");
	table.ajax.reload(null,false);
	dialog.message("保存成功");
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
			<li>基础信息</li>
			<li>服务器配置</li>
		</ul>
	</div>
	<div id="rightinfo" class="rightinfo">
		<div class="tools">
			<ul class="toolbar">
				<li id="watch" ><span><img src="../../common/images/t00.png" /></span>查看</li>
				<li id="add" ><span><img src="../../common/images/t01.png" /></span>添加</li>
				<li id="modify" ><span><img src="../../common/images/t02.png" /></span>修改</li>
				<li id="del"> <span><img src="../../common/images/t03.png" /></span>删除</li>
			</ul>
		</div>
		<table  id="server" class="tablelist">
			<thead>
			<tr>
				<th><input id="checkAll" type="checkbox"></th>
				<th>服务器编号</th>
				<th>服务器IP地址</th>
				<th>服务器类型</th>
			</tr>
			</thead>
		</table>
	</div>
	<div id="dialog">
		<iframe id="iframe_dialog" name="iframe_dialog"  style="position:absolute; scrolling:no;overflow: hidden; height: 100%;width:100%" class="dialog-iframe" src="" ></iframe>
	</div>
</body>
</html>