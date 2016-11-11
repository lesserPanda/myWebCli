<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

function showMessage(message) {
	layer.msg(message, {
	    offset: '10px',
	    shift: 0,
	    time:2000
	}); 
}

$(function(){
	// 全局配置
	layer.config({
	    extend: ['skin/layer.ext.css']
	});
	
	// 弹出提示
	var message = $.cookie('message');
	if (typeof(message) != "undefined") {
		showMessage(message);
		$.removeCookie('message', { path: '/' });
	}
	
	// 计数
	<c:if test="${!empty keepcount}">
	$.keepHeadCount();
	$.keepMenuCount();
	</c:if>
});
</script>

