<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
</head>
<body>

<%
if (session.getAttribute("admin") == null) {
    response.sendRedirect(request.getContextPath() + "/Admin/login.jsp");
    return;
}
%>


</body>
</html>