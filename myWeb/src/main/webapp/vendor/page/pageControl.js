$(function(){
	$.extend({
		submitForm:function(){
			var inputPageCur = $("input[name='attr.pageCur']");
			var inputInfoSize = $("input[name='attr.infoSize']");
			var pageCur = parseInt(inputPageCur.val());
			var infoSize = parseInt(inputInfoSize.val());
			if(isNaN(pageCur) || pageCur <= 0){
				inputPageCur.val(1);
			}
			if(isNaN(infoSize) || infoSize <= 0){
				inputInfoSize.val(15);
			}
			$("form").eq(0).submit();
		}
		,
		turnPage:function(num){
			$("input[name='attr.pageCur']").val(num);
			$.submitForm();
		}
	});
});