
/**
 * udisc　JQuery进行封装
 */
$(function(){
	$.extend({
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
		}
	});
	
	
	/*if(!$.browser.msie){
		alert("开卡操作需要IE浏览器的支持,请使用IE浏览器");
	}*/
	
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