<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body>

<h2>Admin Login</h2>

<form action="<%=request.getContextPath()%>/AdminLogin" method="post">
    <input name="username" placeholder="admin">
    <input type="password" name="password">
    <button>Login</button>
</form>

</body>
</html>