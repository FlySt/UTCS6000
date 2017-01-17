var map;
var layersName = "shanggai";
var ipAddr = "192.168.200.244";
var lon = 115.81451;
var lat = 28.69705;
var prot = 8080;
var mangerTable;
var auditTable;
var guardTable;


var currentGuardId = 0;
var carFeature = null;
var speed, now;
var animating = false;
var geo =null;
var linelayers = null;
var crosFeatures = [];
var geometry;
var guardType = "planManger";
var tableHeight;
$(function(){
    tableHeight = document.documentElement.clientHeight-40+"px";//table高度适应不同屏幕分辨率
    $("#tabs").tabs();
    gisInit();
    elementInit();//初始化地图上已标注的信号机。。。
    mangerTableInit();
    auditTableInit();
    tableInit();
    listenerInit();
    popupInit();
    mangerClickInit();
    AuditClickInit();
    $("#manger_table").show();
/*    //测量距离
    $("#measure_distance").on("click",function(){
        messure("LineString");
    });;
    //测量面积
    $("#measure_area").on("click",function(){
        messure("Polygon");
    });*/
    $('.guard_tab').on("click",function(){
        console.log("tabs ul li click");
        guardType = $(this).attr('id');
        if(guardType=="planManger"){
            $("#manger_table").show();
            $("#audit_table").hide();
            $("#guard_table").hide();
            mangerTable.ajax.reload(false);
            $('.guard-tip').show();
        }else if(guardType == "planAudit"){
            $("#manger_table").hide();
            $("#audit_table").show();
            $("#guard_table").hide();
            auditTable.ajax.reload(false);
            $('.guard-tip').hide();
        }else if(guardType == "guardTask"){
            $("#manger_table").hide();
            $("#audit_table").hide();
            $("#guard_table").show();
            guardTable.ajax.reload(false);
            $('.guard-tip').hide();
        }
        planClear();
        guardClear();
        currentGuardId = 0;
    });
    $.fn.dataTable.ext.errMode = function(s,h,m){};
    $("table ").on("click","tr:gt(0)",function(){
        var row=mangerTable.row($(this));
        var points = row.data().points;
        var guardId = row.data().guardId;
        currentGuardId = guardId;
        if(guardType=="planManger"){
            drawLine(points,0);
        }else if(guardType=="guardTask"){
            guardClear();
            drawLine(points,3);
            $( ".start-btn" ).button({ disabled: false });
        }
        getGuardInfo(guardId);
    })
});
function tableInit(){
    guardTable=$('#guard').DataTable({
        "info": false,
        "lengthChange":false,
        "searching":false,
        "paging": false,
        "scrollY":"350px",//"500px",
        "ajax": path+'/guard_queryGuards.action?guardStatus=1',
        "columns": [
            { "data": "guardId",  "visible":false, "orderable":false},
            { "data": "points", "visible":false,   "orderable":false},
            { "data": "guardName", "width":"35%","orderable":false},
            { "data": "guardStatus", "width":"30%","orderable":false,
                "render": function (data, type, row) {
                    if(data==0){
                        return "<span style='font-size:8pt;color: red;background-color: transparent'>未审核</span>";
                    }else if(data==1){
                        return "<span style='font-size:8pt;color: blue;background-color: transparent'>已审核</span>";
                    }
                }
            },
            { "data": null,  "width":"35%", "orderable":false,"defaultContent":
                "<button style='font-size:1em;padding: 0.1em 0.2em;' class='start-btn ui-button ui-widget ui-state-disabled' type='button'>开启" +
                    "<span class='ui-icon ui-icon-play'></span></button>"}
        ]
    });
}
function mangerTableInit(){
    mangerTable=$('#manger').DataTable({
        "info": false,
        "lengthChange":false,
        "searching":false,
        "paging": false,
        "scrollY": "350px",//"500px",
        "ajax": path+'/guard_queryGuards.action',
        "columns": [
            { "data": "guardId",
                "orderable":false,
                "visible":false
            },
            {
                "data":"points",
                "orderable":false,
                "visible":false
            },
            { "data": "guardName",
                "orderable":false,
                "width":"35%"},
            { "data": "guardStatus", "width":"30%","orderable":false,
                "render": function (data, type, row) {
                    if(data==0){
                        return "<span style='font-size:8pt;color: red;background-color: transparent'>未审核</span>";
                    }else if(data==1){
                        return "<span style='font-size:8pt;color: blue;background-color: transparent'>已审核</span>";
                    }
                }
            },
            { "data": null,  "width":"40%", "orderable":false,"defaultContent":
                "<a style='font-size:1em;padding: 0.1em 0.2em;cursor:pointer' class='del-btn ui-button ui-widget' title='删除' type='button'>" +
                "删除<span class='ui-icon ui-icon-trash'></span></a>"}
        ]
    });
}
function auditTableInit(){
    auditTable=$('#audit').DataTable({
        "info": false,
        "lengthChange":false,
        "searching":false,
        "paging": false,
        "scrollY": "350px",//"500px",
        "ajax": path+'/guard_queryGuards.action',
        "columns": [
            { "data": "guardId",  "visible":false, "orderable":false},
            { "data": "points", "visible":false,   "orderable":false},
            { "data": "guardName", "width":"35%","orderable":false},
            { "data": "guardStatus", "width":"30%","orderable":false,
                "render": function (data, type, row) {
                    if(data==0){
                        return "<span style='font-size:8pt;color: red;background-color: transparent'>未审核</span>";
                    }else if(data==1){
                        return "<span style='font-size:8pt;color: blue;background-color: transparent'>已审核</span>";
                    }
                }
            },
            { "data": "guardStatus",  "width":"35%", "orderable":false,
                "render": function (data, type, row) {
                    if(data==0){
                        return "<button style='font-size:1em;padding: 0.1em 0.2em;' class='audit-btn ui-button ui-widget' type='button'>审核" +
                            "<span class='ui-icon ui-icon-gear'></span></button>";
                    }else if(data==1){
                        return "<button style='font-size:1em;padding: 0.1em 0.2em;' class='reAudit-btn ui-button ui-widget' type='button'>重审" +
                            "<span class='ui-icon ui-icon-gear'></span></button>";
                    }
                }
            }
        ]
    });
}
function getGuardInfo(guardId){
    $.ajax({
        url:path+'/guard_getGuardSignalByGuardId.action',
        dataType:'json',
        type:'get',
        data:{
            "guardId":guardId
        },
        success:function(response){
            showGuardInfo(response.data);
        }
    });
}
function showGuardInfo(data){
    $("#guardInfo").html("");
    console.log(data);
    if(data == undefined){
        var $span = $("<span style='margin-left: 10px;color: #1c77ac;display: block'>方案未审核</span>");
        $("#guardInfo").append($span);
    }else{
        var guardSignals = guardSignalSort(data);
        for(var i=0;i<guardSignals.length;i++){
            var index = guardSignals[i].guardIndex+1;
            var $span = $("<span style='margin-left: 10px;color: #1c77ac;display: block'>"+index+","+ guardSignals[i].signalControlerName+"</span>");
            $("#guardInfo").append($span);
        }
    }
}
//根据信号机guard_index排序
function guardSignalSort(signals){//排序
    var tempSignal;
    for(var i=0;i<signals.length;i++)//根据moduleOrder排序
    {
        for(var j=i+1;j<signals.length;j++){
            if(signals[i].guardIndex>signals[j].guardIndex){
                tempSignal = signals[i];
                signals[i] = signals[j];
                signals[j] = tempSignal;
            }
        }
    }
    return signals;
}