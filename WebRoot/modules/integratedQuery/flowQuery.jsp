<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
   String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统操作日志</title>
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyleEx.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.dataTables.js"></script>
<%--<script type="text/javascript" src="<%=path%>/common/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/datepicker.js"></script>--%>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/modules/main.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/echarts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
	var path = "<%=path%>" ;
	var myChart = null;
	var option = null;
	var value = 0;
	$(function() {
		init();
		$(".query_time").checkboxradio();
		$(".spinner").spinner({
			step:1,
			min:0,
			max:24
		});
		$("input[type='radio']").on("click",function(){
			radioInit();
		});
		$("#crossDate").datepicker({
			changeMonth:true,//是否使用下拉选择月份
			changeYear:true,//是否使用下拉选择年份
			showOn:'both',
			buttonImage:path+'/common/images/calendar.png',
			buttonImageOnly:true,
			buttonText:"选择日期",
			dateFormat: 'yy-mm-dd',
			monthNamesShort:["1","2","3","4","5","6","7","8","9","10","11","12"],
			onChangeMonthYear: function(date) {
				var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
				var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
				var day = $("#ui-datepicker-div .ui-datepicker-calendar :selected").val();
				if(value==0){
					$(this).datepicker('setDate', new Date(year, month, day));
				}else if(value==1){
					$(this).datepicker('setDate', new Date(year, month));
				}else if(value==2){
					$(this).datepicker('setDate', new Date(year));
				}
			},
		});
		// 基于准备好的dom，初始化echarts实例
		myChart = echarts.init(document.getElementById('charts'));
		option = {
			title: {
				text: '交通流统计',
				subtext: '',
				x: 'center'
			},
			tooltip: {
				trigger: 'axis',
				axisPointer: {
					animation: false
				}
			},
			legend: {
				data:['东左','东直','东右','南左','南直','南右','西左','西直','西右','北左','北直','北右'],
				left: '6%',
				top:'bottom',
			},
			toolbox: {
				feature: {
					restore: {},
					saveAsImage: {}
				}
			},
			grid: {
				left: '3%',
				right: '4%',
				bottom: '10%',
				containLabel: true
			},
			xAxis :
			{
				type : 'category',
				boundaryGap : false,
				axisLine: {onZero: true},
				//data:timeData //["00:00","01:00","02:00","03:00","04:00","05:00","06:00","00:00","00:00"]
				data:["00:00","01:00","02:00","03:00","04:00","05:00","06:00","00:00","00:00","00:00","00:80","01:00","02:00","03:00","04:00","05:00","06:00","00:00","00:00","00:00"]
			},
			yAxis :
			{
				name : '交通流量(辆)',
				boundaryGap: [0, '10%'],
				splitLine: {
					show: false
				}
			},
			series : [
				{
					name:'东直',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[10,20,30,40,50,30,40,60,10,20,10,20,30,40,50,30,40,60,10,20]
				},
				{
					name:'东左',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[10,20,40,90,50,30,20,60,10,30,10,20,30,40,50,30,40,60,10,20]
				},
				{
					name:'东右',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,20,40,30,50,30,20,60,40,30,60,20,30,40,70,30,40,60,10,20]
				},
				{
					name:'南左',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,20,40,30,50,30,20,60,40,50,60,20,30,40,70,30,40,60,10,20]
				},
				{
					name:'南直',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,60,40,30,50,30,20,60,40,30,60,20,30,40,70,30,40,60,10,20]
				},
				{
					name:'南右',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,20,40,30,50,30,20,60,40,30,60,20,30,40,70,30,40,60,60,20]
				},
				{
					name:'西左',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,20,40,30,50,30,20,60,40,30,60,20,90,40,70,30,40,60,10,20]
				},
				{
					name:'西直',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,20,40,30,50,30,20,60,40,30,60,20,30,40,70,60,40,60,10,20]
				},
				{
					name:'西右',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[80,20,40,30,50,30,20,60,40,30,60,20,30,40,70,30,40,60,10,20]
				},
				{
					name:'北左',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,20,80,30,50,30,20,60,40,30,60,20,30,40,70,30,40,60,10,20]
				},
				{
					name:'北直',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,20,40,80,50,30,20,60,40,30,60,20,30,40,70,30,40,60,10,20]
				},
				{
					name:'北右',
					type:'line',
					smooth:true,
					symbol: 'none',
					hoverAnimation: false,
					data:[20,20,40,30,80,30,20,60,40,30,60,20,30,40,70,30,40,60,10,20]
				},
			]
		};
		myChart.setOption(option);
		$("#search").on("click", function () {
			if(value==0){
				queryDataByTime();
			}else if(value==1){
				queryOneMonthData();
			}else if(value==2){
				queryOneYearData();
			}
		});
	});
	function queryDataByTime(){
		var startTime = $("#startTime").val()+":00:00";
		var endTime = $("#endTime").val()+":00:00";
		$.ajax({
			url: path + '/trafficData_queryTrafficDataByTime.action',
			type: 'get',
			dataType: 'json',
			data: {
				"crossId": $("#crossId").val(),
				"crossDate": $("#crossDate").val(),
				"startTime": startTime,
				"endTime": endTime
			},
			success: function (response) {
				if(response.data!=null){
					anyliseData(response.data);
				}
			}
		})
	};
	function queryOneMonthData(){
		$.ajax({
			url: path + '/trafficData_queryTrafficDayData.action',
			type: 'get',
			dataType: 'json',
			data: {
				"crossId": $("#crossId").val(),
				"month": $("#crossDate").val(),
			},
			success: function (response) {
				if(response.data!=null){
					anyliseData(response.data);
				}
			}
		})
	}
	function queryOneYearData(){
		$.ajax({
			url: path + '/trafficData_queryTrafficYearData.action',
			type: 'get',
			dataType: 'json',
			data: {
				"crossId": $("#crossId").val(),
				"year": $("#crossDate").val(),
			},
			success: function (response) {
				if(response.data!=null){
					anyliseData(response.data);
				}
			}
		})
	}
	function anyliseData(data){
		var xData = [];
		var eastLeftData = [];
		var eastStraightData = [];
		var eastRightData = [];
		var southLeftData = [];
		var southStraightData = [];
		var southRightData = [];
		var westLeftData = [];
		var westStraightData = [];
		var westRightData = [];
		var northLeftData = [];
		var northStraightData = [];
		var northRightData = [];
		for(var i=0;i<data.length;i++){
			if(value==0){
				xData.push(data[i].crossTime);
			}else{
				xData.push(data[i].crossDate);
			}
			eastLeftData.push(data[i].eastLeft);
			eastStraightData.push(data[i].eastStraight);
			eastRightData.push(data[i].eastRight);
			southLeftData.push(data[i].southLeft);
			southStraightData.push(data[i].southStraight);
			southRightData.push(data[i].southRight);
			westLeftData.push(data[i].westLeft);
			westStraightData.push(data[i].westStraight);
			westRightData.push(data[i].westRight);
			northLeftData.push(data[i].northLeft);
			northStraightData.push(data[i].northStraight);
			northRightData.push(data[i].northRight);
		}
		myChart.setOption({
			xAxis:{
				data:xData
			},
			series: [{data: eastLeftData},{data: eastStraightData}, {data: eastRightData},
				{data: southLeftData}, {data: southStraightData}, {data: southRightData},
				{data: westLeftData},{data: westStraightData},{data: westRightData},
				{data: northLeftData},{data: northStraightData},{data: northRightData},
			]
		})
	}
	function init(){
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
		radioInit();
	}
	function radioInit(){
		value = $("input[type='radio']:checked").val();
		if(value==0){
			$('#crossDate').datepicker('option', 'dateFormat', 'yy-mm-dd');
			$("#time-div").show();
		}else if(value==1){
			$('#crossDate').datepicker('option', 'dateFormat', 'yy-mm');
			$("#time-div").hide();
		}else if(value==2){
			$('#crossDate').datepicker('option', 'dateFormat', 'yy');
			$("#time-div").hide();
		}
	}
	// 显示树的位置
	function showCrossMenu()
	{
		var inputOffset = $("#crossName").offset();
		var left = parseInt(inputOffset.left) ;
		var top = parseInt(inputOffset.top + $("#crossName").outerHeight());
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
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li>综合查询</li>
			<li>交通流查询</li>
		</ul>
	</div>
	<div>
		<div style="margin-top: 100px;margin-left: 30px">
			<form>
				<div class="tra-div">
					<label for="crossName">路口名称:</label>
					<input type="text" id="crossName"  class="crossName" onclick="showCrossMenu()" readOnly="readonly"/>
					<input type="text" id="crossId"   style="display: none"/>
				</div>
				<div id="crossContent" class="ztree_hide" style="width:200px;height:200px;">
					<ul id="crossTree" class="ztree"></ul>
				</div>
				<div class="tra-div">
					<label>查询时间:</label>
					<label for="day">天</label>
					<input class="query_time" type="radio" checked="checked" name="radio-1" id="day" value="0">
					<label for="month">月</label>
					<input class="query_time" type="radio" name="radio-1" id="month" value="1">
					<label for="year">年</label>
					<input class="query_time" type="radio" name="radio-1" id="year" value="2">
				</div>
				<div class="tra-div" id="crossDate-div">
					<label>查询日期:</label>
					<input class="crossDate" id="crossDate" type="text" value="<%=(new SimpleDateFormat("yyyy-MM-dd").format(new Date())) %>"/>
				</div>
				<div class="tra-div" id="time-div">
					<label>查询时段:</label>
					<input id="startTime" class="spinner" style="width: 30px;" value="0"><span>时到</span>
					<input id="endTime" class="spinner" style="width: 30px" value="24"><span>时</span>
				</div>
			</form>
			<div style="margin-top: 20px">
				<input id="search" class="ui-button button" type="button" value="查询"/>
			</div>
		</div>
		<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
		<div id="charts" style="position:absolute;top:50px;left: 280px;bottom: 0;right: 0; width: 800px;height:500px;"></div>
	</div>
</body>
</html>