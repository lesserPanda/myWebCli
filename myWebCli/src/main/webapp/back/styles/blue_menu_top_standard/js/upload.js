/**
 * 文件上传插件
 * 在kindEditor的insertfile复制体上进行的修改
 */
$(function(){
	$.fn.extend({
		upload:function(options){
			var defaults = {
					uploadJson : '/editor/kind/upload.do', // 上传
			        fileManagerJson : '/editor/kind/manage.do', // 文件管理
					allowFileManager : true, // 是否开启文件管理
					extraFileUploadParams:{						
						saveDir:"default", // 保存的相对根目录
						dir:"file"// 默认的文件类型 "image", "flash", "media", "file"四种类型
					},
					fileUrl:"",// 弹出窗口初始化url
					clickFn:function(url,title){ // 点击确定触发函数
						alert(url);// url上传后的文件路径
						alert(title);// 原文件名称
					}
			};
			// 参数整理
			var options = $.extend(defaults, options);
			var saveDir = options.extraFileUploadParams.saveDir;
			if(saveDir != null && saveDir != ""){
				options.fileManagerJson += "?saveDir="+saveDir;
			}
			
			// 初始化基本配置
			var K = KindEditor;
			var editor = K.editor(options);
			
			// 弹出窗口
			K('#' + $(this).attr("id")).click(function() {
				editor.loadPlugin('uploadfile', function() {
					editor.plugin.fileDialog({
						fileUrl : options.fileUrl,
						clickFn : function(url, title) {
							options.clickFn(url, title);
							editor.hideDialog();
						}
					});
				});
			});
			
		}
	});
});