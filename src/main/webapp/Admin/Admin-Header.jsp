<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%@ include file="admin-auth.jsp" %>

<div style="background:green;color:white;padding:10px">
    <h2>Classic Bus – Admin</h2>
    <a href="<%=request.getContextPath()%>/Admin/logout.jsp" style="color:white">
        Logout
    </a>
</div>


</body>
</html>