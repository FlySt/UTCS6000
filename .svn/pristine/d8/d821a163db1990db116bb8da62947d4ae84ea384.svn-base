/**
 * Created by swl on 2016/12/21.
 */
function gisInit(){
    paramInit();
    mapInit();
}
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
    var overviewMapControl = new ol.control.OverviewMap({
        collapseLabel: '\u00BB',
        label: '\u00AB',
        collapsed: false,
        view: new ol.View({
            projection: 'EPSG:4326',
        })
    });
    map = new ol.Map({
        //  interactions: ol.interaction.defaults().extend([new app.Drag()]),
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: source
            })
        ],
        controls: ol.control.defaults().extend([
            new ol.control.FullScreen(),
            overviewMapControl,
            new ol.control.ScaleLine(),
            new ol.control.ZoomSlider(),
        ]),
        view: new ol.View({
            projection: 'EPSG:4326',
            center: [lon, lat],
            zoom: 15,
        })
    });

}