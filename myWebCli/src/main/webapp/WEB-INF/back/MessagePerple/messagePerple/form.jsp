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
<title>留言板</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">留言板 - ${operation=='add' ? '添加' : '修改'}</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
	<c:if test="${operation=='alter'}">
	<div class="editButtonsArea">
		<shiro:hasPermission name="MessagePerple:messagePerple:add">
	 	<div class="add"><a href="add.do">新增</a></div>
	 	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="MessagePerple:messagePerple:view">
	 	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="MessagePerple:messagePerple:remove">
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
		  	<th width="15%">留言人姓名：</th>
		    <td width="35%" >
		    	<s:input path="messagePerple" size="20" maxlength="128" />
		    </td>
		  	<th width="15%">留言人ip：</th>
		    <td width="35%" >
		    	<s:input path="messagePerpleIp" size="20" maxlength="512" />
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">留言时间：</th>
		    <td width="35%" >
		    	<s:input path="createTime" size="20" maxlength="30" />
		    </td>
		  	<th width="15%">留言内容：</th>
		    <td width="35%" >
		    	<s:input path="messageContent" size="20" maxlength="2000" />
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
