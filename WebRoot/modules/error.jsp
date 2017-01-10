<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>错误页面</title>
<link href="../common/css/style.css" rel="stylesheet" type="text/css" />
<script src="../common/js/jquery.js"></script>
<script>
	$(function() {
		$('.error').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 490) / 2
		});
		$(window).resize(function() {
			$('.error').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 490) / 2
			});
		})
	});
</script>
</head>
<body style="background: #edf6fa;">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li>首页</li>
			<li>404错误提示</li>
		</ul>
	</div>
	<div class="error">
		<h2>非常遗憾，您访问的页面不存在！</h2>
		<p>请联系技术人员！</p>
	</div>
</body>
</html>