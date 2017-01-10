<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限管理</title>
<script type="text/javascript">
var path = "<%=path%>";
</script>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<!--  <link href="../../common/css/dataTables.jqueryui.css" rel="stylesheet" type="text/css" />-->
<link href="<%=path%>/common/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.validate.min.all.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/datepicker.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
$(function(){
	$("#tabs").tabs();
	$("#userSelect").selectmenu();
	//userInit();
	
	function userInit(){
		$.ajax( { 
	    	url:path+'/user_queryAllUsers.action',   
	    	type:'post',    
	    	cache:false,
	    	dataType:'json',
	    	traditional : true,
	    	success:function(data) {
				for(var i=0 ;i<data.data.length; i++){
					var value = data.data[i].userId;
					var text = data.data[i].userAccount+"("+data.data[i].userName+")";
					$("#userSelect").append("<option value='"+value+"'>"+text+"</option>");  
				}			    		
	    	}, 
	    	error : function() {    
	    		  
	    	} 
	    });
	}
	var zTreeOrganization;
	var zTreeModule;
	var setting = {
			check: {
				enable: false
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
				url:path+"/treeResource_getOrganizationTrees.action?type=-1&deptLevel=10"
			//	url : path + "/treeResource_getDeptTrees.action"
			},
		};
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
				url:path+"/treeResource_getModuleTrees.action"
			//	url : path + "/treeResource_getDeptTrees.action"
			},
		};

	zTreeOrganization = $.fn.zTree.init($("#treeOrganization"), setting);
	zTreeModule = $.fn.zTree.init($("#treeModule"), module_setting);
	
	  $("#fragment-header").on("click mouseover mouseout",function(event){
		  if( event.type == "click"){
			  if($("#treeOrganization").is(":hidden")){
				  $(this).removeClass("ui-state-hover")
				         .removeClass("ui-state-default")
			  		     .addClass("ui-state-active");
			  }else{
				  $(this).removeClass("ui-state-hover")
			         .removeClass("ui-state-active")
		  		     .addClass("ui-state-default");
			  }
			  $("#treeOrganization").toggle();
		  }else if(event.type == "mouseover"){
			  if( !$(this).hasClass("ui-state-active")){
				  $(this).removeClass("ui-state-default")
			  		 .addClass("ui-state-hover");
			  }

		  } else if(event.type == "mouseout"){
			  if( $(this).hasClass("ui-state-hover")){
				  $(this).removeClass("ui-state-hover")
			  		 .addClass("ui-state-default");
			  }
		  }

	  })
	  $( "input" ).checkboxradio();
});
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li>资源管理</li>
			<li>基础信息</li>
			<li>权限管理</li>
		</ul>
	</div>
	<div id="tabs" >
    <ul>
        <li><a href="#fragment-1"><span>用户</span></a></li>
        <li><a href="#fragment-2"><span>用户组</span></a></li>
    </ul>
    <div id="fragment-1" style="height:500px">
    <!--     <div>
       	    <label for="userSelect">选择用户</label>
	    	<select id="userSelect">
	    	<option value="0">--请选择用户--</option>
			</select>
   		 </div> -->   		 
   		 <div class="fragment">
   			 <h3  id="fragment-header" class="ui-state-active ui-fragment-header">
   			 	<span class="ui-icon"></span> 
   			 	组织结构
   			 </h3>
  			 <ul id="treeOrganization" class="ztree " ></ul>	
   		 </div>
   		  <div class="fragment" style="margin-left:20px">
   			 <h3 class="ui-state-default ui-fragment-header">
   			 	<span class="ui-icon"></span> 
   			 	模块权限
   			 </h3>
  			 <ul id="treeModule" class="ztree" ></ul>	
   		 </div>  
   		 <!--   <h2>Checkbox</h2>
			  <fieldset>
			    <legend>Hotel Ratings: </legend>
			    <label for="checkbox-1">2 Star</label>
			    <input type="checkbox" name="checkbox-1" id="checkbox-1">
			    <label for="checkbox-2">3 Star</label>
			    <input type="checkbox" name="checkbox-2" id="checkbox-2">
			    <label for="checkbox-3">4 Star</label>
			    <input type="checkbox" name="checkbox-3" id="checkbox-3">
			    <label for="checkbox-4">5 Star</label>
			    <input type="checkbox" name="checkbox-4" id="checkbox-4">
			  </fieldset> -->
    </div>
    <div id="fragment-2">
        Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
        Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
    </div>

</div>
</body>
</html>