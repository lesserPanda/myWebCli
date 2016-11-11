/**
 * 日志组件JS操作
 * 依赖easyUI,信息提示页面
 * 如果使用treeSelect,需将treeSelect.js引入
 * @author liuqiang
 */
$(function(){
	var logAddUrl = "/record/editLog/add.do";
	var logShowUrl = "/record/editLog/all.do";
	
	var editLogArray = {};
	// 日期处理
	function combDate(date){
		if(date != null){
			var d = new Date(date);
			return d.getFullYear() + '.'+(d.getMonth()+1) + '.' + d.getDate();
		}
		return "";
	}	
	$.fn.extend({
		/**
		 * 取得修改记录
		 * @param params.tableName 表名
		 * @param params.filedName 字段名
		 * @param params.infoId 信息的ID
		 */
		showEditLog:function(params){
			var $this = $(this);
			$.get(logShowUrl, params, function(data){
				var listNews = "";
				listNews +='<div class="listNews">';
				listNews +='<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listNewsTable">';
				listNews +='<thead>';
				listNews +='<tr>';
				listNews +='<th>原值</th>';
				listNews +='<th>新值</th>';
				listNews +='<th>修改人</th>';
				listNews +='<th>修改时间</th>';
				listNews +=' </tr>';
				listNews +='</thead>';
				listNews +='<tbody>';
				if(!$.isEmptyObject(data)){
					$.each(data, function(i, map){						
						listNews +='<tr class="'+(i%2==0?'normal1':'normal2')+'">';
						listNews +='<td align="left">';
						listNews += map.oldValue;
						listNews +='</td>';
						listNews +='<td align="left">';
						listNews += map.newValue;
						listNews +='</td>';
						listNews +='<td align="left">';
						listNews += map.doName;
						listNews +='</td>';
						listNews +='<td align="center">';
						listNews += combDate(map.doDate);
						listNews +='</td>';
						listNews +='</tr>';
					});
				}else{
					listNews += '<tr><td colspan="4">没有数据</td></tr>';
				}
				listNews +='</tbody>';
				listNews +='</table>';
				listNews +='</div>';
				
				editLogArray[params.tableName+'_'+params.filedName] = listNews;
				
				$this.tooltip({
					content:editLogArray[params.tableName+'_'+params.filedName],
					position:'bottom',
					onShow: function(){
						var t = $(this);
						t.tooltip('arrow').css('left', 20);
						t.tooltip('tip').css('left', t.offset().left);
						t.tooltip('tip').unbind().bind('mouseenter', function(){
							t.tooltip('show');
						 	}).bind('mouseleave', function(){
						 		t.tooltip('hide');
						 });
					}
				});
			}, "json");
			
		}
		,
		editLog:function(options){
			var defaults = {
					log:{
						doStatus:1 // 默认是修改
					},
					dom:{
						type:'text',// 类型
						onclick:null,
						clazz:null,
						size:20,
						format:null
					},
					msg:['添加成功', '修改成功', '删除成功']
			};
			
			options.log = $.extend(defaults.log, options.log);
			options.dom = $.extend(defaults.dom, options.dom);
			options.msg = $.extend(defaults.msg, options.msg);
			
			// 双击绑定事件(可编辑)
			$(this).bind('dblclick',function(){
				if($(this).find("input,select").size() > 0){
					return;
				}
				if(options.dom.type == 'text'){
					$(this).textFnt(options);
				}
				else if(options.dom.type == 'select'){
					$(this).selectFnt(options);			
				}
				else if(options.dom.type == 'radio'){
					$(this).radioFnt(options);		
				}
				else if(options.dom.type == 'checkbox'){
					$(this).checkboxFnt(options);	
				}
				else if(options.dom.type == 'treeSelect'){
					$(this).treeSelectFnt(options);	
				}else{
					alert("设置的类型不正确");
				}
				
			});			
		},
		// 文本
		textFnt:function(options){
			var value = $.trim($(this).text());
			$(this).empty();
			$(this).append('<input type="text" value="'+value+'"/>');
			$(this).append('<input type="button" value="保存">');
			$(this).find(":text").addAttr(options.dom);
			$(this).find(":text").focus();
			$(this).find(":button").bind("click",function(){
				var $text = $(this).prev();
				var newValue = $.trim($text.val());
				if(newValue == ""){
					$text.parent().text(value);
				}
				else if(newValue == value){
					$text.parent().text(value);
					showMessage("无修改");
				}
				else{
					options.log.oldValue = value;
					options.log.newValue = newValue;
					$.ajax({
						async:false,
						type : 'POST',
			            contentType: 'application/json; charset=utf-8',
						data:JSON.stringify(options.log),
						url:logAddUrl,
						dataType:'json',
						success:function(data){
							if(data.status){
								$text.parent().text(newValue);
								showMessage(options.log.filedDesc + options.msg[options.log.doStatus]);
							}else{
								$text.parent().text(value);
								showMessage('修改日志组件异常('+ options.log.filedDesc +')');
							}
						}
					});
				}
			});		
		},
		// 下拉框
		selectFnt:function(options){
			var key = $.trim($(this).attr('key'));
			var text = $.trim($(this).text());
			if(key == ''){
				alert(options.log.filedDesc + 'key值没有初始化');
				return;
			}
			if(!options.dom.format){
				alert(options.log.filedDesc + 'dom.format参数值没有初始化');
				return;
			}
			var selectHtml = '<select>';
			$.each(options.dom.format, function(k, t){
				selectHtml += '<option value="'+ k +'">'+ t +'</option>';
			});
			selectHtml += '</select>';
			
			$(this).empty();
			$(this).append(selectHtml);
			$(this).append('<input type="button" value="保存">');
			var $select = $(this).find("select");
			$select.addAttr(options.dom);
			$select.val(key);
			
			$(this).find(":button").bind("click",function(){
				var newKey = $select.val();
				var newText = $select.find("option:selected").text();
				
				if(newKey == key){
					$select.parent().text(text);
					showMessage("无修改");
				}else{
					options.log.oldValue = key;
					options.log.newValue = newKey;
					$.ajax({
						async:false,
						type : 'POST',
			            contentType: 'application/json; charset=utf-8',
						data:JSON.stringify(options.log),
						url:logAddUrl,
						dataType:'json',
						success:function(data){
							if(data.status){
								$select.parent().attr("key", newKey);
								$select.parent().text(newText);
								showMessage(options.log.filedDesc + options.msg[options.log.doStatus]);
							}else{
								$select.parent().text(text);
								showMessage('修改日志组件异常('+ options.log.filedDesc +')');
							}
						}
					});
				}
			});
		},
		// radio
		radioFnt:function(options){
			// 待实现，可参照select
		},
		// checkbox
		checkboxFnt:function(options){
			// 待实现，可参照select
		},
		// 下拉树，多选或单选
		treeSelectFnt:function(options){
			var key = $.trim($(this).attr('key'));
			if(key == ''){
				alert(options.log.filedDesc + 'key值没有初始化');
				return;
			}
			if(!options.dom.format){
				alert(options.log.filedDesc + 'dom.format参数值没有初始化');
				return;
			}
			var text = $.trim($(this).text());
			var treeHtml = '<select />';
			$(this).empty();
			$(this).append(treeHtml);
			$(this).append('<input type="button" value="保存">');
			var $this = $(this);
			var $tree = $this.find('select');
			options.dom.format.values = key;// 初始化
			$tree.treeSelect(options.dom.format);// 加载树	
			$(this).find(":button").bind("click",function(){
				var ctxTree = $tree.combotree("tree");
				var newKey = '';
				var newText = '';
				if(options.dom.format.multiple){// 多选
					var nodes = ctxTree.tree('getChecked');
					if(!$.isEmptyObject(nodes)){						
						$.each(nodes, function(i, n){
							newKey += (',' + n.id);
							newText += (',' + n.text);
						});
						newKey = newKey.replace(',','');
						newText = newText.replace(',','');
					}
				}else{// 单选
					var node = ctxTree.tree('getSelected');
					if(node != null){
						newKey = node.id;
						newText = node.text;
					}
				}
				if(newKey == '' || newText == ''){
					$this.text(text);
					showMessage("无修改");
				}else{
					options.log.oldValue = key;
					options.log.newValue = newKey;
					$.ajax({
						async:false,
						type : 'POST',
			            contentType: 'application/json; charset=utf-8',
						data:JSON.stringify(options.log),
						url:logAddUrl,
						dataType:'json',
						success:function(data){
							if(data.status){
								$this.attr("key", newKey);
								$this.text(newText);
								showMessage(options.log.filedDesc + options.msg[options.log.doStatus]);
							}else{
								$this.text(text);
								showMessage('修改日志组件异常('+ options.log.filedDesc +')');
							}
						}
					});
				}
			});
		},
		// 批量添加或修改
		batchEditLog:function(options){
			// 用到的时候再去实现了
		},
		// 附加属性
		addAttr:function(dom){
			if(dom.css){
				$(this).css(dom.css);
			}
			if(dom.onclick){
				$(this).attr("onclick",dom.onclick);
			}
			if(dom.clazz){
				$(this).attr("class", dom.clazz);
			}
			if(dom.size && $(this).is("input")){
				$(this).attr("size", dom.size);
			}
		}
	});
});