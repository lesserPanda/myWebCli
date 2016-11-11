/**
 * 信息提示提示插件
 * 对easyUI的提示信息插件做了一些封装
 * @author liuqiang
 */
$(function(){
	
	// 提示信息的样式
	var style = {
		color : "black",
		backgroundColor : '#FFFFFF',
		borderColor : '#FF6600'
	};
	
	$.fn.extend({
		/**
		 * 文本提示
		 * @param options.position 弹出提示信息的位置,'left','right','top','bottom'.
		 * @param options.showEvent 提示信息显示的事件
		 * @param options.hideEvent 提示信息隐藏的事件
		 * @param options.content 提示内容
		 * 关于更详细的参数请参考easyUI tooltip API
		 */
		tipText:function(options){
			var defaults = {
				position: 'right', // 默认显示的位置
				content: '你没有设置提示信息',// 显示内容
				onShow: function(){ // 覆盖默认的样式
					$(this).tooltip(style);
				}
			};
			var options = $.extend(defaults, options);
			$(this).tooltip(options);
		},
		/**
		  * ajax提示
		 * @param options.position 弹出提示信息的位置,'left','right','top','bottom'.
		 * @param options.showEvent 提示信息显示的事件
		 * @param options.hideEvent 提示信息隐藏的事件
		 * @param options.url 请求的URL
		 * @param options.width 宽
		 * @param options.height 高
		 * @param options.border 是否有边框
		 * 关于更详细的参数请参考easyUI tooltip API
		 */
		tipAjax:function(options){
			var defaults = {
			    content : $('<div></div>'),
				onShow : function() {
					$(this).tooltip('arrow').css('left', 20);
					$(this).tooltip('tip').css('left', $(this).offset().left);
				},
				onUpdate : function(cc) {
					cc.panel({
						width : options.width | 500,
						height : options.height | 'auto',
						border : options.border | true,
						href : options.url
					});
				}
			};
			var options = $.extend(defaults, options);
			$(this).tooltip(options);
		},
	});
});