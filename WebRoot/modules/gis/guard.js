
var crossPositions = [];
function listenerInit(){
    $("#guard tbody").on("click",".start-btn",function(e){
        if(currentGuardId==0){
            return;
        }
        crossPositionsInit();
        if (crossPositions.length == 0) {
            $(".remind").html('<i class="icon-info-sign">请先查询轨迹</i>');
            return;
        }
        if (animating) {
            stopAnimation(false);
        } else {
            animating = true;
            now = new Date().getTime();
            speed = 1;//速度
            //      $("#animateBtn").html('<i class="icon-eye-close"></i>&nbsp;取消回放');
            carFeature.setStyle(null);
            // map.getView().setCenter(center);
            map.on('postcompose', moveFeature);
            map.render();
        }
        e.stopPropagation();
    });
    $("#guard tbody").on("click",".watch-btn",function(){
        var row=guardTable.row($(this).parents("tr"));
        var points=row.data().points;
        currentGuardId = row.data().guardId;
        drawLine(points,3);
    })
}
function crossPositionsInit(){
    if(linelayers!=null){
        for(var i =0;i<crosFeatures.length;i++){
            linelayers.getSource().removeFeature(crosFeatures[i]);
        }
    }
    crosFeatures=[];
    crossPositions = [];
    for(var i=0;i<1;i=i+0.01){
        var coordinate = geometry.getCoordinateAt(i);
        crossPositions.push(coordinate);
    }

}
var moveFeature = function(event) {
    var vectorContext = event.vectorContext;
    var frameState = event.frameState;

    if (animating) {
        var elapsedTime = frameState.time - now;
        // here the trick to increase speed is to jump some indexes
        // on lineString coordinates
        var index = Math.round(speed * elapsedTime / 1000);

        if (index >= crossPositions.length) {
            console.log("stop")
            stopAnimation(true);
            return;
        }
        if(index>=1){
            var pos = [crossPositions[index-1],crossPositions[index]];
            var croFeature = new ol.Feature({//路线
                geometry: new ol.geom.LineString(pos,'XY')
            });
            croFeature.setStyle(crossStyle);
            linelayers.getSource().addFeature(croFeature);
            crosFeatures.push(croFeature);
        }
        var currentPoint = new ol.geom.Point(crossPositions[index]);
        var feature = new ol.Feature(currentPoint);
        vectorContext.drawFeature(feature, carStyle);
        map.getView().setCenter(crossPositions[index]);
    }
    // tell OL3 to continue the postcompose animation
    map.render();
};

function stopAnimation(ended) {
    animating = false;
    //  $("#animateBtn").html('<i class="icon-eye-open"></i>&nbsp;轨迹回放');

    // if animation cancelled set the marker at the beginning
    var coord = ended ? crossPositions[crossPositions.length - 1] : crossPositions[0];
    /** @type {ol.geom.Point} */
    (carFeature.getGeometry()).setCoordinates(coord);
    //remove listener
    map.un('postcompose', moveFeature);
}
//轨迹回放end
function guardClear(){
    if(linelayers!=null){
        if(animating){
            stopAnimation(true);
        }
        linelayers.getSource().clear();
        crosFeatures=[];
    }
}
