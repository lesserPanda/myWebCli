/**
 * 文件上传插件
 */
$(function(){
	$("#imageButton").upload({
		uploadJson : '/editor/kind/upload.do', // 上传
        fileManagerJson : '/editor/kind/manage.do', // 文件管理
		allowFileManager : true, // 是否开启文件管理
		extraFileUploadParams:{						
			saveDir:"default", // 保存的相对根目录
			dir:"file"
		},
		clickFn:function(url,title){ // 点击确定触发函数 
			$("#imageDiv").append("<img src='"+url+"' style='width:200px;height150px:' alt='"+title+"' /><input name='image' id='image' hidden='true' value='"+url+"' />");
		}
	})
});
