<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
var path = "<%=path%>";
</script>
<link href="<%=path%>/common/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/theme.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/zTreeStyleEx.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/dialog.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.validate.min.all.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">

    var region_tree;
	var nodeIds = [];
	$(function(){
		var regionId = $("#regionId").val();
		elementInit();
		/* 输入验证 */
		$("#dialog_form").validate(
				{
					rules :
					{
						'regionParam.regionName':{
							required: true,
							maxlength : 25,
							remote :
							{
								type : "post",
								dataType : "json",
								url : path + "/region_validatorRegionName.action",
								data :
								{
									"regionName" : function()
									{
										return $("#regionName").val();
									},
									"regionId" : function()
									{
										return $("#regionId").val();
									}
								}
							}
						},
						'regionParam.regionNum':{
							required : true,
							filterWord : true,
							isNum : true,
							justlength : function(){
								if($("#regionType").val()==0){
									return 9;
								}else{
									return 11;
								}
							},
							isLetterNum : true
						},
						'fatherRegionName':{
							required : true,
						}
					},
					messages :
					{
						'regionParam.regionName' :
						{
							remote : "区域名称已存在"
						}
					}
				});
		var region_setting =
		{
			selectFirstNode : true,
			data :
			{
				simpleData :
				{
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : -1
				}
			},
			async :
			{
				enable : true,
				url:path+"/treeResource_getRegionParamTrees.action"
			},
			callback :
			{
				// zTree单击事件
				onClick : function(event, treeId, treeNode)
				{
					if(treeNode.id.indexOf("region_")!=-1){
						if (isObjExist(nodeIds, treeNode.id)&&treeNode.id!="region_"){
							dialog.message("不能选择自己或自己的子节点作为父节点");
						} else{
							$("#fatherRegionName").val(treeNode.name);
							$("#fatherRegionId").val(treeNode.id.split("_")[1]);
						}
						hideMenu();
					}
				},
				// zTree加载完成事件
				onAsyncSuccess : function()
				{
					getNodeIdAndSonIds(region_tree, "region_" + regionId);
				}
			}
		};
		region_tree = $.fn.zTree.init($("#regionTree"), region_setting);
        $("#regionType").on("change",function(){
        	if($(this).val()==0){
				$("#fatherRegion").hide();
				$("#fatherRegionId").val('-1');
			}else{
				$("#fatherRegion").show();
			}
        })
		function elementInit(){
			if(window.parent.isWatch){  //查看的时候input disabled
				$("#dialog_form :input").attr("disabled","disabled");
			}
			if(regionId!=null && regionId!=""){
				$("#regionType").val('${regionParam.regionType}');
			}else{
				$("#fatherRegionId").val('-1');
			}
			$("#regionType").val()==0?$("#fatherRegion").hide(): $("#fatherRegion").show();
		}
	});

	function clear(){
		$("#dialog_form :input").val("");
		$("#dialog_form :input").removeAttr("disabled");
		window.parent.isWatch = false;
	}
    // 显示树的位置
    function showRegionMenu()
    {
        var inputOffset = $("#fatherRegionName").offset();
        var left = parseInt(inputOffset.left)-40 ;
        var top = parseInt(inputOffset.top + $("#fatherRegionName").outerHeight()) - 5;
        $("#regionContent").css(
                {
                    left : left + "px",
                    top : top + "px",
                }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }
	// 页面body鼠标事件
	function onBodyDown(event)
	{
		if (!(event.target.id == "fatherRegionName" || event.target.id == "regionContent" || $(event.target)
                        .parents(("#regionContent")).length > 0))
		{
			hideMenu();
		}
	}
	// 隐藏zTree
	function hideMenu()
	{
		$("#regionContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
    function dialogSubmit(){
        try{
            var bValid = $("#dialog_form").valid();
            if(bValid){
                var form = $("#dialog_form");
                $.ajax({
                    url: path + '/region_saveRegion.action',
                    type:'post',
                    data:form.serialize(),
                    dataType:'json',
                    success:function(response){
                        if (response.result){
                            window.parent.dialogClose();
                            clear();
                        }
                    },
                    error:function(){
                        dialog.message("保存失败");
                    }
                } );

            }else{
                return ;
            }
        }catch (e){
            dialog.message("保存失败");
        }
    }
</script>
</head>

<body>
	<form id="dialog_form" class="dialog_form"  method="post">
        <div style="display: none">
            <label for="regionId">区域ID:</label>
            <input type="text" id="regionId" name="regionParam.regionId" value="${regionParam.regionId}"/>
        </div>
		<div>
			<label for="regionName">区域名称:</label>
			<input type="text" id="regionName" name="regionParam.regionName" value="${regionParam.regionName}"/>
			<span style="color:red">*</span>
		</div>
		<div>
			<label for="regionNum">区域编号:</label>
			<input type="text" id="regionNum" name="regionParam.regionNum" value="${regionParam.regionNum}"/>
			<span style="color:red">*</span>
		</div>
		<div id="deptContent" class="ztree_hide" style="width:200px;">
			<ul id="deptTree" class="ztree"></ul>
		</div>
		<div>
			<label for="regionType">区域类型:</label>
			<select id="regionType" name="regionParam.regionType" >
				<option value="0" selected="selected">区域</option>
				<option value="1">子区</option>
			</select>
            <span style="color:red">*</span>
		</div>
		<div id="fatherRegion">
			<label for="fatherRegionName">父级区域:</label>
			<input type="text" id="fatherRegionName" name="fatherRegionName" value="${fatherRegionName}" onclick="showRegionMenu()"/>
			<input type="text" id="fatherRegionId" name="regionParam.fatherRegionId" value="${regionParam.fatherRegionId}" style="display: none"/>
		</div>
		<div id="regionContent" class="ztree_hide" style="width:200px;">
			<ul id="regionTree" class="ztree"></ul>
		</div>
		<div>
			<label for="regionDesc">区域描述:</label>
			<textarea id="regionDesc" name="regionParam.regionDesc">${regionParam.regionDesc}</textarea>
		</div>
	</form>
</body>

</html>