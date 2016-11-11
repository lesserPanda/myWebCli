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
<script type="text/javascript">
$(function(){
	$("a[oldPerms]").each(function(i){
		var oldPerms = $(this).attr("oldPerms");
		var perms = $(this).attr("perms");
		$(this).treeDialog({
			url:"/admin/role/perms.do",
			values:perms,
			treeId:"tree"+i,
			dialogId:"dialogId"+i,
			perms:oldPerms
		});
	});
});
</script>
<title>代理</title>
</head>
<body>
<div class="areaTitle">
    <div class="key">查看代理信息</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
	<c:if test="${param.source != 'excute'}">
	<div class="editButtonsArea">
		<shiro:hasPermission name="admin:agent:add">
	 	<div class="add"><a href="add.do">新增</a></div>
	 	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
		</shiro:hasPermission>
	 	<shiro:hasPermission name="admin:agent:alter">
	 	<div class="edit"><a href="alter.do?id=${bean.id}">修改</a></div>
	 	</shiro:hasPermission>
	 	<shiro:hasPermission name="admin:agent:remove">
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
	 	<th width="15%">授予的角色权限：</th>
	   <td width="35%" colspan="3">
	   	<c:forEach var="role" varStatus="s" items="${bean.agentRoles}">
	   	<c:if test="${!empty role.rolePerms}">
	   	<a oldPerms="${role.oldRolePerms}" perms="${role.rolePerms}" href="javascript:void();" >${role.roleName}</a>
	   	</c:if>
	   	</c:forEach>
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">授权人姓名：</th>
	   <td width="35%" >
	   	${bean.ownerName}
	   </td>
	 	<th width="15%">代理人姓名：</th>
	   <td width="35%" >
	   	${bean.agentName}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">授权人账号：</th>
	   <td width="35%" >
	   	${bean.ownerUsername}
	   </td>
	 	<th width="15%">代理人账号：</th>
	   <td width="35%" >
	   	${bean.agentUsername}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">代理开始时间：</th>
	   <td width="35%" >
	   	<fmt:formatDate value="${bean.timeStart}" pattern="yyyy-MM-dd HH:mm:ss"/>
	   </td>
	 	<th width="15%">代理结束时间：</th>
	   <td width="35%" >
	   	<fmt:formatDate value="${bean.timeEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">创建时间：</th>
	   <td width="35%" colspan="3">
	   	<fmt:formatDate value="${bean.timeAdd}" pattern="yyyy-MM-dd HH:mm:ss"/>
	   </td>
	</tr>
	  </table>
	</div>
	<div class="detailBtnArea"> 
		<c:if test="${param.source != 'excute'}">
		<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='listAgent.do'"/>
		</c:if>
		<c:if test="${param.source == 'excute'}">
		<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='listExcute.do'"/>
		</c:if>
	</div>
</div>
</body>
</html>
