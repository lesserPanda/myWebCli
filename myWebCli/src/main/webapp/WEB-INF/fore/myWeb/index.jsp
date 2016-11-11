<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>lesserPanda个人博客</title>
<meta name="keywords" content="黑色模板,个人网站模板,个人博客模板,博客模板,css3,html5,网站模板" />
<meta name="description" content="这是一个有关黑色时间轴的css3 html5 网站模板" />
<jsp:include page="/WEB-INF/fore/commons/head.jsp"/>
<script type="text/javascript" src="/fore/myweb/js/index.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/fore/commons/show_message.jsp"/>
<div id="mainbody">
  <div class="info">
  <!-- 嵌入首页简介 -->
  </div>
  <!--info end 个人技能展示模块-->
  <div class="blank"></div>
  <div class="blogs">
    <ul class="bloglist">
      <!-- <li>
        <div class="arrow_box">
          <div class="ti"></div>
          三角形
          <div class="ci"></div>
          圆形
          <h2 class="title"><a href="/" target="_blank">我希望我的爱情是这样的</a></h2>
          <ul class="textinfo">
            <a href="/"><img src="/fore/images/s1.jpg"></a>
            <p> 我希望我的爱情是这样的，相濡以沫，举案齐眉，平淡如水。我在岁月中找到他，依靠他，将一生交付给他。做他的妻子，他孩子的母亲，为他做饭，洗衣服，缝一颗掉了的纽扣。然后，我们一起在时光中变老。</p>
          </ul>
          <ul class="details">
            <li class="likes"><a href="#">10</a></li>
            <li class="comments"><a href="#">34</a></li>
            <li class="icon-time"><a href="#">2013-8-7</a></li>
          </ul>
        </div>
        arrow_box end 
      </li> -->
    </ul>
    <!--bloglist end-->
    <aside>
      <div class="tuijian">
        <h2>生活琐事</h2>
        <ol>
          <c:forEach var="bean" varStatus="s" items="${pageNav.list }">
          		<li><span><strong>${s.index+1 }</strong></span><a href="/index/toView?id=${bean.id }">${bean.title }</a></li>
          </c:forEach>
         <%-- <li><span><strong>2</strong></span><a href="/WEB-INF/fore/myWeb/music/view.html">励志人生-要做一个潇洒的女人</a></li>
           <li><span><strong>3</strong></span><a href="/">女孩都有浪漫的小情怀——浪漫的求婚词</a></li>
          <li><span><strong>4</strong></span><a href="/">Green绿色小清新的夏天-个人博客模板</a></li>
          <li><span><strong>5</strong></span><a href="/">女生清新个人博客网站模板</a></li>
          <li><span><strong>6</strong></span><a href="/">Wedding-婚礼主题、情人节网站模板</a></li>
          <li><span><strong>7</strong></span><a href="/">Column 三栏布局 个人网站模板</a></li>
          <li><span><strong>8</strong></span><a href="/">时间煮雨-个人网站模板</a></li>
          <li><span><strong>9</strong></span><a href="/">花气袭人是酒香—个人网站模板</a></li> --%>
        </ol>
      </div>
      <div class="toppic">
        <h2>转载收藏</h2>
        <ul>
          <!-- <li><a href="/"><img src="/fore/images/k01.jpg">腐女不可怕，就怕腐女会画画！
            <p>伤不起</p>
            </a></li>
          <li><a href="/"><img src="/fore/images/k02.jpg">问前任，你还爱我吗？无限戳中泪点~
            <p>感兴趣</p>
            </a></li>
          <li><a href="/"><img src="/fore/images/k03.jpg">世上所谓幸福，就是一个笨蛋遇到一个傻瓜。
            <p>喜欢</p>
            </a></li> -->
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
      <!-- <div class="search">
        <form class="searchform" method="get" action="#">
          <input type="text" name="s" value="Search" onfocus="this.value=''" onblur="this.value='Search'">
        </form>
      </div> -->
      <div class="viny">
      <iframe src="/index/toMusic" style="width:315px;height:250px"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
      </div>
    </aside>
  </div>
  <!--blogs end--> 
</div>
<!--mainbody end-->
<jsp:include page="/WEB-INF/fore/commons/foot.jsp"/>
<!-- jQuery仿腾讯回顶部和建议 代码开始 -->
<div id="tbox"> <a id="togbook" href="/e/tool/gbook/?bid=1"></a> <a id="gotop" href="javascript:void(0)"></a> </div>
<!-- 代码结束 -->
</body>
</html>