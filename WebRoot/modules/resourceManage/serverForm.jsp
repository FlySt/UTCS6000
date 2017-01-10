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
<link href="<%=path%>/common/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyleEx.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.validate.min.all.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
	$(function(){
		elementInit();
		/* 输入验证 */
		$("#dialog_form").validate(
			{
				rules :
				{
					'serverParam.serverNo':{
						required: true,
						maxlength : 16,
						remote :
						{
							type : "post",
							dataType : "json",
							url : path + "/server_validatorServerNo.action",
							data :
							{
								"serverId" : function()
								{
									return $("#serverId").val();
								},
								"serverNo" : function()
								{
									return $("#serverNo").val();
								}
							}
						}
					},
					'serverParam.isCenter':{
						required: true,
						remote :
						{
							type : "post",
							dataType : "json",
							url : path + "/server_validatorCenterServer.action",
							data :
							{
								"isCenter" : function()
								{
									return $("#isCenter").val();
								},
								"serverId":function()
								{
									return $("#serverId").val();
								}
							}
						}
					},
					'serverParam.serverIP':{
						required : true,
						filterWord : true,
						isIp:true
					},
				},
				messages :
				{
					'serverParam.serverNo' :
					{
						remote : "服务器编号已存在"
					},
					'serverParam.isCenter' :
					{
						remote : "中心服务器已存在"
					}
				}
			});
		function elementInit(){
			if(window.parent.isWatch){  //查看的时候input disabled
				$("#dialog_form :input").attr("disabled","disabled");
			}
			$("#isCenter").val('${serverParam.isCenter}');
		}
	});

	function clear(){
		$("#dialog_form :input").val("");
		$("#dialog_form :input").removeAttr("disabled");
		window.parent.isWatch = false;
	}
    function dialogSubmit(){
        try{
            var bValid = $("#dialog_form").valid();
            if(bValid){
                var form = $("#dialog_form");
                $.ajax({
                    url: path + '/server_saveServer.action',
                    type:'post',
                    data:form.serialize(),
                    dataType:'json',
                    success:function(response){
                        if (response.result){
                            window.parent.dialogClose();
                            clear();
                        }
                    },
                    error:function(){
                        dialog.message("保存失败");
                    }
                } );

            }else{
                return ;
            }
        }catch (e){
            dialog.message("保存失败");
        }
    }
</script>
</head>

<body>
	<form id="dialog_form" class="dialog_form"  method="post" style="margin-top: 50px">
        <div style="display: none">
            <label for="serverId" style="width: 110px">服务器ID:</label>
            <input type="text" id="serverId" name="serverParam.serverId" value="${serverParam.serverId}"/>
        </div>
		<div>
			<label for="serverNo" style="width: 110px">服务器编号:</label>
			<input type="text" id="serverNo" name="serverParam.serverNo" value="${serverParam.serverNo}"/>
			<span style="color:red">*</span>
		</div>
		<div>
			<label for="serverIP" style="width: 110px">服务器IP地址:</label>
			<input type="text" id="serverIP" name="serverParam.serverIP" value="${serverParam.serverIP}"/>
			<span style="color:red">*</span>
		</div>
		<div>
			<label for="isCenter" style="width: 110px">是否为中心服务器:</label>
			<select id="isCenter" name="serverParam.isCenter" >
				<option value="0" >是</option>
				<option value="1" selected="selected">否</option>
			</select>
		</div>
	</form>
</body>

</html>