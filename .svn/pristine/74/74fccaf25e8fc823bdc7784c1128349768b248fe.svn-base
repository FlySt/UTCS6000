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
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.validate.min.all.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/datepicker.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var table;
$(function(){
	$(document).ready(function() {	
		if(window.parent.isWatch){  //查看的时候input disabled
			$("#dialog_form :input").attr("disabled","disabled");
			$("#dialog_form :checkbox").attr("disabled","disabled");
		}
		/* ============================= 输入验证 ===================================== */

		$("#dialog_form").validate(
		{
			rules :
			{
				'user.userAccount' :
				{
					maxlength : 20,
					required : true,
					filterWord : true,
					isLetterNum : false,
					userAccount :false,
					remote :
					{
						type : "post",
						dataType : "json",
						url : path + "/user_validatorUserAccount.action",
						data :
						{
							"userAccount" : function()
							{
								return $("#userAccount").val();
							},
							"userId" : function()
							{
								return $("#userId").val();
							}
						}

					}
				},
				'user.userName' :
				{
					required : true,
					filterWord : true,
					stringMaxLength : 25
				},
				'user.utcsDept.deptId' :
				{
					required : true,
					selectOption : 0
				},
				'user.identityCard' :
				{
					required : false,
					isIdCardNo : true
				},
				'ips' :
				{
					required : false,
					isIp : true
				},
				'user.userEmail' :
				{
					required : false,
					maxlength : 50,
					email : true
				},
				'user.userTel' :
				{
					required : false,
					isMobile : true
				},
				'user.validStartDate' :
				{
					required : true
				},
				'user.validEndDate' :
				{
					required : true,
					affirmDate : "#validStartDate"
				},
				'user.userExplain' :
				{
					required : false,
					filterWord : true,
					stringMaxLength : 127
				},
				'fileupload' :
				{
					required : false,
					checkIsImage : "#fileupload"
				},
				'userGroupIds':
				{
					required:true,
					maxlength : 255
				}
			},
			messages :
			{
				'user.userAccount' :
				{
					remote : "用户帐号已存在"
				}
			}
		});
	
		   table=$('#userGroup').DataTable({  
	    		"info": false,
	    		"lengthChange":false,
	    		"searching":false,
	    		"paging": false,
	    		"scrollY": "250px",//"500px",
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
							  "width":"95%"},
				        ],
		  		 "initComplete": function(settings, json) {
		  			var userGroupIds = $("input[name='userGroupIds']");
		  			var datas = json.data;
		  			for(var i =0; i <userGroupIds.length;i++){
						var userGroupId = userGroupIds[i].value;
						for(var j =0;j<datas.length;j++){
							var data = datas[j];
							var dataUserGroupId = data.userGroupId;
							
							if(userGroupId == dataUserGroupId){
								table
							    .rows(j)
							    .nodes()
							    .to$()      // Convert to a jQuery object
							    .addClass("selected")
							    .find(":checkbox").prop("checked", true);
								break;
							}
						}
					}
		  			if(window.parent.isWatch){  //查看的时候input disabled
		  				$("#dialog_form :checkbox").attr("disabled","disabled");
		  			}
		  		 }
			});  
	});
	/*把单击行时，如果行处于没选中状态，则把样式设为选中状态
 	 ，当中的复选框设为选中状态；否则，移除选中状态，取消选中状态 排除表头*/
 	$("table ").on("click","tr:gt(0)",function(){
         if ($(this).hasClass("selected")) {
             $(this).removeClass("selected")
             .find(":checkbox").removeAttr("checked");
         }
         else {
             $(this).addClass("selected")
             .find(":checkbox").prop("checked", true);
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
 	});

});
</script>
</head>

<body>
  	
	<form id="dialog_form"  class="user_form"  method="post">
		<div style="display:none;">
			<label for="userPassword">登录密码：</label>
			<input style="width:200px;font-size:15px" type="text" name="user.userPassword" id="userPassword"  value="123456"/>
			<input id="userId" name="user.userId" value="${user.userId}" type="text"/>
			<input id="deptId_0" name="deptId_0" value="${deptId}" type="text"/>
			<label style="display:inline-block;width:70px;" class="row" for="fileupload">照片：</label>
			<input style="width:200px;font-size:15px" type="text" name="fileupload" id="fileupload" />
		</div>
		<div class="left" >
			<div>
				<label for="userAccount">用户账户：</label>
				<input type="text" name="user.userAccount" id="userAccount"  value="${user.userAccount}"/>
				<span class="redTips" style="color:red">*</span>   
			</div>
			
			<div>
				<label for="userName">用户姓名：</label>
				<input type="text" name="user.userName" id="userName" value="${user.userName}"/>
				<span class="redTips" style="color:red">*</span> 
			</div>
			
			<div>
				<label  for="deptId">所属部门：</label>
				<select id="deptId" name="user.utcsDept.deptId" >
					<option value="0">--请选择部门--</option>
					<s:iterator value="deptList" status="deptList" >
						<option value="<s:property value='deptId' />"><s:property value="deptName" /></option>
					</s:iterator>
				</select>
				<span class="redTips" style="color:red">*</span> 
			</div>
			
			<div>
				<label for="identityCard">身份证号：</label>
				<input type="text" name="user.identityCard" id="identityCard" value="${user.identityCard}" />
			</div>
			
			<div>   			   	 
				<label for="userEmail">用户Email：</label>
				<input type="text"  id="userEmail" name="user.userEmail"  value="${user.userEmail}" />
			</div>
			
			<div> 				  
				<label for="userTel">电话号码：</label>
				<input type="text"  id="userTel" name="user.userTel"  value="${user.userTel}" />
			</div>  
			 
			<div> 			   
				<label for="validStartDate">启用日期：</label>
				<input class="datepicker" type="text"  id="validStartDate" name="user.validStartDate" 
					   value="<s:date name="user.validStartDate" format="yyyy-MM-dd"/>">
			</div>
			
			<div>   			 
				<label for="validEndDate">结束日期：</label>
				<input class="datepicker" type="text"  id="validEndDate" name="user.validEndDate" 
					   value="<s:date name="user.validEndDate" format="yyyy-MM-dd"/>" />
			</div>	
			
			<div> 			
				<label class="row" for="useStatus">使用状态：</label>
				<select id="useStatus" name="user.useStatus" >
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select> 
			</div>		
			
			<div> 	
				<label for="ip1">限制IP1：</label>
				<input type="text"  id="ip1" name="ips"   />
			</div>	
			
			<div>			
				<label for="ip2">限制IP2：</label>
				<input type="text"  id="ip2" name="ips"   />
			</div>	
				
			<div>		  	
				<label for="ip3">限制IP3：</label>
				<input type="text"  id="ip3" name="ips"   />
			</div>	
			
		</div>
		<div class="right" >
			<div>
				<div style="float:left;margin-top:10px">
					<label  for="userExplain">用户说明：</label>
				</div>
				<textarea  rows="2" id="userExplain" name="user.userExplain" >${user.userExplain}</textarea>
			</div>
			<div>
				<div class="usergroup" >
					<label for="userGroup">用户组 ：</label>
				</div>
			 	<div class="usergroup_table" >
					 <table  id="userGroup" class="min_table">	
					     <thead>
							<tr>
					  			<th><input id="checkAll" type="checkbox"/></th>
								<th id="row1">用户组名称</th>	
							</tr> 	
						</thead>    
					</table>
				</div>	
			</div>
			<div style="display:none;" id="userGroupDiv" class="">
				<%--用户组ids[]  --%>
				<s:iterator value="userGroupIds" status="userGroupIds" >
				<input type="hidden" name="userGroupIds" style="width: 30px;" value='<s:property/>'/>
				</s:iterator>
			</div>	
		</div > 
	</form>
	
		<script type="text/javascript">
	        if($("#userId").val() != null&&$("#userId").val() != ""){
	        	$("#userAccount").attr("disabled", true);
			    $("#deptId").val('${user.utcsDept.deptId}');
			    $("#useStatus").val('${user.useStatus}');
			    $("#userPassword").val('${user.userPassword}');
			    
			    var limitIpaddrs = '${user.limitIpaddrs}';
			    var ips = limitIpaddrs.split(";");
			    for(var i = 0 ; i< ips.length ; i++){
			    	$('#ip'+(i+1)).val(ips[i]);
			    }
			}else{//添加时
				$('#deptId').val('${deptId}');
			}
			function clear(){
				$("#dialog_form :input").val("");
				$("#dialog_form :input").removeAttr("disabled");
				window.parent.isWatch = false;
			}
			function dialogSubmit(){
				 if(!getUserGroupResult()){
					 return;
				 } 
				 var bValid = $("#dialog_form").valid();
		         if(bValid){
			   		 var form = $("#dialog_form"); 
			   	 	 $.ajax({  
			                   url: path + '/user_saveUser.action',  
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
			function getUserGroupResult(){
				$("#userGroupDiv input").remove();
				var ids = getIds();
				if(ids.length==0){
					dialog.message("请选择用户组");	
					return false;
				}
				for(var i =0;i<ids.length;i++){
					var userGroupId = ids[i]; // 用户组ID
					var button = "<input type='hidden' style='width:30px;' name='userGroupIds' value='" + userGroupId + "' />";
					$("#userGroupDiv").append(button);
				}
				
				return true;
			}
			function getIds(){
				var ids = []; 
			    table.rows('.selected').every(function(){//遍历被选中的所有的行
			    	var userGroupId = this.data().userGroupId;
			    	ids.push(userGroupId);
			    });
			 	return ids;
			}
		</script>
</body>

</html>