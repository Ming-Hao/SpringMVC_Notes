<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
request.setAttribute("tt", "/tmp_pic/");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" 
              content="text/html; charset=UTF-8">
        <title>Upload Success</title>
        
        <style>
        .box {
        	width: 200px;
        	height: 200px
        }
        </style>
    </head>
    <body>
    
    <c:forEach var="single_src" items="${src_lists}" >
    	Image
    	<img src = "${single_src}" class = "box"/>
    	<br/>
    </c:forEach>
    
    <h1>File: ${filename} upload successfully.</h1>
    <h1> ${Name} </h1>
    <h1> ${Type} </h1>
    <h1> ${Size} </h1>
    </body>
</html>