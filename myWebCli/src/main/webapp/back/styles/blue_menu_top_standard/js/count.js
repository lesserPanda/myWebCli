/**
 * 实时统计代码
 */
$(function(){
	$.extend({
		keepHeadCount:function(){
			var counts;
			// 判断当前的位置
			if( window.top != window.self ){
				counts = $(window.parent.document).contents().find("#mainHead a[count]");
			}else{
				counts = $("#mainHead a[count]");
			}
			counts.each(function(){
				var $this = $(this);
				$.get($.fillUrlParam($(this).attr("count")),function(data){
					if(parseInt(data.count) > 0){	
						var redTip = $this.find("div.redTip");
						if(redTip.size() > 0){
							redTip.text(data.count);
						}else{							
							$this.prepend("<div class=\"redTip\">"+data.count+"</div>");
						}
					}else{
						$this.find("div.redTip").remove();
					}
				},"json");
			});
		},
		keepMenuCount:function(){
			var counts;
			// 判断当前的位置
			if( window.top != window.self ){
				counts = $(window.parent.document).contents().find("#menuLeft a[count]");
			}else{
				counts = $("#menuLeft a[count]");
			}
			counts.each(function(){
				var $this = $(this);
				$.get($.fillUrlParam($(this).attr("count")),function(data){					
					if(parseInt(data.count) >= 0){
						var span = $this.find("li span");
						if(span.size() > 0){
							span.text(data.count);
						}else{							
							$this.find("li").append("(<span>"+data.count+"</span>)");
						}
					}
				},"json");
			});
		},
		fillUrlParam:function(url){
			if(url.indexOf("?")>-1){
				url+="&rom="+Math.random();
			}else{
				url+="?rom="+Math.random();
			}
			return url;
		}
	});
});