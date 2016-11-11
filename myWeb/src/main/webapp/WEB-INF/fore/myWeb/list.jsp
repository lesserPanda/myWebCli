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
<title>view-lesserPanda个人博客</title>
<meta name="keywords" content="黑色模板,个人网站模板,个人博客模板,博客模板,css3,html5,网站模板" />
<meta name="description" content="这是一个有关黑色时间轴的css3 html5 网站模板" />
<jsp:include page="/WEB-INF/fore/commons/head.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/fore/commons/show_message.jsp"/>
<!--header end-->
<div id="mainbody">
  <div class="blogs">
    <div class="newlist">
  <h2><span>
</span>您当前的位置：<a href="/index.html">首页</a>&nbsp;>&nbsp;<a href="/jstt/">技术探讨</a></h2>
     <ul>
<c:forEach var="bean" varStatus="s" items="${pageNav.list }">
	<p class="ptit"><b><a href="/index/toView?id=${bean.id }" target="_blank" title=" li列表在网页中常见应用例举（二）"> ${bean.title }</a></b></p>
	<p class="ptime">发布时间：${bean.createDate } 作者：${bean.createBy }  分类：div+css </p>
	<a href="/index/toView?id=${bean.id }" target="_blank" title=" li列表在网页中常见应用例举（二）"><img src="${bean.image }" class="imgdow" alt=" li列表在网页中常见应用例举（二）"></a> <p class="pcon">${bean.description } <a href="/index/toView?id=${bean.id }" target="_blank" title=" li列表在网页中常见应用例举（二）">详细信息</a></P>
	<div class="line"></div>

</c:forEach>

<!-- <p class="ptit"><b><a href="/jstt/div/2013-08-10/575.html" target="_blank" title="li列表在网页中常见应用例举（一）">li列表在网页中常见应用例举（一）</a></b></p>
<p class="ptime">发布时间：2013-08-10 作者：杨青  分类：div+css </p>
<a href="/jstt/div/2013-08-10/575.html" target="_blank" title="li列表在网页中常见应用例举（一）"><img src="images/s2.jpg" class="imgdow" alt="li列表在网页中常见应用例举（一）"></a> <p class="pcon">li在网页中的应用,list-style-type,设置或检索对象的列表项所使用的预设标记。list-style-image：属性使用图像来替换列表项的标记,li列表前面用图片来展示.注意：ol对象和ul对象的type特性为其后的所有列表项目（如li对象）指明列表属性。 <a href="/jstt/div/2013-08-10/575.html" target="_blank" title="li列表在网页中常见应用例举（一）">详细信息</a></P>
<div class="line"></div>

<p class="ptit"><b><a href="/jstt/bj/2013-08-06/570.html" target="_blank" title="帝国cms怎样修改列表分页函数">帝国cms怎样修改列表分页函数</a></b></p>
<p class="ptime">发布时间：2013-08-06 作者：杨青  分类：心得笔记 </p>
<a href="/jstt/bj/2013-08-06/570.html" target="_blank" title="帝国cms怎样修改列表分页函数"><img src="images/s3.jpg" class="imgdow" alt="帝国cms怎样修改列表分页函数"></a> <p class="pcon">修改帝国默认的列表分页样式和伪静态页面留言版的分页样式。本来想在默认的模板标签上修改，看了看代码，b和a标签前后都多了空格，如果在现在的基础上改，简单改改也行，不过实在是受不了那么多空格符，而且如果用display:block的话... <a href="/jstt/bj/2013-08-06/570.html" target="_blank" title="帝国cms怎样修改列表分页函数">详细信息</a></P>
<div class="line"></div>

<p class="ptit"><b><a href="/jstt/div/2013-08-03/549.html" target="_blank" title="html css 十一条网页设计经典实用的代码片段">html css 十一条网页设计经典实用的代码片段</a></b></p>
<p class="ptime">发布时间：2013-08-03 作者：杨青  分类：div+css </p>
<a href="/jstt/div/2013-08-03/549.html" target="_blank" title="html css 十一条网页设计经典实用的代码片段"><img src="images/s4.jpg" class="imgdow" alt="html css 十一条网页设计经典实用的代码片段"></a> <p class="pcon">这十一条网页设计经典实用的代码片段，在网站的应用中都比较广泛。方便快速的查阅以及建设一个新的页面，非常适合刚接触的web前端设计。包括设置一些字体、站点链接、IE6bug的处理，导航菜单以及高亮显示菜单，还有表单的一些设计... <a href="/jstt/div/2013-08-03/549.html" target="_blank" title="html css 十一条网页设计经典实用的代码片段">详细信息</a></P>
<div class="line"></div>

<p class="ptit"><b><a href="/jstt/div/2013-07-31/532.html" target="_blank" title="Fieldset教你如何绘制带标题的表单框">Fieldset教你如何绘制带标题的表单框</a></b></p>
<p class="ptime">发布时间：2013-07-31 作者：杨青  分类：div+css </p>
<a href="/jstt/div/2013-07-31/532.html" target="_blank" title="Fieldset教你如何绘制带标题的表单框"><img src="images/s5.jpg" class="imgdow" alt="Fieldset教你如何绘制带标题的表单框"></a> <p class="pcon">一朋友问我像这样的表单样式是怎么实现的，我看到图，第一眼的印象应该是用图片来实现的，或者是用postion来定位实现的。本例演示如何在数据周围绘制一个带标题的框。 <a href="/jstt/div/2013-07-31/532.html" target="_blank" title="Fieldset教你如何绘制带标题的表单框">详细信息</a></P>
<div class="line"></div>

<p class="ptit"><b><a href="/jstt/bj/2013-07-28/530.html" target="_blank" title="如果要学习web前端开发，需要学习什么？">如果要学习web前端开发，需要学习什么？</a></b></p>
<p class="ptime">发布时间：2013-07-28 作者：杨青  分类：心得笔记 </p>
<a href="/jstt/bj/2013-07-28/530.html" target="_blank" title="如果要学习web前端开发，需要学习什么？"><img src="images/s6.jpg" class="imgdow" alt="如果要学习web前端开发，需要学习什么？"></a> <p class="pcon">遇到很多新手，都会问，如果要学习web前端开发，需要学习什么？难不难？多久能入门？怎么能快速建一个网站？工资能拿到多少？还有些让我推荐一些培训机构什么的要去学习。我建议是自学，实在是觉得自己没有这个能力，确实是需要一个老师的话，那你还是自己做主找个老师吧！ <a href="/jstt/bj/2013-07-28/530.html" target="_blank" title="如果要学习web前端开发，需要学习什么？">详细信息</a></P>
<div class="line"></div>
 -->

  </ul>
<div class="page"><a title="Total record"><b>38</b></a><b>1</b><a href="/jstt/index_2.html">2</a><a href="/jstt/index_2.html">></a><a href="/jstt/index_2.html">>></a></div>
</div>
    <iframe src="/index/toRightModel" style="width:335px;height:1250px;margin:0px 0px 0px 5px;"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
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