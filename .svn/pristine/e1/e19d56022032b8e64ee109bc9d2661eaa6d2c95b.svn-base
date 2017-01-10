<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
   String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统操作日志</title>
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/datepicker.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/modules/main.js"></script> 
<script type="text/javascript">
	var path = "<%=path%>" ;
	var tableHeight;
	$(function(){
		tableHeight = document.documentElement.clientHeight-145+"px";//table高度适应不同屏幕分辨率
		readyDialog("dialog",400,500);//弹出框初始化  jquery ui dialog插件
		tableInit();
		$("#condition").on("click","#userName",function(){
			var config = { 
					id:"dialog",
					title:"操作人员",
					disabled:false,
					src:path+'/modules/common/userElement.jsp?ids='+$("#userId").val(),
				    iframeId:"iframe_dialog"			
				}
			showDialog(config);	
		})
		$("#search").on("click",function(){
		    var param = {
    	        	 userAccount:$("#userAccount").val(),
    	        	 loginIpaddr:$("#loginIpaddr").val(),
    	        	 startDate:$("#startDate").val(),
    	             endDate:$("#endDate").val()
		    };
		    table.settings()[0].ajax.data = param;
		    table.ajax.reload();
		})
		//DataTables插件 具体参数意义可查看DataTables中文网
		function tableInit(){ 
	     table=$('#log').DataTable({
	    		"dom": 'rt<"bottom"iflp><"clear">',
	    		"pagingType":   "full_numbers",
	    		"lengthChange":true,
	    		"lengthMenu":[10,20,50,100],
	    		"searching":false,
	    		"scrollY": tableHeight,//"500px",
                	"scrollCollapse": "true",
			"serverSide": true,
	    	    "language": {
	    		   		"url": "../language.json",
	    	     },
	    	     "ajax":{
	    	         url: path+'/log_queryAllLogs.action',
 	    	         data:{
	    	        	 startDate:$("#startDate").val(),
	    	             endDate:$("#endDate").val()
	    	         } 
	    	     },
			//	"ajax": path+'/log_queryAllLogs.action',
				"columns": [

				            { "data": "index",/* "logId", */
				               "render": function ( data, type, row , meta) {
				        			    /* return "<input type='text'  value='"+meta.row+"'/>"; */
									   var page = table.page.info();
									   //当前第几页，从0开始
									   var pageno = page.page;
									   //每页数据
									   var length = page.length;
								       var number = meta.row+pageno*length+1;
				        			    return "<label>"+number+"</label>";
				               }, 
				               "orderable":false,
				               "width":"5%"
							},
				            { "data": "logId", 
			            	   "visible":false,
				               "orderable":false,
				               "width":"5%"
							}, 
				            { "data": "userAccount",
							  "orderable":false,
							  "width":"15%"},
				            { "data": "userName",
							  "orderable":false,
							  "width":"15%"},
				            { "data": "moduleName",
							  "orderable":false	,
							  "width":"15%"},
				            { "data": "logContent",
							  "orderable":false ,
							  "width":"20%"},
				            { "data": "loginIpaddr",
							  "orderable":false	,
							  "width":"10%"},				            
				            { "data": "logTime",
							  "orderable":false	,
							  "width":"20%"},
				        ]
			});  
		} 
	})
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li>综合查询</li>
			<li>系统操作日志</li>
		</ul>
	</div>
	<div>
		<div class="condition-header">
			<div id="condition" class="condition">
				<label for="userName">操作人员:</label>
				<input id="userName"/>
				<input style="display: none" id="userId"/>
				<input style="display: none" id="userAccount"/>
				
				<label for="loginIpaddr">登录IP：</label>
				<input id="loginIpaddr"/>
				
				<label for="startDate">开始时间：</label>
				<input id="startDate" class="datepicker" type="text" value="<%=(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()-10*24*60*60*1000L)))+" "+"00:00:00" %>"/>
				 			
				<label for="endDate">结束时间：</label>
				<input id="endDate" class="datepicker" type="text" value="<%=(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))+" "+"23:59:59" %>"/>
			</div>
			<div class="inquiry">
				<input id="search" class="ui-button button" type="button" value="查询"/>
			</div>
		</div>
		<div id="dialog">
	 		<iframe id="iframe_dialog" name="iframe_dialog"  style="position:absolute;frameborder: 0; scrolling:no;overflow: hidden; height: 100%;width:100%" class="dialog-iframe" src="" ></iframe>
		</div>
	</div>
	<div>
		<table id="log" class="tablelist">
			<thead>
				<tr>				 
					<th>序号</th>
					<th>日志ID</th>
					<th>用户账号</th>
					<th>用户姓名</th>
					<th>模块名称</th>
					<th>日志内容</th>
					<th>登录IP地址</th>
					<th>操作时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<script type="text/javascript">
	 function dialogClose(ids,accounts,names){
		 $("#dialog").dialog("close");
		 $("#userId").val(ids);
		 $("#userAccount").val(accounts);
		 $("#userName").val(names);
	 }
	</script>
</body>
</html>