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
		missingMessage:"请输入姓名",
		validType:{
			length:[2,10]
		}
	},{
		id:"email",
		required:true,
		missingMessage:"请输入邮箱",
		validType:['email','length[0,50]']
	}]);
});
</script>
<title>维护个人资料页面</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">维护个人资料页面</div>
    <div class="ext"></div>
</div>
<s:form action="own.do" method="post" modelAttribute="bean">
<s:hidden path="id" />
<input type="hidden" id="redirect" name="redirect" value="own"/>
<div class="areaContent">
  <div class="formAreaTitle">基本信息</div>
  <div class="formAreaContent"><br />
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formTable">
  		<tr>
	  	<th width="15%">真实姓名：</th>
	    <td width="35%" >
	    	<s:input path="name" size="20" maxlength="20" />
	    </td>
	  	<th width="15%">登录账号：</th>
	    <td width="35%" >
	    	${bean.username}
	    </td>
	    </tr>
	  	<tr>
	  	<th width="15%">性别：</th>
	    <td width="35%" >
	    	<s:select path="gender">
			<s:option value="1">男</s:option>
			<s:option value="0">女</s:option>
			<s:option value="2">保密</s:option>
			</s:select>
	    </td>
	    <th width="15%">拥有角色：</th>
	    <td width="35%" >
    		<c:forEach var="role" items="${bean.roles}" varStatus="s">
   				${role.name}
   				<c:if test="${!s.last}">,</c:if>
    		</c:forEach>
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">手机：</th>
	    <td width="35%" >
	    	<s:input path="mobie" size="20" maxlength="20" />
	    </td>
	    <th width="15%">当前角色：</th>
	    <td width="35%" >
	    	${bean.currentRole.name }
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">固定电话：</th>
	    <td width="35%" >
	    	<s:input path="phone" size="20" maxlength="20" />
	    </td>
	    <th width="15%">排序：</th>
	    <td width="35%" >
	    	${bean.sortNum }
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">邮箱：</th>
	    <td width="35%" >
	    	<s:input path="email" size="20" maxlength="200" />
	    </td>
	    <th width="15%">添加时间：</th>
	    <td width="35%" >
	    	<fmt:formatDate value="${bean.timeAdd}" pattern="yyyy-MM-dd"/>
	    </td>
		</tr>
    </table>
  	<br />
  </div>
  <div class="formSubArea"> 
  	<input type="submit" class="subBtn" value="保存"/>
  	<input type="submit" class="subBtn" value="保存并重新登录" onclick="$('#redirect').val('login');"/>
  	<input type="reset" value="重置表单" class="subBtn" />
  </div>
</div>
</s:form>
</body>
</html>
