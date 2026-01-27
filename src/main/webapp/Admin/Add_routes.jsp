<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Routes</title>
</head>
<body>

<%@ include file="Admin-Header.jsp" %>
<%@ include file="Sidebar.jsp" %>

<form action="<%= request.getContextPath() %>/RouteServlet" method="post">
    <input name="source" placeholder="From" required>
    <input name="destination" placeholder="To" required>
    <input name="distanceKm" type="number" step="0.1" placeholder="Distance">
    <input name="fareBase" type="number" step="0.1" placeholder="Fare">
    <button type="submit">Add Route</button>
</form>



</body>
</html>