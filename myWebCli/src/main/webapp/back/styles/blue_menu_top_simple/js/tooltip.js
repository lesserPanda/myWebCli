/**
 * 信息提示
 */
$(function(){
	
	// 提示信息的样式
	var style = {
		color : "black",
		backgroundColor : '#FFFFFF',
		borderColor : '#FF6600'
	};
	
	$.fn.extend({
		tipText:function(options){
			var defaults = {
				position: 'right', // 位置
				content: null,// 显示内容
				onShow: function(){ // 覆盖默认的样式
					$(this).tooltip(style);
				}
			};
			var options = $.extend(defaults, options);
			$(this).tooltip(options);
		},
		tipAjax:function(options){
			var defaults = {
			    content : $('<div></div>'),
				onShow : function() {
					$(this).tooltip('arrow').css('left', 20);
					$(this).tooltip('tip').css('left', $(this).offset().left);
				},
				onUpdate : function(cc) {
					cc.panel({
						width : 500,
						height : 'auto',
						border : false,
						href : options.url
					});
				}
			};
			var options = $.extend(defaults, options);
			$(this).tooltip(options);
		},
	});
});