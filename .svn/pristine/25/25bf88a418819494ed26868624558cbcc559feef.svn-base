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
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/ol3/ol.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script src="drawicon.js"></script>
<script src="element.js"></script>
<script type="text/javascript" >
    var map;
    var layersName = "shanggai";
    var ipAddr = "192.168.200.244";
    var lon = 115.81451;
    var lat = 28.69705;
    var port;
    var coordinate;
    $(function(){
        paramInit();
        mapInit();
        popupInit();
    })
    function paramInit(){
        $.ajax({
            url:path+'/traffic_queryMapParams.action',
            dataType:"json",
            type:"post",
            async:false,
            data:{
            },
            success:function(response){
                if(response.msg){
                    layersName = response.layers;
                    ipAddr = response.ipAddr;
                    port = response.port;
                    lon = response.lon;
                    lat = response.lat;
                }else{
                    dialog.message("地图参数配置错误，请重新配置");
                }
            },
            error:function (jqXHR) {
                if(jqXHR.status=="404" || jqXHR.status == "500"){
                    dialog.message("网络错误");
                }
            }
        });
    }
    function mapInit(){
        var source = new ol.source.TileWMS({
            url: "http://"+ipAddr+":"+port+"/geoserver/wms",
            params: {"LAYERS": layersName,'TILED': true},
            serverType: 'geoserver'
        });
        if($("#longitude").val()!=""&&$("#longitude").val()!="undefined"){
            lon = parseFloat($("#longitude").val());
        }
        if($("#latitude").val()!=""&&$("#latitude").val()!="undefined"){
            lat = parseFloat($("#latitude").val());
        }
        map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Tile({
                    source: source
                })
            ],
            view: new ol.View({
                projection: 'EPSG:4326',
                center: [lon, lat],
                zoom: 15,
            })
        });
        if($("#mapSign").val()==1){
            var id = $("#id").val();
            var type = id.substring(0,id.indexOf("_"));
            var iconFeature = {
                name:$("#name").val(),
                id:id,
                type:type,
                lon:$("#longitude").val(),
                lat:$("#latitude").val()
            }
            icon.addElement(iconFeature);
        }
    }
    function popupInit(){
        var container = document.getElementById('popup');
        var content = document.getElementById('popup-content');
        var title = document.getElementById('popup-title');
        var closer = document.getElementById('popup-closer');
        var feature ;
        $(map.getViewport()).on("contextmenu", function(evt) {
            evt.preventDefault();
            feature = map.forEachFeatureAtPixel(map.getEventPixel(evt),
                    function(feature) {
                        return feature;
                    });
            if($("#mapSign").val()==1 && feature){
                $(".add").parent().css("display","none");
                $(".delete").parent().css("display","inline");
                coordinate = map.getEventCoordinate(evt);
                overlay.setPosition(coordinate);
                container.style.display = 'block';
                $("#popup-title").html("操作");
                $("#popup-title").css("display","block");
            }else if($("#mapSign").val()==0){
                $(".add").parent().show();
                $(".delete").parent().hide();
                coordinate = map.getEventCoordinate(evt);
                overlay.setPosition(coordinate);
                container.style.display = 'block';
                $("#popup-title").html("操作");
                $("#popup-title").css("display","block");
            }
        });
        closer.onclick = function(){
            container.style.display = 'none';
            $("#featureId").val("");
            closer.blur();
            return false;
        };
        $("#popup #popup-content .add").on("click",function(){
            var id = $("#id").val();
            var type = id.substring(0,id.indexOf("_"));
            var iconFeature = {
                name:$("#name").val(),
                id:id,
                type:type,
                lon:coordinate[0],
                lat:coordinate[1]
            }
            icon.addElement(iconFeature);
            container.style.display = 'none';
            closer.blur();
            $("#mapSign").val(1);
        });
      $("#popup #popup-content .delete").on("click",function(){

            icon.deleteElement(feature);
            container.style.display = 'none';
            closer.blur();
            $("#mapSign").val(0);
        });
        var overlay = new ol.Overlay({
            element: container
        });
        map.addOverlay(overlay);

    }
    function clear(){};
    function dialogSubmit(){
        if($("#mapSign").val()==1){
            var param = {
                coordinate:coordinate,
                id: $("#id").val(),
            }
             saveElement(param);
        }else{
            var id =  $("#id").val();
            deleteTrafficElement(id);
        }
        window.parent.mapDialogClose();
    }
</script>
</head>
<body>
        <div>
            <input id="id" value="${param.id }" type="hidden" />
            <input id="name" value="${param.name }" type="hidden" />
            <input id="longitude" value="${param.longitude }" type="hidden" />
            <input id="latitude" value="${param.latitude }" type="hidden" />
            <input id="mapSign" value="${param.mapSign }" type="hidden" />
        </div>
        <div id="map">
        </div>
        <div id="popup" class="ol-popup">
            <a href="#" id="popup-closer" class="ol-popup-closer"></a>
            <div id="popup-title" class="popup-title"></div>
            <div id="popup-content" class="popup-content">
                <ul>
                    <li><a href="#" class="add">添加信号机</a></li>
                    <li><a href="#" class="delete">删除信号机</a></li>
                </ul>
                <input style="display:none" id="featureId"/>
            </div>
        </div>
</body>
</html>