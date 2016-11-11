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
<script type="text/javascript" src="/myWeb/myweb/form.js"></script>
<script type="text/javascript" src="/back/js/fromTable.js"></script>
<title>文章</title>
<script type="text/javascript">
$(function(){
	// 主表验证
	var validatorArray = [
	{
		id:"title",
		required:true,
		missingMessage:"请输入标题"	},
	{
		id:"content",
		required:true,
		missingMessage:"请输入文章内容"	},
	{
		id:"keywords",
		required:true,
		missingMessage:"请输入关键字"	},
	{
		id:"description",
		required:true,
		missingMessage:"请输入描述、摘要"	},
	];
	// 从表验证
	// 绑定
 	$("form").validator(validatorArray); 
	
 	// 修改AJAX
	var operation = "${operation}";
	if(operation == "alter"){
		var id = "${bean.id}";
		$("#cmsComment").oneBindInit({
			param:{primaryTableId:id},
			url:"/Article/cmsComment/list.do",
			attrs:["id", "title", "content", "name", "ip", "createDate", "robackName", "robackCreateDate", "auditUserId", "auditDate", "delFlag"]  
		});
	}
	
	$("#zzType").hide();
	
	$("#categoryId").on("click", function(){
		$this = $(this);
		if("003" == $this.find("option:selected").val()){
			$("#zzType").show();
		}else{
			$("#zzType").hide();
		}
		
	});
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/back/commons/show_message.jsp"/>
<div class="areaTitle">
    <div class="key">文章 - ${operation=='add' ? '添加' : '修改'}</div>
    <div class="ext"></div>
</div>
<div class="areaContent">
	<c:if test="${operation=='alter'}">
	<div class="editButtonsArea">
		<shiro:hasPermission name="Article:article:add">
	 	<div class="add"><a href="add.do">新增</a></div>
	 	<div class="cope"><a href="add.do?id=${bean.id}">复制</a></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="Article:article:view">
	 	<div class="look"><a href="view.do?id=${bean.id}">查看</a></div>
		</shiro:hasPermission>
		<shiro:hasPermission name="Article:article:remove">
	    <div class="delete"><a href="javascript:$.remove('${bean.id}');">删除</a></div>
		</shiro:hasPermission>
	</div>
	</c:if>
	<div class="areaTitleInner">
		<div id="article" class="tabHot">文章</div>
		<!-- <div id="cmsComment" class="tabNormal">评论</div> -->
	</div>
	<s:form action="${operation}.do" method="post" modelAttribute="bean">
	<s:hidden path="id" />
	<input type="hidden" id="redirect" name="redirect" value="alter"/>
	  <div id="article_area" class="tabarea">
	  <div class="formAreaTitle">基本信息</div>
	  <div class="formAreaContent"><br />
	  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formTable">
		  	<tr>
		  	<th width="15%">栏目编号：</th>
		    <td width="35%" >
		    	<s:select path="categoryId">
				<s:option value="001">个人总结</s:option>
				<s:option value="002">技能提升</s:option>
				<s:option value="003">转载收藏</s:option>
				<s:option value="004">生活琐事</s:option>
				<s:option value="006">首页介绍</s:option>
				<s:option value="005">其他</s:option>
				</s:select>
		    </td>
		    </tr>
		    
		    <tr id="zzType">
		  	<th width="15%">转载类型：</th>
		    <td width="35%" >
		    	<s:select path="zzFlag">
		    	<s:option value="0030">默认</s:option>
				<s:option value="0031">伤不起</s:option>
				<s:option value="0032">喜欢</s:option>
				<s:option value="0033">感兴趣</s:option>
				</s:select>
		    </td>
		    </tr>
		    
		    <tr>
		  	<th width="15%">标题：</th>
		    <td width="35%" >
		    	<s:input path="title" size="120" maxlength="255" />
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">文章内容：</th>
		    <td width="35%" >
		    	<s:textarea path="content" cssStyle="width:700px;height:300px;" cssClass="editor"/>
		    </td>
		    </tr>
		    <tr>
		  	<th width="15%">文章链接：</th>
		    <td width="35%" >
		    	<s:input path="link" size="100" maxlength="255" />
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">标题颜色：</th>
		    <td width="35%" >
		    	<s:input path="color" size="20" maxlength="50" />
		    </td>
		    </tr>
		    <tr>
		  	<th width="15%">文章图片：</th>
		    <td width="35%" >
		    	<div id="imageDiv"></div><input type="button" id="imageButton" class="subBtn" value="选择图片"/>
		    	<%-- <s:input path="image" size="20" maxlength="255" /> --%>
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">关键字：</th>
		    <td width="35%" >
		    	<s:input path="keywords" size="60" maxlength="255" />
		    </td>
		    </tr>
		    <tr>
		  	<th width="15%">描述、摘要：</th>
		    <td width="35%" >
		    	<s:textarea path="description" row="2" col="200" />
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">权重，越大越靠前：</th>
		    <td width="35%" >
		    	<s:input path="weight" size="20" maxlength="30" />
		    </td>
		    </tr>
		    <tr>
		  	<th width="15%">权重期限：</th>
		    <td width="35%" >
		    	<s:input path="weightDate" cssClass="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">推荐位，多选：</th>
		    <td width="35%" >
		    	<s:input path="posid" size="20" maxlength="10" />
		    </td>
		    </tr>
		    <tr>
		  	<th width="15%">自定义内容视图：</th>
		    <td width="35%" >
		    	<s:input path="customContentView" size="20" maxlength="255" />
		    </td>
			</tr>
		  	<tr>
		  	<th width="15%">视图配置：</th>
		    <td width="35%" >
		    	<s:input path="viewConfig" size="20" maxlength="255" />
		    </td>
		    </tr>
	    </table>
	  	<br />
	  </div>
	  </div>
	  
	  <div id="cmsComment_area" class="tabarea">
	    <div class="formAreaTitle">
	    	
	    	<a id="addCmsComment" class="operation" href="javascript:void(0);" url="/Article/cmsComment/add.do">增加</a>
    		<a id="removeCmsComment" class="operation" href="javascript:void(0);" url="/Article/cmsComment/remove.do">删除</a>
	    </div>
	  	<div class="formAreaContent">
		  	<table id="tbCmsComment" width="100%" cellspacing="0" cellpadding="0" border="0" class="listNewsTable">
		      <thead>
		      <tr>
		        <th width="3%"><input type="checkbox"></th>
			    <th>栏目内容的标题</th>
			    <th>评论内容</th>
			    <th>评论姓名</th>
			    <th>评论IP</th>
			    <th>评论时间</th>
			    <th>回复评论人</th>
			    <th>评论时间</th>
			    <th>审核人</th>
			    <th>审核时间</th>
		      </tr>
		      </thead>
		      <tbody>
			  	<tr class="normal1">
			  		<td></td>
					<td align="center">
						<input type="checkbox" value=""/><input hidden="true" type="text" id="cmsComments_0_id" name="cmsComments[0].id" value=""/>
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_title" name="cmsComments[0].title" size="20" maxlength="255" />
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_content" name="cmsComments[0].content" size="20" maxlength="255" />
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_name" name="cmsComments[0].name" size="20" maxlength="100" />
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_ip" name="cmsComments[0].ip" size="20" maxlength="100" />
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_createDate" name="cmsComments[0].createDate" size="20" maxlength="30" />
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_robackName" name="cmsComments[0].robackName" size="20" maxlength="100" />
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_robackCreateDate" name="cmsComments[0].robackCreateDate" size="20" maxlength="30" />
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_auditUserId" name="cmsComments[0].auditUserId" size="20" maxlength="64" />
					</td>	
					<td align="center">
			    	<input type="text" id="cmsComments_0_auditDate" name="cmsComments[0].auditDate" size="20" maxlength="30" />
					</td>		
			  	</tr>
		      </tbody>
		      </tbody>
	    	</table>
    	</div>
	  </div>
	  <div class="formSubArea"> 
	  	<input type="submit" class="subBtn" value="保存"/>
	  	<input type="submit" class="subBtn" value="保存并返回" onclick="$('#redirect').val('list');"/>
	    <c:if test="${operation=='add'}">
	    <input type="submit" class="subBtn" value="保存并新增" onclick="$('#redirect').val('add');"/>
	    </c:if>
	  	<input type="reset" value="重置表单" class="subBtn" />
	  	<input type="button" value="返回列表" class="subBtn" onclick="javascript:location.href='list.do'"/>
	  </div>
	</s:form>
</div>
</body>
</html>
