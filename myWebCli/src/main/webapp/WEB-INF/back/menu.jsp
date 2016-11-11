<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/back/css/main.css" rel="stylesheet" type="text/css"/>
<title>左菜单</title>
</head>
<body>
<!--左侧菜单区域-->
<c:set var="setCurrentPage" value="${false}"/>
<c:forEach var="m1" items="${menus}">
<shiro:hasPermission name="${m1.key}">
<div class="firstLevelMenuTitle" key="${m1.key}">
	<c:choose>
		<c:when test="${!empty m1.url}">
			<a href="javascript:$.loadCenter('${m1.name}', '${m1.url}');"
			<c:if test="${m1.defaultPage}">defaultPage</c:if>
			<c:if test="${!empty m1.count}">count="${m1.count}"</c:if>
			>${m1.name}</a>
		</c:when>
		<c:otherwise>
			${m1.name}
		</c:otherwise>
	</c:choose>
</div>
<c:if test="${!empty m1.child}">
<c:forEach var="m2" items="${m1.child}">
<shiro:hasPermission name="${m2.key}">
<div class="secCloseMenu" parentKey="${m1.key}" key="${m2.key}">
	<div class="meunTitle">
    	<c:if test="${!empty m2.img}">
            <img src="/back/images/${m2.img}" width="16" height="16" border="0" align="absmiddle" /> 
        </c:if>
		<c:choose>
			<c:when test="${!empty m2.url}">
				<a key="${m2.key}" href="javascript:$.loadPage('${m2.name}', ['${param.key}', '${m1.key}', '${m2.key}'], '${m2.url}');"
				<c:if test="${m2.defaultPage}">defaultPage</c:if>
				<c:if test="${!empty m2.count}">count="${m2.count}"</c:if>
				>${m2.name}</a>
			</c:when>
			<c:otherwise>
				${m2.name} </c:otherwise>
		</c:choose>
	</div>
	<c:if test="${!empty m2.child}">
    <div class="menuList">
    	<c:forEach var="m3" items="${m2.child}">
    	<shiro:hasPermission name="${m3.key}">
    	<c:if test="${!setCurrentKey}">
    	<c:set var="setCurrentPage" value="${true}"/>
    	</c:if>
        <a key="${m3.key}" href="javascript:$.loadPage('${m3.name}', ['${param.key}', '${m1.key}', '${m2.key}', '${m3.key}'], '${m3.url}');"
        <c:if test="${m3.defaultPage}">defaultPage</c:if>
        <c:if test="${!empty m3.count}">count="${m3.count}"</c:if>
        ><li>${m3.name}</li></a>
        </shiro:hasPermission>
    	</c:forEach>
    </div>
	</c:if>
</div>
</shiro:hasPermission>
</c:forEach>
</c:if>
</shiro:hasPermission>
</c:forEach>
</body>
</html>
