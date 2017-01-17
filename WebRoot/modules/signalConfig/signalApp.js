/**
 * Created by Administrator on 2016/12/22.
 */

function getPluginParam(signalControlerNum){
    $.ajax({
        url:path+'/pluginParam_getPluginParam.action',
        dataType:'json',
        type:'post',
        async:false,
        data:{
            "fileName":signalControlerNum+".KANG",
        },
        success:function(response){
            serverIp = response.serverIp;
            ftpUser = response.ftpUser;
            ftpPwd = response.ftpPwd;
            ftpPort = response.ftpPort;
            tcpPort = response.tcpPort;
        }
    })
}

function popupInit(){
    container = document.getElementById('popup');
    content = document.getElementById('popup-content');
    title = document.getElementById('popup-title');
    closer = document.getElementById('popup-closer');
    var feature ;
    $(map.getViewport()).on("contextmenu", function(evt) {
        evt.preventDefault();
        /*   feature = map.forEachFeatureAtPixel(evt.pixel,
         function(feature) {
         return feature;
         });*/
        feature = map.forEachFeatureAtPixel(map.getEventPixel(evt),
            function (feature) {
                return feature;
            });
        if (feature && typeof feature.get("id") != "undefined") {
            var signalControlerId = feature.get("id").split("_")[1];
            var signalControlerName = feature.get("name");
            var coordinate = map.getEventCoordinate(evt);
            popupShow(signalControlerName,signalControlerId,coordinate);
            var treeObj = $.fn.zTree.getZTreeObj("crossTree");
            var node = treeObj.getNodeByParam("id", feature.get("id"), null);
            treeObj.selectNode(node);
            signalInfo(node);
        }
    });
    closer.onclick = function(){
        container.style.display = 'none';
        closer.blur();
        return false;
    };
    overlay = new ol.Overlay({
        element: container
    });
    map.addOverlay(overlay);
}
function popup(coordinate){
    var treeObj = $.fn.zTree.getZTreeObj("crossTree");
    var nodes = treeObj.getSelectedNodes();
    var signalControlerName = nodes[0].name;
    var signalControlerId = nodes[0].id.split("_")[1];
    popupShow(signalControlerName,signalControlerId,coordinate);
}
function popupShow(signalControlerName,signalControlerId,coordinate){
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
            $("#typeF").val(response.data[0].type);
            if(response.data[0].signalState==0){
                $("#signalStateF").val("正常在线");
            }else{
                $("#signalStateF").val("脱机/断线");
            }
        }
    });
    overlay.setPosition(coordinate);
    container.style.display = 'block';
    $("#popup-title").html(signalControlerName);
    $("#popup-title").css("display","block");
}

function signalInfo(treeNode){
    if(treeNode.signalType==0){//非国标信号机
        $('.config').hide();
        $('.signal').show();
        $('#signalType').val("非国标信号机");
        showType(treeNode);
    }else if(treeNode.signalType==1){
        $('.config').show();
        $('.signal').hide();
        $('#signalType').val("国标信号机");
        showType(treeNode);
    }
    if(treeNode.id.indexOf("signal_")==0){
        $('.app-li').show();
        if(treeNode.signalState==0){
            $('#signalState').val("在线").css('color','#222');
        }else{
            $('#signalState').val("脱机/离线").css('color','red');
        }
    }else{
        $('.app-li').hide();
    }
}
function showType(treeNode){
    //规格信号  0-JKC3-新协议 1-JK-D-新协议 2-JK-E-新协议 3-JK-C6 4-JK-XT-100  80-JK-C3-老协议 81-JK-D-老协议  82-JK-E-老协议
    if(treeNode.id.indexOf("signal_")==0){
        var type = treeNode.typeS;
        if(type == 61 || type == 62){
            if(type == 61){
                $('#type').val("JK-XT-100 V1.0");
            }else if(type == 62){
                $('#type').val("JK-XT-100 V2.0");
            }
            $("#signalLog").show();
            $("#greenConflick").show();
            $("#specialLightSet").show();
            $("#downLevelSet").show();
            $("#carDetectionSet").show();
            $("#lightPanelSet").show();
            $("#followSet").show();
        }else if(type == 11 || type == 12 || type == 21 || type == 22){
            if(type == 11 ){
                $('#type').val("JK-C3 V1.0");
            }else if(type == 12){
                $('#type').val("JK-C3 V2.0");
            }else if(type == 21){
                $('#type').val("JK-C6 V1.0");
            }else if(type == 22){
                $('#type').val("JK-C6 V2.0");
            }
            $("#signalLog").hide();
            $("#greenConflick").hide();
            $("#specialLightSet").hide();
            $("#downLevelSet").hide();
            $("#carDetectionSet").hide();
            $("#lightPanelSet").hide();
            $("#followSet").hide();
        }else if(type == 41 || type == 42){
            if(type == 41){
                $('#type').val("JK-D6 V1.0");
            }else if(type == 42){
                $('#type').val("JK-D6 V2.0");
            }
            $("#signalLog").show();
            $("#greenConflick").show();
            $("#specialLightSet").show();
            $("#downLevelSet").show();
            $("#carDetectionSet").show();
            $("#lightPanelSet").hide();
            $("#followSet").hide();
        }else{
            if(type == 31){
                $('#type').val("JK-D3 V1.0");
            }else if(type == 32){
                $('#type').val("JK-D3 V2.0");
            }else if(type == 51){
                $('#type').val("JK-E3 V1.0");
            }else if(type == 52){
                $('#type').val("JK-E3 V2.0");
            }
            $("#signalLog").show();
            $("#greenConflick").hide();
            $("#specialLightSet").hide();
            $("#downLevelSet").hide();
            $("#carDetectionSet").hide();
            $("#lightPanelSet").hide();
            $("#followSet").hide();
        }
    }
}