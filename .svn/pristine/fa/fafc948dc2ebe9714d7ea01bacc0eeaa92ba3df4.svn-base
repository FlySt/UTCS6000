<%@ page import="com.ncjk.utcs.common.servlet.CommonPro" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>城市交通信号控制系统</title>
<link href="common/images/logo.ico" rel="shortcut icon" type="image/x-icon" />
<link href="common/css/style.css" rel="stylesheet" type="text/css" />
<link href="common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<script src="common/js/jquery.js"></script>
<script src="common/js/cloud.js" type="text/javascript"></script>
<script type="text/javascript" src="common/js/dialog.js"></script>
<script type="text/javascript" src="common/js/jquery-ui.js"></script>
<script type="text/javascript">
	var path = "<%=path%>";

	$(function() {
		$('.loginbox').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 692) / 2
		});
		$(window).resize(function() {
			$('.loginbox').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 692) / 2
			});
		})
		$("#about").on("click",function () {
			$("#about_dialog").fadeIn(200);
		});
		$(".tiptop a").click(function(){
			$(".tip").fadeOut(200);
		});
		$("#help").on("click",function(){
			window.open('<%=path%>/modules/ocx/UTCS6000.pdf', 'newwindow', 'width='+(window.screen.availWidth)+',height='+(window.screen.availHeight)+',top=0,left=0,menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=no,resizable=no');
		})
	});
/*	function onAbout(){
		window.open('model/tools.jsp', 'popup', 'width=400,height=100,top=300,left=400,menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=no,resizable=no');
	}*/
	function onLogin(){
		var userAccount = $(".loginuser").val();
		var userPassword = $(".loginpwd").val();
	
		$.ajax( { 
	    	url:path+'/login_userLogin.action',   
	    	type:'post',    
	    	cache:false,
	    	dataType:'json',
	    	traditional : true,
	    	data:{
	    		userAccount:userAccount,
	    		userPassword:userPassword
	    	},
	    	success:function(data) {
	    		if(data.msg!=""){
	    			dialog.message(data.msg);
	    		}else{
	    			window.location='modules/main.jsp?powerModuleId=-1';
	    			/* window.open('modules/main.jsp', 'newwindow', 'width='+(window.screen.availWidth)+',height='+(window.screen.availHeight)+',top=0,left=0,menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=no,resizable=no');
	    			window.opener =null; //打开窗口之后关闭父窗口,对于火狐浏览器需要在地址栏输入about:config找到dom.allow_scripts_to_close_windows这一项将值改为true
	    			window.open("","_self");
	    			window.close(); */
	    		}
	    	}, 
	    	error : function() {    
	    		dialog.message("网络连接失败");  
	    	} 
	    });
	}
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
		if($("#message").is(":hidden")){
			if(e && e.keyCode==13){ // enter 键
				$(".loginbtn").click();
			}
		}
    };
</script>
</head>
<body  style="background-color: #1c77ac; background-image: url(common/images/light.png); background-repeat: no-repeat; background-position: center top; overflow: hidden;">
	<div id="mainBody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div>
	<div class="logintop">
		<span>欢迎登录—城市交通信号控制系统</span>
		<ul>
			<li><a id="help" href="#">帮助</a></li>
			<li><a href="#" id="about">关于</a></li>
		</ul>
	</div>
	<div class="loginbody">
		<span class="systemlogo"></span>
		<div class="loginbox">
			<ul>
				<li><input name="" type="text" class="loginuser" value="Admin" onclick="JavaScript:this.value=''" /></li>
				<li><input name="" type="password" class="loginpwd" value="123456" onclick="JavaScript:this.value=''" /></li>
				<li><input name="" type="button" class="loginbtn" value="登录"  onclick="onLogin()" /><label>Ver-1.0.0 build 2016.12.17</label>
			</ul>
		</div>
	</div>
	<div class="loginbm">版权所有&copy;南昌金科交通科技股份有限公司 2016</div>

	<div id="about_dialog" class="tip">
		<div class="tiptop"><span>关于UTC6000</span><a></a></div>
		<div class="tipinfo">
			<div>
				<img src="<%=path%>/common/images/logo_about.png">
			</div>
			<div style="margin-top:10px;">
				<span>版本:<%=CommonPro.version%></span>
			</div>
			<div style="margin-top:10px;">
				<span>南昌金科科技股份有限公司</span>
			</div>
			<div style="margin-top:10px;">
				<i>版权所有&copy;NCJK 2016</i>
			</div>
		</div>
	</div>
</body>
</html>