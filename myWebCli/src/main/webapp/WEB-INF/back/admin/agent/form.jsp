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
		id:"timeStart",
		required:true,
		missingMessage:"请输代理开始时间"
	},{
		id:"timeEnd",
		required:true,
		missingMessage:"请输入代理结束时间"
	}]);
	
	$("#agentId").multipleSelect({
		title:"请选择用户",
	 	message:"请选择用户",
		selects : [ {
			id : "orgSelect",
			lab:"机构",
			url : "/admin/org/tree.do",
			noKey:true,
			noText:true
		}, {
			id : "dpmSelect",
			lab:"部门",
			url : "/admin/department/tree.do?orgId=",
			noKey:true,
			noText:true
		} , {
			id : "userSelect",
			lab:"用户",
			url : "/admin/user/tree.do?dpmId="
		}]
	});
	
	$("tr[oldPerms]").each(function(i){
		var oldPerms = $(this).attr("oldPerms");
		var perms = $(this).attr("perms");
		$(this).find("input").last().treeDialog({
			url:"/admin/role/perms.do",
			values:perms,
			treeId:"tree"+i,
			dialogId:"dialogId"+i,
			perms:oldPerms
		});
	});
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	$("form").submit(function(){
		var bool = false;
		$("input[name$='rolePerms']").each(function(){
			if($.trim($(this).val()) != ""){
				bool = true;
				return false;
			}
		});
		if(!bool){
			showMessage("至少授权一个角色");
			return false;
		}
		
		var options = $('#agentId').find('option');
		if (options.size() == 0) {
			showMessage("至少选择一个代理用户");
			return false;
		}
		
		var timeStart = $("#timeStart").val();
		var timeEnd = $("#timeEnd").val();
		if(timeStart != "" && timeEnd != ""){
			var beginTimes = timeStart.replace(" ","").substring(0, 10).split('-');
		    var endTimes = timeEnd.replace(" ","").substring(0, 10).split('-');
		    for(var i = 0 ; i< beginTimes.length ; i++){
		    	if (parseInt(beginTimes[i]) > parseInt(endTimes[i])) {
		    		showMessage("代理结束时间应不小于代理开始时间");
					return false;
		    	}
		    }
		}
		return true;
	});
});
</script>
<title>代理</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">${operation=='add' ? '添加' : '修改'}我的授权</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
	<c:if test="${operation=='alter'}">
	<div class="editButtonsArea">
		<shiro:hasPermission name="admin:agent:add">
	 	<div class="add"><a href="add.do">新增</a></div>
	 	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="admin:agent:view">
	 	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="admin:agent:remove">
	    <div class="delete"><a href="javascript:$.remove('${bean.id}');">删除</a></div>
		</shiro:hasPermission>
	</div>
	</c:if>
	
	<s:form action="${operation}.do" method="post" modelAttribute="bean">
	<s:hidden path="id" />
	<input type="hidden" id="redirect" name="redirect" value="alter"/>
	  <div class="formAreaTitle">授权给代理用户</div>
	  <div class="formAreaContent"><br />
	  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formTable">
		  	<tr>
		  	<th width="15%">代理用户：</th>
		    <td width="35%" >
		    	<c:if test="${operation=='add'}">
		    	<s:select id="agentId" path="agentId" multiple="multiple" style="width:300px;"></s:select>
		    	</c:if>
		    	<c:if test="${operation=='alter'}">
		    	<s:hidden path="agentId" />
		    	${bean.agentName}(${bean.agentUsername})
		    	</c:if>
		    </td>
			</tr>
		  	<c:forEach var="role" varStatus="s" items="${bean.agentRoles}">
		  	<tr oldPerms="${role.oldRolePerms}" perms="${role.rolePerms}">
		  	<th width="15%">角色授权：</th>
		    <td width="35%" >
		    	<s:hidden path="agentRoles[${s.index }].id" />
		    	<s:hidden path="agentRoles[${s.index }].agentId" />
		    	<s:hidden path="agentRoles[${s.index }].roleId" />
		    	<s:input path="agentRoles[${s.index }].roleName" />
		    	=>
		    	<s:input path="agentRoles[${s.index }].rolePerms"/>		    	
		    </td>
			</tr>
		  	</c:forEach>
		  	<tr>
		  	<th width="15%">代理开始时间：</th>
		    <td width="35%"  >
		    	<input id="timeStart" name="timeStart" class="Wdate" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'timeEnd\',{d:-1})}', dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${bean.timeStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		    </td>
		    </tr>
		    <tr>
		  	<th width="15%">代理结束时间：</th>
		    <td width="35%"  >
				<input id="timeEnd" name="timeEnd" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'timeStart\',{d:1})}', dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${bean.timeEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">创建时间：</th>
		    <td width="35%" >
		    	<s:input path="timeAdd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" size="20" maxlength="30" cssClass="Wdate"/>
		    </td>
			</tr>
	    </table>
	  	<br />
	  </div>
	  <div class="formSubArea"> 
	  	<input type="submit" class="subBtn" value="保存并返回" onclick="$('#redirect').val('list');"/>
	    <c:if test="${operation=='add'}">
	    <input type="submit" class="subBtn" value="保存并新增" onclick="$('#redirect').val('add');"/>
	    </c:if>
	  	<input type="reset" value="重置表单" class="subBtn" />
	  	<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='listAgent.do'"/>
	  </div>
	</s:form>
</div>
</body>
</html>
