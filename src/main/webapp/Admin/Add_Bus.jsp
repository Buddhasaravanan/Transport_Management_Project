<%@ page import="java.util.List" %>
<%@ page import="model.Bus" %>
<%@ page import="dao.BusDAO" %>

<%@ include file="Admin-Header.jsp" %>
<%@ include file="Sidebar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Bus</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>
<div class="admin-content">

    <section class="page-hero">
        <div>
            <h2>Bus Fleet Management</h2>
            <p>Add new buses to your fleet and review the current inventory used across routes and schedules.</p>
        </div>
        <span class="page-chip">Fleet admin</span>
    </section>

    <div class="card form-card">
        <div class="admin-section-head">
            <div>
                <span class="admin-kicker">Create fleet entry</span>
                <h3>Add Bus</h3>
                <p>Capture the bus number, service type, and total seat capacity used during booking.</p>
            </div>
        </div>

        <form action="<%=request.getContextPath()%>/BusServlet" method="post" class="admin-form">
            <div class="form-grid">
                <div>
                    <label for="busNumber">Bus Number</label>
                    <input id="busNumber" type="text" name="busNumber" placeholder="TN 01 AB 2345" required>
                </div>
                <div>
                    <label for="busType">Bus Type</label>
                    <select id="busType" name="busType">
                        <option value="AC">AC</option>
                        <option value="NON-AC">Non AC</option>
                        <option value="VOLVO">Volvo</option>
                        <option value="NON-AC SLEEPER">Non Ac Sleeper</option>
                        <option value="AC SLEEPER">AC Sleeper</option>
                    </select>
                </div>
            </div>
            <div>
                <label for="totalSeats">Total Seats</label>
                <input id="totalSeats" type="number" name="totalSeats" placeholder="40" required>
            </div>
            <button type="submit">Add Bus</button>
        </form>
    </div>

    <div class="card table-card">
        <div class="admin-section-head">
            <div>
                <span class="admin-kicker">Fleet overview</span>
                <h3>Existing Buses</h3>
                <p>Current bus entries available for schedule creation and route assignment.</p>
            </div>
        </div>

        <table class="admin-table">
            <tr>
                <th>ID</th>
                <th>Bus No</th>
                <th>Type</th>
                <th>Seats</th>
            </tr>

            <%
                BusDAO dao = new BusDAO();
                List<Bus> buses = dao.getAllBuses();
                for (Bus b : buses) {
            %>
            <tr>
                <td><%= b.getBusId() %></td>
                <td><%= b.getBusnumber() %></td>
                <td><%= b.getBusType() %></td>
                <td><%= b.getTotalSeats() %></td>
            </tr>
            <% } %>
        </table>
    </div>

</div>

</body>
</html>