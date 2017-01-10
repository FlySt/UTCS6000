<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
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
	var cross_tree;
	var typeArray = new Array();
	typeArray[0] = [ "10", "JK-C3" ];
	typeArray[1] = [ "20", "JK-C6" ];
	typeArray[2] = [ "30", "JK-D3" ];
	typeArray[3] = [ "40", "JK-D6" ];
	typeArray[4] = [ "50", "JK-E3" ];
	typeArray[5] = [ "60", "JK-XT-100" ];
	var protocolArray = new Array();
	protocolArray[0] = ["JKC3 1.0","JKC3 2.0"];
	protocolArray[1] = ["JKD3 1.0","JKD3 2.0","JKD3 3.0"];
	protocolArray[2] = ["JKE3 1.0"];
	protocolArray[3] = ["XT100 1.0"];
	$(function(){
		var signalControlerId = $("#signalControlerId").val();
		elementInit();
		/* 输入验证 */
		$("#dialog_form").validate(
				{
					rules :
					{
						'signalControler.signalControlerName':{
							required: true,
							maxlength : 25,
							remote :
							{
								type : "post",
								dataType : "json",
								url : path + "/signalControler_validatorSignalControlerName.action",
								data :
								{
									"signalControlerName" : function()
									{
										return $("#signalControlerName").val();
									},
									"signalControlerId" : function()
									{
										return $("#signalControlerId").val();
									}
								}
							}
						},
						'signalControler.signalControlerNum':{
							required : true,
							filterWord : true,
							isNum : true,
							justlength:17,
							isLetterNum : true,
							remote :
							{
								type : "post",
								dataType : "json",
								url : path + "/signalControler_validatorSignalControlerNum.action",
								data :
								{
									"signalControlerNum" : function()
									{
										return $("#signalControlerNum").val();
									},
									"signalControlerId" : function()
									{
										return $("#signalControlerId").val();
									}
								}
							}
						},
						'signalControler.crossParam.crossName': {
							required: true
						},
						'signalControler.deviceIp':{
							isIp:true
						}

					},
					messages :
					{
						'signalControler.signalControlerNum' :
						{
							remote : "信号机编号已存在"
						},
						'signalControler.signalControlerName' :
						{
							remote : "信号机名称已存在"
						}
					}
				});
		var cross_setting =
		{
			selectFirstNode : true,
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
				url:path+"/treeResource_getCrossParamTrees.action"
			},
			callback :
			{
				// zTree单击事件
				onClick : function(event, treeId, treeNode)
				{
					if(treeNode.id.indexOf("cross_")==0){
						$("#crossId").val(treeNode.id.split("_")[1]);
						$("#crossName").val(treeNode.name);
						hideMenu();
					}
				},
				// zTree加载完成事件
				onAsyncSuccess : function()
				{
				}
			}
		};
		cross_tree = $.fn.zTree.init($("#crossTree"), cross_setting);
		function elementInit(){
			if(window.parent.isWatch){  //查看的时候input disabled
				$("#dialog_form :input").attr("disabled","disabled");
			}
			serverInit();
			if($("#supplier").val()==0){//南昌金科
				$("#type").html("");
				for ( var i = 0; i < typeArray.length; i++)
				{
					document.getElementById("type").options.add(new Option(typeArray[i][1], typeArray[i][0]));
				}
				if(signalControlerId!=null &&  signalControlerId!=""){
					$("#type").val('${signalControler.type}');
				}
				if(signalControlerId!=null &&  signalControlerId!=""){
					$("#protocolNum").val('${signalControler.protocolNum}');
					$("#signalType").val('${signalControler.signalType}');
				}
			}
		}
		function serverInit(){
			$("#serverId").html("");
			$.ajax({
				url:path+'/server_findServerParams.action',
				dataType:'json',
				type:'get',
				async:false,
				success:function (response) {
					for(var i=0;i<response.data.length;i++){
						document.getElementById("serverId").options.add(new Option(response.data[i].serverNo, response.data[i].serverId));
					}
				}
			})
			if(signalControlerId!=null &&  signalControlerId!=""){
				$("#serverId").val('${signalControler.serverParam.serverId}');
			}
		}
	});

	function clear(){
		$("#dialog_form :input").val("");
		$("#dialog_form :input").removeAttr("disabled");
		window.parent.isWatch = false;
	}
	// 显示树的位置
	function showCrossMenu()
	{
		var inputOffset = $("#crossName").offset();
		var left = parseInt(inputOffset.left)-40 ;
		var top = parseInt(inputOffset.top + $("#crossName").outerHeight()) - 45;
		$("#crossContent").css(
				{
					left : left + "px",
					top : top + "px",
				}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	// 页面body鼠标事件
	function onBodyDown(event)
	{
		if (!(event.target.id == "crossName" || event.target.id == "crossContent" || $(event.target)
						.parents(("#crossContent")).length > 0))
		{
			hideMenu();
		}
	}
	// 隐藏zTree
	function hideMenu()
	{
		$("#crossContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function dialogSubmit(){
		try{
			var bValid = $("#dialog_form").valid();
			if(bValid){
				$.ajax({
					url: path + '/signalControler_isCrossExistSignalControler.action',
					type:'post',
					data:{
						"crossId":$("#crossId").val(),
						"signalControlerId":$("#signalControlerId").val()
					},
					dataType:'json',
					success:function(response){
						if(response.result){
							dialog.message("该路口下已存在信号机");
						}else{
							var form = $("#dialog_form");
							$.ajax({
								url: path + '/signalControler_saveSignalControler.action',
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
						}
					},
					error:function(){
						dialog.message("保存失败");
					}
				})
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
	<form id="dialog_form" class="dialog_form"  method="post" style="margin-top: 20px">
		<div style="display: none">
			<label for="signalControlerId">信号机ID:</label>
			<input type="text" id="signalControlerId" name="signalControler.signalControlerId" value="${signalControler.signalControlerId}"/>
		</div>
		<div>
			<label for="signalControlerName">信号机名称:</label>
			<input type="text" id="signalControlerName" name="signalControler.signalControlerName" value="${signalControler.signalControlerName}"/>
			<span style="color:red">*</span>
		</div>
		<div>
			<label for="signalControlerNum">信号机编号:</label>
			<input type="text" id="signalControlerNum" name="signalControler.signalControlerNum" value="${signalControler.signalControlerNum}"/>
			<span style="color:red">*</span>
		</div>
		<div>
			<label for="crossName">所属路口:</label>
			<input type="text" id="crossName" name="signalControler.crossParam.crossName" value="${signalControler.crossParam.crossName}" onclick="showCrossMenu()" readOnly="readonly"/>
			<input type="text" id="crossId" name="signalControler.crossParam.crossId" value="${signalControler.crossParam.crossId}"  style="display: none"/>
			<span style="color:red">*</span>
		</div>
		<div id="crossContent" class="ztree_hide" style="width:200px;height:200px;">
			<ul id="crossTree" class="ztree"></ul>
		</div>
		<div>
			<label for="serverId">所属服务器:</label>
			<select id="serverId" name="signalControler.serverParam.serverId" ></select>
			<span style="color:red">*</span>
		</div>
		<div style="display: none">
			<label for="supplier">供应商:</label>
			<select id="supplier" name="signalControler.supplier" >
				<option value="0" selected="selected">南昌金科</option>
			</select>
		</div>
		<div>
			<label for="type">规格型号:</label>
			<select id="type" name="signalControler.type">
			</select>
		</div>
		<div>
			<label for="protocolNum">协议号:</label>
			<select id="protocolNum" name="signalControler.protocolNum">
				<option value="1" selected="selected">V1.0</option>
				<option value="2">V2.0</option>
			</select>
		</div>
		<div>
			<label for="deviceIp">设备IP地址:</label>
			<input id="deviceIp" name="signalControler.deviceIp" value="${signalControler.deviceIp}"/>
		</div>
		<div style="display: none">
			<label for="devicePort">设备通信端口:</label>
			<input id="devicePort" name="signalControler.devicePort" value="${signalControler.devicePort}"/>
		</div>
		<div>
			<label for="signalType">信号机种类:</label>
			<select id="signalType" name="signalControler.signalType">
				<option value="0" selected="selected">非国标信号机</option>
				<option value="1">国标信号机</option>
			</select>
		</div>
	</form>
</body>

</html>