/**
 * 利用LunarCalendar第三方工具
 * 在easyUI的calendar基础上扩展定制
 * 个性化的中国日历，包括农历，休假，工作，待办事项，节假日等
 */
$(function(){
	
	$.ajaxSetup({async:false}); // 开启同步
	var duedocData = {"20140908":2,"20140920":3,"20140926":3};
	
	/*
	// 1.初始化2014年的放假按排
	$.get("holiday.do",{"years":["2014","2015"]},function(data){
		setWorktime(data.workData);
	},"json");
	
	// 2.初始化那天到期的待办事项数量
	// var duedoc = {"20140603":2,"20140604":3,"20141104":3};格式模板
	$.get("duedoc.do",function(data){
		duedocData = data.duedocData;
	},"json");
	*/
	$.ajaxSetup({async:true}); // 关闭同步
	
	/**
	 * 取得今天公文到期未办理的数量
	 * @param y 年
	 * @param m 月
	 * @param d 日
	 */
	function getDueDocNum(y, m, d){
		var num = duedocData[y + (m<10?"0"+m:m) + (d<10?"0"+d:d)];
		if(num){
			return num;
		}
		return undefined;
	}
	
	/**
	 * 格式化日期
	 * @param y 年
	 * @param m 月
	 * @param d 日
	 */
	function fmtDate(y, m, d){
		// 公历转化成农历
		var nl = LunarCalendar.solarToLunar(y, m, d);
		var div = "";
		div += "<div class='op-calendar-new-relative'>";
		// 工作状态
		switch (nl.worktime) {
		case 1:
			div += "<span class='op-calendar-work'>班</span>";
			break;
		case 2:
			div += "<span class='op-calendar-holiday'>休</span>";
			break;
		default:
			break;
		}
		// 待办公方
		var docNum = getDueDocNum(y, m, d);
		if(typeof(docNum) != "undefined") {
			div += "<span class='op-calendar-doc'>办("+docNum+")</span>";
		}
		
		// 阳历日期
		div += "<span class='op-calendar-number'>"+d+"</span>";
		// 节假日
		div += "<div class='op-calendar-almanac'>";
		if(typeof(nl.lunarFestival) != "undefined") {
			div += nl.lunarFestival;
		}
		else if(typeof(nl.term) != "undefined") {
			div += nl.term;
		}
		else if(typeof(nl.solarFestival) != "undefined") {
			div += nl.solarFestival;
		}
		else{
			div += nl.lunarDayName;
		} 
		div += "</div>";
		div += "</div>";
		return div;
	}
	
	$.fn.extend({
		showCalendar:function(options){
			var defaults = {
				firstDay:1,// 起始时间
		    	height:600,// 高度
		    	width:800,// 宽度
		    	weeks:["周日","周一","周二","周三","周四","周五","周六"],//星期的展示形式
		    	formatter:function(date){
		    		var y = date.getFullYear();
		    		var m = date.getMonth() + 1;
		    		var d = date.getDate();
		    		//alert(y+"-"+m+"-"+d);
		    		return fmtDate(y, m, d);
		    	}
			};
			var options = $.extend(defaults, options);
			
			$("#cc").calendar(options);
		}
	});
	
});