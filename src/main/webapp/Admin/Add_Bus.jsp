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

    
    <div class="card">
        <h3>Add Bus</h3>

        <form action="<%=request.getContextPath()%>/BusServlet" method="post">
            <input type="text" name="busNumber" placeholder="Bus Number" required>
            <select name="busType">
                <option value="AC">AC</option>
                <option value="NON-AC">Non AC</option>
                <option value="VOLVO">Volvo</option>
                <option value="NON-AC SLEEPER">Non Ac Sleeper</option>
                <option value="AC SLEEPER">AC Sleeper</option>
            </select>
            <input type="number" name="totalSeats" placeholder="Total Seats" required>
            <button type="submit">Add Bus</button>
        </form>
    </div>

    <!-- BUS LIST TABLE -->
    <div class="card">
        <h3>Existing Buses</h3>

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
