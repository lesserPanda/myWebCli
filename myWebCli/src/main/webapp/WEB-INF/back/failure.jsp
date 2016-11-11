<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>  
<link type="text/css" rel="stylesheet" href="/back/css/main.css"/>
<title>500</title>
<div id="err500">
    <div id="errTitle">单点登录异常!</div>
    <div id="errContent">
    	很抱歉,访问或操作出错！<br/>
        请稍后再试或联系系统管理员！
        
    </div>
    <div id="errCode">
  		Error code ： 
        <%  
			// 页面打印出错的信息
			//Exception exception = (Exception)request.getAttribute("exception");  
			//out.print(exception.getMessage());
		%> 
    </div>
</div> 
 
