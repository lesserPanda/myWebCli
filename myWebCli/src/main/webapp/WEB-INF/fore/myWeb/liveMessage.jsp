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
<title>留言板</title>
<meta name="keywords" content="黑色模板,个人网站模板,个人博客模板,博客模板,css3,html5,网站模板" />
<meta name="description" content="这是一个有关黑色时间轴的css3 html5 网站模板" />
<script type="text/javascript" src="/vendor/jquery.js"></script>
<script type="text/javascript" src="/vendor/layer/layer.js"></script>
<script type="text/javascript" src="/fore/myweb/js/liveMessage.js"></script>
<link href="/fore/myweb/css/message.css" rel="stylesheet" />
</head>
<body>

    <form action="/index/saveLiveMessage" method="post" class="dark-matter">
    <h1>留言板
    <span>请留下您珍贵的意见.</span>
    </h1>
    <label>
    <span>您的名字 :</span>
    <input id="messagePerple" type="text" name="messagePerple" placeholder="Your Full Name" />
    </label>

    <label>
    <span>您的email :</span>
    <input id="messageEmail" type="email" name="messageEmail" placeholder="Valid Email Address" />
    </label>

    <label>
    <span>留言内容 :</span>
    <textarea id="messageContent" name="messageContent" placeholder="Your Message to Us"></textarea>
    </label>
    <label>
    <span>主题 :</span>
    <input id="messageTitle" type="text" name="messageTitle" placeholder="Your Title" />
    </label>
    <label>
    <span>&nbsp;</span>
    <input type="button" id="submit" class="button" value="留言" />
    </label>
    </form>

</body>
</html>