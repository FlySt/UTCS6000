<%@ page import="com.ncjk.utcs.common.servlet.CommonPro" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>城市交通信号控制系统</title>
<link href="<%=path%>/common/images/logo.ico" rel="shortcut icon" type="image/x-icon" />
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script src="main.js"></script>
<script type="text/javascript">
var path = "<%=path%>";
	$(function() {
        getSignalDevice();
		//顶部导航切换
		$(".nav ").on("click","li a",function(){
			$(".nav li a.selected").removeClass("selected")
			$(this).addClass("selected");
		});
		$("#about").on("click",function () {
            $("#about_dialog").fadeIn(200);
		});
        $(".topright ul").on("click","#logout",function(){
            $("#logout_dialog").fadeIn(200);
        });
        $(".tiptop a").click(function(){
            $(".tip").fadeOut(200);
        });
        $(".ocx-tiptop a").click(function(){
            $(".ocx-tip").fadeOut(200);
        });
        $(".sure").click(function(){
            $(".tip").fadeOut(100);
            window.location = path+'/login.jsp';
        });
        $(".cancel").click(function(){
            $(".tip").fadeOut(100);
        });
        $("#ocx").on("click",function(){
            $("#controlDownload").fadeIn(200);
        });
        $("#help").on("click",function(){
            window.open('<%=path%>/modules/ocx/UTCS6000.pdf', 'newwindow', 'width='+(window.screen.availWidth)+',height='+(window.screen.availHeight)+',top=0,left=0,menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=no,resizable=no');
        })
	});
function getSignalDevice(){
    $.ajax( {
        url:path+'/signal_initSignalActiveX.action',
        type:'post',
        cache:false,
        dataType:'text',
        success:function(data) {
            var v=document.getElementById("JKCommon");
            if(v){
                var result=v.Start(data);
            }
        },
        error : function() {
            alert("异常！");
        }
    });
}

</script>
</head>
<body style="background: url(../common/images/topbg.gif) repeat-x;">
	<div id="frameTop">
		<input id="powerModuleId" value="${param.powerModuleId }" type="hidden" />
        <div class="topleft">
            <img src="../common/images/logo.png" title="系统首页" />
        </div>
		<ul class="nav">
			<li><a href="<%=path%>/modules/gis/gis.jsp" target="frameMain" class="selected"><img src="../common/images/icon25.png" title="首&nbsp;&nbsp;页" />
					<h2>首&nbsp;&nbsp;页</h2></a></li>
		</ul>
		<div class="topright">
			<ul>
                <li><a href="#" id="ocx">控件下载</a></li>
				<li><a href="#" id="help">帮助</a></li>
				<li><a href="#" id="about">关于</a></li>
				<li><a href="#" id="logout">退出</a></li>
			</ul>
		</div>
	</div>
	<div id="about_dialog" class="tip ui-corner-all">
        <div class="tiptop ui-dialog-titlebar ui-corner-all ui-widget-header"><span>关于UTC6000</span><a></a></div>
        <div class="tipinfo">
            <div>
                <img src="../common/images/logo_about.png">
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
    <div class="ui-dialog" style="position:absolute;display:none;height: auto; width: 300px; top: 92.5px; left: 346px; z-index: 101;">
        <div class="ui-dialog-titlebar">
            <span id="ui-id-2" class="ui-dialog-title">部门删除</span>
        </div>
        <div style="width: auto; min-height: 70px; max-height: none; height: auto;" class="ui-dialog-content ui-widget-content">
            <span id="spanmessage" style="display:block;line-height:20px;text-align:center;margin-top:20px">确认删除选中的部门？</span>
        </div>
    </div>
	<div class="frameMain">
		<iframe id="frameMain" src="<%=path%>/modules/gis/gis.jsp" name="frameMain"> </iframe>
	</div>
	<div id="frameBottom">
		<div class="footer">
			<span>欢迎使用 【城市交通信号控制系统】</span> <i>版权所有&copy;NCJK 2016</i>
		</div>
	</div>

    <div id="logout_dialog" class="tip ui-corner-all .ui-dialog-content">
        <div class="tiptop ui-dialog-titlebar ui-corner-all ui-widget-header"><span>系统提示</span><a></a></div>

        <div class="tipinfo .ui-dialog-content">
            <div class="tipright">
                <p>是否确认退出系统 ？</p>
                <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
            </div>
        </div>
        <div class="tipbtn">
            <input name="" type="button"  class="sure" value="确定" />&nbsp;
            <input name="" type="button"  class="cancel" value="取消" />
        </div>
    </div>
    <div class="nav">
        <div class="main-nav"></div>
        <div style="display: none">
            <object id="JKCommon" classid="clsid:78A94A2B-CAEE-4419-B2B4-6E9E1571244A"> </object>
        </div>
        <div class="sub-nav"></div>
    </div>
    <div id="controlDownload"  class="ocx-tip ui-corner-all">
        <div class="ocx-tiptop ui-dialog-titlebar ui-corner-all ui-widget-header"><span>控件下载</span><a></a></div>
        <div class="ocx-tipinfo">
            <ul>
                <li><a style="cursor: pointer;" href="<%=path%>/modules/ocx/dotNetFx40_Full_x86_x64.exe">dotNet4.0安装环境</a></li>
                <li><a style="cursor: pointer;" href="<%=path%>/modules/ocx/dotNetFx40LP_Full_x86_x64zh-Hans.exe">dotNet4.0汉化包</a></li>
                <li><a style="cursor: pointer;" href="<%=path%>/modules/ocx/JKSignalOcx(V5.0.1).msi">非国标信号机控件下载</a></li>
                <li><a style="cursor: pointer;" href="<%=path%>/modules/ocx/Setup.exe">国标信号机控件下载</a></li>
            </ul>
        </div>
    </div>
</body>
</html>