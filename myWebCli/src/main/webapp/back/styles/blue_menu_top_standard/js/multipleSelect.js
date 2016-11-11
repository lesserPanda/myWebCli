/**
 * 弹出多级菜单树
 * 参数说明:
 * selects:[{
 * 	id:selectId,这个自己定义
 * 	url:请求地址,
 *  lab:select说明
 * 	noKey:true,表示不需要这个对应的key
 *  noText:true,表示不需要这个对应的text
 * },{
 * }
 * ]
 * 
 * 
 */
$(function(){
	
	$.extend($.fn.datalist.defaults.view, {
		onBeforeRender: function(target, rows){
			// 清除options
			$(target).empty();
			$.each(rows, function(i, row){
				$(target).append('<option value="'+row.value+'">'+row.text+'</option>');
			});
			$(target).find('option').attr('selected', true);
		}
	});
	
	$.fn.extend({
		multipleSelect : function(options) {
			var defaults = {
					title:"请选择", // 标题
					multiple:true, // 最后一个选项是否为多选
					width:400, // 宽
					height:200, // 高
					left:null,
					top:null,
					container:"container", // 定义容器的ID,如果页面同时有多个联动菜单,这个要指定
					selects:null, // 级联select
					message:"请选择内容", // 为空时提示
					treeOkfnt:null
			};
			var options = $.extend(defaults, options);
			// 对select的处理
			options.hander = $(this);
			if(options.multiple){
				options.hander.attr('multiple', true);
			}
			var container = options.container;
			// 初始化容器
			var container = $.initContainer(options);
			// 加载select选项
			$.loadSelect(container, options.multiple, options.selects, 0);
			// 是否加载全选
			$.bindAllCheckbox(options);
			
			// 初始化dataList
			$(this).datalist({
				singleSelect:false,
				toolbar:[{
		            text:'添加',
		            iconCls:'icon-add',
		            handler:function(){
		            	$.openDialog(container, options);
		            }
		        },'-',{
		            text:'删除',
		            iconCls:'icon-cut',
		            handler:function(){
		            	var selections = options.hander.datalist('getSelections');
		            	var rows = options.hander.datalist('getRows');
		            	if (selections == null || selections.length == 0) {
		            		/*if (rows.length == 0) {
		            			showMessage("无效操作");
		            		}*/
		            		if (options.multiple) {		            			
		            			if (rows.length > 1) {		            				
		            				showMessage(options.message);
		            				return;
		            			}
		            		}
		            		options.hander.datalist('deleteRow', 0);
		            	} else {		            		
		            		var index;
		            		$.each(selections, function(i, selection) {
		            			index = options.hander.datalist('getRowIndex', selection);
		            			options.hander.datalist('deleteRow', index);
		            			options.hander.find('option[value="'+selection.value+'"]').remove();
		            		});
		            	}
		            }
		        }]
			});
		}
	});
	
	$.extend({
		/**
		 * 弹出窗口
		 * @param container 窗口ID
		 * @param options 参数
		 */
		openDialog: function(container, options){
			container.show();
			container.dialog({
				title:options.title,
				width: options.width,
				height: options.height,
				left:options.left,
				top:options.top,
				buttons:[{
					text:'确定',
					handler:function(){
						if(options.treeOkfnt != null){
							var array = $.getValueArray(options.multiple, options.message, options.hander, options.selects);
							if(array != null){									
								options.treeOkfnt(array);
								container.dialog("close");
							}
							return;
						}
						if($.setDatalistValue(options.multiple, options.message, options.hander, options.selects)){								
							container.dialog("close");
						}
					}
				},{
					text:'关闭',
					handler:function(){
						container.dialog("close");
					}
				}]
			});
		}
		,
		/**
		 * 点击确定设置值
		 */
		setDatalistValue:function(multiple, message, hander, selects){
			var node;
			var key = "";
			var value = "";
			var ctxTree;
			var lgh = selects.length;
			for(var i=0 ; i < lgh; i++){								
				ctxTree = $("#"+selects[i].id).combotree("tree");
				if(i+1 == lgh && multiple){
					var nodes = ctxTree.tree("getChecked");
					if(nodes.length == 0){
						showMessage(message);
						return false;
					}
					var key_;
					var value_;
					var option;
					var rows = [];
					for(var j=0; j< nodes.length; j++){
						key_ = key + nodes[j].id;
						value_ = value + nodes[j].text;
						rows.push({
							value: key_,
							text: value_
						});
					}
					hander.datalist('loadData',$.getRightRows(hander, rows));
				}else{
					node = ctxTree.tree("getSelected");
					if(node == null){
						showMessage(message);
						return false;
					}
					if(!selects[i].noKey){						
						key += (node.id + "#");
					}
					if(!selects[i].noText){						
						value += (node.text + "-");
					}
				}
			}
			if(!multiple && key != ""){
				key = key.substring(0,key.length - 1);
				value = value.substring(0,value.length - 1);
				hander.datalist('loadData',[{value:key, text: value}]);
			}
			return true;
		},
		/**
		 * 将当前的datalist插件中的值与当前选中的值进行组合，过滤到重复的值
		 * 得到正确的数组
		 * @param hander datalist对象
		 * @param rows 当前选中的值
		 */
		getRightRows: function(hander, rows){
			var oldRows = hander.datalist('getRows');
			var rightRows = $.merge(oldRows, rows)
			return $.uniqueRows(rightRows);
		},
		/**
		 * 过滤数组中重复的元素
		 * @param rows 选择的值
		 */
		uniqueRows:function(rows){
			var newRows = [];
			var temp;
			$.each(rows, function(i, row){
				if (i == 0) {
					temp = row.value;
					newRows.push(row);
				}else{
					if (temp.indexOf(row.value) == -1) {
						temp += "," + row.value;
						newRows.push(row);
					}
				}
			});
			temp = null;
			return newRows;
		}
	});
});