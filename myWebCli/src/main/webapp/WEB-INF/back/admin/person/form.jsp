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
	$("#btnRemove").click(function(){
		$("#userIds").find("option:selected").remove();
	});
	$("#userIds").multipleSelect({
		title:"请选择用户",
	 	message:"请选择用户",
		selects : [ {
			id : "orgSelect",
			lab:"机构",
			url : "/admin/org/tree.do",
			noKey:true,
			noText:true
		}, {
			id : "dpmSelect",
			lab:"部门",
			url : "/admin/department/tree.do?orgId=",
			noKey:true,
			noText:true
		}, {
			id : "userSelect",
			lab:"用户",
			url : "/admin/user/tree.do?dpmId="
		}]
	});
	
	$("form").validator([{
		id:"name",
		required:true,
		missingMessage:"请输人员组名称",
		validType:{
			length:[0,50]
		}
	}]);
	// 表单验证
	$("form").unbind();
	$("form").submit(function(){
		var options = $('#userIds').find('option');
		if (options.size() == 0) {
			showMessage("请选择用户");
			return false;
		}
		return $(this).form('validate');
	});
});
</script>
<title>人员组</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">${operation=='add' ? '添加' : '修改'}人员组</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
<c:if test="${operation=='alter'}">
<div class="editButtonsArea">
 	<shiro:hasPermission name="admin:peron:add">
	<div class="add"><a href="add.do">新增</a></div>
	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:peron:view">
	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:peron:remove">
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
	  	<th width="15%">名称：</th>
	    <td width="35%" >
	    	<s:input path="name" size="20" maxlength="200" />
	    </td>
	  	<%-- <th width="15%">分类：</th>
	    <td width="35%" >
	    	<label><s:radiobutton path="category" value="0" />局长</label>
	    	<label><s:radiobutton path="category" value="1" />待扩展</label>
	    </td> --%>
		</tr>
		<tr>
	  	<th width="15%">选择用户：</th>
	    <td width="35%" >
	    	<select id="userIds" name="userIds" style="width:300px;">
	    		<c:if test="${bean.users != null}">
	    		<c:forEach var="user" items="${bean.users}">
	    			<option value="${user.id}">
	    				${user.name}
	    			</option>
	    		</c:forEach>
	    		</c:if>
	    	</select>
	    </td>
		</tr>
    </table>
  	<br />
  </div>
  <div class="formSubArea"> 
  	<input type="submit" class="subBtn" value="保存" onclick="$('#userIds').find('option').attr('selected',true);"/>
  	<input type="submit" class="subBtn" value="保存并返回" onclick="$('#redirect').val('list');$('#userIds').find('option').attr('selected',true);"/>
    <c:if test="${operation=='add'}">
    <input type="submit" class="subBtn" value="保存并新增" onclick="$('#redirect').val('add');$('#userIds').find('option').attr('selected',true);"/>
    </c:if>
  	<input type="reset" value="重置表单" class="subBtn" />
  	<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='list.do'"/>
  </div>
</s:form>
</div>
</body>
</html>
