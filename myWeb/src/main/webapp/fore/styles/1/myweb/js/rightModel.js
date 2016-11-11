$(function(){
	
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('JNmap'));

    // 指定图表的配置项和数据
    var option = {
    	    backgroundColor: '#2c343c',

    	    title: {
    	        text: '技能展示',
    	        left: 'center',
    	        top: 20,
    	        textStyle: {
    	            color: '#ccc'
    	        }
    	    },

    	    tooltip : {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },

    	    visualMap: {
    	        show: false,
    	        min: 80,
    	        max: 600,
    	        inRange: {
    	            colorLightness: [0, 1]
    	        }
    	    },
    	    series : [
    	        {
    	            name:'技能展示',
    	            type:'pie',
    	            radius : '55%',
    	            center: ['50%', '50%'],
    	            data:[
    	                {value:335, name:'直接访问'},
    	                {value:310, name:'邮件营销'},
    	                {value:274, name:'联盟广告'},
    	                {value:235, name:'视频广告'},
    	                {value:400, name:'搜索引擎'}
    	            ].sort(function (a, b) { return a.value - b.value}),
    	            roseType: 'angle',
    	            label: {
    	                normal: {
    	                    textStyle: {
    	                        color: 'rgba(255, 255, 255, 0.3)'
    	                    }
    	                }
    	            },
    	            labelLine: {
    	                normal: {
    	                    lineStyle: {
    	                        color: 'rgba(255, 255, 255, 0.3)'
    	                    },
    	                    smooth: 0.2,
    	                    length: 10,
    	                    length2: 20
    	                }
    	            },
    	            itemStyle: {
    	                normal: {
    	                    color: '#c23531',
    	                    shadowBlur: 200,
    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
    	                }
    	            }
    	        }
    	    ]
    	};
    
	$.ajax({
		url:"/index/getSkillInfo",
		data:{},
		type:"get",
		dataType:"json",
		success:function(data){
			option.series[0].data=[];
			$.each(data.pageNav.list, function(index, value){
				option.series[0].data.push({value:value.skillPer, name:value.skillName});
			});
			// 使用刚指定的配置项和数据显示图表。
		    myChart.setOption(option);
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
				
				if(index < 3){
					var zzFlagHtml = "";
					if("0031" == value.zzFlag){
						zzFlagHtml = '<p class="1" id="'+value.id+'" zzFlag="'+value.zzFlag+'">伤不起</p>';
					}else if("0032" == value.zzFlag){
						zzFlagHtml = '<p class="2" id="'+value.id+'" zzFlag="'+value.zzFlag+'">喜欢</p>';
					}else if("0033" == value.zzFlag){
						zzFlagHtml = '<p class="3" id="'+value.id+'" zzFlag="'+value.zzFlag+'">感兴趣</p>';
					}
				
					gr2HTML += '<li><a val="'+value.id+'" href="javascript:void(0)"><img src="'+value.image+'">'+value.description+'</a>"'+zzFlagHtml+'"</li>';
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
								layer.tips('+1', ".1", {
									  tips: [3, '#78BA32']
								});
							}else if("2" == clas){
								layer.tips('+1', ".2", {
									  tips: [3, '#78BA32']
								});
							}else if("3" == clas){
								layer.tips('+1', ".3", {
									  tips: [3, '#78BA32']
								});
							}
							
						}
					});
				});
			});
			
			$(".toppic ul li").each(function(){
				$(this).delegate("a", "click", function(){
					parent.window.document.location.href="/index/toView?id="+$(this).attr("val"); 
				});
			});
			
		}
	});
	
	//加载生活琐事
	$.ajax({
		url:"/index/getSHSH",
		data:{},
		type:"get",
		dataType:"json",
		success:function(data){
			var gr2HTML = '';
			$.each(data, function(index, value){
				gr2HTML += "<li><span><strong>"+(index+1)+"</strong></span><a val="+value.id+" href='javascript:void(0)'>"+value.title+"</a></li>";///index/toView?id="++"
			})
			
			$(".tuijian ol").append(gr2HTML);
			
			$(".tuijian ol li a").on("click", function(){
				parent.window.document.location.href="/index/toView?id="+$(this).attr("val"); 
				
				console.log(_parent);
			});
		}
	})
	
});
