<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户组管理</title>
<script type="text/javascript">
var path = "<%=path%>";
</script>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<!--  <link href="../../common/css/dataTables.jqueryui.css" rel="stylesheet" type="text/css" />-->
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.validate.min.all.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=path%>/modules/main.js"></script> 
<script type="text/javascript">
var table;
var tableHeight;
var power_userGroupId;
$(function(){
	tableHeight = document.documentElement.clientHeight-145+"px";//table高度适应不同屏幕分辨率
	tableInit();//表格初始化
	readyDialog("dialog",450,400);//弹出框初始化  jquery ui dialog插件
	readyDialog("power_dialog",300,500);//弹出框初始化  jquery ui dialog插件
	/*把单击行时，如果行处于没选中状态，则把样式设为选中状态
  	 ，当中的复选框设为选中状态；否则，移除选中状态，取消选中状态 排除表头*/
  	$("table ").on("click","tr:gt(0)",function(){
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
  	$("#checkAll").on("click",function(){    
  	    if(this.checked){    
  	        $("tbody :checkbox").prop("checked", true);   
  	        $("tbody tr").addClass("selected");
  	    }else{    
  	        $("tbody :checkbox").removeAttr("checked");
  	        $("tbody tr").removeClass("selected");
  	    }  
  	    toolbarStatus();
  	    
  	});
	function toolbarStatus(){
		var num = $("input:checkbox:checked").size()
		if(num==0){
			$("#watch").addClass("gray").unbind("click",watch);
			$("#modify").addClass("gray").unbind("click",modify);				
			$("#del").addClass("gray").unbind("click",del);
		}else if(num==1){
			$("#modify").removeClass("gray").bind("click",modify);
			$("#watch").removeClass("gray").bind("click",watch);
			$("#del").removeClass("gray").bind("click",del);
		}else{
			$("#watch").addClass("gray").unbind("click",watch);
			$("#del").removeClass("gray").bind("click",del);
			$("#modify").addClass("gray").unbind("click",modify);	
		}
   }
	//DataTables插件 具体参数意义可查看DataTables中文网
	function tableInit(){ 
	 $.fn.dataTable.ext.errMode = function(s,h,m){};
     table=$('#user').DataTable({  
    		"dom": 'rt<"bottom"iflp><"clear">',
    		"pagingType":   "full_numbers",
    	//	"pageLength":5,
    		"lengthChange":true,
    		"lengthMenu":[10,20,50,100],
    		"searching":false,
    		"scrollY": tableHeight,//"500px",
            "scrollCollapse": "true",
    	    "language": {
    		   		"url": "../language.json",
    	     },
			"ajax": path+'/userGroup_queryAllUserGroups.action',
			"columns": [
			            { "data": "userGroupId",
			              "render": function ( data, type, row ) {
			        			    return "<input type='checkbox' name='checklist' value='"+data+"'/>";
			        		  },
			               "orderable":false,
			               "width":"5%"
						},
			            { "data": "userGroupName",
						  "orderable":false,
						  "width":"50%"},
			            { "data": "userGroupDesc",
						  "orderable":false,
						  "width":"45%"}
			        ]
		});  
	} 
	//修改编辑
	function modify(){	
		var userGroupId = table.row('.selected').data().userGroupId; 
		var config = { 
				id:"dialog",
				title:"编辑用户",
				disabled:false,
				src:path+'/userGroup_modifyUserGroup.action?userGroupId='+userGroupId,
			    iframeId:"iframe_dialog"			
			}
		showDialog(config);		 
	}
	//添加
	$("#add").click(function() {
		var config = { 
				id:"dialog",
				title:"添加用户组",
				disabled:false,
				src:path+'/userGroup_modifyUserGroup.action',
			    iframeId:"iframe_dialog"			
			}
		showDialog(config);
	});
	//查看
	function watch(){
		isWatch = true;
		var userGroupId =table.row('.selected').data().userGroupId; 
		var config = { 
			id:"dialog",
			title:"查看用户组",
			disabled:true,
			src:path+'/userGroup_modifyUserGroup.action?userGroupId='+userGroupId,
		    iframeId:"iframe_dialog"			
		}
		showDialog(config);	
	}
	//删除
	function del(){	
		var ids = [];
		var names = [];
	    ids = getIds();
	    names = getNames();
		dialog.confirm("确认删除选中的用户组？","用户删除",function(){				
			$.ajax( { 
		    	url:path+'/userGroup_delUserGroups.action',   
		    	type:'post',    
		    	cache:false,
		    	dataType:'json',
		    	traditional : true,
		    	data:{
		    		ids:ids,
		    		names:names
		    	},
		    	success:function(data) {
		    		table.ajax.reload(null,false);
		    		$("#checkAll").removeAttr("checked");
		    		dialog.message("删除成功");;			    		
		    	}, 
		    	error : function() {    
		    		dialog.message("删除失败");;    
		    	} 
		    });
		});
	}
	//搜索
	$("#search").click(function(){
		var searchName = $("#searchinput").val();
		var url = path+'/userGroup_queryAllUserGroups.action?searchName='+searchName;
		url = encodeURI(url);
		table.ajax.url(url).load();			
	});
	//编辑权限
	$("#power_edit").on("click",function(){
		power_userGroupId =table.row('.selected').data().userGroupId; 
		var config = { 
			id:"power_dialog",
			title:"模块权限",
			disabled:false,
			src:"powerForm.jsp",
		    iframeId:"iframe_power_dialog"			
		}
		showDialog(config);	
		
	})
	function getIds(){
		var ids = []; 
	    table.rows('.selected').every(function(){//遍历被选中的所有的行
	    	var userGroupId = this.data().userGroupId;
	    	ids.push(userGroupId);
	    });
	 	return ids;
	}
	function getNames(){
		var names = []; 
	    table.rows('.selected').every(function(){//遍历被选中的所有的行
	    	var name = this.data().userGroupName;
	    	names.push(name);
	    });
	 	return names;
	}
})
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
			<li>用户组管理</li>
		</ul>
	</div>
		<div id="rightinfo" class="rightinfo">
		<div class="tools">
			<ul class="toolbar">
				<li  id="watch" ><span><img src="../../common/images/t00.png" /></span>查看</li>
				<li id="add" class="click"><span><img src="../../common/images/t01.png" /></span>添加</li>
				<li id="modify" class="click"><span><img src="../../common/images/t02.png" /></span>修改</li>
				<li id="del"> <span><img src="../../common/images/t03.png" /></span>删除</li>
				<li id="power_edit" class="click"><span><img src="../../common/images/t02.png" /></span>编辑权限</li>
			</ul>
			<ul class="toolbar1">
				<li style="padding-right: 0px;"><input style="width: 320px; margin-left: 0px;" id="searchinput" name="searchinput" type="text" class="searchinput" placeholder="请输入用户组名称进行查询" /></li>
				<li id="search"><span><img src="../../common/images/t05.png" /></span>搜索</li>
			</ul>			
		</div>
		<table  id="user" class="tablelist">	
		     <thead>
				<tr>
		  			<th><input id="checkAll" type="checkbox"></th>
					<th id="row1">用户组名称</th>
					<th id="row2">描述</th>		
				</tr> 	
			</thead>    
		</table>
	</div>
	<div id="dialog" >
	  <iframe id="iframe_dialog" name="iframe_dialog"  style="position:absolute;frameborder: 0; scrolling:no;overflow: hidden; height: 100%;width:100%" class="dialog-iframe" src="" ></iframe>
	</div>
	<div id="power_dialog" >
	  <iframe id="iframe_power_dialog" name="iframe_power_dialog"  style="position:absolute;frameborder: 0; scrolling:no;overflow: hidden; height: 100%;width:100%" class="dialog-iframe" src="" ></iframe>
	</div>
	<script type="text/javascript">
		function dialogClose(){
 			$("#dialog").dialog("close");
 			table.ajax.reload(null,false);
 			dialog.message("保存成功");
 		}
		function powerdialogClose(){
 			$("#power_dialog").dialog("close");
 			table.ajax.reload(null,false);
 			dialog.message("保存成功");
 		}
	</script>
</body>
</html>