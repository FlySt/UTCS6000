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
var userGroupId;

$(function(){
	userGroupId =  window.parent.power_userGroupId;
	
	function zTreeOnAsyncSuccess(){ //获取用户组的模块权限
  	 	 $.ajax({  
             url: path + '/userGroup_getUserGroupPower.action',  
             type:'post',  
             traditional: true, 
             dataType:'json', 
             data:{
          	   userGroupId:userGroupId
             }, 
             success:function(data){  
				var datas = data.data;
				var nodes = zTreeModule.transformToArray(zTreeModule.getNodes());
				for(var i=0;i<datas.length;i++){
					var node = datas[i];
					for(var j=0;j<nodes.length;j++){
						if(node.id == nodes[j].id){	
							zTreeModule.checkNode(nodes[j], true, false);
						}
					}
				}
             },  
             error:function(){  			          
          	   return;
             }  
         } );
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
				url:path+"/treeResource_getModuleTrees.action"
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
  	
	  <form id="dialog_form"  class="power_form"  method="post">	
		  <div>
  			 <ul id="treeModule" class="ztree" ></ul>	
   		 </div> 
   		 <input style="display:none" name="userGroupId" id="userGroupId" value=""/>
	</form>
	
		<script type="text/javascript">
			function clear(){
				$("#dialog_form :input").val("");
				$("#dialog_form :input").removeAttr("disabled");
			}
			function replacePos(strObj, pos, replacetext)
			{
				var str = strObj.substr(0, pos-1) + replacetext + strObj.substring(pos, strObj.length);
				return str;
			}
			function getNodes(){
				var nodes = zTreeModule.getCheckedNodes(true);
				var powerIds = [];
				for(var i=0;i<nodes.length;i++){
					var power = "00000000"
					var id = nodes[i].id;
					var moduleId = id.substring(id.indexOf("_")+1,id.length);
					if(nodes[i].isParent){
						var c_nodes = nodes[i].children;
						if(c_nodes!=null){						
							for(var j=0;j<c_nodes.length;j++){
								var name = c_nodes[j].name;
								if(c_nodes[j].checked){
									if(name=="查看" ){ //有查看权限
									    power = replacePos(power,1, "1");
									}
									if(name=="增加"){ //有添加权限
										power = replacePos(power,2, "1");
									}
									if(name=="修改"){ //有修改权限
										power = replacePos(power,3, "1");
									} 
									if(name=="删除"){ //有删除权限
										power = replacePos(power,4, "1");
									}
								}
							}
							var powerId = moduleId+"_"+power;
							powerIds.push(powerId);
						}
						
					}else{
						if(nodes[i].pId=="-1"){
							var powerId = moduleId+"_"+power;
							powerIds.push(powerId);
						}
					}
				}
				return powerIds;
			}
			function dialogSubmit(){
					 var powerId = [];
					 powerId = getNodes() ;
			   	 	 $.ajax({  
			                   url: path + '/userGroup_saveUserGroupPower.action',  
			                   type:'post',  
			                   traditional: true, 
			                   dataType:'json', 
			                   data:{
			                	   powerId:powerId,
			                	   userGroupId:userGroupId
			                   }, 
			                   success:function(response){  
			                	   if (response.msg == "ok"){			                		   
			                		   window.parent.powerdialogClose();	
			                		   clear();
			                	   }
			                   },  
			                   error:function(){  			          
			                	   return;
			                   }  
			               } );
			}
		</script>
</body>

</html>