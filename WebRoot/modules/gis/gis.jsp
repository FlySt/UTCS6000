<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>gis</title>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/ol3/ol.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyleEx.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/common/js/jquery.js"></script>
<script src="<%=path%>/common/js/jquery-ui.js"></script>
<script src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script src="<%=path%>/common/js/dialog.js"></script>
<script src="<%=path%>/common/js/ol3/ol.js"></script>
<script src="drawicon.js"></script>
<script src="element.js"></script>
<script src="gis.js"></script>
<script src="measure.js"></script>
<script src="popup.js"></script>
<script src="gisInit.js"></script>
</head>
<body>
    <div class="place">
        <input id="powerModuleId" value="${param.powerModuleId }" type="hidden" />
        <input id="level" value="${param.level }" type="hidden" />
        <span>位置：</span>
        <ul class="placeul">
            <li>首页</li>
            <li>电子地图</li>
        </ul>
    </div>
    <div class="map">
        <div id="map">
        </div>
    </div>
    <div id="popup" class="ol-popup" style="width: 250px">
        <a href="#" id="popup-closer" class="ol-popup-closer"></a>
        <div id="popup-title" class="popup-title"></div>
        <div id="popup-content" class="popup-content">
            <form class="info">
                <div>
                    <label for="signalControlerNum">信号机编号:</label>
                    <input readonly="readonly" id="signalControlerNum" >
                </div>
                <div>
                    <label for="deviceIp">信号机IP地址:</label>
                    <input readonly="readonly" id="deviceIp">
                </div>
                <div>
                    <label for="crossName">所属路口:</label>
                    <input readonly="readonly" id="crossName">
                    <input id="crossId" style="display: none">
                </div>
                <div>
                    <label for="serverNo">所属服务器:</label>
                    <input readonly="readonly" id="serverNo">
                </div>
                <div>
                    <label for="type">规格型号:</label>
                    <input readonly="readonly" id="type">
                </div>
                <div>
                    <label for="signalState">信号机状态:</label>
                    <input readonly="readonly" id="signalState">
                </div>
                <input style="display: none" id="signalType">
                <input style="display: none" id="signalControlerId">
                <input style="display: none" id="signalControlerName">
            </form>
            <div>
                <button  style="font-size:1em;padding: 0.1em 1em; display: inline-block" id="realTime" class="ui-button ui-widget ui-corner-all">实时信息</button>
                <button style="font-size:1em;padding: 0.1em 1em;;display: inline-block" id="config" class="ui-button ui-widget ui-corner-all">信号机配置</button>
            </div>
            <input style="display:none" id="featureId"/>
        </div>
    </div>
    <div id="add-element-dialog" style="display: none" >
        <iframe id="iframe_dialog" name="iframe_dialog"  style="position:absolute; height: 100%;width:100%" class="dialog-iframe" src="" ></iframe>
    </div>
    <div id="right_toolbar" class="right_toolbar">
        <ul class="menu">
            <li><a id="location" href="#">定位</a>
            </li>
     <%--       <li><a href="#">显示</a>
                <ul>
                    <li><a href="#" class="signal">信号机</a></li>
                    <li><a href="#" class="cross">路口</a></li>
                </ul>
            </li>--%>
            <li><a id="tool" href="#">工具</a>
                <ul>
                    <li><a id="measure_distance" href="#" class="distance">测距离</a></li>
                    <li><a id="measure_area" href="#" class="area">测面积</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <div id="menuContent" class="menuContent" >
        <ul id="leftTree" class="ztree" style="margin-top:0; width:200px; height: 300px;"></ul>
    </div>
    <div id="gridContainer" style="width:100%;height: 90%;">
        <div>
            <object id="showTable" classid="clsid:96004938-def8-498d-a1fc-6a8c4328a191" style="height: 90%">
            </object>
        </div>
    </div>
    <div style="width:800px; height:750px;">
        <OBJECT ID="UTCSOcx" CLASSID="CLSID:641EAB80-11CE-41DB-A11C-6823980DF786" style="width:800px; height:750px; background-color: #FFFFFF;"></OBJECT>
    </div>
</body>
</html>