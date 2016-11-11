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
		id:"name",
		required:true,
		missingMessage:"请输入部门名称"
	},{
		id:"header",
		required:true,
		missingMessage:"请输入负责人姓名"
	},{
		id:"email",
		required:true,
		missingMessage:"请输入邮箱",
		validType:['email','length[0,50]']
	},{
		id:"workDuty",
		required:true,
		missingMessage:"请输入工作职责",
		validType:"editor"
	}]);
	
	$("#orgId").treeSelect({
		required:true,
		missingMessage:"请选择所属机构",
		url:"/admin/org/tree.do",
		values:["${bean.orgId}"]
	});
});
</script>
<title>部门</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">${operation=='add' ? '添加' : '修改'}部门信息</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
<c:if test="${operation=='alter'}">
<div class="editButtonsArea">
 	<shiro:hasPermission name="admin:department:add">
	<div class="add"><a href="add.do">新增</a></div>
	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:department:view">
	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:department:remove">
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
	  	<th width="15%">所属机构：</th>
	    <td width="35%" colspan="3">
	    	<s:select id="orgId" path="orgId" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">部门名称：</th>
	    <td width="35%" >
	    	<s:input path="name" size="20" maxlength="200" />
	    </td>
	  	<th width="15%">负责人：</th>
	    <td width="35%" >
	    	<s:input path="header" size="20" maxlength="50" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">联系电话：</th>
	    <td width="35%" >
	    	<s:input path="phone" size="20" maxlength="20" />
	    </td>
	  	<th width="15%">传真：</th>
	    <td width="35%" >
	    	<s:input path="fax" size="20" maxlength="20" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">邮箱：</th>
	    <td width="35%" >
	    	<s:input path="email" size="20" maxlength="200" />
	    </td>
	  	<th width="15%">排序：</th>
	    <td width="35%" >
	    	<s:input path="sortNum" size="20" maxlength="22" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">工作职责：</th>
	    <td width="35%" colspan="3">
	    	<s:textarea path="workDuty" cssStyle="width:700px;height:300px;" cssClass="editor"/>
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
</body>
</html>
