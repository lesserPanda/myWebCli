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
<title>音乐信息</title>
<script type="text/javascript">
$(function(){
	// 主表验证
	var validatorArray = [
	{
		id:"musicName",
		required:true,
		missingMessage:"请输入音乐名字"	},
	{
		id:"musicAuthor",
		required:true,
		missingMessage:"请输入歌唱家"	},
	{
		id:"musicSrc",
		required:true,
		missingMessage:"请输入网络链接地址"	},
	];
	// 从表验证
	// 绑定
 	$("form").validator(validatorArray); 
	
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">音乐信息 - ${operation=='add' ? '添加' : '修改'}</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
	<c:if test="${operation=='alter'}">
	<div class="editButtonsArea">
		<shiro:hasPermission name="MusicInfo:musicInfo:add">
	 	<div class="add"><a href="add.do">新增</a></div>
	 	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="MusicInfo:musicInfo:view">
	 	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="MusicInfo:musicInfo:remove">
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
		  	<th width="15%">音乐名字：</th>
		    <td width="35%" >
		    	<s:input path="musicName" size="80" maxlength="256" />
		    </td>
		    </tr>
		    <tr>
		  	<th width="15%">音乐作者：</th>
		    <td width="35%" >
		    	<s:input path="musicAuthor" size="50" maxlength="256" />
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">音乐链接：</th>
		    <td width="35%" >
		    	<s:input path="musicSrc" size="120" maxlength="2000" />
		    </td>
		    </tr>
		    <tr>
		  	<th width="15%">备注信息：</th>
		    <td width="35%" >
		    	<s:textarea path="remarks" cols="280" rows="3" size="20" maxlength="255" />
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
