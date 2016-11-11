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
		id:"oldPwd",
		required:true,
		missingMessage:"请输入旧密码"
	},{
		id:"newPwd",
		required:true,
		missingMessage:"请输入新密码"
	},{
		id:"newPwdQr",
		required:true,
		missingMessage:"请确认新密码",
		validType:"equals['#newPwd']"
	}]);	
});
</script>
<title>修改登录密码</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">修改登录密码</div>
    <div class="ext"></div>
</div>
<s:form action="pwd.do" method="post" modelAttribute="bean">
<s:hidden path="id" />
<input type="hidden" id="redirect" name="redirect" value="own"/>
<div class="areaContent">
  <div class="formAreaTitle">基本信息</div>
  <div class="formAreaContent"><br />
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formTable">
  		<tr>
	  	<th width="15%">登录账号：</th>
	    <td width="35%" > 
	    	${bean.username}
	    </td>
	    </tr>
  		<tr>
	  	<th width="15%">旧密码：</th>
	    <td width="35%" > 
	    	<input id="oldPwd" type="password" name="oldPwd" size="20" maxlength="50"/>
	    </td>
	    </tr>
	    <tr>
	  	<th width="15%">新密码：</th>
	    <td width="35%" >
	    	<input id="newPwd" type="password" name="newPwd" size="20" maxlength="50"/>
	    </td>
	    </tr>
	    <tr>
	  	<th width="15%">确认新密码：</th>
	    <td width="35%" >
	    	<input id="newPwdQr" type="password" name="newPwdQr" size="20" maxlength="50"/>
	    </td>
	    </tr>
	 </table>
  	<br />
  </div>
  <div class="formSubArea"> 
  	<input type="submit" class="subBtn" value="保存"/>
  	<input type="reset" value="重置表单" class="subBtn" />
  </div>
</div>
</s:form>
</body>
</html>
