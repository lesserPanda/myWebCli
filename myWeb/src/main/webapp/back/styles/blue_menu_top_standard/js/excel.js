/**
 * excel导入导出组件JS
 * JQuery + easyui(弹出框) + layer(加载等待页面)
 */
$(function(){
	$.extend({
		// 初始化导出弹出窗口的内容
		initExportContent: function(data) {
			var content = '';
			content += '<div id="'+ data.name +'" class="export-excel" >';
			$.each(data.fields, function(i, field){				
				content += '<span><input type="checkbox" id="'+field.name+'" name="fieldNames" value="'+field.name+'"/><label for="'+field.name+'">'+field.comments+'</label></span>';
			});
			content += '</div>';
			return $(content);
		}
	});
	$.fn.extend({
		// 导出弹出窗口
		exportExcel: function(options) {
			var defaults = {
				title: 'My Dialog',
			    width: 400,
			    height: 200
			};
			var options = $.extend(defaults, options);
			
			// 初始化
			var curdown;
			var $this = $(this);
			$.get(options.url, function(data){
				// 初始化弹出窗口的内容
				curdown = $.initExportContent(data);
				// 弹出窗口属性及事件的绑定
				$this.bindExportEvent(options, curdown, data);
			}, "json");		
			
			$(this).click(function(){
				curdown.dialog(options);
			});
		},		
		// 弹出窗口属性及事件的绑定
		bindExportEvent: function(options, curdown, data) {
			var $this = $(this);
			// 标题
			options.title = data.comments;
			
			// 快速选择
			options.toolbar = [{
                text:'全选',
                handler:function(){
                	$('#' + data.name).find(":checkbox").prop("checked", true);
                }
            },'-',{
                text:'反选',
                handler:function(){
                	$('#' + data.name).find(":checkbox").each(function() {
                		$(this).prop("checked", !$(this).prop("checked"));
                	});
                }
            },'-',{
                text:'取消',
                handler:function(){
                	$('#' + data.name).find(":checkbox").prop("checked", false);
                }
            }];
			// 按钮
            options.buttons = [{
                text:'确定',
                handler:function(){
                	var checkbox = $('#' + data.name).find(":checked");
                	if (checkbox.length == 0) {
                		showMessage("没有选中任何字段");
                	}
                	// 提交表单
                	var form = $('<form method="post" action="'+ options.url +'" name="excel"></form>');	
                	// 
                	console.log($this);
                	var searchClone = $this.parents(".searchArea").clone();
                	form.append(searchClone);
                	form.append(curdow.clone());
                	form.appendTo('body');
                	form.submit();
                	form.remove();
                }
            },{
                text:'取消',
                handler:function(){
                	curdown.dialog("close");
                }
            }];
		}
	});
});
// Excel文件导入数据
$(function(){
	$.extend({
		// 初始化导出弹出窗口的内容
		initImportContent: function(data, url) {
			var content = '';
			content += '<div id="'+ data.name +'" class="import-excel" >';
			content += '<form method="post" action="'+url+'" name="'+data.name+'" enctype="multipart/form-data">';
			content += '<input name="excelFile" style="width:300px;"/>';
			content += '</form>';
			content += '</div>';
			return $(content);
		}
	});
	$.fn.extend({
		// 导出弹出窗口
		importExcel: function(options) {
			var defaults = {
				title: 'My Dialog',
			    width: 400,
			    height: 200,
			    success:function(msg){}
			};
			var options = $.extend(defaults, options);
			
			// 初始化
			var curdown;
			var $this = $(this);
			$.get(options.url, function(data){
				// 初始化弹出窗口的内容
				curdown = $.initImportContent(data, options.url);
				// 弹出窗口属性及事件的绑定
				$this.bindImportEvent(options, curdown, data);
			}, "json");	
			
			$(this).click(function(){
				curdown.dialog(options);
			});
		},		
		// 弹出窗口属性及事件的绑定
		bindImportEvent: function(options, curdown, data) {
			var $this = $(this);
			// 标题
			options.title = data.comments;
			
			// 文本框
			var inputFile = curdown.find('input');
			inputFile.filebox({
			    buttonText: '选择文件',
			    onChange: function(newValue, oldValue){
			    	if (newValue.toUpperCase().indexOf(".XLS") == -1) {
			    		showMessage("请选择Excel文件");
			    		inputFile.filebox('clear');
			    	}
			    }
			});

			// 快速选择
			options.toolbar = [{
                text:'下载模板',
                handler:function(){
                	location.href = options.url.replace('import', 'template');
                }
            }];
			// 按钮
            options.buttons = [{
                text:'确定',
                handler:function(){
                	// 提交表单
                	var form = curdown.find('form');
                	form.ajaxSubmit({
        				beforeSubmit:function(){
        					var jsonFile = curdown.find("input[name='excelFile']").fieldValue(); 
        					if(!jsonFile[0]){
        						alert("请选择要上传的文件");
        						return false;
        					}      					
        				    return true;
        				},
        				success:function(responseText, statusText, xhr, $form){
        					showMessage(responseText);
        					if (responseText == "导入数据成功"){        						
        						curdown.dialog("close");
        						if ($.isFunction(options.success)) {        							
        							options.success(responseText);
        						}
        					}
        				},
        				// 强制使用irame提交,保证浏览器之间的兼容性
        				iframe:true
        			});
                }
            },{
                text:'取消',
                handler:function(){
                	curdown.dialog("close");
                }
            }];
		}
	});
});
