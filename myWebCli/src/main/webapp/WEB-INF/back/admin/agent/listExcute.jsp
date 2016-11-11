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
<title>代理</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">我代理的用户</div>
</div>
<s:form action="listExcute.do" method="post" modelAttribute="query">
<div class="areaContent">
	<div class="searchArea">
    	<table border="0" cellspacing="5" cellpadding="0">
         <thead>
          <tr>
          	<th>授权人账号：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['ownerUsername']" />
            	</div>
            </td>
          	<th>授权人姓名：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['ownerName']" />
            	</div>
            </td>
            <td>
            	<input type="submit" value=" 搜  索 " class="searchAreaSubBtn" />
            </td>
          </tr>
          </thead>
        </table>
  </div>
  <div class="listNews">
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listNewsTable">
      <thead>
      <tr>
        <th width="3%" align="center"><input type="checkbox" id="allBox"/></th>
	    <th><span class="ls-sort" pagesort="ownerName">被代理人</span></th>
	    <th><span class="ls-sort" pagesort="timeStart">代理开始时间</span></th>
	    <th><span class="ls-sort" pagesort="timeEnd">代理结束时间</span></th>
	    <th><span class="ls-sort" pagesort="timeAdd">创建时间</span></th>
	    <th >状态</th>
        <th width="15%">编辑</th>
      </tr>
      </thead>
      <tbody>
	  <c:forEach var="bean" varStatus="s" items="${pageNav.list}">
	  <tr class="${s.index%2==0?'normal1':'normal2' }">
	    <td align="center"><input type="checkbox" name="id" value="${bean.id}"/></td>
	    <td align="center">
	    	${bean.ownerName}(${bean.ownerUsername})
	    </td>
	    <td align="center">
	    	<fmt:formatDate value="${bean.timeStart}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </td>
	    <td align="center">
	    	<fmt:formatDate value="${bean.timeEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </td>
	    <td align="center">
	    	<fmt:formatDate value="${bean.timeAdd}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </td>
	    <td align="center">
	    	<%
	    		request.setAttribute("curDate", new java.util.Date());
	    	%>
	    	<c:if test="${bean.timeEnd < requestScope.curDate}">
	    	已过期
	    	</c:if>
	    </td>
	  <td align="center" class="listEditTd">
	  		<shiro:hasPermission name="admin:agent:view">
	  		<a class="ls-opt" href="view.do?id=${bean.id}&source=excute">查看</a>
	  		</shiro:hasPermission>
	  		<%-- <a class="ls-opt" href="/back/runAs.do?userId=${bean.ownerId}">执行代理</a> --%>
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
