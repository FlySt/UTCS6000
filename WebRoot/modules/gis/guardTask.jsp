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
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/common/js/jquery.js"></script>
<script src="<%=path%>/common/js/jquery-ui.js"></script>
<script src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script src="<%=path%>/common/js/dialog.js"></script>
<script src="<%=path%>/common/js/ol3/ol.js"></script>
<script src="<%=path%>/common/js/jquery.dataTables.js"></script>
<script src="guardTask.js"></script>
<script src="planManger.js"></script>
<script src="planAudit.js"></script>
<script src="guard.js"></script>
<script src="drawPlan.js"></script>
<script src="drawLine.js"></script>
<script src="math.js"></script>
<script src="element.js"></script>
<script src="drawicon.js"></script>
<%--<script src="measure.js"></script>--%>
<script src="gisInit.js"></script>
<script src="style.js"></script>
</head>
<body>
    <div style="display: none">
        <input id="powerModuleId" value="${param.powerModuleId }" type="hidden" />
        <input id="level" value="${param.level }" type="hidden" />
    </div>
    <div style="width: 100%;height: 100%">
        <div style="width: 200px;height: 100%;float: left;border: solid 1px #4169e1" >
            <div  style="display: none" class="guard_table" id="manger_table">
                <table  id="manger" class="gis_table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>描点集合</th>
                        <th>名称</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div style="display: none" class="guard_table" id="audit_table">
                <table  id="audit" class="gis_table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>描点集合</th>
                        <th>名称</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div style="display: none" class="guard_table" id="guard_table">
                <table  id="guard" class="gis_table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>描点集合</th>
                        <th>名称</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div style="position: absolute;top: 400px;left: 0;right: 0;bottom: 0">
                <h2 style="color: #1c77ac;margin-left: 5px">方案信息：</h2>
                <div id="guardInfo">

                </div>
            </div>
        </div>
        <div id="tabs"  style="position: absolute;left: 200px;right: 0;top: 1px;bottom: 0;">
            <ul>
                <li id="planManger" class="guard_tab"><a href="#planMap"><span>预案管理</span></a></li>
                <li id="planAudit" class="guard_tab"><a href="#planMap"><span>预案审核</span></a></li>
                <li id="guardTask" class="guard_tab"><a href="#planMap"><span>警卫任务</span></a></li>
                <span style="color:red;margin-left: 50px" class="guard-tip">* 右键点击地图添加方案</span>
            </ul>
            <div id="planMap" class="planMap" >
                <div id="map">
                </div>
            </div>
        </div>
    </div>
 <%--   <div id="right_toolbar" class="right_toolbar" style="top:50px">
        <ul class="menu" style="width: 80px">
            <li><a id="tool" href="#">工具</a>
                <ul>
                    <li><a id="measure_distance" href="#" class="distance">测距离</a></li>
                    <li><a id="measure_area" href="#" class="area">测面积</a></li>
                </ul>
            </li>
        </ul>
    </div>--%>
    <div id="popup" class="ol-popup">
        <a href="#" id="popup-closer" class="ol-popup-closer"></a>
        <div id="popup-title" class="popup-title"></div>
        <div id="popup-content" class="popup-content">
            <ul>
                <li ><a href="#" class="add" >添加方案</a></li>
                <li><a href="#" class="can" >取消</a></li>
            </ul>
            <input style="display:none" id="featureId"/>
        </div>
    </div>
    <div id="new_guard" title="添加新方案">
        <div style="margin-top: 10px;margin-left: 10px">
            <label for="guardName">方案名称:</label>
            <input id="guardName" type="text" style="line-height: 25px;height:25px;width: 180px" class="text ui-widget-content ui-corner-all">
        </div>
    </div>
    <div id="pointInfo" title="设置信息">
        <div style="margin-top: 10px;margin-left: 10px">
            <label for="lastToTime" style="width: 150px;display: inline-block">上个一路到达改点所需时间:</label>
            <input id="lastToTime" type="text" style="line-height: 25px;height:25px;width: 150px" class="text ui-widget-content ui-corner-all">
            <label>(单位:秒)</label>
            <input id="guardSignalId" type="text" style="display: none;">
        </div>
        <div style="margin-top: 10px;margin-left: 10px">
            <label for="passTime" style="width: 150px;display: inline-block">经过该路口所需时间:</label>
            <input id="passTime" type="text" style="line-height: 25px;height:25px;width: 150px" class="text ui-widget-content ui-corner-all">
            <label>(单位:秒)</label>
        </div>
        <div style="margin-top: 10px;margin-left: 10px">
            <label for="direction" style="width: 150px;display: inline-block">方向:</label>
            <select id="direction" type="text" style="line-height: 25px;height:25px;width: 150px" class="text ui-widget-content ui-corner-all">
                <option value="0">由东向西</option>
                <option value="1">由西向东</option>
                <option value="2">由南向北</option>
            </select>
        </div>
        <div style="margin-top: 10px;margin-left: 10px">
            <label for="isAdd" style="width: 150px;display: inline-block">是否将该路口加入该方案:</label>
            <select id="isAdd" type="text" style="line-height: 25px;height:25px;width: 150px" class="text ui-widget-content ui-corner-all">
                <option value="0">是</option>
                <option value="1">否</option>
            </select>
        </div>
    </div>
</body>
</html>