<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="Admin-Header.jsp" %>
<%@ include file="Sidebar.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Driver Management</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-content">
    <section class="page-hero">
        <div>
            <h2>Driver Management</h2>
            <p>Prepare your admin panel for assigned drivers, contact records, and future schedule-driver mapping.</p>
        </div>
        <span class="page-chip">Driver admin</span>
    </section>

    <div class="card form-card">
        <div class="admin-section-head">
            <div>
                <span class="admin-kicker">Module ready</span>
                <h3>Driver module placeholder</h3>
                <p>This page is now styled and ready for the next backend step. We can add driver creation, license details, and bus assignment in the next pass.</p>
            </div>
        </div>
        <a class="secondary-link" href="<%=request.getContextPath()%>/Admin/Dashboard.jsp">Back to dashboard</a>
    </div>
</div>

</body>
</html>