/**
 * 从表相关的JS操作
 */
$(function(){
	var validatorArrayFromTable = new Object();
	$.extend({
		convertRightVae:function(vae, format){
			var realVae;
			if(vae == null){
				realVae = "";
			}else if(format.indexOf("{") != -1 && format.indexOf("}") != -1){
				var map = $.strToMap(format);
				realVae = map[vae + ""];
			}else{
				// 时间格式
				vae = new Date(parseInt(vae));
				realVae = $.dateFormat(vae, format);
			}
			return realVae;
		},
		strToMap:function(str){
			str = str.substring(str.indexOf("{") + 1, str.indexOf("}"));
			str = $.ToCDB(str);
			var s1 = str.split(",");
			var map = new Object();
			var s2;
			for(var i = 0 ; i < s1.length ; i++){
				s2 = s1[i].split(":");
				map[s2[0] + ""] = s2[1];
			}
			return map;
		},
		dateFormat:function(date, fmt){
			var o = {   
				    "M+" : date.getMonth()+1,                 //月份   
				    "d+" : date.getDate(),                    //日   
				    "H+" : date.getHours(),                   //小时   
				    "m+" : date.getMinutes(),                 //分   
				    "s+" : date.getSeconds(),                 //秒   
				    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
				    "S"  : date.getMilliseconds()             //毫秒   
				  };   
		  if(/(y+)/.test(fmt))   
			  fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  for(var k in o)   
			  if(new RegExp("("+ k +")").test(fmt))   
				  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		  return fmt;  
		},
		//全角转换为半角函数
		ToCDB:function(str){
			var tmp = "";
			for (var i = 0; i < str.length; i++) {
				if (str.charCodeAt(i) > 65248 && str.charCodeAt(i) < 65375) {
					tmp += String.fromCharCode(str.charCodeAt(i) - 65248);
				} else {
					tmp += String.fromCharCode(str.charCodeAt(i));
				}
			}
			return tmp 
		}
	});
	$.fn.extend({
		// 删除
		removeById:function(url, id){
			var $this = $(this);
			$.get(url,{"id":id},function(data){
				if(data.status){
					showMessage("删除成功");
					$this.remove();
				}else{
					showMessage("删除失败,请查看异常信息");
				}
			},"json");
		},
		// 还原初始状态
		restore:function(){
			$(this).find("input, select, textarea").val("").removeClass();
			$(this).find(":checked").prop("checked",false);
			$(this).find(":selected").attr("selected",false);
			return $(this);
		},
		// 整理顺序
		comborder:function(){
			$(this).children("tr").each(function(i){				
				$(this).find("[name*='\[']").each(function(){
					var name = $(this).attr("name");
					var newName = name.substring(0, name.indexOf("[") + 1) + i + name.substring(name.indexOf("]"));
					var newId = newName.replace("[","_").replace("].","_");
					$(this).attr("name", newName);
					$(this).attr("id", newId);
				});
			});
		},
		// 动态绑定验证
		bindValidator:function(){
			var tbId = $(this).attr("id");
			var map = $(this).find("tbody tr:first").getValidatorMap(tbId);
			$(this).find("[name*='\[']").each(function(){
				var name = $(this).attr("name");
				var nameBack = name.substring(name.indexOf(".") + 1);
				var options = map[nameBack];
				if(typeof(options) != "undefined" && options != null){
					var thisClone = $(this).clone(false);
					var oldValue = $(this).val();
					thisClone.validatebox(options);
					$(this).replaceWith(thisClone);
					if ($(this).is('textarea') && oldValue != '') {
						thisClone.val(oldValue);
					}
				}
			});
		},
		// 取得绑定的验证MAP
		getValidatorMap:function(tbId){
			var map = validatorArrayFromTable[tbId];
			//if(typeof(map) != "undefined" && map != null){
			if(map == null || map == undefined){
				map = new Object();
				$(this).find("[name*='\[']").each(function(){
					var options;
					try {
						options = $(this).validatebox("options");
						options.id = null;
					} catch (e) {
						options = undefined;
					}
					if(typeof(options) != "undefined"){
						var name = $(this).attr("name");
						var nameBack = name.substring(name.indexOf(".") + 1);
						map[nameBack] = options;
					}
				});
			}
			return map;
		},
		// 从表初化化
		oneBindInit:function(options){
			var table = $("#" + $(this).attr("id") + "_area").find("table.listNewsTable");
			var tbody = table.find("tbody");
			var firstTr = tbody.find("tr :first");
			
			$(this).one("click", function(){				
				$.get(options.url, options.param, function(data){
					if(!$.isEmptyObject(data)){
						$.each(data, function(i, map){	
							var newTr = firstTr.clone(true);
							$.each(options.attrs, function(i, attr){	
								var mh = attr.indexOf(":");
								if(mh != -1){
									var format = attr.substring(mh + 1);
									attr = attr.substring(0, mh);
									newTr.find("[name$='"+attr+"']").val($.convertRightVae(map[attr],format));
								}else{									
									newTr.find("[name$='"+attr+"']").val(map[attr]);
								}
							});
							newTr.find(":checkbox :first").val(map["id"]);// ID给值
							tbody.append(newTr);
						});
						firstTr.remove();
						tbody.comborder();
						table.bindValidator();
					}
				}, "json");
			});
		},
		// 直接初始化
		directInit :function(options) {
			var table = $(this);
			var tbody = table.find("tbody");
			var firstTr = tbody.find("tr :first");
			options.param = $.extend({d:new Date().getTime()}, options.param);
			$.get(options.url, options.param, function(data){
				if(!$.isEmptyObject(data)){
					$.each(data, function(i, map){	
						var newTr = firstTr.clone(true);
						$.each(options.attrs, function(i, attr){	
							var mh = attr.indexOf(":");
							if(mh != -1){
								var format = attr.substring(mh + 1);
								attr = attr.substring(0, mh);
								newTr.find("[name$='"+attr+"']").text($.convertRightVae(map[attr],format));
							}else{			
								newTr.find("[name$='"+attr+"']").val(map[attr]);
							}
						});
						//newTr.find(":checkbox :first").val(map["id"]);// ID给值
						tbody.append(newTr);
					});
					firstTr.remove();
					tbody.comborder();
					tbody.comborderFirstTdContent();
					table.bindValidator();
				}
			}, "json");
		},
		// 显示
		oneBindShow:function(options){
			var table = $("#" + $(this).attr("id") + "_area").find("table.listNewsTable");
			var tbody = table.children("tbody");
			
			$(this).one("click", function(){		
				options.param = $.extend({d:new Date().getTime()}, options.param);
				$.get(options.url, options.param, function(data){
					if(!$.isEmptyObject(data)){
						tbody.viewDataLoading(options, data);
					}
				}, "json");
			});
		},
		// 分页显示
		oneBindShowOfPage:function(options){
			var table = $("#" + $(this).attr("id") + "_area").find("table.listNewsTable");
			var tbody = table.children("tbody");
			
			$(this).one("click", function(){				
				tbody.viewDataLoadingOfPage(options);
			});
		},
		// 分页显示
		viewDataLoadingOfPage:function(options){
			var tbody = $(this);
			options.param = $.extend({d:new Date().getTime()}, options.param);
			$.get(options.url, options.param, function(data){
				if(!$.isEmptyObject(data)){
					// 加载分页数据
					tbody.viewDataLoading(options, data.list);
					// 初始化分页栏
					tbody.refreshPagination(options, data.attr);
				}
			}, "json");
		},
		// 分页的刷新
		refreshPagination: function(options, attr) {
			var tbody = $(this);
			tbody.next().find('.easyui-pagination').pagination('refresh',{
				total: attr.infoTotal,
				pageSize:attr.infoSize,
				pageNumber: attr.pageCur,
				onSelectPage: function(pageNumber, pageSize){
					console.log(pageNumber);
					console.log(pageSize);
					options.param['attr.pageCur'] = pageNumber;
					options.param['attr.infoSize'] = pageSize;
					tbody.viewDataLoadingOfPage(options);
	            }
			});
		},
		// 详细页面数据的加载
		viewDataLoading: function(options, data) {
			var tbody = $(this);
			tbody.empty();
			$.each(data, function(i, map){	
				var newTr = "<tr>"
				var textVae;
				$.each(options.attrs, function(i, attr){		
					newTr += "<td align=\"center\">"
					var mh = attr.indexOf(":");
					if(mh != -1){
						var format = attr.substring(mh + 1);
						attr = attr.substring(0, mh);
						textVae = $.convertRightVae(map[attr],format);
					}else{
						textVae = map[attr]==null?"":map[attr];
					}
					//alert(textVae);
					newTr += textVae;
					newTr += "</td>";
				});
				newTr += "</tr>";
				tbody.append(newTr);
			});
		},
		// 将从表前面第一个td用1，2，3标识
		comborderFirstTdContent: function(){
			$(this).children("tr").each(function(i){	
				$(this).children('td').first().text(i + 1);
			});
		}
	});
	
	
	/**
	 * 添加
	 */
	$("a[id^='add']").click(function(){
		var tbId = $(this).attr("id").replace("add","tb");
		var tbody = $("#" + tbId).children("tbody");
		var trs = tbody.children("tr");
		var newTr = trs.first().clone(true);
		newTr.children("td").first().children(":checkbox").val("");
		if((trs.size() + 2) % 2 == 0){
			newTr.attr("class", "normal1");
		}else{
			newTr.attr("class", "normal2");
		}
		// 还原
		newTr.restore();
		// 插入内容
		tbody.append(newTr);
		// 整理顺序
		tbody.comborder();
		// 重新绑定验证
		$("#" + tbId).bindValidator();
	});
	
	// 之前添加
	$('.addPrve').click(function(){
		var ownTr = $(this).parent().parent();
		var tbody = ownTr.parent();
		var trs = tbody.children("tr");
		var newTr = trs.first().clone(true);
		newTr.children("td").first().children(":checkbox").val("");
		if((trs.size() + 2) % 2 == 0){
			newTr.attr("class", "normal1");
		}else{
			newTr.attr("class", "normal2");
		}
		// 还原
		newTr.restore();
		// 插入内容
		//tbody.append(newTr);
		ownTr.before(newTr);
		// 整理顺序
		tbody.comborder();
		// 整理前面排序，例1，2，3
		tbody.comborderFirstTdContent();
		// 重新绑定验证
		tbody.parent().bindValidator();
	});
	
	// 之后添加
	$('.addNext').click(function(){
		var ownTr = $(this).parent().parent();
		var tbody = ownTr.parent();
		var trs = tbody.children("tr");
		var newTr = trs.first().clone(true);
		newTr.children("td").first().children(":checkbox").val("");
		if((trs.size() + 2) % 2 == 0){
			newTr.attr("class", "normal1");
		}else{
			newTr.attr("class", "normal2");
		}
		// 还原
		newTr.restore();
		// 插入内容
		//tbody.append(newTr);
		ownTr.after(newTr);
		// 整理顺序
		tbody.comborder();
		// 整理前面排序，例1，2，3
		tbody.comborderFirstTdContent();
		// 重新绑定验证
		tbody.parent().bindValidator();
	});
	
	/**
	 * 删除
	 */
	$("a[id^='remove']").click(function(){
		var tbId = $(this).attr("id").replace("remove","tb");
		var tbody = $("#" + tbId).children("tbody");
		var url = $(this).attr("url");
		var trBak = tbody.children("tr").first().clone(true);
		$.ajaxSetup({async:false}); // 开启同步
		tbody.find("input:checked").each(function(){
			var vae = $(this).attr("value");
			var parentTr = $(this).parent().parent();
			if(vae == ""){
				parentTr.remove();
			}else{
				if(confirm("确定从系统中删除吗?")){					
					parentTr.removeById(url, vae);
				}
			}
		});
		// 删除完了
		if(tbody.children("tr").size() == 0){
			tbody.append(trBak.restore());
			$("#" + tbId).bindValidator();
		}
		$.ajaxSetup({async:true}); // 关闭同步
		$("#" + tbId).children("thead").find(":checkbox :first").prop("checked", false);
		tbody.comborder();
	});
	
	// 删除
	$(".delThis").click(function(){
		var ownTr = $(this).parent().parent();
		var tbody = ownTr.parent();
		var trBak = tbody.children("tr").first().clone(true);
		
		
		var url = $(this).attr("url");
		var vae = $(this).parent().find("input[name$='id']").val();
		$.ajaxSetup({async:false}); // 开启同步
		if(vae == ""){
			ownTr.remove();
		}else{
			if(confirm("确定从系统中删除吗?")){					
				ownTr.removeById(url, vae);
			}
		}
		$.ajaxSetup({async:true}); // 关闭同步
		// 删除完了
		if(tbody.children("tr").size() == 0){
			tbody.append(trBak.restore());
			tbody.parent().bindValidator();
		}
		// 整理顺序
		tbody.comborder();
		// 整理前面排序，例1，2，3
		tbody.comborderFirstTdContent();
	});
	
});