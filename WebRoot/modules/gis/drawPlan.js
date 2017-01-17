/**
 * Created by swl on 2016/9/6.
 */
    var wgs84Sphere = new ol.Sphere(6378137);
    var sketch;
    var measureTooltipElement;
    var measureTooltip;
    var isDrawEnd = false;
    var pointArrays = "";
    var source = null;
    var pointerMoveHandler = function(evt) {
        if(isDrawEnd){
            isDrawEnd = false;
            map.un('pointermove', pointerMoveHandler);
            map.removeInteraction(draw);
            return ;
        }
        if (evt.dragging) {
            return;
        }
        if(sketch){
            return;
        }
        var helpMsg = '单击确定起点';
        measureTooltipElement.innerHTML = helpMsg;
        measureTooltip.setPosition(evt.coordinate);
    };
    var draw; // global so we can remove it later
    function addInteraction() {
        source = new ol.source.Vector();
        var measureVector = new ol.layer.Vector({
            source:source,
            style: new ol.style.Style({
                fill: new ol.style.Fill({
                    color: 'rgba(255, 255, 255, 0.2)'
                }),
                stroke: new ol.style.Stroke({
                    color: '#4169e1',
                    width: 4
                }),
                image: new ol.style.Circle({
                    radius: 7,
                    fill: new ol.style.Fill({
                        color: '#4169e1'
                    })
                }),
            })
        });
        map.addLayer(measureVector);
      //  vector.setMap(map);
        var type = 'LineString';//(typeSelect.value == 'area' ? 'Polygon' : );
        draw = new ol.interaction.Draw({
            source: source,
            type: /** @type {ol.geom.GeometryType} */ (type),
            style: new ol.style.Style({
                fill: new ol.style.Fill({
                    color: 'rgba(255, 255, 255, 0.6)'
                }),

                stroke: new ol.style.Stroke({
                  //  color: 'rgba(0, 0, 0, 0.5)',
                    color: '#4169e1',
                    width: 4
                }),
                image: new ol.style.Circle({
                    radius: 7,
                    fill: new ol.style.Fill({
                        color: '#4169e1'
                    })
                })
            })
        });
        var listener;
        var click;
        draw.on('drawstart',
            function(evt) {
                console.log("drawstart");
                pointArrays = "";
                // set sketch
                sketch = evt.feature;
                /** @type {ol.Coordinate|undefined} */
                var tooltipCoord = sketch.getGeometry().getLastCoordinate();
                var startFeature = new ol.Feature({//起点
                    geometry: new ol.geom.Point(tooltipCoord),
                    population: 4000,
                    rainfall: 500
                });
                startFeature.setStyle(startStyle);
                source.addFeature(startFeature);

                listener = sketch.getGeometry().on('change', function(evt) {
                    var geom = evt.target;
                    tooltipCoord = geom.getLastCoordinate();
                    measureTooltipElement.innerHTML = "点击确定,双击保存";
                    measureTooltip.setPosition(tooltipCoord);
                });
                click = map.on('click',function (evt) {
                    console.log("click");
                    var coordinate = evt.coordinate;
                    console.log(coordinate[0]);
                    var point = coordinate[0]+","+coordinate[1];
                    pointArrays = pointArrays+point+";";
                });
            }, this);

        draw.on('drawend',
            function(evt) {
                var isSave = false;
                measureTooltipElement.innerHTML = "";
                measureTooltipElement.className = 'hidden';
                sketch = evt.feature;
                var coord = sketch.getGeometry().getLastCoordinate();
                var endFeature = new ol.Feature({//终点
                    geometry: new ol.geom.Point(coord),
                    population: 4000,
                    rainfall: 500
                });
                endFeature.setStyle(endStyle);
                source.addFeature(endFeature);
                // unset sketch
                sketch = null;
                // unset tooltip so that a new one can be created
                ol.Observable.unByKey(listener);
                map.unByKey(click);
                isDrawEnd = true;
                console.log(pointArrays);

                $("#new_guard").dialog({
                    height: 120,
                    width: 300,
                    modal: true,
                    buttons:{
                        "保存": function(){
                            $.ajax({
                                url:path+'/guard_addGuard.action',
                                type:'post',
                                dataType:'json',
                                traditional : true,
                                data:{
                                    "guardName":$("#guardName").val(),
                                    "pointArrays":pointArrays,
                                },
                                success:function(response){
                                    if(response.result){
                                        isSave = true;
                                        $( "#new_guard" ).dialog( "close" );
                                        mangerTable.ajax.reload(false);
                                    }
                                }
                            })
                        },
                        "取消": function() {
                            $( this ).dialog( "close" );
                        }
                    },
                    close: function() {
                        console.log(isSave);
                        if(!isSave){
                            source.clear();
                        }
                        showAddAndCan();
                    }
                })
            }, this);
    }
    /**
     * Creates a new measure tooltip
     */
    function createMeasureTooltip() {
        if (measureTooltipElement) {
            measureTooltipElement.parentNode.removeChild(measureTooltipElement);
        }
        measureTooltipElement = document.createElement('div');
        measureTooltipElement.className = 'tooltip tooltip-measure';
        measureTooltip = new ol.Overlay({
            element: measureTooltipElement,
            offset: [0, -15],
            positioning: 'bottom-center'
        });
        map.addOverlay(measureTooltip);
    }

    function drawPlan(){
        planClear();
        addInteraction();
        createMeasureTooltip();
        map.addInteraction(draw);
        map.on('pointermove', pointerMoveHandler);
    }
    function cancelPlan() {
        if(measureTooltipElement!=null && measureTooltipElement!=undefined){
            measureTooltipElement.innerHTML = "";
            measureTooltipElement.className = 'hidden';
        }
        if(source!=null){
            source.clear();
        }
        map.un('pointermove', pointerMoveHandler);
        map.removeInteraction(draw);
    }

    function planClear() {
        if(source!=null){
            source.clear();
        }
        $("#guardInfo").html("");
    }
