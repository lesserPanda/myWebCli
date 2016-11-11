/**
 * iframe高度自适应
 */

/*
 * 框架ID与定时器ID的映射
 * 是为方法查找定时器ID,便于取消定时器。
 */
var iframeIntervalMapping={};

function reinitIframe(id){

	var iframe = document.getElementById(id);
	if(iframe==null){
		alert("指定的iframe不存在");
		window.clearInterval(iframeIntervalMapping[id]);
		return;
	}
	try {
		var bHeight =0;
		try{
			bHeight=iframe.contentWindow.document.body.scrollHeight;
		}catch(e){}
		
		var dHeight = 0;
		 try{
			 dHeight=iframe.contentWindow.document.documentElement.scrollHeight;
		 }catch(e){}
		 
		var height = Math.max(bHeight, dHeight);
		
		iframe.height = height;
		if(height>10){			
			window.clearInterval(iframeIntervalMapping[id]);
		}
	} catch (ex) {
		alert(ex);
	}
}

/*
 * 页面调用此方法
 */
function initIframHeight(id){
	var intervalid=window.setInterval("reinitIframe('"+id+"')", 200);	
	iframeIntervalMapping[id]=intervalid;
}
