<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ncjk.utcs.common.jtp.*"%>
<%
	String path = request.getContextPath();
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
	var socket;
	if (!window.WebSocket) {
		window.WebSocket = window.MozWebSocket;
	}
	if (window.WebSocket) {
		socket = new WebSocket("ws://localhost:5000/ws");
		socket.onmessage = function(event) {
			var ta = document.getElementById('responseText');
			ta.value = ta.value + '\n' + event.data
		};
		socket.onopen = function(event) {
			var ta = document.getElementById('responseText');
			ta.value = "连接开启!";
		};
		socket.onclose = function(event) {
			var ta = document.getElementById('responseText');
			ta.value = ta.value + "连接被关闭";
		};
	} else {
		alert("你的浏览器不支持 WebSocket！");
	}
	function send(message) {
		if (!window.WebSocket) {
			return;
		}
		if (socket.readyState == WebSocket.OPEN) {
			socket.send(message);
		} else {
			alert("连接没有开启.");
		}
	}
</script>
</head>
<body>
	<form onsubmit="return false;">
		<h3>WebSocket</h3>
		<textarea id="responseText" style="width: 500px; height: 300px;"></textarea>
		<br>
		<input type="text" name="message"  style="width: 300px" value="Welcome to www.waylau.com">
		<input type="button" value="发送消息" onclick="send(this.form.message.value)">
		<input type="button" onclick="javascript:document.getElementById('responseText').value=''" value="清空聊天记录">
	</form>
</body>
</html>