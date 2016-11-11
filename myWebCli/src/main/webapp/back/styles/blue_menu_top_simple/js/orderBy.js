$(function(){
	// 初始化排序
	var divPagesort = $("div.pagesort");
	divPagesort.find("span").each(function(){
		var hiddens = $(this).find(":hidden");
		$("span[pagesort='"+hiddens.first().val()+"']").attr("class", hiddens.last().val().toLowerCase()+"-sort");
	});
		
	// 点击排序提交
	$("span[pagesort]").click(function(){
		var orderBy;
		if($(this).attr("class") == "desc-sort"){
			orderBy = "ASC";
		}
		else{
			orderBy = "DESC";
		}
		var pagesort = $(this).attr("pagesort");
		divPagesort.empty();
		divPagesort.append('<span>');
		divPagesort.append('<input type="hidden" name="collates[0].sortField" value="'+pagesort+'"/>');
		divPagesort.append('<input type="hidden" name="collates[0].sortType" value="'+orderBy+'"/>');
		divPagesort.append('</span>');
		$("form").submit();
	});
});