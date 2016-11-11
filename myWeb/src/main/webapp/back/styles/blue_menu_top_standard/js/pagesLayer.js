/**
 * 查询分页数据弹出选择层
 * url,param,back自定义添加的属性
 * 其它的属性请参照layer官网文档 http://sentsin.com/jquery/layer/api.html#base
 */
$(function(){
	var openLayerObject = new Object();
	$.fn.extend({
		/**
		 * 注册弹出窗口事件
		 */
		layerOpen : function(options) {
			var defaults = {
				type: 2,
				title: false, // 弹出窗口标签
			    shadeClose: true,
			    shade: 0.8,
			    area: ['800px', '60%'],
			}
			
			var options = $.extend(defaults, options);
			// 判断条件是否具备
			if(options.url == null || options.back == null){
				showMessage("请求的URL或回调函数必需设置");
				return;
			}
			// 参数拼接
			options.content = [options.url, 'auto'];
			
			// 点击弹出窗口
			$(this).click(function() {
				// 缓存保存属性
				$(this).data("options", $.extend(true, {}, options));
				var optionsBak = $(this).data("options")
				if(optionsBak.param != null){	
					$.each(optionsBak.param, function(i, n) {
						if($.isFunction(n)){
							//alert(n);
							optionsBak.param[i] = optionsBak.param[i]();
						}
					});
					optionsBak.content[0] += '?' + $.param(optionsBak.param);
				}
				var i = layer.open(optionsBak); 
				
				$(this).data("options", $.extend(true, {}, options));
				openLayerObject["layer" + i] = $(this);
			});
		},
		
		/**
		 * 取得窗口初始化的属性
		 */
		layerOptions : function() {
			return $(this).data("options");
		}
	});
	
	$.extend({
		/**
		 * 执行回调函数
		 */
		layerBack : function(data, i) {
			var $this = openLayerObject["layer" + i];
			openLayerObject["layer" + i] = null;
			$this.data("options").back(data, $this);
			layer.close(i);
		}
	});
});
