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
            <section class="page-hero">
                <div>
                    <h2>Schedule Planning</h2>
                    <p>Map buses to routes, set journey dates, and define departure and arrival timing for customer bookings.</p>
                </div>
                <span class="page-chip">Schedule admin</span>
            </section>

            <div class="card form-card">
                <div class="admin-section-head">
                    <div>
                        <span class="admin-kicker">Create schedule</span>
                        <h3>Add Schedule</h3>
                        <p>Use valid bus and route IDs from your admin inventory, then publish a new trip for booking.</p>
                    </div>
                </div>

                <form action="<%=request.getContextPath()%>/ScheduleServlet" method="post" class="admin-form">
                    <div class="form-grid">
                        <div>
                            <label for="busId">Bus ID</label>
                            <input id="busId" type="number" name="busId" required>
                        </div>
                        <div>
                            <label for="routeId">Route ID</label>
                            <input id="routeId" type="number" name="routeId" required>
                        </div>
                    </div>

                    <div class="form-grid">
                        <div>
                            <label for="journeyDate">Journey Date</label>
                            <input id="journeyDate" type="date" name="journeyDate" required>
                        </div>
                        <div>
                            <label for="departureTime">Departure Time</label>
                            <input id="departureTime" type="time" name="departureTime" required>
                        </div>
                    </div>

                    <div>
                        <label for="arrivalTime">Arrival Time</label>
                        <input id="arrivalTime" type="time" name="arrivalTime" required>
                    </div>

                    <button class="btn btn-primary" type="submit">Add Schedule</button>
                </form>
            </div>

        </div>
    </div>

</div>

</body>
</html>