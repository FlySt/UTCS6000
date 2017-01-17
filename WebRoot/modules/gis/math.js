/**
 * Created by swl on 2016/12/13.
 */
var radius = 50;//已半径为50米查找
var signals = [];
var signalControlerIds = [];

function findSignal(geometry){
    signalControlerIds = [];
    getSignals();
    console.log(signals);
    for(var i = 0;i<=1;i=i+0.01){
        var coordinate = geometry.getCoordinateAt(i);
        contrastSignal(coordinate,signals);
    }
    if(signalControlerIds.length>0){
        addSignalToGuard(signalControlerIds);
    }else{
        dialog.message("方案中不存在信号机");
    }
}
function getSignals(){
    $.ajax({
        url:path+'/traffic_findElementOnMap.action',
        dataType:"json",
        type:"get",
        async:false,
        success:function(array){
            signals = array;
        },
        error:function (jqXHR) {
            if(jqXHR.status=="404" || jqXHR.status == "500"){
                dialog.message("网络错误");
            }
        }
    })
}
/**
 * 与所有已在地图上标记的信号机比较
 * @param coordinate
 */
function contrastSignal(coordinate,signals){
    for(var i=0;i<signals.length;i++){
        var position = [];
        position[0] = parseFloat(signals[i].lon);
        position[1] = parseFloat(signals[i].lat);
        calculation(coordinate,position,signals[i]);
    }
}
/**
 * 计算距离 在沿线半径50米圆的范围内的信号机则显示
 * @param coordinate
 * @param position
 */
function calculation(coordinate,position,data){
    var wgs84Sphere = new ol.Sphere(6378137);
    var length ;
    var sourceProj = map.getView().getProjection();
    var c1 = ol.proj.transform(coordinate, sourceProj, 'EPSG:4326');
    var c2 = ol.proj.transform(position, sourceProj, 'EPSG:4326');
    length = wgs84Sphere.haversineDistance(c1, c2);
    var output = (Math.round(length * 100) / 100);
  // console.log("output:"+output);
    if(output<radius){
        var feature={
            name:data.name,
            id:data.type+"_"+data.id,
            type:data.type,
            lon:parseFloat(data.lon),
            lat:parseFloat(data.lat)
        };
        icon.addElement(feature);
        console.log(data.id);
        if(!contains(signalControlerIds,data.id)){
            signalControlerIds.push(data.id);
        }
        console.log(signalControlerIds);
    }
}
function addSignalToGuard(signalControlerIds){
    var guadIndexs = [];
    for(var i=0;i<signalControlerIds.length;i++){
        guadIndexs.push(i);
    }
    console.log(signalControlerIds);
    $.ajax({
        url:path+'/guard_addGuardSignal.action',
        type:'post',
        dataType:'json',
        traditional : true,
        data:{
            "signalControlerIds":signalControlerIds,
            "guardId":currentGuardId,
            "guardIndexs":guadIndexs
        },
        success:function (reponse) {
            if(!reponse.result){
                dialog.message(reponse.signalControlerName+"已存在于方案"+reponse.guardName+"中");
            }
        }
    })
}
function contains(arr, obj) {
    var i = arr.length;
    while (i--) {
        if (arr[i] === obj) {
            return true;
        }
    }
    return false;
}