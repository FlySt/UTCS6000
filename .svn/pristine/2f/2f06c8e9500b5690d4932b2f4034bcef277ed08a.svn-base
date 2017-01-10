<%@page import="com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	UtcsUser user = (UtcsUser)session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>left</title>
<link href="../../common/css/style.css" rel="stylesheet" type="text/css" />
<script src="../../common/js/jquery.js"></script>
<script src="<%=path%>/modules/main.js"></script> 

<script type="text/javascript">
	var path = "<%=path%>";
	$(function() {
		//左侧导航切换
		$(".leftmenu").on("click",".menuson .header",function(){
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
		})
/* 		// 三级菜单点击
		$('.sub-menus li').click(function(e) {
			$(".sub-menus li.active").removeClass("active")
			$(this).addClass("active");
		}); */
		$(".leftmenu").on("click",".title",function(){
			var $ul = $(this).next('ul');
			$('dd').find('.menuson').slideUp();
			if ($ul.is(':visible')) {
				$(this).next('.menuson').slideUp();
			} else {
				$(this).next('.menuson').slideDown();
			}
		})
		$(window).resize(function() {
			$(".leftmenu").css("height", $(window).height() - 128);//$(window).height()与document.documentElement.clientHeight相同
		})
		$(".leftmenu").css("height", $(window).height() - 128);

	})
</script>
</head>
<body style="background: #edf6fa;">
	<div style="background: #f0f9fd;">
		<input id="powerModuleId" value="${param.powerModuleId }" type="hidden" />
		<input id="level" value="${param.level }" type="hidden" />
		<div class="lefttop">
			<span></span>欢迎您：<%=user.getUserAccount() %>
		</div>
		<dl class="leftmenu">
		</dl>
	</div>
</body>
</html>