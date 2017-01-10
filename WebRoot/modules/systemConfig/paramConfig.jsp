<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统参数配置</title>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/modules/main.js"></script> 
<script type="text/javascript">
var path = "<%=path%>";
$(function(){
	$("#tabs").tabs();
	$("#generalParam").on("click",function(){
		saveParam($("#general_form"));
	})
	$("#communicationParam").on("click",function(){
		saveParam($("#communication_form"));
	})
	function saveParam(form){
		$.ajax({
			dataType:'json',
			type:'post',
			data:form.serialize(), 
			url:path+'/systemParam_saveParam.action',
			success:function(result){
				if(result.msg=="ok"){
					dialog.message("保存成功");
				}else{
					dialog.message("保存失败")
				}
			},
			error:function(result){
				dialog.message("网络连接失败");
			}
		})
	}
	$("#netParam").on("click",function(){
		var form = $("#net_form");
		$.ajax({
			dataType:'json',
			type:'post',
			data:form.serialize(),
			url:path+'/netParam_saveParam.action',
			success:function(result){
				if(result.msg=="ok"){
					dialog.message("保存成功");
				}else{
					dialog.message("保存失败")
				}
			},
			error:function(result){
				dialog.message("网络连接失败");
			}
		})
	})
})
</script>
</head>
<body>
	<div class="place">
		<input id="powerModuleId" value="${param.powerModuleId }" type="hidden" />
		<input id="level" value="${param.level }" type="hidden" />
		<span>位置：</span>
		<ul class="placeul">
			<li>系统配置</li>
			<li>系统参数配置</li>
		</ul>
	</div >
	<div id="tabs" >
	    <ul>
	        <li><a href="#fragment-1"><span>系统常规参数</span></a></li>
	        <li><a href="#fragment-2"><span>服务器通信参数</span></a></li>
	        <li><a href="#fragment-3"><span>网络参数</span></a></li>
	    </ul>
		<div id="fragment-1" class="param">
			<form  id="general_form" >
				<div>
					<label for="systemName">系统名称：</label>
					<input id="systemName" name="utcsSystemParam.systemName" type="text" value="${utcsSystemParam.systemName}">
				</div>
				<div>
					<label for="systemVersion">版本号:</label>
					<input  id="systemVersion" name="utcsSystemParam.systemVersion" type="text" value="${utcsSystemParam.systemVersion}">
				</div>
				<div>
					<label for="supplier">供应商:</label>
					<input  id="supplier" name="utcsSystemParam.supplier" type="text" value="${utcsSystemParam.supplier}">
				</div>
				<div>
					<label for="accreditCustomer">授权客户名称:</label>
					<input id="accreditCustomer" name="utcsSystemParam.accreditCustomer" type="text" value="${utcsSystemParam.accreditCustomer}">
				</div>
				<div>
					<label for="lastUpdateTime">最后更新时间:</label>
					<input  disabled="disabled" id="lastUpdateTime" type="text" 
							value="<s:date name="utcsSystemParam.lastUpdateTime" format="yyyy-MM-dd"/>" >
				</div>
				<div style="display: none">
					<input  name="utcsSystemParam.paramId" type="text" value="${utcsSystemParam.paramId}">
					<input  name="utcsSystemParam.serverIPAddr" type="text" value="${utcsSystemParam.serverIPAddr}">
					<input   name="utcsSystemParam.serverPort" type="text" value="${utcsSystemParam.serverPort}">
					<input   name="utcsSystemParam.localIPAddr" type="text" value="${utcsSystemParam.localIPAddr}">
					<input    name="utcsSystemParam.localPort" type="text" value="${utcsSystemParam.localPort}">
				</div>
			</form>
			<div style="text-align:center">
				<input id="generalParam" style="margin-top:20px" class="ui-button " type="button" value="保存">
			</div>
		</div>
		<div id="fragment-2" class="param">
			<form id="communication_form">
				<div>
					<label for="serverIPAddr">信号接入主服务器IP地址：</label>
					<input id="serverIPAddr" name="utcsSystemParam.serverIPAddr" type="text" value="${utcsSystemParam.serverIPAddr}">
				</div> 
				<div>
					<label for="serverPort">信号接入主服务器通信端口:</label>
					<input  id="serverPort" name="utcsSystemParam.serverPort" type="text" value="${utcsSystemParam.serverPort}">
				</div>
				<div>
					<label for="localIPAddr">控制系统部署服务器IP地址:</label>
					<input  id="localIPAddr" name="utcsSystemParam.localIPAddr" type="text" value="${utcsSystemParam.localIPAddr}">
				</div>
				<div>
					<label for="localPort">控制系统部署服务器通信端口:</label>
					<input id="localPort" name="utcsSystemParam.localPort" type="text" value="${utcsSystemParam.localPort}">
				</div>
				<div style="display: none">
					<input  name="utcsSystemParam.systemName" type="text" value="${utcsSystemParam.systemName}">
					<input  name="utcsSystemParam.systemVersion" type="text" value="${utcsSystemParam.systemVersion}">
					<input   name="utcsSystemParam.supplier" type="text" value="${utcsSystemParam.supplier}">
					<input   name="utcsSystemParam.accreditCustomer" type="text" value="${utcsSystemParam.accreditCustomer}">
					<input    name="utcsSystemParam.paramId" type="text" value="${utcsSystemParam.paramId}">
				</div>
			</form>
			<div style="text-align:center">
				<input  id="communicationParam" style="margin-top:20px" class="ui-button " type="button" value="保存">
			</div>
		</div>
		<div id="fragment-3" class="param">
			<form id="net_form">
				<div>
					<label for="insidePort">内部协议监听端口：</label>
					<input id="insidePort" name="utcsNetWorkParam.insidePort" type="text" value="${utcsNetWorkParam.insidePort}">
				</div>
				<div>
					<label for="insideConNum">内部协议连接数:</label>
					<input  id="insideConNum" name="utcsNetWorkParam.insideConNum" type="text" value="${utcsNetWorkParam.insideConNum}">
				</div>
				<div>
					<label for="insideHJNum">内部协议有效心跳次数:</label>
					<input  id="insideHJNum" name="utcsNetWorkParam.insideHJNum" type="text" value="${utcsNetWorkParam.insideHJNum}">
				</div>
				<div>
					<label for="insideHJStep">内部协议心跳步长:</label>
					<input id="insideHJStep" name="utcsNetWorkParam.insideHJStep" type="text" value="${utcsNetWorkParam.insideHJStep}">
				</div>
				<div>
					<label for="gat1049Port">1049协议监听端口：</label>
					<input id="gat1049Port" name="utcsNetWorkParam.gat1049Port" type="text" value="${utcsNetWorkParam.gat1049Port}">
				</div>
				<div>
					<label for="gat1049ConNum">1049协议连接数:</label>
					<input  id="gat1049ConNum" name="utcsNetWorkParam.gat1049ConNum" type="text" value="${utcsNetWorkParam.gat1049ConNum}">
				</div>
				<div>
					<label for="gat1049HJNum">1049协议有效心跳次数:</label>
					<input  id="gat1049HJNum" name="utcsNetWorkParam.gat1049HJNum" type="text" value="${utcsNetWorkParam.gat1049HJNum}">
				</div>
				<div>
					<label for="gat1049HJStep">1049协议心跳步长:</label>
					<input id="gat1049HJStep" name="utcsNetWorkParam.gat1049HJStep" type="text" value="${utcsNetWorkParam.gat1049HJStep}">
				</div>
				<div>
					<label for="gat1049User">1049协议登录用户名:</label>
					<input  id="gat1049User" name="utcsNetWorkParam.gat1049User" type="text" value="${utcsNetWorkParam.gat1049User}">
				</div>
				<div>
					<label for="gat1049Pwd">1049协议登录密码:</label>
					<input id="gat1049Pwd" name="utcsNetWorkParam.gat1049Pwd" type="password" value="${utcsNetWorkParam.gat1049Pwd}">
				</div>
				<div style="display: none">
					<input name="utcsNetWorkParam.netId" type="text" value="${utcsNetWorkParam.netId}">
				</div>
			</form>
			<div style="text-align:center">
				<input  id="netParam" style="margin-top:20px" class="ui-button " type="button" value="保存">
			</div>
		</div>
	</div >
</body>
</html>