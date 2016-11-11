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
	$("#look").treeDialog({
		url:"/admin/role/perms.do",
		values:"${bean.perms}"
	});
});
</script>
<title>角色</title>
</head>
<body>
<div class="areaTitle">
    <div class="key">查看角色信息</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
<div class="editButtonsArea">
	<shiro:hasPermission name="admin:role:add">
	<div class="add"><a href="add.do">新增</a></div>
	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:role:alter">
	<div class="edit"><a href="alter.do?id=${bean.id}">修改</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:role:remove">
	<div class="delete"><a href="javascript:$.remove('${bean.id}');">删除</a></div>
	</shiro:hasPermission>
</div>
  <div class="detailAreaTitle">基本信息
  <div class="ext">
 	<input type="button" class="detailAreaClosetBtn" value=" " />
  </div>
  </div>
  <div class="detailAreaContent">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="detailTable">
	  	<tr>
	  	<th width="15%">角色名称：</th>
	    <td width="35%" >
	    	${bean.name}
	    </td>
	  	<th width="15%">角色功能权限：</th>
	    <td width="35%" >
	    	<a href="javascript:void();" id="look">查看详细</a>
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">所有权限：</th>
	    <td width="35%" >
	    	<c:choose>
        		<c:when test="${bean.isAllPerms==0}">否</c:when>
        		<c:when test="${bean.isAllPerms==1}">是</c:when>
        	</c:choose>
	    </td>
	  	<th width="15%">角色的简单描述：</th>
	    <td width="35%" >
	    	${bean.description}
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">自身范围还是机构范围：</th>
	    <td width="35%" colspan="3">
	    	<c:choose>
        		<c:when test="${bean.isRangePerms==0}">自身范围</c:when>
        		<c:when test="${bean.isRangePerms==1}">机构范围</c:when>
        	</c:choose>
	    </td>
		</tr>
    </table>
  </div>
  <div class="detailBtnArea"> 
  	<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='list.do'"/>
  </div>
</div>
</body>
</html>
