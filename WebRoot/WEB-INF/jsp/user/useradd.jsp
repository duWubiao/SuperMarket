<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'useradd.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%--日期控件 --%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/calendar/WdatePicker.js"></script>
  </head>
  
  <body>
  <%--spring 自带的标签库 --%>
    	<fm:form method="post" modelAttribute="user"><%--modelAttribute指定绑定的模型属性，默认为command。action属性可以不指定，则自动提交到获取表单页面的URL --%>
    		<fm:errors path="userCode"/>
    		用户编码：<fm:input path="userCode"/><br/><%--path与name类似 --%>
    		<fm:errors path="userName"/>
    		用户名称：<fm:input path="userName"/><br/>
    		<fm:errors path="userPassword"/>
    		用户密码：<fm:password path="userPassword"/><br/>
    		<fm:errors path="birthday"/>
    		用户生日：<fm:input path="birthday" Class="Wdate" id="birthday" name="birthday" 
					readonly="readonly" onclick="WdatePicker();"/>
    		用户地址：<fm:input path="address"/><br/>
    		联系电话：<fm:input path="phone"/>
    		用户角色：
    		<fm:radiobutton path="userRole" value="1"/>系统管理员
    		<fm:radiobutton path="userRole" value="2"/>经理
    		<fm:radiobutton path="userRole" value="3" checked="checked"/>普通用户
    		<input type="submit" value="保存"/>
    	</fm:form>
  </body>
</html>
