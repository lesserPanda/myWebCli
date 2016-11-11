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
	$.fn.extend({
		treeRelateDialog:function(options){
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
			
			var container = options.container;
			
			// 初始化容器
			var container = $.initContainer(options);
			// 加载select选项
			$.loadSelect(container, options.multiple, options.selects, 0);
			// 是否加载全选
			$.bindAllCheckbox(options);
			
			$(this).click(function(){
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
							if($.setSelectValue(options.multiple, options.message, options.hander, options.selects)){								
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
			});		
		}
	});
	
	$.extend({
		initContainer:function(options){
			if($("#"+options.container).size()==0){
				var htmlContent = '<div id="'+options.container+'" style="display: none;">';
				    htmlContent += '<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formTable">';
				    htmlContent += '<tbody>';
				    for(var i=0 ; i < options.selects.length ; i++){
				    htmlContent += '<tr style="display: none;"><th width="15%">'+options.selects[i].lab+'：</th>';
		    		htmlContent += '<td width="35%" colspan="3">';
	    			htmlContent += '<select id="'+options.selects[i].id+'" style="height:24px !important;width:180px;"/>';
	    			if((options.selects.length == i + 1) && options.multiple){
	    			htmlContent += '<label style="display: none;"><input type="checkbox"/>全选</label>';	
	    			}
    				htmlContent += '</td></tr>';
				    }
					htmlContent += '</tbody></table></div>';
					$("body").append(htmlContent);
			}
			return $("#"+options.container);
			
		}
		,
		loadSelect:function(container, multiple, selects, i){
			var cur = selects[i];
			i = i + 1;
			var next = selects[i];
			if(cur != null){
				// 初始始参数
				var curArray = new Array();
				curArray.url = cur.url + (cur.paramId==null?"":cur.paramId);
				if(next == null){//如果是最后一个select多选
					curArray.multiple = multiple;
					curArray.onShowPanel = function(){
						if(multiple){
							var selectId = cur.id;
							var label = $("#"+selectId).next().next();
							label.show();
						}
					};
				}
				curArray.onSelect = function(node){
					if(next == null){
						// something
					}else{
						selects[i].paramId = node.id;
						$.loadSelect(container, multiple, selects, i);
					}
				};
				// 加载select
				$("#"+cur.id).parents("tr").show();
				$("#"+cur.id).combotree(curArray);
			}
		}
		,
		setSelectValue:function(multiple, message, hander, selects){
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
					for(var j=0; j< nodes.length; j++){
						key_ = key + nodes[j].id;
						value_ = value + nodes[j].text;
						if($("#"+hander).find("option[value='"+key_+"']").size() == 0){
							option = '<option value="'+key_+'">'+value_+'</option>';							
							$("#"+hander).append(option);
						}
					}
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
				$("#"+hander).find("option").remove();
				$("#"+hander).append('<option value="'+key+'">'+value+'</option>');
			}
			return true;
		},
		getValueArray:function(multiple, message, hander, selects){
			var node;
			var key = "";
			var value = "";
			var ctxTree;
			var lgh = selects.length;
			var array = new Array();
			for(var i=0 ; i < lgh; i++){		
				ctxTree = $("#"+selects[i].id).combotree("tree");
				if(i+1 == lgh && multiple){
					var nodes = ctxTree.tree("getChecked");
					if(nodes.length == 0){
						showMessage(message);
						return null;
					}
					array.push(nodes);
				}else{
					node = ctxTree.tree("getSelected");
					if(node == null){
						showMessage(message);
						return null;
					}
					array.push(node);
				}
			}
			return array;
		},
		bindAllCheckbox:function(options){
			if(options.multiple){
				var lgh = options.selects.length;
				var selectId = options.selects[lgh-1].id;
				$("#"+selectId).parent().find("label :checkbox").change(function(){
					var ctxTree = $("#"+selectId).combotree("tree");
					var nodes = ctxTree.tree("getRoots");
					if($(this).is(":checked")){
						for(var i=0; i< nodes.length;i++){
							ctxTree.tree('check', nodes[i].target);
						}
					}else{
						for(var i=0; i< nodes.length;i++){
							ctxTree.tree('uncheck', nodes[i].target);
						}
					}
				});
			}
		}
	});
});