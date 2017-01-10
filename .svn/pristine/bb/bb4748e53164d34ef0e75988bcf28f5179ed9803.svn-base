/**
 * 
 */

$(function(){
	 var boardDiv = "<div id='message' style='display:none;'><span style='display:block;line-height:20px;text-align:center;margin-top:20px'  id='spanmessage'></span></div>";
	 $(document.body).append(boardDiv);
});
	var iframeId;
	function readyDialog(dialogid,width,height){
		 $("#"+dialogid).dialog({
			  width:width,
			  height:height,
			  closeOnEscape: false,
		      autoOpen: false,
		      modal: true,
		      resizable:false,
		      open:function(event,ui){
		    	  $(".ui-dialog-titlebar-close").hide();
		      },
		      buttons: {
		    	"保存":function(){
		    		document.getElementById(iframeId).contentWindow.dialogSubmit();
		    	},
		        "取消": function() {
		        	document.getElementById(iframeId).contentWindow.clear();
		           $( this ).dialog( "close" );		           
		        }
		      },
			 close:function(){
				 $('.ui-dialog-buttonpane .help').html("");
			 }
		    }); 
	} 
	function showDialog(config){
		 if(config.disabled){
			 $('.ui-dialog-buttonpane').find('button:contains("保存")').attr("disabled", "disabled");
		 }else{
			 $('.ui-dialog-buttonpane').find('button:contains("保存")').removeAttr("disabled");
		 }
		 iframeId = config.iframeId;
		var iframe = document.getElementById(config.iframeId);
		iframe.src = config.src;
        if (iframe.attachEvent){
        	iframe.attachEvent("onload", function(){
       		 $("#"+config.id).dialog("open");
    		 $("#"+config.id).dialog('option', 'title', config.title);
        	});
        } else {
        	iframe.onload = function(){
       		 $("#"+config.id).dialog("open");
    		 $("#"+config.id).dialog('option', 'title', config.title);
        	};
        }			
	}
      var dialog={
      		error:function(text,callback) {
                $("#spanmessage").text(text);
                $("#message").dialog({
                    title:"系统提示",
                    modal: true,
/*  			      open:function(event,ui){
  			    	  $(".ui-dialog-titlebar-close").hide();
  			      },*/
                    buttons: {
                        "确定": function() {
                      	    callback.call();//方法回调
                            $(this).dialog("close");
                        }
                    }
                });
      		},
    		message:function(text) {
              $("#spanmessage").text(text);
              $("#message").dialog({
                  title:"系统提示",
                  modal: true,
			     open:function(event,ui){
			    	  $(".ui-dialog-titlebar-close").hide();
			      },
                  buttons: {
                      "确定": function() {
                          $(this).dialog("close");
                      }
                  }
              });
    		},
    		confirm:function(text, title,callback){
    	      	  $("#spanmessage").text(text);
    	          $("#message").dialog({
    	              title: title,
    	              modal: true,
    	              resizable: false,
/*    			      open:function(event,ui){
    			    	  $(".ui-dialog-titlebar-close").hide();
    			      },*/
    	              buttons: {
						  "是": function() {
							  callback.call();//方法回调
							  $(this).dialog("close");
						  },
    	                  "否": function() {
    	                      $(this).dialog("close");
    	                  }
    	              }
    	          });
    		}  
      };
