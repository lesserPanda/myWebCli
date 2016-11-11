$(function(){
	var index = parent.layer.getFrameIndex(window.name);
	//var index = parent.layer.getFrameIndex("index.jsp");
	$("#submit").on("click",function(){
		
		//$("form").submit();
		var comment = $("#messageContent").val();
		
		if("" == comment || null == comment){
			layer.msg("请留下您宝贵的意见");
			$("#messageContent").addClass("areaborder");
			return;
		}else{
			var messagePerple = $("#messagePerple").val();
			var messageEmail = $("#messageEmail").val();
			var messageContent = $("#messageContent").val();
			var messageTitle = $("#messageTitle").val();
			$("#messageContent").removeClass("areaborder");
		};
		$.ajax({
			url:"/index/saveLiveMessage",
			data:{messagePerple:messagePerple,messageEmail:messageEmail,messageContent:messageContent,messageTitle:messageTitle},
			type:"post",
			dataType:"json",
			success:function(data){
				console.log(data);
				
				if(data.message){
					layer.open({
						  type: 1, //page层
						  closeBtn: 0, //不显示关闭按钮
						  area: ['350px', '0px'],
						  title: '感谢您宝贵的意见！留言成功*^v^*',
						  shade: 0.6, //遮罩透明度
						  time: 2000, //2秒后自动关闭
						  moveType: 1, //拖拽风格，0是默认，1是传统拖动
						  shift: 1, //0-6的动画形式，-1不开启
						  //content: '<div style="padding:50px;">感谢您宝贵的意见！留言成功*^v^*</div>',
						  end: function(){ //此处用于演示
							  parent.layer.close(index);
						  }
						});
					/*alert(data.message);
					layer.msg('感谢您宝贵的意见！留言成功*^v^*', {time: 5000,icon: 1});
					parent.layer.close(index);*/
				}
			}
		});
		
	});
	
});
