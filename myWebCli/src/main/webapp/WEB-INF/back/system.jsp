<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/WEB-INF/back/commons/head.jsp"/>
<title>系统运行信息</title>
</head>
<body>
<div class="areaTitle">
    <div class="key">系统运行信息</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
<div class="editButtonsArea"></div>
  <div class="detailAreaTitle">基本信息
  <div class="ext">
 	<input type="button" class="detailAreaClosetBtn" value=" " />
  </div>
  </div>
<div class="detailAreaContent">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="detailTable">
<tr>
	<th width="15%">操作系统</th>
	<td>${props['os.name']} ${props['os.version']}</td>
	
</tr>
<tr>
	<th width="15%">JAVA运行环境</th>
	<td>${props['java.runtime.name']} ${props['java.runtime.version']}</td>
	
</tr>
<tr>
	<th width="15%">JAVA虚拟机</th>
	<td>${props['java.vm.name']} ${props['java.vm.version']}</td>
	
</tr>
<tr>
	<th width="15%">系统用户</th>
	<td>${props['user.name']}</td>
	
</tr>
<tr>
	<th width="15%">用户主目录</th>
	<td>${props['user.home']}</td>
	
</tr>
<tr>
	<th width="15%">用户工作目录</th>
	<td>${props['user.dir']}</td>
	
</tr>
<tr>
	<th width="15%">用户临时目录</th>
	<td>${props['java.io.tmpdir']}</td>
	
</tr>
<tr>
	<th width="15%">最大内存</th>
	<td><fmt:formatNumber value="${maxMemoryMB}" pattern="#.00"/> MB</td>
	
</tr>
<tr>
	<th width="15%">已用内存</th>
	<td><fmt:formatNumber value="${usedMemoryMB}" pattern="#.00"/> MB</td>
	
</tr>
<tr>
	<th width="15%">可用内存</th>
	<td><fmt:formatNumber value="${useableMemoryMB}" pattern="#.00"/> MB</td>
	
</tr>
</table>
</div>
<div class="detailBtnArea"> 
  	<input type="button" value="返回上一页" class="subBtn" onclick="javascript:history.back();"/>
  </div>
</div>
</body>
</html>
