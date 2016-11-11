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
<title>操作日志</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">操作日志 管理列表</div>
</div>
<s:form action="list.do" method="post" modelAttribute="query">
<div class="areaContent">
	<div class="searchArea">
    	<table border="0" cellspacing="5" cellpadding="0">
         <thead>
          <tr>
          	<th>操作人：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['userName']" />
            	</div>
            </td>
          	<th>操作用户名：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['userUsername']" />
            	</div>
            </td>
          	<th>操作类型：</th>
            <td>
            	<div class="inputDiv">
            	<s:select path="search['type']">
            	<option value="">---所有---</option>
				<s:option value="0">登录失败</s:option>
				<s:option value="1">登录成功</s:option>
				<s:option value="2">操作日志</s:option>
				</s:select>
            	</div>
            </td>
            <td>
            	<input type="submit" value=" 搜  索 " class="searchAreaSubBtn" />
            </td>
          </tr>
          </thead>
        </table>
  </div>
  <div class="editButtonsArea">
  	<shiro:hasPermission name="admin:operationLog:remove">
    <div class="delete"><a href="javascript:$.remove();">删除</a></div>
    </shiro:hasPermission>
  </div>
  <div class="listNews">
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listNewsTable">
      <thead>
      <tr>
        <th width="3%" align="center"><input type="checkbox" id="allBox"/></th>
	    <th><span class="ls-sort" pagesort="userUsername">操作用户名</span></th>
	    <th><span class="ls-sort" pagesort="userName">操作用户真实姓名</span></th>
	    <th><span class="ls-sort" pagesort="dataId">操作数据的ID</span></th>
	    <th><span class="ls-sort" pagesort="description">操作描述</span></th>
	    <th><span class="ls-sort" pagesort="text">操作内容</span></th>
	    <th><span class="ls-sort" pagesort="ip">操作人的IP地址</span></th>
	    <th><span class="ls-sort" pagesort="time">操作时间</span></th>
	    <th><span class="ls-sort" pagesort="type">操作类型</span></th>
        <th width="15%">编辑</th>
      </tr>
      </thead>
      <tbody>
	  <c:forEach var="bean" varStatus="s" items="${pageNav.list}">
	  <tr class="${s.index%2==0?'normal1':'normal2' }">
	    <td align="center"><input type="checkbox" name="id" value="${bean.id}"/></td>
	    <td align="center">
	    	${bean.userUsername}
	    </td>
	    <td align="center">
	    	${bean.userName}
	    </td>
	    <td align="center">
	    	${bean.dataId}
	    </td>
	    <td align="center">
	    	${bean.description}
	    </td>
	    <td align="center">
	    	${bean.text}
	    </td>
	    <td align="center">
	    	${bean.ip}
	    </td>
	    <td align="center">
	    	<fmt:formatDate value="${bean.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </td>
	    <td align="center">
	    	<c:choose>
        		<c:when test="${bean.type==0}">登录失败</c:when>
        		<c:when test="${bean.type==1}">登录成功</c:when>
        		<c:when test="${bean.type==2}">操作日志</c:when>
        	</c:choose>
	    </td>
	  <td align="center" class="listEditTd">
	  		<shiro:hasPermission name="admin:operationLog:remove">
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
        <td colspan="10">${pageNav.view}</td>
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
