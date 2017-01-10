<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ncjk.utcs.common.jtp.*"%>
<%
	String path = request.getContextPath();
	String e = System.getProperty("catalina.home");
%>
<!DOCTYPE html>
<html>
<head>
<title>JinKe'TCP Communication Test.</title>
<script src="common/js/jquery.js"></script>
<style>
#customers {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
}

#customers td, #customers th {
	font-size: 1em;
	border: 1px solid #98bf21;
	padding: 3px 7px 2px 7px;
}

th {
	font-size: 1.1em;
	text-align: left;
	padding-top: 5px;
	padding-bottom: 4px;
	background-color: #A7C942;
	color: #ffffff;
}

#curstomers tr.alt td {
	color: #000000;
	background-color: #EAF2D3;
}

.box textarea {
	width: 99%;
	height: 280px;
	padding: 2px 0.75%;
	margin-left: -6px;
	border: 1px solid #cd0000;
	overflow: auto;
}

.box textarea:-moz-read-write {
	width: calc(100% - 6px);
	padding: 2px 3px;
}
</style>
<script src="common/js/jquery.js"></script>
<script>
	var path = "<%=path%>";
	var url = "<%=e%>";
	function clearLog() {
		$("#info").html("");
	}

	function sendAjax(msg){
		$.ajax({
			url:path+'/jtpServer_send.action',
			data:{
                flag:1,
				content:1
			},
			dataType:'json',
			type:'post',
			success : function(response) {
				//$("#info").html(response.result);
            //   alert(response.content);
			}
		});
	}
	function cmd15_onclick(){
		$.ajax({
			url:path+'/jtpServer_logout.action',
			data:{
			},
			dataType:'json',
			type:'post',
		});
	}
	function cmd16_onclick(){
		$.ajax({
			url:path+'/jtpServer_changeSysout.action',
			data:{
				isOut:false
			},
			dataType:'json',
			type:'post',
		});
	}
	function cmd17_onclick(){
		$.ajax({
			url:path+'/jtpServer_changeSysout.action',
			data:{
				isOut:true
			},
			dataType:'json',
			type:'post',
		});
	}
	function cmd18_onclick(){
		$.ajax({
			url:path+'/jtpServer_timer.action',
			data:{
			},
			dataType:'json',
			type:'post',
			success : function(response) {
				$("#info").html(response.result);
			}
		});
	}
	function cmd19_onclick(){
		console.log("cmd19");
		$.ajax({
			url:path+'/jtpServer_changePingState.action',
			data:{
				state:0
			},
			dataType:'json',
			type:'post',
		});
	}
	function cmd20_onclick(){
		$.ajax({
			url:path+'/jtpServer_changePingState.action',
			data:{
				state:1
			},
			dataType:'json',
			type:'post',
		});
	}
	function sendCmd(){
		$.ajax({
			url:path+'/jtpServer_sendCustom.action',
			data:{
				xml:$("#send").val()
			},
			dataType:'json',
			type:'post',
			success : function(response) {
				$("#info").html(response.result);
			}
		});
	}
	function cmd1_onclick(){
		$.ajax({
			url:path+'/jtpServer_down.action',
			data:{
			},
			dataType:'json',
			type:'post',
		});
	}
	function ocxSend(msg){
		$.ajax({
			url:path+'/jtpServer_sendCustom.action',
			data:{
				msg:msg
			},
			dataType:'json',
			type:'post',
			success : function(response) {
				$("#info").html(response.result);
			}
		});
	}
</script>
</head>
<body>
	<h2 style="text-align: center;">信号机应用平台TCP通信测试模块</h2>
	<table id="customers">
		<tr>
			<th colspan="5">信号机控制系统与信号接入服务器通信-测试命令</th>
		</tr>
		<tr>
			<td><input id="cmd1" type="button" value="测试" onclick="cmd1_onclick()" /></td>
			<td><input id="cmd2" type="button" value="公共同步时间" onclick="cmd2_onclick()" /></td>
			<td><input id="cmd3" type="button" value="时基调度表行数" onclick="cmd3_onclick()"/></td>
			<td><input id="cmd4" type="button" value="命令4-获取信号机参数" onclick="cmd4_onclick()"/>
				<label>命令个数:</label>
				<select id="cmdnum">
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
				</select>
			</td>
			<td><input id="cmd5" type="button" value="命令5-获取信号机参数" /></td>
		</tr>
		<tr>
			<td><input id="cmd6" type="button" value="命令6-获取信号机参数" /></td>
			<td><input id="cmd7" type="button" value="命令7-获取信号机参数" /></td>
			<td><input id="cmd8" type="button" value="命令8-获取信号机参数" /></td>
			<td><input id="cmd9" type="button" value="命令9-获取信号机参数" /></td>
			<td><input id="cmd10" type="button" value="命令10-获取信号机参数" /></td>
		</tr>
		<tr>
			<td><input id="cmd11" type="button" value="命令11-获取信号机参数" /></td>
			<td><input id="cmd12" type="button" value="命令12-获取信号机参数" /></td>
			<td><input id="cmd13" type="button" value="命令13-获取信号机参数" /></td>
			<td><input id="cmd14" type="button" value="命令14-获取信号机参数" /></td>
			<td><input id="cmd15" type="button" value="GAT协议系统断开/退出" onclick="cmd15_onclick()"/></td>
		</tr>
		<tr>
			<td><input id="cmd16" type="button" value="关闭服务端调试输出信息" onclick="cmd16_onclick()"/></td>
			<td><input id="cmd17" type="button" value="打开服务端调试输出信息" onclick="cmd17_onclick()"/></td>
			<td><input id="cmd18" type="button" value="内部协议校时" onclick="cmd18_onclick()"/></td>
			<td><input id="cmd19" type="button" value="中断心跳包" onclick="cmd19_onclick()"/></td>
			<td><input id="cmd20" type="button" value="开启心跳包" onclick="cmd20_onclick()"/></td>
		</tr>
		<tr>
			<td colspan="5" style="height: 30px;"></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<th colspan="5">信号机控制系统与信号接入服务器通信-测试信息打印&nbsp;&nbsp;<input id="cmbClear" type="button" value="清空日志" onclick="clearLog()" /></th>
			<th colspan="5">信号机控制系统与信号接入服务器通信-测试输入信息&nbsp;&nbsp;<input id="xml" type="button" value="发送命令" onclick="sendCmd()" /></th>
		</tr>
		<tr>
			<td colspan="5"><div class="box">
					<textarea id="info" rows="20"></textarea>
				</div></td>
			<td colspan="5"><div class="box">
				<textarea id="send" rows="20"><Body>\n\n\</Body></textarea>
			</div></td>
		</tr>
	</table>
</body>
</html>