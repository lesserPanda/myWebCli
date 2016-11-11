$(function(){
	// 分页列表的id
	var pagedTableId = "listNewsTable";
	
	$.extend({
		href:function(url){
			var boxs = $("table."+pagedTableId).find("tbody").find(":checked");
			if(boxs.size() > 1){
				showMessage("只能选择一条数据");
			}else if(boxs.size() == 0){
				showMessage("请选择数据");
			}else{
				location.href = url + boxs.eq(0).val();
			}
		}
		,
		remove:function(id){
			if(id == null){
				var boxs = $("table."+pagedTableId).find("tbody").find(":checked");
				var lgh = boxs.size();
				if(lgh == 0){
					showMessage("请选择数据");
					return;
				}else{					
					id = "";
					for(var i = 0 ; i < lgh ; i++){
						id += ("," + boxs.eq(i).val()); 
					}
					id = id.replace(",", "");
				}
			}
			if(confirm("确定删除吗？")){
				location.href = "remove.do?id=" + id;
			}
		},
		alertActive:function(msg, url){
			if(confirm(msg)){
				location.href = url;
			}
		}
	});
});