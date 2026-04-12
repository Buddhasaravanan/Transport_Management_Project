<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dao.RouteDAO" %>
<%@ page import="model.Route" %>
<%@ include file="Admin-Header.jsp" %>
<%@ include file="Sidebar.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Boarding Point</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-content">
    <section class="page-hero">
        <div>
            <h2>Boarding Point Management</h2>
            <p>Assign pickup locations and boarding times to routes so passengers can select clear boarding options during booking.</p>
        </div>
        <span class="page-chip">Boarding admin</span>
    </section>

    <div class="card form-card">
        <div class="admin-section-head">
            <div>
                <span class="admin-kicker">Create boarding stop</span>
                <h3>Add Boarding Point</h3>
                <p>Select a route, add the pickup location, and define the boarding time for the trip.</p>
            </div>
        </div>

        <form action="<%=request.getContextPath()%>/BoardingPointServlet" method="post" class="admin-form">
            <div>
                <label for="routeId">Select Route</label>
                <select id="routeId" name="routeId" required>
                    <option value="">-- Select Route --</option>
<%
RouteDAO rdao = new RouteDAO();
List<Route> routes = rdao.getAllRoutes();
for(Route r : routes){
%>
                    <option value="<%=r.getRouteId()%>">
                        <%=r.getSource()%> → <%=r.getDestination()%>
                    </option>
<% } %>
                </select>
            </div>

            <div class="form-grid">
                <div>
                    <label for="location">Boarding Location</label>
                    <input id="location" type="text" name="location" placeholder="Eg: Chennai CMBT" required>
                </div>
                <div>
                    <label for="time">Boarding Time</label>
                    <input id="time" type="time" name="time" required>
                </div>
            </div>

            <button type="submit">Add Boarding Point</button>
        </form>
    </div>
</div>

</body>
</html>