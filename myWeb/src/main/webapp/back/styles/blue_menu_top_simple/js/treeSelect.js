/**
 * 单选、多选下拉选择框插件
 * 对easyui及自己相关的业务逻辑进行了一些封装
 * 兼容三种情况
 * 1.地区编码,10位共5级,后面不足10位补0
 * 2.非地区编码,2位数字表示一个层级,可无限级
 * 3.parentId类型编码,初始化的时候需要relateUrl支持,这个连接的作用是取得所有上级分类的数组
 * 
 */
$(function(){
	$.fn.extend({
		treeSelect:function(options){
			var defaults = {
					url:null, // 请求地址,返回JSON格式
					relateUrl:null,// 是否是编码关联的
					animate:true, // 折叠、展开的动画效果
					multiple:false, // 是否为多选
					downSelectOnly:false, // 最下面的地区才能选择
					width:220, // 默认宽度
					height:24, // 默认高度
					values:null // 值的初始化
			};
			var options = $.extend(defaults, options);
						
			if(options.values == null || options.values.length == 0){	
				$(this).combotree(options);
				$(this).bindDownSelectOnly(options);
			}else{		
				$.ajaxSetup({async:false}); // 开启同步
				$(this).combotree(options); // 加载
				$(this).bindDownSelectOnly(options);
				$(this).initTreeSelect(options); // 初始化
				$.ajaxSetup({async:true}); // 关闭同步
			}
			
			/*// 单选的时候
			// 只能选择最下属的地区
			if(options.downSelectOnly && !options.multiple){
				$(this).bindDownSelectOnly(options);
			}*/
		}
		,
		initTreeSelect:function(options){
			if(!$.isArray(options.values)){
				options.values = $.trim(options.values).split(",");
			}
			if(options.multiple){ // 多选
				for(var i = 0 ; i < options.values.length ; i++){
					//alert(options.values[i]);
					$(this).expandTreeSelectNode(options.relateUrl, options.values[i]);
				}
				$(this).combotree("setValues", options.values);
			}else{ // 单选
				$(this).expandTreeSelectNode(options.relateUrl, options.values[0]);
				$(this).combotree("setValue", options.values[0]);
			}
		}
		,
		expandTreeSelectNode:function(relateUrl, value){
			var parents;
			var parentNode;
			var ctxTree = $(this).combotree("tree");
			var rootNode = ctxTree.tree("getRoot");
			if(relateUrl != null){
				parents = $.getIDParents(relateUrl, rootNode.id, value);	
			}else{
				parents = $.getCodeParents(rootNode.id, value);				
			}
			if(parents != null && parents.length > 0){					
				for(var j = 0 ; j < parents.length ; j++){
					parentNode = ctxTree.tree("find",parents[j]);// 取得父节点
					if(parentNode == undefined || parentNode == null){
						alert("初始化值错误,节点ID不存在");
						return;
					}else{
						ctxTree.tree("expand",parentNode.target);
					}
				}
			}
		}
		,
		bindDownSelectOnly:function(options){
			if(options.downSelectOnly && !options.multiple){
				var $this = $(this);
				var ctxTree = $(this).combotree("tree");
				ctxTree.tree({
					onClick: function(node){
						var bool = ctxTree.tree("isLeaf", node.target);
						if(!bool){
							showMessage("请选择具体的下属地区");
						}else{	
							$this.combotree('setValue', node.id);
							$this.combotree('hidePanel');
						}
					}
				});
			}
		}
	});
	
	$.extend({
		/**
		 * 取得上级地区编码数组
		 * @param rootValue　根编码
		 * @param value　　　当前编码
		 */
		getCodeParents:function(rootValue, value){
			if(rootValue == null || value == null){
				alert("初始化值出错,节点ID=NULL");
				return null;
			}
			// 判断此编码是否补0
			var isEndZero = false;
			if(value.length == rootValue.length){
				isEndZero = true;
			}
			value = value.replace(/00/g,"");
			rootValue = rootValue.replace(/00/g,"");
			var array = new Array();
			for(var i = 0 ; i < value.length/2 ; i++){
				array.push(value.substring(i*2,i*2+2));
			}
			var newArray = new Array();
			if(array.length > 1){
				for(var j = 0 ; j < array.length-1 ; j++){
					if(j > 0){						
						array[j] = array[j - 1] + array[j];
					}
					if(array[j].length >= rootValue.length){
						if(array[j].indexOf(rootValue) == -1){
							alert("初始化值出错,初始化的值不在这个节点树中");
							return null;
						}
						newArray[j] = isEndZero?array[j] + "0000000000".substring(array[j].length):array[j];
					}
				}
			}
			return newArray;
		}
		,
		/**
		 * 取得上级ID数组
		 * @param rootValue　根ID
		 * @param value　　　当前ID
		 */
		getIDParents:function(relateUrl, rootValue, value){
			if(relateUrl == ""){
				return null;
			}
			var parents;
			if(rootValue != value){				
				$.get(relateUrl,{"rootValue":rootValue, "value":value},function(data){				
					parents = data.reverse();
				});
			}else{
				parents = new Array();
				parents.push(rootValue);
			}
			return parents;
		}
	});
});