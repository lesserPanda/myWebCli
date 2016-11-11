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
<script type="text/javascript" src="/back/js/udisk.js"></script>
<object id="udisk" classid="clsid:8D39268B-BC9C-4C5D-A2FE-99269911BDFC" ></object>
<object id="udiskft" classid="clsid:EACBA60A-E7AD-4C6F-8741-3FCB8C429ADD"></object>
<script type="text/javascript">
$(function(){
	var effectiveStatus = "${bean.effectiveStatus}";
	if($.trim(effectiveStatus) != "0"){
		$("#effectiveStatus").next().hide();
	}
	
	
	$("#effectiveStatus select").change(function(){
		if($(this).val() == "1"){
			$("#effectiveStatus").next().hide();
		}else{
			$("#effectiveStatus").next().show();
		}
	});
	
	//alert($.diskCheck());
	//alert($.diskUUID());
	
	$("form").submit(function(){
		if(!$.isIE()){
			showMessage("请使用IE浏览器才能完成此操作");
			return false;
		}else{
			var slt = $("#effectiveStatus select").val();
			if(slt == "0"){
				if($.trim($("#timeStart").val()) == ""){
					showMessage("请输入有效期开始时间");
					return false;
				}
				if($.trim($("#timeEnd").val()) == ""){
					showMessage("请输入有效期结束时间");
					return false;
				}
			}
			
			if(!$.diskCheck()){
				showMessage("未安装驱动");
				return false;
			}
			if(!$.diskWrite($("#username").val())){
				showMessage("往U盾中写入信息失败");
				return false;
			}
			var code = $.diskRead();
			if(code == null || code == ""){
				showMessage("从U盾中读取信息失败");
				return false;
			}
			
			$("#diskCode").val(code);
			return true;
		}
		return true;
	});
});


</script>
<title>U盾开卡记录</title>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">${operation=='add' ? '用户名写入U盾' : '重置U盾的用户名'}</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
	<s:form action="${operation}.do" method="post" modelAttribute="bean">
	<s:hidden path="id" />
  	<s:hidden path="userId" />
  	<s:hidden path="username" />
  	<s:hidden path="realname" />
  	<s:hidden path="diskCode" />
	<input type="hidden" id="redirect" name="redirect" value="alter"/>
	  <div class="formAreaTitle">基本信息</div>
	  <div class="formAreaContent"><br />
	  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formTable">
	  	<tr>
	  	<th width="15%">用户名：</th>
	    <td width="35%" >
	    	${bean.username }(${bean.realname })
	    </td>
	  	</tr>
	  	<tr id="effectiveStatus">
	  	<th width="15%">是否永久有效：</th>
	    <td width="35%" >
	    	<s:select path="effectiveStatus">
			<s:option value="1">是</s:option>
			<s:option value="0">否</s:option>
			</s:select>
	    </td>
	    </tr>
	    <tr>
	    <th width="15%" >有效期：</th>
	    <td width="35%" >
           	<s:input maxlength="12" size="20" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" path="timeStart"/>
			至 
			<s:input maxlength="12" size="20" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" path="timeEnd"/>
	    </td>
	  	</tr>
	    </table>
	  	<br />
	  </div>
	  <div class="formSubArea"> 
	  	<input type="submit" class="subBtn" value="保存"/>
	  	<input type="submit" class="subBtn" value="保存并返回" onclick="$('#redirect').val('list');"/>
	    <c:if test="${operation=='alter'}">
	    <input type="button" class="subBtn" value="撤销U盾" onclick="javascript:$.alertActive('确定撤销U盾?','remove.do?id=${bean.id}');"/>
	    </c:if>
	  	<input type="reset" value="重置表单" class="subBtn" />
	  	<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='/admin/user/list.do'"/>
	  </div>
	</s:form>
</div>
</body>
</html>
