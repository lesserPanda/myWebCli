
/**
 * udisc　JQuery进行封装
 * 新增了IE11的判断
 */
$(function(){
	$.extend({
		// 登录usbkey验证
		validateUsbkey:function(usbkey){
			if (!(usbkey == "true")) {
				return true;
			}
			if(!$.isIE()){
				$(".loginMsg").text("使用U盾登录, 请使用IE浏览器");
				return false;
			}
			if(!$.diskCheck()){
				$(".loginMsg").text("检查是否安装U盾驱动及U盾是否插入");
				return false;
			}
			var user = $.diskReadUser();
			if(user == null){
				$(".loginMsg").text("无效的身份识别卡信息");
				return false;
			}
			$(".loginUser").val(user);
			$(".loginUser").attr("readonly", true);
			return true;
		},
		// 验证用户
		validateUser:function(){
			var username = $.trim($(".loginUser").val());
			if(username == ""){
				$(".loginMsg").text("请输入用户名");
				return false;
			}
			var password = $.trim($(".loginPwd").val());
			if(password == ""){
				$(".loginMsg").text("请输入密码");
				return false;
			}
			if($(".loginChk").size() > 0){
				var chk = $.trim($(".loginChk").val());
				if(chk == ""){
					$(".loginMsg").text("请输入验证码");
					return false;
				}
			}
			return true;
		},
		// 测试disk　code的有效性
		diskCodeValidate:function(code, check){
			var re = eval("/^9000" + code + "/");
		    if (re.test(check))
		      return true;
		    else
		      return false;
		},
		// 检查disk是否存在
		diskCheck:function(){
			var length = DiscArray.length;
			for(var i=0 ; i < length ; i++){
				if(DiscArray[i].check()){
					return true;
				}
			}
			return false;
		},
		// 读取disk内容
		diskReadUser:function(){
			var code = $.diskRead();
			if(code == null){
				//alert("读取身份识别卡信息失败");
				return null;
			}
			var re = /^9000[0-9a-zA-Z]+_([0-9a-zA-Z]+)_[0-9a-zA-Z]+_[0-9]*\s/;
			var arr=code.match(re);
			if (arr==null){      
				//alert("无效的身份识别卡信息！");
				return null;     
			}
			return arr[1]; 
		},
		diskRead:function(){
			var length = DiscArray.length;
			var code;
			for(var i=0 ; i < length ; i++){
				code = DiscArray[i].read();
				if(code != null && $.trim(code) != ""){
					return code;
				}
			}
			return null;
		},
		// 往disk中写入内容
		diskWrite:function(code){
			code = $.diskUUID() + "_" + code + "_" + $.diskUUID() + "_" + new Date()*1;
			var length = DiscArray.length;
			for(var i=0 ; i < length ; i++){
				if(DiscArray[i].write(code)){
					return true;
				}
			}
			return false;
		},
		// 32位uuid
		diskUUID:function(){
			return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			      var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
			      return v.toString(16);
			    });
		},
		// IE判断
		isIE:function(){
			var Sys = {};
		    var ua = navigator.userAgent.toLowerCase();
		    var s;
		    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] :
		    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
		    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
		    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
		    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
		    (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
		    return Sys.ie;
		}
	});
	
	
	// 读写加密字符串
	var discKey = "12345678";
	
	// 定义类
	var USBDisc = function(array){
		this.object = array.object;// 控件
		this.check = array.check;// 检测控件是否存在
		this.read = array.read;// 控件的读操作
		this.write = array.write;// 控件写操作
	};
	
	// 初始化disc信息
	DiscArray = new Array();
	
	// 握奇USBKEY
	DiscArray.push(new USBDisc({
		check:function(){
			try{
				var r=udisk.open("USB1");//握奇
				udisk.close(r);
				return r>0;
			}catch(e){
				return false;
			}
		},
		read:function(){
			try{
				var fd = udisk.open("USB1");
				code = udisk.ReadBin(discKey, 0, 256);
				udisk.close(fd);
				return code;
			}catch(e){
				return null;
			}
		},
		write:function(code){
			try{
				var fd = udisk.open("USB1");
		    	udisk.WriteBin(discKey, 0, code + "\n");
		     	check = udisk.ReadBin(discKey, 0, 256);
		    	udisk.close(fd);
		    	return $.diskCodeValidate(code, check);
			}catch(e){
				return false;
			}
		}
	}));
	
	// 飞天USBKEY 1
	DiscArray.push(new USBDisc({
		check:function(){
			try{
				var r=udiskft.open("USB1");
				udiskft.close(r);
				return r>0;
			}catch(e){
				return false;
			}
		},
		read:function(){
			try{
				var fd = udiskft.open("USB1");
		        code = udiskft.ReadBin(discKey, 0, 256);
		   		udiskft.close(fd);
				return code;
			}catch(e){
				return null;
			}
		},
		write:function(code){
			try{
				var fd = udiskft.open("USB1");
		   	 	udiskft.WriteBin(discKey, 0, code + "\n");
		     	check = udiskft.ReadBin(discKey, 0, 256);
		    	udiskft.close(fd);
		    	return $.diskCodeValidate(code, check);
			}catch(e){
				return false;
			}
		}
	}));
	// 飞天USBKEY 2
	DiscArray.push(new USBDisc({
		check:function(){
			try{
				var r=udiskft.openHC("USB2");
				udiskft.closeHC(r);
				return r>0;
			}catch(e){
				return false;
			}
		},
		read:function(){
			try{
				var fd = udiskft.openHC("USB2");
			    code = udiskft.ReadBinHC(discKey, 0, 256);
			    udiskft.CloseHC(fd);
				return code;
			}catch(e){
				return null;
			}
		},
		write:function(code){
			try{
		    	var fd = udiskft.openHC("USB2");
		    	udiskft.WriteBinHC(discKey, 0, code + "\n");
		        check = udiskft.ReadBinHC(discKey, 0, 256);
		        udiskft.CloseHC(fd);
		    	return $.diskCodeValidate(code, check);
			}catch(e){
				return false;
			}
		}
	}));
	
	// 有新key的继续加就可以了
	// to add new usbkey
});