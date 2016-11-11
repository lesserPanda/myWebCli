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
		id:"username",
		required:true,
		missingMessage:"请输入用户名",
		validType:"username['#userId']"
	},{
		id:"password",
		required:true,
		missingMessage:"请输入密码"
	},{
		id:"passwordQr",
		required:true,
		missingMessage:"请输入确认密码",
		validType:"equals['#password']"
	},{
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
	},{
		id:"sortNum",
		required:true,
		missingMessage:"请输入排序",
		validType:"number",
		precision:0,
		min:0
	}]);
	
	<c:if test="${operation=='alter'}">
	$("#password").validatebox({
		novalidate:true
	});
	$("#passwordQr").validatebox({
		novalidate:true
	});
	</c:if>
	
	
	$("#roleIds").multipleSelect({
		title:"请选择角色",
	 	message:"请选择角色",
		selects : [ {
			id : "orgSelect",
			lab:"机构",
			url : "/admin/org/tree.do",
			noKey:true
		}, {
			id : "dpmSelect",
			lab:"部门",
			url : "/admin/department/tree.do?orgId=",
			noKey:true
		} , {
			id : "roleSelect",
			lab:"角色",
			url : "/admin/role/tree.do?dpmId="
		}]
	});
	
	// 表单验证
	$("form").unbind();
	$("form").submit(function(){
		var options = $('#roleIds').find('option');
		if (options.size() == 0) {
			showMessage("请分配角色");
			return false;
		}
		return $(this).form('validate');
	});
		
});
</script>
<title>用户</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">${operation=='add' ? '添加' : '修改'}用户</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
<c:if test="${operation=='alter'}">
<div class="editButtonsArea">
 	<shiro:hasPermission name="admin:user:add">
	<div class="add"><a href="add.do">新增</a></div>
	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:user:view">
	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:user:remove">
	<div class="delete"><a href="javascript:$.remove('${bean.id}');">删除</a></div>
	</shiro:hasPermission>
</div>
</c:if>
<s:form action="${operation}.do" method="post" modelAttribute="bean">
<s:hidden path="id" id="userId"/>
<input type="hidden" id="redirect" name="redirect" value="alter"/>
  <div class="formAreaTitle">基本信息</div>
  <div class="formAreaContent"><br />
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formTable">
  		<tr>
	  	<th width="15%">分配角色：</th>
	    <td width="35%" colspan="3">
	    	<select id="roleIds" name="roleIds" style="width:300px;">
	    		<c:if test="${bean.roles != null}">
	    		<c:forEach var="role" items="${bean.roles}">
	    			<c:if test="${role.org !=null && role.department!=null}">
	    			<option value="${role.id}">
	    				${role.org.name}-${role.department.name}-${role.name}
	    			</option>
	    			</c:if>
	    		</c:forEach>
	    		</c:if>
	    	</select>
	    </td>
	    <tr>
	  	<th width="15%">真实姓名：</th>
	    <td width="35%" >
	    	<s:input path="name" size="20" maxlength="20" />
	    </td>
	    </tr>
	    <th width="15%">出生年月：</th>
	    <td width="35%" >
	    	<s:input path="birthday" size="12" maxlength="20" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" cssClass="Wdate"/>
	    </td>
	    </tr>
	    </tr>
	  	<tr>
	  	<th width="15%">登录账号：</th>
	    <td width="35%" >
	    	<s:input id="username" path="username" size="20" maxlength="50" />
	    </td>
	  	<th width="15%">性别：</th>
	    <td width="35%" >
	    	<s:select path="gender">
			<s:option value="1">男</s:option>
			<s:option value="0">女</s:option>
			<s:option value="2">保密</s:option>
			</s:select>
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">密码：</th>
	    <td width="35%" >
	    	<s:password id="password" path="password" size="20" maxlength="50" />
	    </td>
	  	<th width="15%">手机：</th>
	    <td width="35%" >
	    	<s:input path="mobie" size="20" maxlength="20" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">确认密码：</th>
	    <td width="35%" >
	    	<s:password id="passwordQr" path="passwordQr" size="20" maxlength="50" />
	    </td>
	  	<th width="15%">固定电话：</th>
	    <td width="35%" >
	    	<s:input path="phone" size="20" maxlength="20" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">邮箱：</th>
	    <td width="35%" >
	    	<s:input path="email" size="20" maxlength="200" />
	    </td>
	  	<th width="15%">排序：</th>
	    <td width="35%" >
	    	<s:input path="sortNum" size="20" maxlength="50" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">状态：</th>
	    <td width="35%" >
	    	<s:select path="status">
			<s:option value="1">正常</s:option>
			<s:option value="2">锁定</s:option>
			<s:option value="3">待验证</s:option>
			</s:select>
	    </td>
	  	<th width="15%">添加时间：</th>
	    <td width="35%" >
	    	<s:input path="timeAdd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" size="20" maxlength="30"/>
	    </td>
		</tr>
    </table>
  	<br />
  </div>
  <div class="formSubArea"> 
  	<input type="submit" class="subBtn" value="保存" />
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
