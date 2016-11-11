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
	$("#orgid").treeSelect({
		url:"/admin/org/tree.do",
		values:["${query.search['orgid']}"]
	});
});
</script>
<title>用户</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">用户列表</div>
</div>
<s:form action="list.do" method="post" modelAttribute="query">
<div class="areaContent">
  <div class="searchArea">
    <table border="0" cellspacing="5" cellpadding="0">
         <thead>
          <tr>
          	<th>所属机构：</th>
            <td>
            	<s:select id="orgid" path="search['orgid']" />
            </td>
          	<th>账号：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['username']" />
            	</div>
            </td>
          	<th>姓名：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['name']" />
            	</div>
            </td>
          	<th>状态：</th>
            <td>
            	<div class="inputDiv">
            	<s:select path="search['status']">
            	<s:option value="">---所有---</s:option>
				<s:option value="1">正常</s:option>
				<s:option value="2">锁定</s:option>
				<s:option value="3">待验证</s:option>
				</s:select>
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
          <tr>
          	<th>性别：</th>
            <td>
            	<div class="inputDiv">
            	<s:select path="search['gender']">
            	<s:option value="">---所有---</s:option>
				<s:option value="1">男</s:option>
				<s:option value="0">女</s:option>
				<s:option value="2">保密</s:option>
				</s:select>
            	</div>
            </td>
          	<th>手机：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['mobie']" />
            	</div>
            </td>
          	<th>电话：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['phone']" />
            	</div>
            </td>
          	<th>邮箱：</th>
            <td>
            	<div class="inputDiv">
            	<s:input path="search['email']" />
            	</div>
            </td>
            </tr>
            <tr>       
            <th>是否开卡：</th>
            <td>
            	<div class="inputDiv" id="slt1">
            	<s:select path="search['disk']">
            	<s:option value="">---所有---</s:option>
				<s:option value="yes">是</s:option>
				<s:option value="no">否</s:option>
				</s:select>
            	</div>
            </td> 	
          	<th>永久有效：</th>
            <td>
            	<div class="inputDiv" id="slt02">
            	<s:select path="search['effectiveStatus']">
            	<s:option value="">---所有---</s:option>
				<s:option value="1">是</s:option>
				<s:option value="0">否</s:option>
				</s:select>
            	</div>
            </td>
          	<th>有效时间：</th>
            <td colspan="3">
            	<div class="inputDiv">
            	<s:input maxlength="12" size="12" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" path="search['timeStart']"/>
				至 
				<s:input maxlength="12" size="12" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" path="search['timeEnd']"/>
            	</div>
            </td>
            </tr>
          </tbody>
        </table>	
  </div>
  <div class="editButtonsArea">
  	<shiro:hasPermission name="admin:user:add">
	<div class="add"><a href="add.do">新增</a></div>
	<div class="cope"><a href="javascript:$.href('add.do?id=');">复制</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:user:view">
	<div class="look"><a href="javascript:$.href('view.do?id=');">查看</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:user:alter">
	<div class="edit"><a href="javascript:$.href('alter.do?id=');">修改</a></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="admin:user:remove">
	<div class="delete"><a href="javascript:$.remove();">删除</a></div>
	</shiro:hasPermission>
  </div>
  <div class="listNews">
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listNewsTable">
      <thead>
      <tr>
        <th width="3%" align="center"><input type="checkbox" id="allBox"/></th>
	    <th><span class="ls-sort" pagesort="username">登录账号</span></th>
	    <th><span class="ls-sort" pagesort="name">真实姓名</span></th>
	    <th><span class="ls-sort" pagesort="gender">性别</span></th>
	    <th><span class="ls-sort" pagesort="mobie">手机</span></th>
	    <th><span class="ls-sort" pagesort="phone">固定电话</span></th>
	    <th><span class="ls-sort" pagesort="email">邮箱</span></th>
	    <th><span class="ls-sort" pagesort="status">状态</span></th>
	    <th>发卡状态</th>
	    <th><span class="ls-sort" pagesort="sortNum">排序</span></th>
	    <th><span class="ls-sort" pagesort="timeAdd">添加时间</span></th>
        <th width="15%">编辑</th>
      </tr>
      </thead>
      <tbody>
	  <c:forEach var="bean" varStatus="s" items="${pageNav.list}">
	  <tr class="${s.index%2==0?'normal1':'normal2' }">
	    <td align="center"><input type="checkbox" name="id" value="${bean.id}"/></td>
	    <td align="left">
	    	${bean.username}
	    </td>
	    <td align="center">
	    	${bean.name}
	    </td>
	    <td align="center">
	    	<c:choose>
        		<c:when test="${bean.gender==2}">保密</c:when>
        		<c:when test="${bean.gender==1}">男</c:when>
        		<c:when test="${bean.gender==0}">女</c:when>
        	</c:choose>
	    </td>
	    <td align="center">
	    	${bean.mobie}
	    </td>
	    <td align="center">
	    	${bean.phone}
	    </td>
	    <td align="left">
	    	${bean.email}
	    </td>
	    <td align="center">
	    	<c:choose>
        		<c:when test="${bean.status==3}">待验证</c:when>
        		<c:when test="${bean.status==2}">锁定</c:when>
        		<c:when test="${bean.status==1}">正常</c:when>
        	</c:choose>
	    </td>
	    <td align="center">
	    	<c:choose>
        		<c:when test="${bean.discCodeId != null}">已开卡</c:when>
        		<c:otherwise>未开卡</c:otherwise>
        	</c:choose>
	    </td>
	    <td align="center">
	    	${bean.sortNum}
	    </td>
	    <td align="center">
	    	<fmt:formatDate value="${bean.timeAdd}" pattern="yyyy-MM-dd"/>
	    </td>
	  <td align="center" class="listEditTd">
		<shiro:hasPermission name="admin:user:view">
		<a class="ls-opt" href="view.do?id=${bean.id}">查看</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="admin:user:alter">
		<a class="ls-opt" href="alter.do?id=${bean.id}">修改</a>
		</shiro:hasPermission>
		<c:choose>
       		<c:when test="${bean.discCodeId != null}">
			<shiro:hasPermission name="admin:discCode:alter">
       		<a class="ls-opt" href="/admin/diskCode/alter.do?id=${bean.discCodeId}">U盾管理</a>
       		</shiro:hasPermission>
       		</c:when>
       		<c:otherwise>
       		<shiro:hasPermission name="admin:diskCode:add">
       		<a class="ls-opt" href="/admin/diskCode/add.do?userId=${bean.id}">发放U盾</a>
       		</shiro:hasPermission>
       		</c:otherwise>
       	</c:choose>
		<shiro:hasPermission name="admin:user:remove">
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
        <td colspan="11">${pageNav.view}</td>
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
