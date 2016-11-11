/**
 * 这个JS主要用于左菜单与多标签互动切换
 * 1.重写的右击弹出菜单
 * 2.实现标签与菜单的一致性
 * 3.增加等待特效
 * 4.优化程序，减少事件重复绑定
 * 5.标签的打开数据限定为10个
 */
$(function(){
	// 不作检查
	$.cookie.raw = true;
	
	// 标签数量
	var TAB_MORE = 10;
	// 左菜单
	var panel = $("body").layout("panel","west");
	
	$.extend({
		/**
		 * 重新加载完成后重新定位左菜单
		 * @param title 连接名称
		 * @param keyArray 各个菜单等级key组成的数组
		 */
		menuLoadLeft: function(title, keyArray) {
			// 重新定位head 菜单
			$("#firstGuideList").find(".firstGuideObjectHot").attr("class","firstGuideObject");
			$("div[key='"+keyArray[0]+"']").attr("class","firstGuideObjectHot");
			
			// 加载左菜单
			panel.panel({
				href:"menu.do?key=" + keyArray[0],
				onLoad : function(){
					$.menuGpsLeft(title, keyArray);
					$.keepMenuCount();
				},
				extractor: function(data){
					var json;
					try {
						json = $.parseJSON(data);
					} catch (e) {
						json = null;
					}
					if(json != null){
						location.href = '/back/login.do?message=' + (json.status==9?'kickOut':'timeOut');
					}
					return data;
				}
		   });
		},
		/**
		 * 左菜单重新定位
		 * @param title 连接名称
		 * @param keyArray 各个菜单等级key组成的数组
		 */
		menuGpsLeft: function(title, keyArray) {
			var a = $("a[key='"+keyArray[3]+"']");
			$(".menuList li").removeClass();
			a.children().attr("class","selected");
			//$("div[class='secOpenMenu']").attr("class", "secCloseMenu");
			$("div[class='secCloseMenu'][key='"+keyArray[2]+"']").attr("class", "secOpenMenu");
		},
		/**
		 * 菜单的精准定位，保证菜单与之对应的上级菜单一致性
		 * @param title 连接名称
		 * @param keyArray 各个菜单等级key组成的数组
		 */
		menuGps: function(title, keyArray) {
			var firstMenu = $(".firstLevelMenuTitle");
			if (firstMenu.attr("key") == keyArray[1]) {
				// 当前左菜单不变
				$.menuGpsLeft(title, keyArray);
			} else {
				// 重新定位
				$.menuLoadLeft(title, keyArray);
			}
		},
		/**
		 * 加载页面并定位左菜单
		 * @param title 连接名称
		 * @param keyArray 各个菜单等级key组成的数组
		 * @param url 地址
		 */
		loadPage: function(title, keyArray, url) {
			$.loadCachePage(title, keyArray, url);
			$.menuGpsLeft(title, keyArray);
		},
		/**
		 * 加载页面并缓存数据
		 * @param title 连接名称
		 * @param keyArray 各个菜单等级key组成的数组
		 * @param url 地址
		 */
		loadCachePage: function(title, keyArray, url) {
			if(keyArray.length < 3){
				alert("二级菜单尚未支持, 很抱歉");
				return;
			}
			var tab = $.loadCenter(title, url);
			// 缓存数据
			tab.data("url", url);
			tab.data("keyArray", keyArray);
		},
		/**
		 * 加载页面并定位
		 * @param title 连接名称
		 * @param keyArray 各个菜单等级key组成的数组
		 * @param url 地址
		 */
		loadGpsPage: function(title, keyArray, url) {
			// 加载页面
			$.loadCachePage(title, keyArray, url);
			// 定位
			$.menuGps(title, keyArray);
		},
		/**
		 * 点击加载左菜单
		 * @param key 顶级菜单的key
		 */
		navigation : function(key) {
			if(key) {
				panel.panel({
					href:"menu.do?key=" + key,
					onLoad : function(){
						$.showDefaultPage();
						$.keepMenuCount();
					  //　setInterval("$.keepMenuCount()", 60*1000);
					},
					extractor: function(data){
						var json;
						try {
							json = $.parseJSON(data);
						} catch (e) {
							json = null;
						}
						if(json != null){
							location.href = '/back/login.do?message=' + (json.status==9?'kickOut':'timeOut');
						}
						return data;
					}
			   });
			}
		},
		/**
		 * 点击加载中心页面
		 * @param title 连接名称
		 * @param url 地址
		 * @return 返回页面的容器tab对象
		 */
		loadCenter : function (title, url){
			// 如果不存在则不刷新页面
			//if ($('#tabUICenter').tabs('exists', plugin)) {
			//	$('#tabUICenter').tabs("select", plugin);
			//	return;
			//}
			// 删除cookie
			$.removeCookie(url, { path: '/' });
			// 加载页面
			var content = '<div class="icon-loading">&nbsp;</div><iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;" onload="$(this).prev().remove();"></iframe>';
			if ($('#tabUICenter').tabs('exists', title)){
				$('#tabUICenter').tabs('select', title);
				var tab = $('#tabUICenter').tabs('getTab', title);
				$('#tabUICenter').tabs('update', {
					tab: tab,
					options: {
						content:content,
						closable:true
					}
				});
			} else {
				var tabs = $('#tabUICenter').tabs("tabs");
				if(tabs.length > TAB_MORE-1){
					var tab = $('#tabUICenter').tabs('getSelected');
					tab.find('iframe')[0].contentWindow.showMessage("请关闭暂时不使用的标签!");
					return;
				}
				var tt = $('#tabUICenter').tabs('add',{
					title:title,
					content:content,
					closable:true
				});
			}
			return $('#tabUICenter').tabs("getTab", title);
		},
		// 显示默认的页面
		showDefaultPage : function() {
			var targetA;
			var pages =  $("a[defaultPage]");
			if(pages.size() > 0){
				targetA = pages.eq(0);
			}else{
				targetA = $(".menuList a[href]").first();
			}
			$.resolveHrefFmt(targetA.attr("href"));
			targetA.children().attr("class","selected");
			targetA.parent().parent().attr("class","secOpenMenu");
		},
		// 分析href并正确的加载
		resolveHrefFmt : function(href) {
			href = href.substring(href.indexOf(":") + 1);
			eval(href);
		},
		// 供子面调用
		fullscreen: function(bool) {
			var method = bool?'collapse':'expand';
			$("body").layout(method,"north");
			$("body").layout(method,"west");
			return bool;
		},
		// 创建一个新的menu
		createNewMenu: function() {
			if ($("#m-jump").size() > 0) {     
    			$('#smartmenu').menu('removeItem', $("#m-jump")[0]);
    		}else{    			
    			$('#smartmenu').menu('appendItem', {
    				separator: true
    			});
    		}
			$('#smartmenu').menu('appendItem', {
				id:'m-jump',
				text: '跳转到'
			});
			return $('#smartmenu').menu('getItem', $("#m-jump")[0]);
		}
	});
	
	// 统计
	$.keepHeadCount();
	setInterval("$.keepHeadCount()", 60*1000);
	setInterval("$.keepMenuCount()", 60*1000);
	
	// tab事件的指定
	$('#tabUICenter').tabs({
		// 在tab创建的时候绑定点击事件，主要作用是点击tab时上级菜单的精准定位
		onAdd: function(title, index) {
			$("ul.tabs li").eq(index).click(function() {
				if( !$(this).hasClass("tabs-selected") ){
					var curTab = $('#tabUICenter').tabs("getTab", $(this).index());
					//var url = curTab.data("url");
					var keyArray = curTab.data("keyArray");
					$.menuGps(title, keyArray);
				}
			});
		},
		// 右击弹出menu事件
		onContextMenu: function(e, title, index) {
            e.preventDefault();
            // 当前tab
            var curTab = $('#tabUICenter').tabs("getTab", index);
            // 所有tabs
            var tabsLength = $('#tabUICenter').tabs("tabs").length - 1;
            $('#smartmenu').menu({
            	// 动态填充跳转到哪个页面的导航
            	onShow: function() {
            		if (tabsLength != 0) {                			
            			var tabs = $('#tabUICenter').tabs("tabs");
            			var jumpItem = $.createNewMenu();
            			$.each(tabs, function(i, tab){
            				var options = tab.panel("options");
            				if (curTab != tab) {        						
            					$('#smartmenu').menu('appendItem', {
            						parent: jumpItem.target,  // the parent item element
            						text: options.title,
            						onclick: function(){
            							//$('#tabUICenter').tabs("select", options.title);
            							$("ul.tabs").find("li:contains('"+options.title+"')").trigger('click');
            						}
            					});
            				}
            			});
            		}
            	},
            	// 快捷操作
        		onClick: function(item) {
        			switch (item.id) {
						case 'm-close':
							$('#tabUICenter').tabs("close", index);
							break;
						case 'm-close-other':
					    	for(var i = tabsLength; i > -1 ; i--){
					    		if(i != index){
					    			$('#tabUICenter').tabs("close", i);
					    		}
					    	}
							break;
						case 'm-close-all':
					    	for(var i = tabsLength; i > -1 ; i--){
				    			$('#tabUICenter').tabs("close", i);
					    	}
							break;
						case 'm-close-left':
					    	for(var i = tabsLength; i > -1 ; i--){
					    		if(i < index){
					    			$('#tabUICenter').tabs("close", i);
					    		}
					    	}
							break;
						case 'm-close-right':
					    	for(var i = tabsLength; i > -1 ; i--){
					    		if(i > index){
					    			$('#tabUICenter').tabs("close", i);
					    		}
					    	}
							break;
						case 'm-refresh':
							var src =  curTab.data("url");
							$("ul.tabs").find("li:contains('"+title+"')").trigger('click');
							$.loadCenter(title, src);
							break;
						default:
							break;
					}
        		}
        	});
            $('#smartmenu').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
		}
	});
});