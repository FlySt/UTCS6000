var map;
var zLeftTree;
var layersName = "shanggai";
var ipAddr = "192.168.200.244";
var port = 8080;
var lon = 115.81451;
var lat = 28.69705;

var serverIp;
var ftpUser;
var ftpPwd;
var ftpPort;
var tcpPort;
$(function(){
    gisInit();
    elementInit();//初始化地图上已标注的信号机。。。
    toolBarInit();
    signalTreeInit();
    popupInita();
    readyDialog("add-element-dialog",300,400);
    //测量距离
    $("#measure_distance").on("click",function(){
        messure("LineString");
    })
    //测量面积
    $("#measure_area").on("click",function(){
        messure("Polygon");
    })
    $("#location").on("mouseover",function(){
        showMenu();
    });
    $(".menu  li:gt(0)").hover(function(){
        hideMenu();
    });
    $("#realTime").on("click",function(){
        if( $("#signalType").val()==0){
            console.log("realTime");
            var signalId =  $("#signalControlerId").val();
            var signalName = $("#signalControlerName").val();
            var iWidth=800;                          //弹出窗口的宽度;
            var iHeight=600;                         //弹出窗口的高度;
            //获得窗口的垂直位置
            var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
            //获得窗口的水平位置
            var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
            var url = path+'/modules/resourceManage/realTime.jsp?signalId='+signalId+'&signalName='+signalName;
            window.open(url,'_blank',
                'width='+iWidth+',height='+iHeight+',top='+iTop+',left='+iLeft+',menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=no,resizable=no');
        }else{
            dialog.message("国标信号机暂无实时信息");
        }
    });
    $("#config").on("click",function(){
        if( $("#signalType").val()==0){//非国标信号机
            console.log("config");
            var signalName = $("#signalControlerName").val();
            var v=document.getElementById("showTable");
            var result=v.OpenSchemeSetUI(signalName);
        }else{//国标信号机
            var signalControlerNum =  $("#signalControlerNum").val();
            var crossName = $("#crossName").val();
            try{
                UTCSOcx.OcxGetDlgWidth();
            }catch (e){
                dialog.message("请先安装国标信号机控件");
                return;
            }
            getPluginParam(signalControlerNum);
            UTCSOcx.OcxSetFuncParam(2);
            var DlgWidth = UTCSOcx.OcxGetDlgWidth();
            var DlgHeight = UTCSOcx.OcxGetDlgHeight();
            document.all("UTCSOcx").style["width"]= DlgWidth ;
            document.all("UTCSOcx").style["height"]= DlgHeight;
            UTCSOcx.OcxSetFtpParam(serverIp,ftpPort,ftpUser,ftpPwd);
            UTCSOcx.OcxSetTcpParam(serverIp,tcpPort);
            var res1 = UTCSOcx.OcxSetCfgParam(signalControlerNum,crossName,signalControlerNum+".KANG","配置.why");
            if(res1 == 0){
                UTCSOcx.OcxLunch();
            }
            else
                dialog.message("设置参数失败");
        }
    });
});
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
function toolBarInit(){
    var viewport = map.getViewport();
    $(viewport).append($("#left_toolbar"));
    $(viewport).append($("#right_toolbar"));
}
var module_setting = {
    check: {
        chkStyle : "checkbox",
        enable: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey : "id",
            pIdKey : "pId",
            rootPId : -1
        }
    },
    async :
    {
        enable : true,
        url:path+"/treeResource_getOnMapSignalControlerTrees.action"
    },
    callback: {
        // zTree单击事件
        onClick : function(event, treeId, treeNode)
        {
            if(treeNode.id.indexOf("signal_")==0){
                hideMenu();
                var lon = treeNode.lon;
                var lat = treeNode.lat;
                var coordinate = [];
                coordinate[0] = parseFloat(lon);
                coordinate[1] = parseFloat(lat);
                map.getView().setCenter(coordinate);
                map.getView().setZoom(15);
            }
        },
        //	onAsyncSuccess: zTreeOnAsyncSuccess
    }
};
//信号机查找树
function signalTreeInit(){
    zLeftTree = $.fn.zTree.init($("#leftTree"), module_setting);
}
function refreshTree(){
    var treeObj = $.fn.zTree.getZTreeObj("leftTree");
    treeObj.reAsyncChildNodes(null, "refresh");
}
// 显示树的位置
function showMenu()
{
    var inputOffset = $("#location").offset();
    var left = parseInt(inputOffset.left) ;
    var top = parseInt(inputOffset.top + $("#location").outerHeight()) + 10;
    $("#menuContent").css(
        {
            left : left + "px",
            top : top + "px",
        }).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
// 页面body鼠标事件
function onBodyDown(event)
{
    if (!(event.target.id == "menuBtn" || event.target.id == "location" || event.target.id == "menuContent" ||
        $(event.target).parents("#menuContent").length>0))
    {
        hideMenu();
    }
}
// 隐藏zTree
function hideMenu()
{
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function dialogClose(){
    $("#add-element-dialog").dialog("close");
    dialog.message("保存成功");
}
function addElement(feature){
    icon.addElement(feature);
}