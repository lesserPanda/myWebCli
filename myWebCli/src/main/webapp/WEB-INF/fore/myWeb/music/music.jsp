<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/myWeb/myweb/music/css/styles.css" media="all" >
</head>
<body>
<div class="cuplayer">
<section class="demo">
        <div id="myAudio" style="margin:0 auto;">
			<audio>
				<c:forEach var="bean" varStatus="s" items="${pageNav.list}">
				<source title="${bean.musicAuthor } - ${bean.musicName}" src="${bean.musicSrc }" />
				</c:forEach>
				<%-- <source title="韦礼安 - 还是会.mp3" src="http://music.huoxing.com/upload/20121215/1355575227640_8200.mp3" />
				<source title="王若琳 - Lost in paradise.mp3" src="http://rm.sina.com.cn/wm/VZ200812161100307607VK/music/MUSIC0812161100379667.mp3" />
				<source title="郁可唯 - 时间煮雨.mp3" src="http://rm.sina.com.cn/wm/VZ200708161037153290VK/music/1.mp3/20091229/90e6ba45adf30ca3abfe28.mp3" />
				<source title="王若琳 - 三个人的晚餐.mp3" src="http://rm.sina.com.cn/wm/VZ2010050511043310440VK/music/MUSIC1005051622027270.mp3" />
				<source title="王若琳 - I Love You.mp3" src="http://rm.sina.com.cn/wm/VZ200812161100307607VK/music/MUSIC0812161100379667.mp3" />
				<source title="王若琳 - Lemon Tree(中文版).mp3" src="http://rm.sina.com.cn/wm/VZ200708161037153290VK/music/1.mp3/20091229/90e6ba45adf30ca3abfe28.mp3" />
				<source title="王若琳 - 亲密爱人.mp3" src="http://rm.sina.com.cn/wm/VZ2010050511043310440VK/music/MUSIC1005051622027270.mp3" /> --%>
			</audio>
		</div>
		<script type="text/javascript" src="/vendor/jquery.js"></script>
		<script type="text/javascript" src="/myWeb/myweb/music/js/AudioPlayer.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#myAudio").initAudio();
			});
		</script>
    </section>
</div>
</body>
</html>