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
<!--  <link href="../../common/css/dataTables.jqueryui.css" rel="stylesheet" type="text/css" />-->
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyleEx.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.validate.min.all.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
var nodeIds = [];
var orgTree;
var levelArray = new Array();
levelArray[0] = [ "1", "总队" ];
levelArray[1] = [ "2", "支队" ];
levelArray[2] = [ "3", "大队" ];
levelArray[3] = [ "4", "中队" ];
levelArray[4] = [ "5", "其他" ];
var deptId;
var parentDeptId;
var deptLevel;
$(function(){
	deptId = $("#deptId").val();
	parentDeptId = $("#parentDeptId").val();
	deptLevel = $("#deptLevel").val();;
	/* ========== 部门树 ========== */
	var setting =
	{
		selectFirstNode : true,
		check :
		{
			chkStyle : "checkbox",
			enable : false
		},
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
			url:path+"/treeResource_getDeptTrees.action?type=-1&deptLevel=10"
		//	url : path + "/treeResource_getDeptTrees.action"
		},
 		callback :
		{
			// zTree单击事件
			onClick : function(event, treeId, treeNode)
			{
				var zTreeName = treeNode.name;
				var zTreeId = treeNode.id;
				var zTreePId = treeNode.pId;
				var deptPId = "dept_" + deptId;
				var deptl = parseInt(treeNode.deptLevel);
				if (isObjExist(nodeIds, zTreeId)&&zTreeId!="dept_")
				{
					dialog.message("不能选择自己或自己的子节点作为父节点");
					selectNode(orgTree, "dept_" + parentDeptId);
				} else
				{
					$("#parentDept").val(zTreeName);
					$("#parentDeptId").val(zTreeId.split("_")[1]);
				}
				
				hideMenu();
				$("#deptLevel").html("");
				for ( var i = 0; i < levelArray.length; i++)
				{
					document.getElementById("deptLevel").options.add(new Option(levelArray[i][1], levelArray[i][0]));
				}
				if(deptl<5){
					deptl = parseInt(deptl)+1;
				}
				
				$("#deptLevel").val(deptl);
			},
			// zTree加载完成事件
			onAsyncSuccess : function()
			{
				getNodeIdAndSonIds(orgTree, "dept_" + deptId);
				if (parentDeptId != null && parentDeptId != "")
				{
					var zTreeParentId = "dept_" + parentDeptId;
					// 根据zTree的ID获取节点
					selectNode(orgTree, zTreeParentId);
				} else
				{
					var zTreeParentId = "dept_-1";
					// 根据zTree的ID获取节点
					// var zTreeParentId = orgTree.getNodes()[0].id;
					selectNode(orgTree, zTreeParentId);
				}
			}
		} 
	};
	$(document).ready(function() {	
		if(window.parent.isWatch){  //查看的时候input disabled
			$("#dialog_form :input").attr("disabled","disabled");
		}
		/* 输入验证 */
		$("#dialog_form").validate(
		{				
			rules :
			{
				'deptInfo.deptName':{
					required: true,
					maxlength : 25,
					remote :
					{
						type : "post",
						dataType : "json",
						url : path + "/dept_validatorDeptName.action",
						data :
						{
							"deptName" : function()
							{
								return $("#deptName").val();
							},
							"deptId" : function()
							{
								return $("#deptId").val();
							}
						}
					}
				},
				'deptInfo.deptCode':{
					required : false,
					filterWord : true,
					isNum : true,
					justlength : 12,
					isLetterNum : true
				},
				'deptInfo.regionCode':{
					required : false,
					isNum : true,
					justlength : 6
				}
			},	
			messages :
			{
				'deptInfo.deptName' :
				{
					remote : "部门名称已存在"
				}
			}
		});
	
		orgTree = $.fn.zTree.init($("#leftTree"), setting);
	});

	// 选中某个节点
	function selectNode(treeObj, nodeId)
	{
		var node = treeObj.getNodeByParam("id", nodeId, null);
		if (node)
		{
			var zTreeName = node.name;
			var zTreeId = node.id;
			// 选中节点
			orgTree.selectNode(node);
			$("#parentDept").val(zTreeName);
			$("#parentDeptId").val(zTreeId.split("_")[1]);
		}
	}
});
</script>
</head>

<body>
  	
	<form id="dialog_form" class="dialog_form"  method="post">
		<div>
			<label for="deptName">部门名称：</label>
			<input type="text" name="deptInfo.deptName" id="deptName"  value="${deptInfo.deptName}">
			<span class="deptNameTips" style="color:red">*</span>
		</div> 
		<div>  
			<label for="deptCode">部门代码：</label>
			<input type="text" name="deptInfo.deptCode" id="deptCode" value="${deptInfo.deptCode}"> 	
		</div> 
		<div> 
			<label for="parentDept">上级部门：</label>
			<input id="parentDept" name="parentDept" type="text" onclick="showMenu()" readOnly="readonly" />
			<input id="parentDeptId" name="deptInfo.parentDeptId" value="${deptInfo.parentDeptId}"	type="hidden" />
		</div> 
		<div id="menuContent" class="ztree_hide" style="width:200px;">
			<ul id="leftTree" class="ztree"></ul> 
		</div>
		<div> 
			<label for="regionCode">行政区划：</label>
			<input type="text" name="deptInfo.regionCode" id="regionCode" value="${deptInfo.regionCode}" ><br/>
		</div> 
		<div style="display:none;">
			<label for="deptLevel">部门等级：</label>
			<select name="deptInfo.deptLevel" id="deptLevel" >
			<option value="1">总队</option>
			<option value="2" selected="selected">支队</option>
			<option value="3">大队</option>
			<option value="4" >中队</option>
			<option value="5" >其他</option>
			</select>
			<input id="deptId" name="deptInfo.deptId" value="${deptInfo.deptId}" type="text"/>
		</div>
		<div>
			<label for="deptType">部门类型：</label>
			<select  name="deptInfo.deptType" id="deptType" >
				<option value="0" selected="selected">部门</option>
				<option value="1">区域</option>
			</select>
		</div>
		<div>
			<label for="dealAddress">处理地址：</label>
			<input type="text"  id="dealAddress" name="deptInfo.dealAddress"  value="${deptInfo.dealAddress}" ><br/>
		</div>
		<div>
			<label for="deptExplain">部门描述：</label>
			<textarea name="deptInfo.deptExplain" id="deptExplain" >${deptInfo.deptExplain}</textarea>
		</div> 
	</form>
	
		<script type="text/javascript">

	        if(deptId != null && deptId != ""){
		    	$("#deptType").val('${deptInfo.deptType}');
		    	var deptl = '${deptInfo.deptLevel}';
		    	var parentLevel = '${deptInfo.parentDeptId}';
		    	if(parentLevel==""){
		    		deptl = 1;
		    	}
				$("#deptLevel").html("");
				for (var i = 0; i < levelArray.length; i++) {
					document.getElementById("deptLevel").options.add(new Option(levelArray[i][1], levelArray[i][0]));
				}
				$("#deptLevel").val('${deptInfo.deptLevel}');
			}
			var inputObj = $("#parentDept");
			var divObj = $("#menuContent");
			// 显示树的位置
			function showMenu()
			{
				var inputOffset = inputObj.offset();
				var left = parseInt(inputOffset.left)-40 ;
				var top = parseInt(inputOffset.top + inputObj.outerHeight()) - 5;
				divObj.css(
				{
					left : left + "px",
					top : top + "px",
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDown);
			}
			// 页面body鼠标事件
			function onBodyDown(event)
			{
				var inputId = inputObj[0].id;
				var divId = divObj[0].id;
				if (!(event.target.id == "menuBtn" || event.target.id == inputId || event.target.id == divId || $(event.target)
						.parents(("#" + divId)).length > 0))
				{
					hideMenu();
				}
			}
			// 隐藏zTree
			function hideMenu()
			{
				divObj.fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
			function clear(){
				$("#dialog_form :input").val("");
				$("#dialog_form :input").removeAttr("disabled");
				window.parent.isWatch = false;
			}
			function dialogSubmit(){
				 var bValid = $("#dialog_form").valid();
		         if(bValid){
			   		 var form = $("#dialog_form"); 
			   	 	 $.ajax({  
			                   url: path + '/dept_saveDept.action',  
			                   type:'post',  
			                   data:form.serialize(),  
			                   dataType:'json', 
			                   success:function(response){  
			                	   if (response.msg == "ok"){
			                		   
			                		   window.parent.dialogClose();	
			                		   clear();
			                	   }
			                   },  
			                   error:function(){  			          
			                	   return;
			                   }  
			               } );
		   	 	
		        }else{
		       	 return ;
		        }
			}
		</script>
</body>

</html>