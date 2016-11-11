/**
 * 表单验证插件
 * id:验证选项的ID
 * required:true/false, 是否必需输入
 * validType:请看下面扩展,默认有4个email,url,length,remote,新增加editor的验证
 * tipPosition:'left','right','top','bottom' // 提示位置
 * missingMessage:为空提示信息
 * invalidMessage:不为空验证提示信息
 * 
 */
$(function(){
	
	// 扩展验证的方法
	$.extend($.fn.validatebox.defaults.rules, {
		// 两文本内容比较
		equals : {
			validator : function(value, param) {
				return value == $(param[0]).val();
			},
			message : '确认密码错误,两次输入的密码不一致'
		},
		// 验证身份证 
		idcard : {
	        validator : function(value) { 
	            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value); 
	        }, 
	        message : '身份证号码格式不正确' 
	    },
	    // 最少输入字符
	    minLength: {
	        validator: function(value, param){
	            return value.length >= param[0];
	        },
	        message: '请输入至少（2）个字符.'
	    },
	    // 验证电话号码 
	    phone : {
	        validator : function(value) { 
	            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
	        }, 
	        message : '格式不正确,请使用下面格式:020-88888888' 
	    }, 
	    // 验证手机号码 
	    mobile : {
	        validator : function(value) { 
	            return /^(13|15|18)\d{9}$/i.test(value); 
	        }, 
	        message : '手机号码格式不正确' 
	    },
	    // 验证中文 
	    chinese : {
	        validator : function(value) { 
	            return /^[\Α-\￥]+$/i.test(value); 
	        }, 
	        message : '请输入中文' 
	    }, 
	    // 验证英语 
	    english : {
	        validator : function(value) { 
	            return /^[A-Za-z]+$/i.test(value); 
	        }, 
	        message : '请输入英文' 
	    }, 
	    // 验证是否包含空格和非法字符 
	    unnormal : {
	        validator : function(value) { 
	            return /.+/i.test(value); 
	        }, 
	        message : '输入值不能为空和包含其他非法字符' 
	    }, 
	    // 验证邮政编码 
	    zip : {
	        validator : function(value) { 
	            return /^[1-9]\d{5}$/i.test(value); 
	        }, 
	        message : '邮政编码格式不正确' 
	    },
	    // 验证用户名的唯一性
	    username : {
	    	validator : function(value, param) {
	    		var bool = false;
	    		var array = new Object();
	    		array["username"] = value;
	    		array["id"] = $(param[0]).val();
	    		$.ajax({
	    			async:false,//保证同步
	    			type:"GET",
	    			data:array,
	    			url:"/admin/user/unique.do",
	    			dataType:"json",
	    			success:function(data){
	    				bool = data.success; 
	    			}
	    		});
	    		return bool;
	        }, 
	        message : "用户名已存在，请更换"
	    }
	});
	
	$.fn.extend({
		validator:function(array){			
			// 加上验证信息
			if(array == undefined || array == null){
				alert("验证未初始化");
			}else{
				for(var i = 0 ; i < array.length ; i++){
					if(array[i]["validType"] == "number"){
						$("#" + array[i].id).numberbox(array[i]);
					}
					else if(array[i]["validType"] == "editor"){
						$("#" + array[i].id).prev().validatorEditor(array[i]);
					}
					else{						
						$("#" + array[i].id).validatebox(array[i]);
					}
				}
			}		
			
			// 提交表单
			$(this).submit(function(){
				// 验证表单
				var isValid = $(this).form('validate');
				
				// 验证kindEditor
				if(isValid){					
					var editors = $(".ke-edit-iframe");
					$(".validator-editor").each(function(){
						var thisEditor = editorArray[editors.index($(this))];
						thisEditor.sync();
						if(thisEditor.isEmpty()){
							isValid = false;
							$(this).tooltip("show");
						}
					});
				}
				return isValid;	
			});
		},
		validatorEditor:function(map){
			$(this).find("iframe").addClass("validator-editor");
			
			var editors = $(".ke-edit-iframe");
			var tipcontent = map["missingMessage"] || "请输入内容";
			// 注册提示信息
			$(".validator-editor").tooltip({
				position: 'right',
				content: tipcontent,
				onShow: function(){//样式保持一样
					$(this).tooltip('tip').css({
						color:"black",
						backgroundColor: '#FFFFFF',
						borderColor: '#FF6600'
					});
				}
			});
			// 删除相关的事件
			$(".validator-editor").unbind(); 
			$(".validator-editor").mouseover(function(){
				var thisEditor = editorArray[editors.index($(this))];
				thisEditor.sync();
				if(thisEditor.isEmpty()){
					$(this).tooltip("show");
				}
			});
			$(".validator-editor").mouseout(function(){
				var thisEditor = editorArray[editors.index($(this))];
				thisEditor.sync();
				if(!thisEditor.isEmpty()){
					$(this).tooltip("hide");
				}
			});
		}
	});
});