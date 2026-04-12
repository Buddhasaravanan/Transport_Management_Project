<%@ page import="java.util.List" %>
<%@ page import="dao.RouteDAO" %>
<%@ page import="model.Route" %>

<%@ include file="Admin-Header.jsp" %>
<%@ include file="Sidebar.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Dropping Point</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>

<body>

<div class="admin-content">
    <section class="page-hero">
        <div>
            <h2>Dropping Point Management</h2>
            <p>Configure destination stops and drop timings so every route has clear arrival point options for passengers.</p>
        </div>
        <span class="page-chip">Dropping admin</span>
    </section>

    <div class="card form-card">
        <div class="admin-section-head">
            <div>
                <span class="admin-kicker">Create arrival stop</span>
                <h3>Add Dropping Point</h3>
                <p>Map each route to a valid destination stop and a scheduled dropping time.</p>
            </div>
        </div>

        <form action="<%=request.getContextPath()%>/DroppingPointServlet" method="post" class="admin-form">
            <div>
                <label for="routeId">Select Route</label>
                <select id="routeId" name="routeId" required>
                    <option value="">-- Select Route --</option>
                    <%
                        RouteDAO rdao = new RouteDAO();
                        List<Route> routes = rdao.getAllRoutes();
                        for (Route r : routes) {
                    %>
                    <option value="<%=r.getRouteId()%>">
                        <%=r.getSource()%> → <%=r.getDestination()%>
                    </option>
                    <% } %>
                </select>
            </div>

            <div class="form-grid">
                <div>
                    <label for="location">Dropping Location</label>
                    <input id="location" type="text" name="location" placeholder="Eg: Madurai Periyar Bus Stand" required>
                </div>
                <div>
                    <label for="time">Dropping Time</label>
                    <input id="time" type="time" name="time" required>
                </div>
            </div>

            <button type="submit">Add Dropping Point</button>
        </form>
    </div>

</div>

</body>
</html>