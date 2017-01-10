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
<script type="text/javascript">
$(function(){
	$(document).ready(function() {	
		if(window.parent.isWatch){  //查看的时候input disabled
			$("#dialog_form :input").attr("disabled","disabled");
		}
		/* ============================= 输入验证 ===================================== */

		$("#dialog_form").validate(
		{
			rules: 
			{
				'userGroup.userGroupName':
				{
			        maxlength: 25,
					required: true,
					remote:
					{
						type: "post",
			         	dataType: "json",
						url:path+"/userGroup_validatorGroupName.action",
						data: 
						{
						    "userGroupName":function(){
								return $("#userGroupName").val();
							}, 
							"userGroupId":function(){
								return $("#userGroupId").val();
							}
						}
					}
				},
				'userGroup.userGroupDesc':
				{
					required: false, 
					filterWord: true, 
					stringMaxLength: 127
				}
			},
		        messages:
		        {
					'userGroup.userGroupName':
					{
		                remote: "组名称已存在"
		            }
		        }
			});
	});


});
</script>
</head>

<body>
  	
	 <form id="dialog_form" class="dialog_form"  method="post">	
	  	<div>
		    <label for="userGroupName">用户组名称：</label>
		    <input type="text" name="userGroup.userGroupName" id="userGroupName"  value="${userGroup.userGroupName}"/>
		    <span class="redTips" style="color:red">*</span> 	
	    </div>
	    <div >
		 	 <label  class="row" for="userGroupDesc">用户组描述：</label>
			 <textarea rows="2" id="userGroupDesc" name="userGroup.userGroupDesc" ></textarea>
		</div>  
		<div style="display:none;">
			<input id="userGroupId" name="userGroup.userGroupId" value="${userGroup.userGroupId}" type="text"/>
		</div>  
	</form>
	
		<script type="text/javascript">
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
			                   url: path + '/userGroup_saveUserGroup.action',  
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