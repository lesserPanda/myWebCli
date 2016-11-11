<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript">
if( window.top != window.self ){
	parent.location.href = location.href;
}
</script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${conf['back.title']}</title>
<object id="udisk" classid="clsid:8D39268B-BC9C-4C5D-A2FE-99269911BDFC" ></object>
<object id="udiskft" classid="clsid:EACBA60A-E7AD-4C6F-8741-3FCB8C429ADD"></object>
<jsp:include page="/WEB-INF/back/commons/head.jsp"/>
<script type="text/javascript" src="/back/js/udisk.js"></script>
<script type="text/javascript">
$(function(){
	var usbkey = "${conf['login.usbkey']}";
	$.validateUsbkey(usbkey);
	// 登录验证
	$("form").submit(function() {
		return $.validateUsbkey(usbkey) && $.validateUser();
	});
});
</script>
</head>
<body class="loginBodyStyle">
	<form action="/back/login.do" method="post">
    <div class="loginPageParentDiv">
        <div class="loginAreaTable">
        	<div class="loginTitle"></div>
            <div class="loginMsg" style="height:16px;">
            	<c:if test="${!empty shiroLoginFailure}">
                    <c:choose>
                        <c:when test="${shiroLoginFailure=='net.tuxun.component.admin.shiro.exception.CaptchaException'}">
                           	验证码错误
                        </c:when>
                        <c:when test="${shiroLoginFailure=='net.tuxun.component.admin.shiro.exception.NoDiskCodeExcetion'}">
                           	账号没有发放U盾
                        </c:when>
                        <c:when test="${shiroLoginFailure=='net.tuxun.component.admin.shiro.exception.OverdueDiskCodeExcetion'}">
                           	U盾已过期
                        </c:when>
                        <c:when test="${shiroLoginFailure=='net.tuxun.component.admin.shiro.exception.UserLoginExcetion'}">
                                                  账户异常
                        </c:when>
                        <c:when test="${shiroLoginFailure=='org.apache.shiro.authc.UnknownAccountException'}">
                                                  账户不存在
                        </c:when>
                        <c:when test="${shiroLoginFailure=='org.apache.shiro.authc.LockedAccountException'}">
                                                  账户未激活
                        </c:when>
                        <c:when test="${shiroLoginFailure=='net.tuxun.component.admin.shiro.exception.RelevantExcetion'}">
                                                  账号取得权限出错
                        </c:when>
                        <c:otherwise>
                           	用户名或密码错误
                        </c:otherwise>
                    </c:choose>
				</c:if>
				<c:if test="${!empty param.message}">
					<c:choose>
						<c:when test="${param.message=='timeOut'}">
	                       	登录超时
	                    </c:when>
	                    <c:otherwise>
	                      	账号已在别处登录
	                    </c:otherwise>
                    </c:choose>
				</c:if>
            </div>
            <div class="loginItem">用户名：<input name="username" type="text" class="loginUser"/></div>
            <div class="loginItem">密&emsp;码：<input name="password" type="password" class="loginPwd"/></div>
            <c:if test="${conf['login.captcha.times']==0 || sessionScope.shiroCaptchaRequired}">
            <div class="loginItem">验证码：<input type="text" id="captcha" name="captcha" class="loginChk"/>
              <a href="javascript:void(0);">
              	<img src="${ctx}/captcha.servlet" align="absmiddle"  title="看不清可点击切换" 
                     onclick="this.src='/captcha.servlet?d='+new Date()*1" border="0"/>
              </a>
            </div>
            </c:if>
            <div class="loginBtn">
              <input type="image" name="imageField" id="imageField" src="/back/images/loginBtn.jpg"/> 
              <input type="checkbox" name="rememberMe" value="true"/>记住我！
            </div>
        </div>
        <div style="color: white;">【主办单位】：${conf['host.enterprise']}  【技术支持】：${conf['support.enterprise']} <br/>请用IE8以上浏览器访问   
        </div>
    </div>
	</form>
</body>
</html>