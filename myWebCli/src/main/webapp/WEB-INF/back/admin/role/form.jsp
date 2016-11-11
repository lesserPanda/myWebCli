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
		missingMessage:"请输入角色名称",
		validType:{
			length:[0,50]
		}
	}]);
	
	var perms = "<shiro:principal property='currentPerms'/>";
	$("input[name='perms']").treeDialog({
		url:"/admin/role/perms.do",
		values:"${bean.perms}",
		perms:perms
	});
	if(perms != "*"){
		$(".formTable tr").eq(1).hide();
	}
	
	$("#romts").multipleSelect({
	 	title:"所属机构部门",
	 	multiple:false,
	 	message:"请选择所属机构部门",
		selects : [ {
			id : "orgSelect",
			lab:"机构",
			url : "/admin/org/tree.do"
		}, {
			id : "dpmSelect",
			lab:"部门",
			url : "/admin/department/tree.do?orgId="
		}]
	});
	
	$("#trIsAllPerms :radio").click(function(){
		if($(this).attr("value") == "1"){
			$("#trPerms").hide();
		}else{
			$("#trPerms").show();
		}
	});
	
	// 表单验证
	$("form").unbind();
	$("form").submit(function(){
		var options = $('#romts').find('option');
		if (options.size() == 0) {
			showMessage("请选择所属机构部门");
			return false;
		}
		return $(this).form('validate');
	});
});		
</script>
<title>角色</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">${operation=='add' ? '添加' : '修改'}角色信息</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
<c:if test="${operation=='alter'}">
<div class="editButtonsArea">
 	<shiro:hasPermission name="admin:role:add">
	<div class="add"><a href="add.do">新增</a></div>
	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:role:view">
	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:role:remove">
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
	  	<th width="15%">角色名称：</th>
	    <td width="35%" colspan="3">
	    	<s:input path="name" size="20" maxlength="100" />
	    </td>
		</tr>
	  	<tr>
	  	<th width="15%">拥有所有的功能权限：</th>
	    <td width="35%" colspan="3" id="trIsAllPerms">
	    	<label><s:radiobutton path="isAllPerms" value="0" />否</label>
	    	<label><s:radiobutton path="isAllPerms" value="1" />是</label>
	    </td>
	    </tr>
	    <tr id="trPerms" <c:if test="${bean.isAllPerms==1}">style="display: none;"</c:if>>
	  	<th width="15%">角色功能权限：</th>
	    <td width="35%" colspan="3">
	    	<s:input path="perms" size="20" readonly="true"/>
	    </td>
		</tr>
		<tr>
		<th width="15%">所属机构部门：</th>
	    <td width="35%" colspan="3" valign="top">
	    	<select id="romts" name="romts" style="width:300px;">
	    		<c:if test="${operation=='alter'}">
	    		<option value="${bean.org.id}#${bean.department.id}">${bean.org.name}-${bean.department.name}</option>
	    		</c:if>
	    	</select>
	    </td>
	    </tr>
	    <%-- <tr>
	  	<th width="15%">权限范围：</th>
	    <td width="35%" colspan="3">
	    	<label><s:radiobutton path="isRangePerms" value="0" />自身范围</label>
	    	<label><s:radiobutton path="isRangePerms" value="1" />机构范围</label>
	    </td>
		</tr> --%>
	  	<tr>
	  	<th width="15%">角色的简单描述：</th>
	    <td width="35%" colspan="3">
	    	<s:textarea path="description" rows="2" cols="50"/>
	    </td>
		</tr>
    </table>
  	<br />
  </div>
  <div class="formSubArea"> 
  	<input type="submit" class="subBtn" value="保存" onclick="$('#romts').find('option').attr('selected',true);"/>
  	<input type="submit" class="subBtn" value="保存并返回" onclick="$('#redirect').val('list');$('#romts').find('option').attr('selected',true);"/>
    <c:if test="${operation=='add'}">
    <input type="submit" class="subBtn" value="保存并新增" onclick="$('#redirect').val('add');$('#romts').find('option').attr('selected',true);"/>
    </c:if>
  	<input type="reset" value="重置表单" class="subBtn" />
  	<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='list.do'"/>
  </div>
</s:form>
</div>
</body>
</html>
