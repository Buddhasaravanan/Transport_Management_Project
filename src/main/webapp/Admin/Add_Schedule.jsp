<!DOCTYPE html>
<html>
<head>
<title>Add Schedule</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-container">

    <%@ include file="Sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="Admin-Header.jsp" %>

        <div class="content">
            <h2>Add Schedule</h2>

            <div class="card form-card">
                <form action="<%=request.getContextPath()%>/ScheduleServlet" method="post">

                    <label>Bus ID</label>
                    <input type="number" name="busId" required>

                    <label>Route ID</label>
                    <input type="number" name="routeId" required>

                    <label>Journey Date</label>
                    <input type="date" name="journeyDate" required>

                    <label>Departure Time</label>
                    <input type="time" name="departureTime" required>

                    <label>Arrival Time</label>
                    <input type="time" name="arrivalTime" required>

                    <button class="btn btn-primary">Add Schedule</button>
                </form>
            </div>

        </div>
    </div>

</div>

</body>
</html>
