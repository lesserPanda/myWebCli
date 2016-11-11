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
<title>文章</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">文章 - 列表</div>
</div>
<s:form action="list.do" method="post" modelAttribute="query">
<div class="areaContent">
	<div class="searchArea">
    	<table border="0" cellspacing="5" cellpadding="0">
         <thead>
          <tr>
          	<th>栏目编号：</th>
            <td>
            	<div class="inputDiv">
            	<s:select path="search['categoryId']">
            	<option value="">---所有---</option>
				<s:option value="001">个人总结</s:option>
				<s:option value="002">技能提升</s:option>
				<s:option value="003">转载收藏</s:option>
				<s:option value="004">生活琐事</s:option>
				<s:option value="005">其他</s:option>
				</s:select>
            	</div>
            </td>
          	<th>标题：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['title']" />
            	</div>
            </td>
          	<th>关键字：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['keywords']" />
            	</div>
            </td>
            <td>
            	<input type="submit" value=" 搜  索 " class="searchAreaSubBtn" />
            </td>
            <td>
            	<input type="button" class="${param.searchclass=='searchAreaOpentBtn'?'searchAreaOpentBtn':'searchAreaClosetBtn'}" value=" " />
            	<input type="hidden" name="searchclass" value="${param.searchclass}"/>
            </td>
          </tr>
          </thead>
          <tbody>
          	<th>创建者：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['createBy']" />
            	</div>
            </td>
          </tbody>
        </table>
  </div>
  <div class="editButtonsArea">
  	<shiro:hasPermission name="Article:article:add">
  	<div class="add"><a href="add.do">新增</a></div>
  	<div class="cope"><a href="javascript:$.href('add.do?id=');">复制</a></div>
  	</shiro:hasPermission>
  	<shiro:hasPermission name="Article:article:view">
  	<div class="look"><a href="javascript:$.href('view.do?id=');">查看</a></div>
  	</shiro:hasPermission>
  	<shiro:hasPermission name="Article:article:alter">
    <div class="edit"><a href="javascript:$.href('alter.do?id=');">修改</a></div>
  	</shiro:hasPermission>
  	<shiro:hasPermission name="Article:article:remove">
    <div class="delete"><a href="javascript:$.remove();">删除</a></div>
  	</shiro:hasPermission>
  </div>
  <div class="listNews">
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listNewsTable">
      <thead>
      <tr>
        <th width="3%" align="center"><input type="checkbox" id="allBox"/></th>
	    <th><span class="ls-sort" pagesort="title">标题</span></th>
	    <th><span class="ls-sort" pagesort="keywords">关键字</span></th>
	    <th><span class="ls-sort" pagesort="createBy">创建者</span></th>
	    <th><span class="ls-sort" pagesort="createDate">创建时间</span></th>
        <th width="15%">编辑</th>
      </tr>
      </thead>
      <tbody>
	  <c:forEach var="bean" varStatus="s" items="${pageNav.list}">
	  <tr class="${s.index%2==0?'normal1':'normal2' }">
	    <td align="center"><input type="checkbox" name="id" value="${bean.id}"/></td>
	    <td align="center">
	    	${bean.title}
	    </td>
	    <td align="center">
	    	${bean.keywords}
	    </td>
	    <td align="center">
	    	${bean.createBy}
	    </td>
	    <td align="center">
	    	<fmt:formatDate value="${bean.createDate}" pattern="yyyy-MM-dd"/>
	    </td>
	  <td align="center" class="listEditTd">
	  		<shiro:hasPermission name="Article:article:view">
	  		<a class="ls-opt" href="view.do?id=${bean.id}">查看</a>
	  		</shiro:hasPermission>
	  		<shiro:hasPermission name="Article:article:alter">
	    	<a class="ls-opt" href="alter.do?id=${bean.id}">修改</a>
	  		</shiro:hasPermission>
	  		<shiro:hasPermission name="Article:article:remove">
	    	<a class="ls-opt" href="javascript:$.remove('${bean.id}');">删除</a>
	  		</shiro:hasPermission>
	  </td>
	  </tr>
	  </c:forEach>
	  <%-- <c:if test="${fn:length(pageNav.list) le 0}"> 
		<div class="ls-norecord margin-top5">没有相关的数据!!</div>
	  </c:if> --%>
      </tbody>
      <tfoot>
      <tr>
        <td colspan="6">${pageNav.view}</td>
      </tr>
      </tfoot>
    </table>
  </div>
</div>
<div class="pagesort">
<c:forEach var="bean" varStatus="s" items="${query.collates}">
<span>
	<input type="hidden" name="collates[${s.index}].sortField" value="${bean.sortField}"/>
	<input type="hidden" name="collates[${s.index}].sortType" value="${bean.sortType}"/>
</span>
</c:forEach>
</div>
</s:form>
</body>
</html>
