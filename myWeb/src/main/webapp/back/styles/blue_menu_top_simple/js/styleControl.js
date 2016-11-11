/**
 * 样式控制
 */

$(function(){
	
	// 选项卡控制
	$("div.areaTitle,div.areaTitleInner").find("div[class^='tab']").click(function(){		
		$(".tabarea").hide();
		var id=$(this).attr("id");
		$("#"+id+"_area").show();
		
		$("div.tabHot").removeClass("tabHot").addClass("tabNormal");
		$(this).removeClass("tabNormal").addClass("tabHot");
		
		//以下是重置框架页面高度的操作，去除滚动条
		var tabHeight = $("body").height();
		var frameOldHeight = $("#editAreaFrame", window.parent.document).height();
		var vBodyHeight=$(window).height()-115;
		//当前页面比框架页高，则重置框架页面的高度
		if(tabHeight>frameOldHeight){
			$("#editAreaFrame", window.parent.document).height(tabHeight);
		}else{
			if(vBodyHeight>=tabHeight){
				$("#editAreaFrame", window.parent.document).height(vBodyHeight);
			}else{
				$("#editAreaFrame", window.parent.document).height(tabHeight+5);
			}
		}
		
	});
	
	$("div.tabHot").click();
	
	
	// 按钮控制
	$(".subBtn").mouseover(function(){
		$(this).removeClass("subBtn").addClass("subBtnHot");
	}).mouseout(function(){
		$(this).removeClass("subBtnHot").addClass("subBtn");
	});
	
	// 鼠标划动变色
	/*$(".listNewsTable").find("tbody").find("tr").mouseover(function(){			
		$(this).data("bc",$(this).css("background-color"));
		$(this).css("background-color","#CAE4F9");
	}).mouseout(function(){		
		$(this).css("background-color",$(this).data("bc"));
	});*/
	
	//详细信息页面行间隔变换
	$(".detailTable").find("tbody").find("tr").attr("class", "normal1");
	$(".detailTable").find("tbody").find("tr:odd").attr("class", "normal2");
		
	//详细信息页面行间隔变换
	$(".formTable").find("tbody").find("tr").attr("class", "normal1").find("th");
	$(".formTable").find("tbody").find("tr:odd").attr("class", "normal2").find("th");
		
	
	$(".iconTableList").find("tr").mouseover(function(){			
		$(this).data("bc",$(this).css("background-color"));
		$(this).css("background-color","#CAE4F9");
	}).mouseout(function(){		
		$(this).css("background-color",$(this).data("bc"));
	});
	
	$(".mailTableList").find("tr").mouseover(function(){			
		$(this).data("bc",$(this).css("background-color"));
		$(this).css("background-color","#CAE4F9");
	}).mouseout(function(){		
		$(this).css("background-color",$(this).data("bc"));
	});
	
	
	$("div.searchArea").find("tbody").hide();
	
	//搜索区域缩放
	$(".searchAreaClosetBtn").click(function(){
		$(this).parents("table").find("tbody").toggle('slow','linear');		
	}).toggle(function () {
				    $(this).removeClass("searchAreaClosetBtn").addClass("searchAreaOpentBtn");
				  },function () {
				    $(this).removeClass("searchAreaOpentBtn").addClass("searchAreaClosetBtn");
				  });
				  
				  
	//页面区域缩放控制
	$(".detailAreaClosetBtn").mouseover(function(){
		$(this).attr("title","点击收起详细！");
	});
	$(".detailAreaClosetBtn").click(function(){
		var curDiv = $(this).parent().parent().next("div");
		curDiv.toggle("fast");
	}).toggle(function () {
					$(this).attr("title","点击展开详细！");
				    $(this).removeClass("detailAreaClosetBtn").addClass("detailAreaOpentBtn");
					$(this).parent().parent().removeClass("detailAreaTitle").addClass("detailAreaTitleConttentClose");
				  },function () {
					 $(this).attr("title","点击收起详细！");
				    $(this).removeClass("detailAreaOpentBtn").addClass("detailAreaClosetBtn");
					$(this).parent().parent().removeClass("detailAreaTitleConttentClose").addClass("detailAreaTitle");
				  });
	
});