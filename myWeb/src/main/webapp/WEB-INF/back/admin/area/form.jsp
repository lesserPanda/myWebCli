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
	$("form").validator([{
		id:"areaName",
		required:true,
		missingMessage:"请输入地区名称"
	},{
		id:"areaCode",
		required:true,
		missingMessage:"请输入10位地区编码",
		invalidMessage:"不足10位的请用0补齐",
		validType:{
			length:[10,10] 
		}
	}]);
	
	$("#parentId").treeSelect({
		required:true,
		missingMessage:"请选择上级地区",
		url:"/admin/area/tree.do",
		values:["${bean.parentId}"]
	});	
	
});
</script>
<title>地区</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">${operation=='add' ? '添加' : '修改'}地区信息</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
<c:if test="${operation=='alter'}">
<div class="editButtonsArea">
	<shiro:hasPermission name="admin:area:add">
 	<div class="add"><a href="add.do">新增</a></div>
 	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:area:view">
 	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
 	</shiro:hasPermission>
 	<shiro:hasPermission name="admin:area:remove">
    <div class="delete"><a href="javascript:$.remove('${bean.id}');">删除</a></div>
    </shiro:hasPermission>
</div>
</c:if>
<s:form action="${operation}.do" method="post" modelAttribute="bean">
<s:hidden path="id" />
<input type="hidden" id="redirect" name="redirect" value="alter"/>
  <div class="formAreaTitle">基本信息</div>
  <div class="formAreaContent"><br />
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formTable">
	  	<tr>
	  	<th width="15%">上级地区：</th>
	    <td width="35%" >
	    	<s:select id="parentId" path="parentId" />
	    </td>
	  	<th width="15%">地区名称：</th>
	    <td width="35%" >
	    	<s:input path="areaName" size="20" maxlength="30" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">地区编码：</th>
	    <td width="35%" >
	    	<s:input path="areaCode" size="10" maxlength="10" />
	    </td>
	  	<th width="15%">地区级别：</th>
	    <td width="35%" >
	    	<s:select path="areaLevel">
			<s:option value="0">国家</s:option>
			<s:option value="1">省</s:option>
			<s:option value="2">市</s:option>
			<s:option value="3">区县</s:option>
			<s:option value="4">街道/镇</s:option>
			<s:option value="5">社区/村</s:option>
			</s:select>
	    </td>
		</tr>
    </table>
  	<br />
  </div>
  <div class="formSubArea"> 
  	<input type="submit" class="subBtn" value="保存"/>
  	<input type="submit" class="subBtn" value="保存并返回" onclick="$('#redirect').val('list');"/>
    <c:if test="${operation=='add'}">
    <input type="submit" class="subBtn" value="保存并新增" onclick="$('#redirect').val('add');"/>
    </c:if>
  	<input type="reset" value="重置表单" class="subBtn" />
  	<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='list.do'"/>
  </div>
</s:form>
</div>
<!-- 弹出窗口DIV -->
<div id="dialogPerms" style="display: none;">
	<ul id="treePerms" class="easyui-tree"></ul>
</div>
</body>
</html>
