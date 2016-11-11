/**
 * 一级导航菜单
 * 1.点击滚动
 * 2.鼠标滑轮滚动
 * 3.鼠标左击拖拽(暂未支持)
 * 
 * **参数说明
 * navMoveSpeed : 可选的有"show","normal","fast",也可以是具体的毫秒数
 * navMoveEasing : 有32特效供选择
 */

$(function(){	
	
	$.extend({		
		// 导航菜单数量
		navNum:function(){			
			var firstGuide=($(window).width()-$("#logoArea").width()-$("#oneSelfArea").width()-50);	
			var num= parseInt(firstGuide/90);
			return num<1?1:num;	
		},
		
		// 是否有隐藏的前菜单
		hasHideStart:function(){
			var firstShow = $("#navigation").children(":not(:hidden)").first();
			return firstShow.prev().size() > 0;
		},
		// 是否有隐藏的后菜单
		hasHideEnd:function(){
			var lastShow = $("#navigation").children(":not(:hidden)").last();
			return lastShow.next().size() > 0;
		},
		// 取得显示菜单的前i个
		getShowStart:function(i){
			return $("#navigation").children(":not(:hidden)").slice(0,i);
		},
		// 取得显示菜单的后i个
		getShowEnd:function(i){
			return $("#navigation").children(":not(:hidden)").slice(navShow + i);
		},
		// 取得前面隐藏菜单后i个
		getHideStart:function(i){
			var firstShow = $("#navigation").children(":not(:hidden)").first();
			var hideStart = firstShow.prevAll();
			return hideStart.slice(0,-i);
		},
		// 取得后面隐藏菜单的前i个
		getHideEnd:function(i){
			var lastShow = $("#navigation").children(":not(:hidden)").last();
			var hideEnd = lastShow.nextAll();
			return hideEnd.slice(0, i);
		},
		/**
		 * 移动菜单
		 * @param i i>0向后移动,i<0向前动,i移动的菜单数
		 */
		navMove:function(i){
			$.unBindNavEvent();
			if(i > 0){
				if($.hasHideEnd()){
					var shows = $.getShowStart(i);
					var hides = $.getHideEnd(i);
					$.easingAnimation(shows, hides);
				}else{
					$.bindNavEvent();
				}	
			}
			if(i < 0){
				if($.hasHideStart()){
					var shows = $.getShowEnd(i);
					var hides = $.getHideStart(i);
					$.easingAnimation(shows, hides);
				}else{
					$.bindNavEvent();
				}
			}
		},
		// 绑定事件
		bindNavEvent:function(){
			// 向前移动
			$("#navPrev").bind("click",function(){
				$.navMove(-1);
			});
			// 向后移动
			$("#navNext").bind("click",function(){
				$.navMove(1);
			});
			// 菜单增加鼠标滚动事件
			$("#firstGuideList").bind("mousewheel",function(event, delta) {
				$.navMove(delta > 0 ? -1 : 1);
				return false;
			});
			// 菜单增加鼠标拖拽事件
			// dosomething
		},
		// 解除绑定
		unBindNavEvent:function(){
			$("#navPrev").unbind();
			$("#navNext").unbind();
			$("#firstGuideList").unbind("mousewheel");
			$("#firstGuideList").bind("mousewheel",function() {
				return false;
			});
		},
		// 动画效果
		easingAnimation:function(shows, hides){
			shows.hide({
				duration:navMoveSpeed,
				easing:navMoveEasing
			});
			hides.show({
				duration:navMoveSpeed,
				easing:navMoveEasing,
				complete:function(){
					$.bindNavEvent();
				}
			})
		},
		
		// 一级菜单初始化
		init:function(navAll,navShow){			
			// 多的隐藏
			$("#navigation").children("div :gt("+(navShow-1)+")").hide();
			$("#navigation").children("div :lt("+(navShow)+")").show();
			if(navAll > navShow){				
				
				// 动态显示或隐藏菜单左右滚动按钮
				$("#firstGuideList").hover(function(){
					$(".navbtn").show();
				},function(){
					$(".navbtn").hide();
				});				
						
			}else{
				// 如果菜单全部显示
				$("#firstGuideList").unbind("hover");
			}
		}
	});
	
	// 显示菜单数量
	var navShow = $.navNum();
	
	// 动画的速度
	var navMoveSpeed = "fast";
	// 动画的效果
	var navMoveEasing = "easeInExpo";
	
	// 实际菜单数量
	var navAll = $("#navigation").children("div").size();
	
	// 隐藏
	$(".navbtn").hide();
		
	$.init(navAll,navShow);
	// 通过动态的绑定及解除绑定，实现动画效果同步
	// 绑定主要事件
	$.bindNavEvent();
	
	$(window).resize(function(){
		navShow = $.navNum();		
		$.init(navAll,navShow);
	});
	
});