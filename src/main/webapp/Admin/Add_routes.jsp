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

    <section class="page-hero">
        <div>
            <h2>Route Network Management</h2>
            <p>Create city-to-city routes with distance and fare details used across user search and booking schedules.</p>
        </div>
        <span class="page-chip">Route admin</span>
    </section>

    <div class="card form-card">
        <div class="admin-section-head">
            <div>
                <span class="admin-kicker">Create route</span>
                <h3>Add Route</h3>
                <p>Define a travel corridor between cities and keep pricing aligned with your booking flow.</p>
            </div>
        </div>

        <form action="<%=request.getContextPath()%>/RouteServlet" method="post" class="admin-form">
            <div class="form-grid">
                <div>
                    <label for="source">From City</label>
                    <input id="source" type="text" name="source" placeholder="Chennai" required>
                </div>
                <div>
                    <label for="destination">To City</label>
                    <input id="destination" type="text" name="destination" placeholder="Madurai" required>
                </div>
            </div>
            <div class="form-grid">
                <div>
                    <label for="distanceKm">Distance (KM)</label>
                    <input id="distanceKm" type="number" step="0.1" name="distanceKm" placeholder="462.5" required>
                </div>
                <div>
                    <label for="fare">Fare Amount</label>
                    <input id="fare" type="number" step="0.1" name="fare" placeholder="850" required>
                </div>
            </div>
            <button type="submit">Add Route</button>
        </form>
    </div>

    <div class="card table-card">
        <div class="admin-section-head">
            <div>
                <span class="admin-kicker">Route directory</span>
                <h3>Existing Routes</h3>
                <p>These routes are available to schedules, boarding points, and the customer search experience.</p>
            </div>
        </div>

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