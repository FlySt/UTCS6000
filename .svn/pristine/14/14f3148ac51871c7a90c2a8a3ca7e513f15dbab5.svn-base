function mangerClickInit(){
    $("#manger tbody").on("click",".del-btn",function(){
        var row=mangerTable.row($(this).parents("tr"));
        var guardId = row.data().guardId;
        var ids = [];
        ids.push(guardId);
        $.ajax({
            url:path+'/guard_deleteGuard.action',
            dataType:'json',
            type:'post',
            traditional : true,
            data:{
                "ids":ids
            },
            success:function(response){
                if(response.result){
                    mangerTable.ajax.reload(false);
                }
            }
        })
    });

    $("#manger tbody").on("click",".watch-btn",function(){
        console.log(".watch-btn");
        var row=mangerTable.row($(this).parents("tr"));
        var points = row.data().points;
        drawLine(points,0);
    });
}
function popupInit(){
    var container = document.getElementById('popup');
    var content = document.getElementById('popup-content');
    var title = document.getElementById('popup-title');
    var closer = document.getElementById('popup-closer');
    var feature ;
    $(map.getViewport()).on("contextmenu", function(evt) {
        if(guardType != "planManger"){
            return ;
        }
        evt.preventDefault();
        feature = map.forEachFeatureAtPixel(map.getEventPixel(evt),
            function(feature) {
                return feature;
            });
            coordinate = map.getEventCoordinate(evt);
            overlay.setPosition(coordinate);
            container.style.display = 'block';
            $("#popup-title").html("操作");
            $("#popup-title").css("display","block");
            var isCancelHidden = $("#popup #popup-content .can").parent().is(":hidden");
            var isAddHidden = $("#popup #popup-content .add").is(":hidden");
            console.log(isCancelHidden);
            console.log(isAddHidden);
            if(!isAddHidden && !isCancelHidden){
                $("#popup #popup-content .can").parent().hide();
            }else if(!isAddHidden && isCancelHidden){
                $("#popup #popup-content .can").parent().show();
                $("#popup #popup-content .add").parent().hide();
            }
    });
    closer.onclick = function(){
        container.style.display = 'none';
        closer.blur();
        showAddAndCan();
        return false;
    };
    $("#popup #popup-content .add").on("click",function(){
        container.style.display = 'none';
        closer.blur();
        if(linelayers!=null){
            linelayers.getSource().clear();
        }
        drawPlan();
    });
    $("#popup #popup-content .can").on("click",function(){
        container.style.display = 'none';
        closer.blur();
        cancelPlan();
        showAddAndCan();
    });
    var overlay = new ol.Overlay({
        element: container
    });
    map.addOverlay(overlay);

}
function showAddAndCan(){
    $("#popup #popup-content .can").parent().show();
    $("#popup #popup-content .add").parent().show();
}
