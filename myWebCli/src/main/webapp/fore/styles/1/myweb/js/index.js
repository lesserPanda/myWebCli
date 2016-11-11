$(function(){
	//加载首页简介
	$.ajax({
		url:"/index/getSYJJ",
		data:{"categoryId":"006"},
		type:"get",
		dataType:"json",
		success:function(data){
			var gr0HTML = '';
		
			$.each(data, function(index, value){
				var createDate = new Date(value.createDate);
				//alert(value.content);
				//这里是为了只加载最新的第一个
				if(index == 0){
					gr0HTML += '<figure> <img src="'+value.image+'" style="width:630px;height:250px"  alt="Panama Hat"><figcaption><strong>'+value.title+'</strong>'+value.description+value.content+'</figcaption></figure><div class="card"><h2>我的名片</h2>'+
				      '<p>网名：lesserPanda | 小熊猫</p><p>姓名：潘登</p><p>职业：java开发</p><p>电话：13396680863</p><p>Email：pandeng123321sina.com</p><ul class="linkmore"><li><a href="javascript:void(0)" id="liveMessage" class="talk" title="给我留言"></a></li>'+
				      '<li><a href="/" class="address" title="联系地址"></a></li><li><a href="mailto:pandeng123321@sina.com" class="email" title="给我写信"></a></li><li><a href="/" class="photos" title="生活照片"></a></li><li><a href="/" class="heart" title="关注我"></a></li>'+
				      '</ul></div>';
				}
				
			})
			$("#mainbody .info").append(gr0HTML);
			
		}
	});
	//加载个人技能
	$.ajax({
		url:"/index/getGRJN",
		data:{"categoryId":"002"},
		type:"get",
		dataType:"json",
		success:function(data){
			
			var gr1HTML = '';
			$.each(data, function(index, value){
				var createDate = new Date(value.createDate);
				
				
					gr1HTML += '<li><div class="arrow_box"><div class="ti"></div><!--三角形--><div class="ci"></div><!--圆形--><h2 class="title"><a href="/index/toView?id='+value.id+'" target="_blank">'+value.title+
					'</a></h2><ul class="textinfo"><a href="/index/toView?id='+value.id+'" target="_blank"><img src="'+value.image+'"></a><p class="slue">'+value.description+'</p></ul><ul class="details"><li class="likes">'+(value.pushParise == null?0:value.pushParise)+
					'</li><li class="comments">'+(value.cmsComments == null?0:value.cmsComments.size())+'</li><li class="icon-time">'+createDate.getFullYear()+'-'+(createDate.getMonth()+1)+'-'+createDate.getDay()+'</li></ul></div></li>';
				
				
			})
			
			$(".blogs .bloglist").append(gr1HTML);
		}
	});
	
	//加载转载收藏
	$.ajax({
		url:"/index/getZZSC",
		data:{"categoryId":"003"},
		type:"get",
		dataType:"json",
		success:function(data){
			var gr2HTML = '';
			$.each(data, function(index, value){
				console.log(value.zzFlag);
				if(index < 3){
					var zzFlagHtml = "";
					if("0031" == value.zzFlag){
						zzFlagHtml = '<p class="1" id="'+value.id+'" zzFlag="'+value.zzFlag+'">伤不起</p>';
					}else if("0032" == value.zzFlag){
						zzFlagHtml = '<p class="2" id="'+value.id+'" zzFlag="'+value.zzFlag+'">喜欢</p>';
					}else if("0033" == value.zzFlag){
						zzFlagHtml = '<p class="3" id="'+value.id+'" zzFlag="'+value.zzFlag+'">感兴趣</p>';
					}
				
					gr2HTML += '<li><a href="/index/toView?id='+value.id+'"><img src="'+value.image+'">'+value.description+'</a>"'+zzFlagHtml+'"</li>';
				}
				
			})
			
			$(".toppic ul").append(gr2HTML);
			
			$(".toppic ul li").each(function(){
				$(this).delegate("p", "click", function(){
					var clas = $(this).attr("class");
					$.ajax({
						url:"/index/updateZZSC",
						data:{id:$(this).attr("id"),zzFlag:$(this).attr("zzFlag")},
						type:"get",
						dataType:"json",
						success:function(data){
							if("1" == clas){
								layer.tips('+1', ".1");
							}else if("2" == clas){
								layer.tips('+1', ".2");
							}else if("3" == clas){
								layer.tips('+1', ".3");
							}
						}
					});
				});
			});
		}
	});
	
	
	
	//给我留言
	$("#mainbody").delegate("#liveMessage", "click", function() {
		layer.open({
			  type: 2,
			  title: '给我留言',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['600px', '85%'],
			  content: '/index/toLiveMessage' //iframe的url
			}); 
	});
});
