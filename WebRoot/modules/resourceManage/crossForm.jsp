<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  /*  var signal_tree;*/
	var featureArray = new Array();
	featureArray[0] = [ "0", "环形交叉口" ];
	featureArray[1] = [ "11", "匝道/出入口" ];
	featureArray[2] = [ "21", "路段" ];
	featureArray[3] = [ "31", "T形路口" ];
	featureArray[4] = [ "32", "Y形路口" ];
	featureArray[5] = [ "33", "错位T形路口" ];
	featureArray[6] = [ "34", "错位Y形路口" ];
	featureArray[7] = [ "41", "十字形路口" ];
	featureArray[8] = [ "42", "斜交路口" ];
	featureArray[9] = [ "51", "多路路口" ];
	featureArray[10] = [ "99", "其他" ];
	$(function(){
		var crossId = $("#crossId").val();
		elementInit();
		/* 输入验证 */
		$("#dialog_form").validate(
				{
					rules :
					{
						'crossParam.crossName':{
							required: true,
							maxlength : 25,
							remote :
							{
								type : "post",
								dataType : "json",
								url : path + "/cross_validatorCrossName.action",
								data :
								{
									"crossName" : function()
									{
										return $("#crossName").val();
									},
									"crossId" : function()
									{
										return $("#crossId").val();
									}
								}
							}
						},
						'crossParam.crossNum':{
							required : true,
							filterWord : true,
							isNum : true,
							minlength:14,
							maxlength:16,
							isLetterNum : true,
							remote :
							{
								type : "post",
								dataType : "json",
								url : path + "/cross_validatorCrossNum.action",
								data :
								{
									"crossNum" : function()
									{
										return $("#crossNum").val();
									},
									"crossId" : function()
									{
										return $("#crossId").val();
									}
								}
							}
						},
						'crossParam.regionParam.regionName': {
							required: true,
						},
						'iskey':{
							required : true,
						}
					},
					messages :
					{
						'crossParam.crossName' :
						{
							remote : "路口名称已存在"
						},
						'crossParam.crossNum' :
						{
							remote : "路口编号已存在"
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
					$("#regionId").val(treeNode.id.split("_")[1]);
					$("#regionName").val(treeNode.name);
					hideMenu();
				},
				// zTree加载完成事件
				onAsyncSuccess : function()
				{
				}
			}
		};
		region_tree = $.fn.zTree.init($("#regionTree"), region_setting);
	/*	var signal_setting =
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
				url:path+"/treeResource_getSignalControlerTrees.action"
			},
			callback :
			{
				// zTree单击事件
				onClick : function(event, treeId, treeNode)
				{
					$("#signalControlerId").val(treeNode.id.split("_")[1]);
					$("#signalControlerName").val(treeNode.name);
					hideMenu();
				},
				// zTree加载完成事件
				onAsyncSuccess : function()
				{
				}
			}
		};
		signal_tree = $.fn.zTree.init($("#signalTree"), signal_setting);*/
		function elementInit(){
			if(window.parent.isWatch){  //查看的时候input disabled
				$("#dialog_form :input").attr("disabled","disabled");
			}
			$("#feature").html("");
			for ( var i = 0; i < featureArray.length; i++)
			{
				document.getElementById("feature").options.add(new Option(featureArray[i][1], featureArray[i][0]));
			}
			console.log('${crossParam.feature}');
			if(crossId!=null && crossId!=""){
				$("#feature").val('${crossParam.feature}');
				$("#isKey").val('${crossParam.isKey}');
				$("#crossType").val('${crossParam.crossType}');
			}
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
        var inputOffset = $("#regionName").offset();
        var left = parseInt(inputOffset.left)-40 ;
        var top = parseInt(inputOffset.top + $("#regionName").outerHeight()) - 5;
        $("#regionContent").css(
                {
                    left : left + "px",
                    top : top + "px",
                }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }

/*	// 显示树的位置
	function showSignalMenu()
	{
		var inputOffset = $("#signalControlerName").offset();
		var left = parseInt(inputOffset.left)-40 ;
		var top = parseInt(inputOffset.top + $("#signalControlerName").outerHeight()) - 5;
		$("#signalContent").css(
				{
					left : left + "px",
					top : top + "px",
				}).slideDown("fast");
		$("body").bind("mousedown", onBodySignalDown);
	}*/
	// 页面body鼠标事件
	function onBodyDown(event)
	{
		if (!(event.target.id == "regionName" || event.target.id == "regionContent" || $(event.target)
						.parents(("#regionContent")).length > 0))
		{
			hideMenu();
		}
	}
/*	// 页面body鼠标事件
	function onBodySignalDown(event)
	{
		if (!(event.target.id == "signalControlerName" || event.target.id == "signalContent" || $(event.target)
						.parents(("#signalContent")).length > 0))
		{
			hideMenu();
		}
	}*/
	// 隐藏zTree
	function hideMenu()
	{
		$("#regionContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);

	/*	$("#signalContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodySignalDown);*/
	}
	function clear(){
		$("#dialog_form :input").val("");
		$("#dialog_form :input").removeAttr("disabled");
		window.parent.isWatch = false;
	}
    function dialogSubmit(){
        try{
            var bValid = $("#dialog_form").valid();
            if(bValid){
                var form = $("#dialog_form");
                $.ajax({
                    url: path +'/cross_saveCross.action',
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
            <label for="crossId">路口ID:</label>
            <input type="text" id="crossId" name="crossParam.crossId" value="${crossParam.crossId}"/>
        </div>
		<div>
			<label for="crossName">路口名称:</label>
			<input type="text" id="crossName" name="crossParam.crossName" value="${crossParam.crossName}"/>
			<span style="color:red">*</span>
		</div>
		<div>
			<label for="crossNum">路口编号:</label>
			<input type="text" id="crossNum" name="crossParam.crossNum" value="${crossParam.crossNum}"/>
			<span style="color:red">*</span>
		</div>
		<div>
			<label for="regionName">所属区域:</label>
			<input type="text" id="regionName" name="crossParam.regionParam.regionName" value="${crossParam.regionParam.regionName}" onclick="showRegionMenu()" readOnly="readonly"/>
			<input type="text" id="regionId" name="crossParam.regionParam.regionId" value="${crossParam.regionParam.regionId}"  style="display: none"/>
			<span style="color:red">*</span>
		</div>
		<div id="regionContent" class="ztree_hide" style="width:200px;">
			<ul id="regionTree" class="ztree"></ul>
		</div>

<%--		<div>
			<label for="crossName">所属信号机:</label>
			<input type="text" id="signalControlerName" name="crossParam.signalControler.signalControlerName" value="${crossParam.signalControler.signalControlerName}" onclick="showSignalMenu()" readOnly="readonly"/>
			<input type="text" id="signalControlerId" name="crossParam.signalControler.signalControlerId" value="${crossParam.signalControler.signalControlerId}"  style="display: none"/>
			<span style="color:red">*</span>
		</div>
		<div id="signalContent" class="ztree_hide" style="width:200px;height:200px;">
			<ul id="signalTree" class="ztree"></ul>
		</div>--%>
		<div>
			<label for="feature">路口特征:</label>
			<select id="feature" name="crossParam.feature" >
			</select>
            <span style="color:red">*</span>
		</div>
		<div>
			<label for="isKey">是否关键路口:</label>
			<select id="isKey" name="crossParam.isKey">
				<option value="0">非关键路口</option>
				<option value="1">关键路口</option>
			</select>
		</div>
		<div>
			<label for="crossType">路口类型:</label>
			<select id="crossType" name="crossParam.crossType">
				<option value="0">一般路口</option>
				<option value="1">特殊路口</option>
			</select>
		</div>
		<div>
			<label for="crossDesc">路口描述:</label>
			<textarea id="crossDesc" name="crossParam.crossDesc">${crossParam.crossDesc}</textarea>
		</div>
	</form>
</body>

</html>