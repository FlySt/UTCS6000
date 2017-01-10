/**
 * Created by sw on 2016/12/13.
 */


var layers = null ;
var positions = [];

//type 0-预案管理查看 1-审核  2-重审 3-警卫任务查看
function drawLine(points,type){
  //  map.removeLayer(layers);//清除
    if(linelayers!=null){
        linelayers.getSource().clear();
    }
    pointsInit(points);
    drawRoute(type);
}
function pointsInit(points) {
    positions=[];//清空
    var pointArrays = points.split(";")
    for(var i =0 ;i<pointArrays.length-1;i++){
        var point = [parseFloat(pointArrays[i].split(",")[0]),parseFloat(pointArrays[i].split(",")[1])];
        positions.push(point);
    }
    var center = [parseFloat(pointArrays[0].split(",")[0]),parseFloat(pointArrays[0].split(",")[1])];
    map.getView().setCenter(center);
    map.getView().setZoom(15);
}
function drawRoute(type) {
    console.log(positions);
    //轨迹line layer
    var vsource = new ol.source.Vector({
        type: 'LineString',
        features: []
    });
    linelayers = new ol.layer.Vector({
        source: vsource,
        style: new ol.style.Style({
            fill: new ol.style.Fill({
                color: '#0044CC'
            }),
            stroke: new ol.style.Stroke({
                color: '#0044CC',
                width: 4
            })
        })
    });
    geometry = new ol.geom.LineString(positions,'XY');
    var lineFeature = new ol.Feature({//路线
        geometry: geometry
    });

    linelayers.getSource().addFeature(lineFeature);

    var startFeature = new ol.Feature({//起点
        geometry: new ol.geom.Point(positions[0]),
        population: 4000,
        rainfall: 500
    });
    startFeature.setStyle(startStyle);
    linelayers.getSource().addFeature(startFeature);
    var endFeature = new ol.Feature({//终点
        geometry: new ol.geom.Point(positions[positions.length-1]),
        population: 4000,
        rainfall: 500
    });
    endFeature.setStyle(endStyle);
    linelayers.getSource().addFeature(endFeature);
    if(type == 3){//警卫任务查看
        carFeature = new ol.Feature({//车子
            geometry: new ol.geom.Point(positions[0]),
            population: 4000,
            rainfall: 500
        });
        carFeature.setStyle(carStyle);
        linelayers.getSource().addFeature(carFeature);
    }
    map.addLayer(linelayers);
    if(type==1){
        findSignal(geometry);
    }
}
//初始化方案里有的信号机
function guardSignalInit(guardId){
    $.ajax({
        url:path+'/guard_getSignalControlers.action',
        dataType:'json',
        type:'get',
        data:{
            "guardId":guardId
        },
        success:function (array) {
            for(var i =0;i<array.length;i++){
                var data = array[i];
                var feature={
                    name:data.name,
                    id:data.type+"_"+data.id,
                    type:data.type,
                    lon:parseFloat(data.lon),
                    lat:parseFloat(data.lat)
                }
                icon.addElement(feature);
            }
        }
    });

}
