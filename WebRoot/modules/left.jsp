<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>left</title>
<link href="../common/css/style.css" rel="stylesheet" type="text/css" />
<script src="../common/js/jquery.js"></script>
<script type="text/javascript">
	var clientHeight;
	$(function() {
		//页面加载
		$(document).ready(function() {
			//alert("dd");
			$("#frameContent").css("src","resourceManage/dept.jsp");
			//$("#dept").click();
			$("#liSignalReal").addClass("active");
			

		});	
		

		//左侧导航切换
		$(".menuson .header").click(function() {
			var $parent = $(this).parent();
			$(".menuson>li.active").not($parent).removeClass("active open").find('.sub-menus').hide();
			$parent.addClass("active");
			if (!!$(this).next('.sub-menus').size()) {
				if ($parent.hasClass("open")) {
					$parent.removeClass("open").find('.sub-menus').hide();
				} else {
					$parent.addClass("open").find('.sub-menus').show();
				}
			}
		});
		// 三级菜单点击
		$('.sub-menus li').click(function(e) {
			$(".sub-menus li.active").removeClass("active")
			$(this).addClass("active");
		});
		$('.title').click(function() {
			var $ul = $(this).next('ul');
			$('dd').find('.menuson').slideUp();
			if ($ul.is(':visible')) {
				$(this).next('.menuson').slideUp();
			} else {
				$(this).next('.menuson').slideDown();
			}
		});
		$(window).resize(function() {
			$(".leftmenu").css("height", $(window).height() - 128);//$(window).height()与document.documentElement.clientHeight相同
		})
		$(".leftmenu").css("height", $(window).height() - 128);

	})
</script>
</head>
<body style="background: #edf6fa;">
	<div style="background: #f0f9fd;">
		<div class="lefttop">
			<span></span>欢迎您：Admin
		</div>
		<dl class="leftmenu">
			<%
				String menuId = request.getParameter("menuId");
				if (menuId.equals("2")) {
			%>
			<dd>
				<div class="title">
					<span></span>电子地图应用
				</div>
				<ul class="menuson">
					<li id="liSignalReal">
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">信号机实时信息</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">信号机管理</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">警卫任务</a><i></i>
						</div>
					</li>
				</ul>
			</dd>
			<%
				} else if (menuId.equals("3")) {
			%>

			<%
				} else if (menuId.equals("4")) {
			%>
			<dd>
				<div class="title">
					<span></span>基础信息
				</div>
				<ul class="menuson">
					<li class="active">
						<div class="header">
							<cite></cite><a id="dept" href="resourceManage/dept.jsp" target="frameContent">部门管理</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="resourceManage/userGroup.jsp" target="frameContent">用户组管理</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">用户管理</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">权限管理</a><i></i>
						</div>
					</li>
				</ul>
			</dd>
			<dd>
				<div class="title">
					<span></span>资源管理
				</div>
				<ul class="menuson">
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">区域管理</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">子区管理</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">路口管理</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">服务器配置</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">信号机管理</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">其他设备管理</a><i></i>
						</div>
					</li>
				</ul>
			</dd>
			<%
				} else if (menuId.equals("5")) {
			%>
			<dd>
				<div class="title">
					<span></span>综合查询
				</div>
				<ul class="menuson">
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">车流量查询</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">信号机日志查询</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">平台操作日志</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">系统运行日志</a><i></i>
						</div>
					</li>
				</ul>
			</dd>
			<%
				} else if (menuId.equals("6")) {
			%>
			<dd>
				<div class="title">
					<span></span>系统配置
				</div>
				<ul class="menuson">
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">电子地图配置</a><i></i>
						</div>
					</li>
					<li>
						<div class="header">
							<cite></cite><a href="right.html" target="frameContent">系统参数配置</a><i></i>
						</div>
					</li>
				</ul>
			</dd>
			<%
				}
			%>
		</dl>
	</div>
	<div>
		<iframe id="frameContent" src="resourceManage/dept.jsp" name="frameContent" style="frameborder: 0; overflow: hidden; height: 99%; width: 100%"></iframe>
	</div>
</body>
</html>