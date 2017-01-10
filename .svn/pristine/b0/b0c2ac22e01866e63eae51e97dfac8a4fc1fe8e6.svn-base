/**
 * Created by swl on 2016/9/6.
 */
    var wgs84Sphere = new ol.Sphere(6378137);
    var sketch;
    var startTooltipElement;
    var startTooltip;
    var measureTooltipElement;
    var measureTooltip;
    var isDrawEnd = false;

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
    function addInteraction(type) {
        var source = new ol.source.Vector();
        var measureVector = new ol.layer.Vector({
            source:source,
            style: new ol.style.Style({
                fill: new ol.style.Fill({
                    color: 'rgba(255, 255, 255, 0.2)'
                }),
                stroke: new ol.style.Stroke({
                    color: '#ffcc33',
                    width: 2
                }),
                image: new ol.style.Circle({
                    radius: 7,
                    fill: new ol.style.Fill({
                        color: '#ffcc33'
                    })
                }),
                image: new ol.style.Icon(({
                    anchor: [0.5, 0.5],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'pixels',
                    src:path+'/common/images/gis/delete.png'
                }))
            })
        });
        map.addLayer(measureVector);
      //  vector.setMap(map);
        var type = type;//'LineString';//(typeSelect.value == 'area' ? 'Polygon' : );
        draw = new ol.interaction.Draw({
            source: source,
            type: /** @type {ol.geom.GeometryType} */ (type),
            style: new ol.style.Style({
                fill: new ol.style.Fill({
                    color: 'rgba(255, 255, 255, 0.2)'
                }),

                stroke: new ol.style.Stroke({
                  //  color: 'rgba(0, 0, 0, 0.5)',
                    color: '#F9B99A',
                    lineDash: [10, 10],
                    width: 2
                }),

                image: new ol.style.Icon( ({
                    anchor: [0.5, 0.5],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'pixels',
                    src:path+'/common/images/icon/ruler.png'
                }))
            })
        });

        var listener;
        var distance;

        draw.on('drawstart',
            function(evt) {
                // set sketch
                sketch = evt.feature;

                /** @type {ol.Coordinate|undefined} */
                var tooltipCoord = sketch.getGeometry().getLastCoordinate();
                if(type=="LineString"){
                    startTooltipElement.innerHTML = "起点";
                    startTooltip.setPosition(tooltipCoord);
                    $(startTooltipElement).removeClass('hidden');
                }
                listener = sketch.getGeometry().on('change', function(evt) {
                    var geom = evt.target;
                    var output;
                    if (geom instanceof ol.geom.Polygon) {
                        output = formatArea((geom));
                        tooltipCoord = geom.getInteriorPoint().getCoordinates();
                        measureTooltipElement.innerHTML = "<div>面积:"+"<span class='output'>"+output;
                    } else if (geom instanceof ol.geom.LineString) {
                        output = formatLength((geom));
                        tooltipCoord = geom.getLastCoordinate();
                        measureTooltipElement.innerHTML = "<div>总长:"+"<span class='output'>"+output+"</span></div>"+"单击确定地点,双击结束";

                    }
                    distance = output;
                    measureTooltip.setPosition(tooltipCoord);
                });
            }, this);

        draw.on('drawend',
            function(evt) {
                if(type=="LineString"){
                    measureTooltipElement.innerHTML = "<div>总长:"+"<span class='output'>"+distance+"</span></div>";
                }else{
                    measureTooltipElement.innerHTML = "<div>面积:"+"<span class='output'>"+distance;
                }
                measureTooltipElement.className = 'tooltip tooltip-static';
                measureTooltip.setOffset([0, -7]);
                // unset sketch
                sketch = null;

                measureTooltipElement = null;
                // unset tooltip so that a new one can be created
                ol.Observable.unByKey(listener);
                isDrawEnd  = true ;

                var coordinate = evt.feature.getGeometry().getLastCoordinate();
                var iconFeature = new ol.Feature({
                    geometry: new ol.geom.Point(coordinate),
                    name: "measure",
                    population: 4000,
                    rainfall: 500,
                    _source:source,
                    _startTooltip:startTooltip,
                    _measureTooltip:measureTooltip,
                });
                source.addFeature(iconFeature);
                map.on("click",function(evt){
                    var feature = map.forEachFeatureAtPixel(evt.pixel,
                        function(feature) {
                            return feature;
                        });
                    if (feature && feature.get("name") == "measure"){
                        map.removeOverlay(feature.get("_startTooltip"));
                        map.removeOverlay(feature.get("_measureTooltip"));
                        feature.get("_source").clear();
                    }

                })
            }, this);
    }

    function createStartooltip() {
        startTooltipElement = document.createElement('div');
        startTooltipElement.className = 'tooltip-start hidden';
        startTooltip = new ol.Overlay({
            element: startTooltipElement,
            offset: [0, 30],
            positioning: 'bottom-center'
        });
        map.addOverlay(startTooltip)
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
    /**
     * format length output
     * @param {ol.geom.LineString} line
     * @return {string}
     */
    var formatLength = function(line) {
        var length;
        var coordinates = line.getCoordinates();
        length = 0;
        var sourceProj = map.getView().getProjection();
        for (var i = 0, ii = coordinates.length - 1; i < ii; ++i) {
            var c1 = ol.proj.transform(coordinates[i], sourceProj, 'EPSG:4326');
            var c2 = ol.proj.transform(coordinates[i + 1], sourceProj, 'EPSG:4326');
            length += wgs84Sphere.haversineDistance(c1, c2);
        }
        var output;
        if (length > 100) {
            output = (Math.round(length / 1000 * 100) / 100) +
                ' ' + '公里';
        } else {
            output = (Math.round(length * 100) / 100) +
                ' ' + '米';
        }
        return output;
    };
    var formatArea = function(polygon) {
        var area;
        var sourceProj = map.getView().getProjection();
        var geom = /** @type {ol.geom.Polygon} */(polygon.clone().transform(
            sourceProj, 'EPSG:4326'));
        var coordinates = geom.getLinearRing(0).getCoordinates();
        area = Math.abs(wgs84Sphere.geodesicArea(coordinates));
        var output;
        if (area > 10000) {
            output = (Math.round(area / 1000000 * 100) / 100) +
                ' ' + '平方公里';
        } else {
            output = (Math.round(area * 100) / 100) +
                ' ' + '平方米';
        }
        return output;
    };

    function messure(type){
        addInteraction(type);
        createStartooltip();
        createMeasureTooltip();
        map.addInteraction(draw);
        map.on('pointermove', pointerMoveHandler);
    }
