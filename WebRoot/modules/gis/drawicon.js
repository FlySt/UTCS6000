/**
 * Created by Administrator on 2016/9/6.
 */
var sources = [];
    var icon = {
        addElement:function(feature){
            var src="";
            var coordinate = [];
            if(typeof feature.lon=="undefined" || typeof feature.lat=="undefined"){
                coordinate  = map.getView().getCenter();
            }else{
                coordinate[0] = feature.lon;
                coordinate[1] = feature.lat;
            }
            if(feature.type=="signal"){
                src = path+'/common/images/icon/small_signal.png';
            }else{

            }
            var source = new ol.source.Vector();
            sources.push(source);
            var iconFeature = new ol.Feature({
                geometry: new ol.geom.Point(coordinate),
                name: feature.name,
                population: 4000,
                rainfall: 500,
                id:feature.id,
                _source:source
            });
            var style = new ol.style.Style({
                 text: new ol.style.Text({
                     text: feature.name,
                     offsetY:30,
                     fill: new ol.style.Fill({
                     color: 'red'
                     })
                 }),
                 image: new ol.style.Icon(({
                     anchor: [0.5, 0.5],
                     anchorXUnits: 'fraction',
                     anchorYUnits: 'pixels',
                     src:src
                     }))
             })
            iconFeature.setStyle(style);
            source.addFeature(iconFeature);
            var featureOverlay = new ol.layer.Vector({
                source: source,
            });
            featureOverlay.setMap(map);
            // 监听地图层级变化
            map.getView().on('change:resolution', function(){
                // 重新设置图标的缩放率，基于层级10来做缩放
                style.getImage().setScale(this.getZoom() / 15);
                style.getText().setScale(this.getZoom() / 15);
                iconFeature.setStyle(style);
            })
        },
        deleteElement:function(feature){
            feature.get("_source").clear();
        },
        clear:function(){//清除
            for(var i=0;i<sources.length;i++){
                sources[i].clear();
            }
        }
    }

