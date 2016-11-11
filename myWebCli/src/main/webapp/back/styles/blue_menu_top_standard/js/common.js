/**
 * 样式的控制
 * pop 与 attr的差别，1.11.3版本官方给出的建议是<br>
 * 具有 true 和 false 两个属性的属性，如 checked, selected 或者 disabled 使用prop()，其他的使用 attr()
 */
$(function(){
	$.fn.extend({
		showHot:function() {
			$(this).addClass("hot");
		}
		,
		showNomal:function(){
			$(this).removeClass("hot");
		}
	});
	
	var listTable = $("table.listNewsTable"); // 列表
	var trs = listTable.find("tbody tr"); // 信息行
	var topBox = listTable.find("thead :checkbox:first"); // 全选框
	var sonBoxs = listTable.find("tbody :checkbox"); // 子选框
	var items = listTable.find("tfoot .item"); // 分页
	
	// 管理列表全选框
	topBox.click(function() {
		var checked =  $(this).is(":checked") || false;
		$("table.listNewsTable").find("tbody input:checkbox").prop("checked", checked);
		if (checked) {
			listTable.find("tbody tr").each(function(){				
				$(this).showHot();
			});
		} else {
			listTable.find("tbody tr").each(function(){				
				$(this).showNomal();
			});
		}
	});
	// 管理列表子选框
	sonBoxs.click(function() {
		if (listTable.find("tbody :checked").size() > 0) {
			topBox.prop("checked", true);
		} else {
			topBox.prop("checked", false);
		}
	});
	
	// tr样式切换,列表页面鼠标划动颜色变化。
	trs.hover(function() {
		if ($(this).find(":checked").size() == 0) {
			$(this).showHot();
		}
	},function(){
		if ($(this).find(":checked").size() == 0) {
			$(this).showNomal();
		}
	});
	
	// 分页标签点击
	items.click(function() {
		var pageCur = parseInt($(this).children().text());
		if (!isNaN(pageCur)) {			
			$.turnPage(pageCur);
		}
	});
	// 按钮控制
	$(".subBtn").hover(
		function() {
		    $(this).attr("class", "subBtnHot");
		},
		function() {
			$(this).attr("class", "subBtn");
		}
	);
	
	// 选项卡控制
	$("div.areaTitle,div.areaTitleInner").find("div[class^='tab']").click(function(){		
		$(".tabarea").hide();
		var id=$(this).attr("id");
		$("#"+id+"_area").show();
		
		$("div.tabHot").removeClass("tabHot").addClass("tabNormal");
		$(this).removeClass("tabNormal").addClass("tabHot");	
	});
	
	$(".areaContent .tabarea").slice(1).hide();
	
	// 详细信息页面行间隔变换
	$(".detailTable").find("tbody").find("tr").attr("class", "normal1");
	$(".detailTable").find("tbody").find("tr:odd").attr("class", "normal2");
		
	// 详细信息页面行间隔变换
	$("table.formTable").children("tbody").children("tr").attr("class", "normal1").find("th");
	$("table.formTable").children("tbody").children("tr:odd").attr("class", "normal2").find("th");
	
	// 更多搜索条件的隐藏
	$("input.searchAreaClosetBtn").parents("table").find("tbody").hide();
	$(".searchAreaClosetBtn").click(function(){
		$(this).parents("table").find("tbody").toggle('slow','linear');		
		$(this).removeClass("searchAreaClosetBtn").addClass("searchAreaOpentBtn");
	    $(this).next().val("searchAreaOpentBtn");
	});
	$(".searchAreaOpentBtn").click(function(){
		$(this).parents("table").find("tbody").toggle('slow','linear');		
		 $(this).removeClass("searchAreaOpentBtn").addClass("searchAreaClosetBtn");
		 $(this).next().val("searchAreaClosetBtn");
	});

	
	//页面区域缩放控制
	$(".detailAreaClosetBtn").mouseover(function(){
		$(this).attr("title","点击收起详细！");
	});
	$(".detailAreaClosetBtn").click(function(){
		var curDiv = $(this).parent().parent().next("div");
		curDiv.toggle("fast");
	}).toggle(
		function() {
			$(this).attr("title","点击展开详细！");
		    $(this).removeClass("detailAreaClosetBtn").addClass("detailAreaOpentBtn");
			$(this).parent().parent().removeClass("detailAreaTitle").addClass("detailAreaTitleConttentClose");
		},function () {
			$(this).attr("title","点击收起详细！");
		    $(this).removeClass("detailAreaOpentBtn").addClass("detailAreaClosetBtn");
			$(this).parent().parent().removeClass("detailAreaTitleConttentClose").addClass("detailAreaTitle");
		}
	);
		
});