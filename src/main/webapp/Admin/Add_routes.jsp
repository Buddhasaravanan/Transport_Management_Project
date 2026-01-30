<%@ page import="java.util.List" %>
<%@ page import="model.Route" %>
<%@ page import="dao.RouteDAO" %>

<%@ include file="Admin-Header.jsp" %>
<%@ include file="Sidebar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Routes</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-content">

    <!-- ADD ROUTE -->
    <div class="card">
        <h3>Add Route</h3>

        <form action="<%=request.getContextPath()%>/RouteServlet" method="post" class="admin-form">
            <input type="text" name="source" placeholder="From City" required>
            <input type="text" name="destination" placeholder="To City" required>
            <input type="number" step="0.1" name="distanceKm" placeholder="Distance (KM)" required>
            <input type="number" step="0.1" name="fare" placeholder="Fare Amount" required>

            <button type="submit">Add Route</button>
        </form>
    </div>

    <!-- ROUTE LIST -->
    <div class="card">
        <h3>Existing Routes</h3>

        <table class="admin-table">
            <tr>
                <th>ID</th>
                <th>From</th>
                <th>To</th>
                <th>Distance (KM)</th>
                <th>Fare</th>
            </tr>

            <%
                RouteDAO dao = new RouteDAO();
                List<Route> routes = dao.getAllRoutes();
                for (Route r : routes) {
            %>
            <tr>
                <td><%= r.getRouteId() %></td>
                <td><%= r.getSource() %></td>
                <td><%= r.getDestination() %></td>
                <td><%= r.getDistance() %></td>
                <td><%= r.getFare() %></td>
            </tr>
            <% } %>
        </table>
    </div>

</div>

</body>
</html>
