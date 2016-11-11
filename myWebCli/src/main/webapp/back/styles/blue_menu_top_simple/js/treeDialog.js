$(function() {
	$.fn.extend({
		treeDialog:function(options) {
			var defaults = {
					url:null, // 请求地址,返回JSON格式
					animate:true, // 折叠、展开的动画效果
					checkbox:true, // 是否为多选
					values:null,// 值初始化
					treeId:"treePerms",// 菜单默认的ID
					dialogId:"dialogPerms", // 弹出窗口默认的ID
					perms:null // 显示的可选结点
			};
			var options = $.extend(defaults, options);
			
			
			var div = '<div id="'+options.dialogId+'" style="display: none;">';
			div += '<ul id="'+options.treeId+'" class="easyui-tree"></ul>';
			div += '</div>';
			$("body").append(div);
			
			var dialogPerms = $('#'+options.dialogId);
			var treePerms = $("#"+options.treeId);
			var inputPerms = $(this);
			
			// 初始化
			options.onLoadSuccess = function(){
				var nodes;
				var parentNode;
				var node;
				// 显示的权限
				if(options.perms != null &&options.perms != ""){
					nodes = treePerms.tree("getChecked","unchecked");
					for(var i = 0; i < nodes.length; i++){
						node = nodes[i];
						if(options.perms != null && options.perms.indexOf(node.id) == -1){
							treePerms.tree("remove",node.target);
						}
					}
				}
				// 值初始化
				if(options.values != null &&options.values != ""){
					nodes = treePerms.tree("getChecked","unchecked");
					for(var i = 0; i < nodes.length; i++){
						node = nodes[i];
						if(options.values.indexOf(node.id) != -1){
							if(node.children != undefined && node.children.length > 0){
								treePerms.tree("expand", node.target);
							}else{
								parentNode = treePerms.tree("getParent", node.target);
								if(options.values.indexOf(parentNode.id) != -1){									
									treePerms.tree("check", node.target);
								}
							}
						}
					}
				}
			};
			
			treePerms.tree(options);
						
			// 弹出窗口
			inputPerms.focus(function(){
				dialogPerms.show();
				dialogPerms.dialog({
					title:'请分配权限',
					width: 300,
					height: 500,
					buttons:[{
						text:'确定',
						handler:function(){
							var nodes = treePerms.tree('getChecked',['checked','indeterminate']);
							var endKey = "";
							$.each(nodes, function(i, n){
								endKey += "," + n.id;
							});
							endKey = endKey.replace(",","");
							inputPerms.val(endKey);
							dialogPerms.dialog("close");
						}
					},{
						text:'关闭',
						handler:function(){
							dialogPerms.dialog("close");
						}
					}]
				});
			});
		}
	});
});