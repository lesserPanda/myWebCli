<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html>
<head>
<jsp:include page="/WEB-INF/fore/commons/head.jsp"/>
<script type="text/javascript" src="/fore/myweb/js/rightModel.js"></script>
</head>
<body>
    <aside>
      <!-- <div class="search">
      	
        <form class="searchform" method="get" action="#">
          <input type="text" name="s" value="Search" onfocus="this.value=''" onblur="this.value='Search'">
        </form>
      </div> -->
      <div>
      	<div id="JNmap"  style="width:335px;height:300px;border:0px"></div>
        <!-- <ul>
          <li><a href="/web/" target="_blank" title="网站建设">网站建设</a></li>
          <li><a href="/newshtml5/" target="_blank" title="HTML5 / CSS3">HTML5 / CSS3</a></li>
          <li><a href="/jstt/" target="_blank" title="技术探讨">技术探讨</a></li>
          <li><a href="/news/s/" target="_blank" title="慢生活">慢生活</a></li>
        </ul> -->
      </div>
      <div class="tuijian">
        <h2>生活琐事</h2>
        <ol>
        </ol>
      </div>
      <div class="toppic">
        <h2>转载收藏</h2>
        <ul>
        </ul>
      </div>
      <div class="clicks">
        <h2>热门点击</h2>
        <ol>
          <li><span><a href="/">慢生活</a></span><a href="/">有一种思念，是淡淡的幸福,一个心情一行文字</a></li>
          <li><span><a href="/">爱情美文</a></span><a href="/">励志人生-要做一个潇洒的女人</a></li>
          <li><span><a href="/">慢生活</a></span><a href="/">女孩都有浪漫的小情怀——浪漫的求婚词</a></li>
          <li><span><a href="/">博客模板</a></span><a href="/">Green绿色小清新的夏天-个人博客模板</a></li>
          <li><span><a href="/">女生个人博客</a></span><a href="/">女生清新个人博客网站模板</a></li>
          <li><span><a href="/">Wedding</a></span><a href="/">Wedding-婚礼主题、情人节网站模板</a></li>
          <li><span><a href="/">三栏布局</a></span><a href="/">Column 三栏布局 个人网站模板</a></li>
          <li><span><a href="/">个人网站模板</a></span><a href="/">时间煮雨-个人网站模板</a></li>
          <li><span><a href="/">古典风格</a></span><a href="/">花气袭人是酒香—个人网站模板</a></li>
        </ol>
      </div>
    </aside>
</body>
</html>