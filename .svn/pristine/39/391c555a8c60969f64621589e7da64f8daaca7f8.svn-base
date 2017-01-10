var map;
var layersName = "shanggai";
var ipAddr = "192.168.200.244";
var lon = 115.81451;
var lat = 28.69705;
var port;
var table;

    function AuditClickInit(){
        $("#audit tbody").on("click",".audit-btn",function(){
            console.log("click");
            var row=auditTable.row($(this).parents("tr"));
            var points=row.data().points;
            currentGuardId= row.data().guardId;
            drawLine(points,1);
        });
        //重审
        $("#audit tbody").on("click",".reAudit-btn",function(){
            var row=auditTable.row($(this).parents("tr"));
            var points=row.data().points;
            currentGuardId= row.data().guardId;
            drawLine(points,2);
        });
        $(map.getViewport()).on("contextmenu", function(evt) {
            if(currentGuardId == 0){
                return ;
            }
            evt.preventDefault();
            var feature = map.forEachFeatureAtPixel(map.getEventPixel(evt),
                function(feature) {
                    return feature;
                });
            if (feature && typeof feature.get("id")!="undefined") {
                var id = feature.get("id");
                var signalControlerId = id.substring(id.indexOf("_")+1,id.length);
                console.log("signalControlerId："+signalControlerId);
                var isSuccess = formInit(signalControlerId);
                console.log("isSuccess:"+isSuccess);
                if(formInit(signalControlerId)){
                    $("#pointInfo").dialog({
                        height: 300,
                        width: 400,
                        modal: true,
                        buttons:{
                            "保存": function(){
                                if($("#isAdd").val()==1){
                                    deleteGuardSiganl($("#guardSignalId").val());
                                }else{
                                    modifyGuardSignal($("#guardSignalId").val());
                                }
                                $("#pointInfo").dialog("close");
                            },
                            "取消": function() {
                                $( this ).dialog( "close" );
                            }
                        }
                    })
                }
            }
        });
    }


function deleteGuardSiganl(guardSignalId) {
    $.ajax({
        url:path+'/guard_deleteGuardSiganl.action',
        type:'post',
        dataType:'json',
        traditional : true,
        data:{
            "guardSignalId":$("#guardSignalId").val(),
        },
        success:function(response){
            if(response.result){
            }
        }
    })
}
function modifyGuardSignal(guardSignalId){
    $.ajax({
        url:path+'/guard_modifyGuardSignal.action',
        type:'post',
        dataType:'json',
        traditional : true,
        data:{
            "lastToTime":$("#lastToTime").val(),
            "passTime":$("#passTime").val(),
            "direction":$("#direction").val(),
            "guardSignalId":$("#guardSignalId").val(),
        },
        success:function(response){
            if(response.result){
            }
        }
    })
}
function formInit(signalControlerId){
    var isSuccess = false;
    $.ajax({
        url:path+'/guard_getGuardSignal.action',
        dataType:'json',
        type:'get',
        async:false,
        cache:false,
        data:{
            "signalControlerId":signalControlerId,
            "guardId":currentGuardId
        },
        success:function(response){
            if(response.result){
                $("#lastToTime").val(response.lastToTime);
                $("#passTime").val(response.passTime);
                $("#direction").val(response.direction);
                $("#guardSignalId").val(response.guardSignalId);
                isSuccess = true;
            }
        }
    });
    return isSuccess;
}


