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
<script type="text/javascript" src="/back/js/fromTable.js"></script>
<title>文章</title>
<script type="text/javascript">
$(function(){
	var id = "${bean.id}";
	$("#cmsComment").oneBindShow({
		param:{primaryTableId:id},
		url:"/Article/cmsComment/list.do",
		attrs:["title","content","name","ip","createDate","robackName","robackCreateDate","auditUserId","auditDate","delFlag"]
	});
});
</script>
</head>
<body>
<div class="areaTitle">
    <div class="key">文章 - 查看</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
	<div class="editButtonsArea">
		<shiro:hasPermission name="Article:article:toAdd">
	 	<div class="add"><a href="add.do">新增</a></div>
	 	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
		</shiro:hasPermission>
	 	<shiro:hasPermission name="Article:article:toAlter">
	 	<div class="edit"><a href="alter.do?id=${bean.id}">修改</a></div>
	 	</shiro:hasPermission>
	 	<shiro:hasPermission name="Article:article:remove">
	    <div class="delete"><a href="javascript:$.remove('${bean.id}');">删除</a></div>
	 	</shiro:hasPermission>
	</div>
	<div class="areaTitleInner">
		<div id="article" class="tabHot">文章</div>
		<div id="cmsComment" class="tabNormal"></div>
	</div>
	<div id="article_area" class="tabarea">
	<div class="detailAreaTitle">基本信息
	<div class="ext">
	<input type="button" class="detailAreaClosetBtn" value=" " />
	</div>
	</div>
	<div class="detailAreaContent">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="detailTable">
	 	<tr>
	 	<th width="15%">栏目编号：</th>
	   <td width="35%" >
	   	<c:choose>
	      		<c:when test="${bean.categoryId==001}">个人总结</c:when>
	      		<c:when test="${bean.categoryId==002}">技能提升</c:when>
	      		<c:when test="${bean.categoryId==003}">转载收藏</c:when>
	      		<c:when test="${bean.categoryId==004}">生活琐事</c:when>
	      		<c:when test="${bean.categoryId==005}">其他</c:when>
	      	</c:choose>
	   </td>
	 	<th width="15%">标题：</th>
	   <td width="35%" >
	   	${bean.title}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">文章内容：</th>
	   <td width="35%" >
	   	${bean.content}
	   </td>
	 	<th width="15%">文章链接：</th>
	   <td width="35%" >
	   	${bean.link}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">标题颜色：</th>
	   <td width="35%" >
	   	${bean.color}
	   </td>
	 	<th width="15%">文章图片：</th>
	   <td width="35%" >
	   	${bean.image}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">关键字：</th>
	   <td width="35%" >
	   	${bean.keywords}
	   </td>
	 	<th width="15%">描述、摘要：</th>
	   <td width="35%" >
	   	${bean.description}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">权重，越大越靠前：</th>
	   <td width="35%" >
	   	${bean.weight}
	   </td>
	 	<th width="15%">权重期限：</th>
	   <td width="35%" >
	   	${bean.weightDate}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">点赞数量：</th>
	   <td width="35%" >
	   	${bean.pushParise}
	   </td>
	 	<th width="15%">点击数：</th>
	   <td width="35%" >
	   	${bean.hits}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">推荐位，多选：</th>
	   <td width="35%" >
	   	${bean.posid}
	   </td>
	 	<th width="15%">自定义内容视图：</th>
	   <td width="35%" >
	   	${bean.customContentView}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">视图配置：</th>
	   <td width="35%" >
	   	${bean.viewConfig}
	   </td>
	 	<th width="15%">创建者：</th>
	   <td width="35%" >
	   	${bean.createBy}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">创建时间：</th>
	   <td width="35%" >
	   	${bean.createDate}
	   </td>
	 	<th width="15%">更新时间：</th>
	   <td width="35%" >
	   	${bean.updateDate}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">删除标记：</th>
	   <td width="35%" >
	   	${bean.delFlag}
	   </td>
	 	<th width="15%">更新者：</th>
	   <td width="35%" >
	   	${bean.updateBy}
	   </td>
	</tr>
	 	<tr>
	 	<th width="15%">备注信息：</th>
	   <td width="35%" colspan="3">
	   	${bean.remarks}
	   </td>
	</tr>
	  </table>
	</div>
	</div>
	<div id="cmsComment_area" class="tabarea">
	<div class="detailAreaTitle">
	<div class="ext">
	<input type="button" class="detailAreaClosetBtn" value=" " />
	</div>
	</div>
	<div class="detailAreaContent">
		<table id="tbStudent" width="100%" cellspacing="0" cellpadding="0" border="0" class="listNewsTable">
	      <thead>
	      <tr>
		    <th>栏目内容的标题</th>
		    <th>评论内容</th>
		    <th>评论姓名</th>
		    <th>评论IP</th>
		    <th>评论时间</th>
		    <th>回复评论人</th>
		    <th>评论时间</th>
		    <th>审核人</th>
		    <th>审核时间</th>
		    <th>删除标记</th>
	      </tr>
	      </thead>
	      <tbody>
	      </tbody>
	    </table>
	</div>
	</div>
	<div class="detailBtnArea"> 
		<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='list.do'"/>
	</div>
</div>
</body>
</html>
