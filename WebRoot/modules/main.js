var rootPower;
var leftPower;
var leftSonPower;
var buttonPower;
$(function() {
		if($("#powerModuleId").val() == -1){
			$.ajax({
				url: path+'/user_getCurrentUserPowerInfo.action',
				data:{},
				type:'post',
				dataType:'json',
				success:function(response){
					rootPower = response.root;
					rootMenuPower();
				},
				error:function(){					
				}
			})
		}else if($("#level").val()==2){
			$.ajax({
				url: path+'/user_getCurrentUserPowerInfo.action',
				data:{},
				type:'post',
				dataType:'json',
				success:function(response){
					leftPower = response.left;
					leftSonPower = response.leftSon;
					leftMenuPower();
				},
				error:function(){					
				}
			})
		}else if($("#level").val()==4){
			$.ajax({
				url: path+'/user_getCurrentUserPowerInfo.action',
				data:{},
				type:'post',
				dataType:'json',
				success:function(response){
					leftSonPower = response.leftSon;
					buttonPower = response.button;
					buttonMenuPower();
				},
				error:function(){					
				}
			})
		}
		function rootMenuPower(){
			rootPower = powerSort(rootPower);
			for(var i=0;i<rootPower.length;i++){
				var $li = $("<li> <a href='"+path+"/"+rootPower[i].modulePath+"?powerModuleId="+rootPower[i].moduleId+
						    "&level=2' target='frameMain'><img src='"+rootPower[i].iconPath+"'title='"+
						    rootPower[i].moduleName+"'/><h2>"+rootPower[i].moduleName+"</h2></a></li>"
						);
				$("#frameTop .nav").append($li);
			}			
		}		
		function leftMenuPower(){
			var power = [];
			for(var i=0;i<leftPower.length;i++){
				if(leftPower[i].moduleParentId==$("#powerModuleId").val()){
					power.push(leftPower[i]);
				}
			}
			power = powerSort(power);
			var tempPower = null;
			for(var i =0;i<power.length;i++){
				var $dd = $("<dd></dd>");
				var $div = $("<div class='title'><span></span>"+power[i].moduleName+"</div>");
				var $ul = $("<ul class='menuson'></ul>");
				var powerSon = [];
				for(var j=0;j<leftSonPower.length;j++){
					if(leftSonPower[j].moduleParentId == power[i].moduleId){
						powerSon.push(leftSonPower[j]);
					}
				}
				powerSon = powerSort(powerSon);
				if(i==0){
					tempPower = powerSon[0];
				}
				for(var z=0;z<powerSon.length;z++){
					if(z==0&&i==0){
						var active = "class='active'";
					}else{
						var active = "";
					}
					var $li = $("<li "+active+">" +
							     "<div class='header'> <cite></cite><a href='"+path+"/"+
							     powerSon[z].modulePath+"?powerModuleId="+powerSon[z].moduleId+
								 "&level=4' target='frameContent'>"+
							     powerSon[z].moduleName+"</a><i></i></div></li>");
					$ul.append($li);
				}
				$dd.append($div);
				$dd.append($ul);
				$(".leftmenu").append($dd);
			}
			if(tempPower!=null){
				var $div = $("<div class='frameContent'><iframe id='frameContent' src='"+path+"/"+
					tempPower.modulePath+"?powerModuleId="+tempPower.moduleId+
					"&level=4' name='frameContent'></iframe></div>");
				/*"&level=4' name='frameContent' style='position:absolute;frameborder: 0; overflow: hidden; height: 100%; width:  100% ! important'></iframe></div>");*/
				$(document.body).append($div);
			}
		}
		function buttonMenuPower(){
			var power = [];
			for(var i=0;i<buttonPower.length;i++){
				if(buttonPower[i].pId==("module_"+$("#powerModuleId").val())){
					power.push(buttonPower[i]);
				}
			}
			power = powerSort(power);
			$("#rightinfo .tools .toolbar #watch").hide();
			$("#rightinfo .tools .toolbar #add").hide();
			$("#rightinfo .tools .toolbar #modify").hide();
			$("#rightinfo .tools .toolbar #del").hide();
			var power_edit = false;
			for(var i=0;i<power.length;i++){
				if(power[i].name=="查看"){
					$("#rightinfo .tools .toolbar #watch").show();
				}
				if(power[i].name=="增加"){
					$("#rightinfo .tools .toolbar #add").show();
					power_edit = true;
				}
				if(power[i].name=="修改"){
					$("#rightinfo .tools .toolbar #modify").show();
				}
				if(power[i].name=="删除"){
					$("#rightinfo .tools .toolbar #del").show();
				}
			}
			if($("#rightinfo .tools .toolbar:has('#power_edit')").length>0){//针对用户组权限编辑
					if($("#rightinfo .tools .toolbar #add:visible").length>0){
						$("#rightinfo .tools .toolbar #power_edit").show();
					}
					else{
						$("#rightinfo .tools .toolbar #power_edit").hide();	
					}
			}
			if($("#rightinfo .tools .toolbar:has('#mapEdit')").length>0){//针对电子地图编辑
				if($("#rightinfo .tools .toolbar #add:visible").length>0){
					$("#rightinfo .tools .toolbar #mapEdit").show();
				}
				else{
					$("#rightinfo .tools .toolbar #mapEdit").hide();
				}
			}
		}		
		function powerSort(power){//排序
			var tempPower;
			for(var i=0;i<power.length;i++)//根据moduleOrder排序
			{
				for(var j=i+1;j<power.length;j++){
					if(power[i].moduleOrder>power[j].moduleOrder){
						tempPower = power[i];
						power[i] = power[j];
						power[j] = tempPower;
					}
				}									
			}	
			return power;
		}
});