/**
 * Created by Administrator on 2016/9/8.
 */
function saveElement(param){ //id:signal_*
    var type = param.id.substring(0,param.id.indexOf("_"));
    var id = param.id.substring(param.id.indexOf("_")+1,param.id.length);
    try{
        $.ajax({
            url:path+'/traffic_saveElementOnMap.action',
            dataType:"json",
            type:"post",
            async:false,
            data:{
                type:type,
                objId:id,
                lon:param.coordinate[0],
                lat:param.coordinate[1]
            },
            success:function(response){
                if(!response.result){
                    dialog.message("网络错误");
                }else{
                    //     refreshTree();
                }
            },
            error:function (jqXHR) {
                if(jqXHR.status=="404" || jqXHR.status == "500"){
                    dialog.message("网络错误");
                }
            }
        })
    }catch (e){
        return false;
    }
    return true;
}
function addTrafficElement(array){
    for(var i = 0;i<array.length;i++){
        var feature={
            name:array[i].name,
            id:array[i].type+"_"+array[i].id,
            type:array[i].type,
            lon:parseFloat(array[i].lon),
            lat:parseFloat(array[i].lat)
        }
        icon.addElement(feature)
    }
}
//地图元素初始化
function elementInit(){
    $.ajax({
        url:path+'/traffic_findElementOnMap.action',
        dataType:"json",
        type:"post",
        data:{},
        success:function(array){
            addTrafficElement(array);
        },
        error:function (jqXHR) {
            if(jqXHR.status=="404" || jqXHR.status == "500"){
                dialog.message("网络错误");
            }
        }
    })
}
//删除地图元素
function deleteTrafficElement(id){
    var type = id.substring(0,id.indexOf("_"));
    var id = id.substring(id.indexOf("_")+1,id.length);
    $.ajax({
        url:path+'/traffic_deleteElementOnMap.action',
        dataType:"json",
        type:"post",
        async:false,
        data:{
            type:type,
            objId:id,
        },
        success:function(response){
            if(!response.result){
                dialog.message("删除失败");
            }else{
              //  refreshTree();
            }
        },
        error:function (jqXHR) {
            if(jqXHR.status=="404" || jqXHR.status == "500"){
                dialog.message("网络错误");
            }
        }
    })
}

