/**
 * Created by Administrator on 2016/9/6.
 */
function popupInita(){
    var container = document.getElementById('popup');
    var content = document.getElementById('popup-content');
    var title = document.getElementById('popup-title');
    var closer = document.getElementById('popup-closer');
    var feature ;
    $(map.getViewport()).on("contextmenu", function(evt) {
        evt.preventDefault();
      /*   feature = map.forEachFeatureAtPixel(evt.pixel,
            function(feature) {
                return feature;
            });*/
        feature = map.forEachFeatureAtPixel(map.getEventPixel(evt),
            function(feature) {
                return feature;
            });
        if (feature && typeof feature.get("id")!="undefined") {
            var signalControlerId = feature.get("id").split("_")[1];
            $.ajax({
                url:path + '/signalControler_queryAllSignalControlers.action',
                type:'get',
                dataType:'json',
                cache:false,
                data:{
                  "signalControlerId":signalControlerId,
                },
                success:function(response){
                    $("#signalControlerNum").val(response.data[0].signalControlerNum);
                    $("#deviceIp").val(response.data[0].deviceIp);
                    $("#crossName").val(response.data[0].crossName);
                    $("#serverNo").val(response.data[0].serverNo);
                    $("#type").val(response.data[0].type);
                    if(response.data[0].signalState==0){
                        $("#signalState").val("正常在线");
                        $( "#realTime" ).button({ disabled: false });
                        $( "#config" ).button({ disabled: false });
                    }else{
                        $("#signalState").val("脱机/断线");
                        $( "#realTime" ).button({ disabled: true });
                        $( "#config" ).button({ disabled: true });
                    }
                    $("#signalType").val(response.data[0].signalType);
                }
            })
           // var coordinate = evt.coordinate;
            var coordinate = map.getEventCoordinate(evt);
            overlay.setPosition(coordinate);
            container.style.display = 'block';
            $("#popup-title").html(feature.get("name"));
            $("#popup-title").css("display","block");
            $("#featureId").val(feature.get("id"));
            $("#signalControlerName").val(feature.get("name"));
            $("#signalControlerId").val(signalControlerId);
        //    map.getView().setCenter(coordinate);
        }
    });
    closer.onclick = function(){
        container.style.display = 'none';
        $("#featureId").val("");
        closer.blur();
        return false;
    };
    $("#popup #popup-content .delete").on("click",function(){
        var id = $("#featureId").val();
        deleteTrafficElement(id);
        icon.deleteElement(feature);
        container.style.display = 'none';
        $("#featureId").val("");
        closer.blur();
    });
    var overlay = new ol.Overlay({
        element: container
    });
    map.addOverlay(overlay);

}


