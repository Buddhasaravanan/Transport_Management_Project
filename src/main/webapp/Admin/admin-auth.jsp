<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

User u = (User) session.getAttribute("loggedUser");

if (u == null || !"ADMIN".equals(u.getRole())) {
    response.sendRedirect(request.getContextPath() + "/User/Login.jsp");
    return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Access | GreenBus</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-auth-page">
    <div class="admin-auth-card">
        <span class="auth-badge">Admin access verified</span>
        <h2>Welcome back, admin</h2>
        <p>Your session is active and ready to manage routes, buses, schedules, and bookings from the GreenBus control panel.</p>
        <div class="auth-actions">
            <a class="primary-action secondary-link" style="background: linear-gradient(135deg, #0f7a43, #1b9857); color: #fff; border: none;" href="<%=request.getContextPath()%>/Admin/Dashboard.jsp">Open Dashboard</a>
            <a class="secondary-link" href="<%=request.getContextPath()%>/User/home.jsp">Back to site</a>
        </div>
    </div>
</div>

</body>
</html>