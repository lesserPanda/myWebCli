/**
 * 样式的控制
 */
$(function(){
	$.fn.extend({
		showHot:function(){
			$(this).attr("oldClass", $(this).attr("class"));
			$(this).attr("class", "hot");
		}
		,
		showNomal:function(){
			if($.trim($(this).attr("oldClass"))==""){
				$(this).removeClass();
				return;
			}
			$(this).attr("class", $(this).attr("oldClass"));
			$(this).removeAttr("oldClass");
		}
	});
	
	var listTable = $("table.listNewsTable"); // 列表
	var trs = listTable.find("tbody tr"); // 信息行
	var topBox = listTable.find("thead :checkbox :first"); // 全选框
	var sonBoxs = listTable.find("tbody :checkbox"); // 子选框
	var items = listTable.find("tfoot .item"); // 分页
	var btns = 
	
	// 管理列表全选框
	topBox.click(function(){
		var checked = $(this).attr("checked") || false;
		sonBoxs.attr("checked", checked);
		if(checked){
			trs.each(function(){				
				$(this).showHot();
			});
		}else{
			trs.each(function(){				
				$(this).showNomal();
			});
		}
	});
	// 管理列表子选框
	sonBoxs.click(function(){
		if(listTable.find("tbody :checked").size() > 0){
			topBox.attr("checked", true);
		}else{
			topBox.attr("checked", false);
		}
	});
	// tr样式切换
	trs.hover(function(){
		if($(this).find(":checked").size()==0){
			$(this).showHot();
		}
	},function(){
		if($(this).find(":checked").size()==0){
			$(this).showNomal();
		}
	});
	// 分页标签点击
	items.click(function(){
		var pageCur = parseInt($(this).children().text());
		if(!isNaN(pageCur)){			
			$.turnPage(pageCur);
		}
	});
	
});