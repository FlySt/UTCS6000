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