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
<title>用户</title>
</head>
<body>
<div class="areaTitle">
    <div class="key">查看用户信息</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
<c:if test="${param.source != 'agent'}">
<div class="editButtonsArea">
 	<shiro:hasPermission name="admin:user:add">
	<div class="add"><a href="add.do">新增</a></div>
	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:user:alter">
	<div class="edit"><a href="alter.do?id=${bean.id}">修改</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:user:remove">
	<div class="delete"><a href="javascript:$.remove('${bean.id}');">删除</a></div>
	</shiro:hasPermission>
</div>
</c:if>
  <div class="detailAreaTitle">基本信息
  <div class="ext">
 	<input type="button" class="detailAreaClosetBtn" value=" " />
  </div>
  </div>
  <div class="detailAreaContent">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="detailTable">
	  	<tr>
	  	<th width="15%">登录账号：</th>
	    <td width="35%" >
	    	${bean.username}
	    </td>
	  	<th width="15%">拥有角色：</th>
	    <td width="35%" >
			<c:if test="${bean.roles != null}">
    		<c:forEach var="role" items="${bean.roles}" varStatus="s">
    			${role.name}
    			<c:if test="${!s.last}">
    			,
    			</c:if>
    		</c:forEach>
    		</c:if>
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">真实姓名：</th>
	    <td width="35%" >
	    	${bean.name}
	    </td>
	  	<th width="15%">性别：</th>
	    <td width="35%" >
	    	<c:choose>
        		<c:when test="${bean.gender==1}">男</c:when>
        		<c:when test="${bean.gender==0}">女</c:when>
        		<c:when test="${bean.gender==2}">保密</c:when>
        	</c:choose>
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">手机：</th>
	    <td width="35%" >
	    	${bean.mobie}
	    </td>
	  	<th width="15%">固定电话：</th>
	    <td width="35%" >
	    	${bean.phone}
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">邮箱：</th>
	    <td width="35%" >
	    	${bean.email}
	    </td>
	  	<th width="15%">排序：</th>
	    <td width="35%" >
	    	${bean.sortNum}
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">状态：</th>
	    <td width="35%" >
	    	<c:choose>
        		<c:when test="${bean.status==1}">正常</c:when>
        		<c:when test="${bean.status==2}">锁定</c:when>
        		<c:when test="${bean.status==3}">待验证</c:when>
        	</c:choose>
	    </td>
	  	<th width="15%">添加时间：</th>
	    <td width="35%" >
	    	<fmt:formatDate value="${bean.timeAdd}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </td>
		</tr>
    </table>
  </div>
  <div class="detailBtnArea"> 
  	<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href = document.referrer;"/>
  </div>
</div>
</body>
</html>
