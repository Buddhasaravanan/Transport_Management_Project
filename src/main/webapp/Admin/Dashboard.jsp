<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-container">

    <jsp:include page="Admin-Header.jsp" />
    <jsp:include page="Sidebar.jsp" />

    <div class="main-content">
        <div class="content">
            <section class="page-hero">
                <div>
                    <h2>GreenBus Control Dashboard</h2>
                    <p>Manage schedules, route inventory, operational buses, and booking performance from one clean admin workspace.</p>
                </div>
                <span class="page-chip">Live admin overview</span>
            </section>

            <div class="card-grid">
                <div class="card-dashboard">Total Users <strong>256</strong></div>
                <div class="card-dashboard">Total Bookings <strong>1050</strong></div>
                <div class="card-dashboard">Cancelled <strong>98</strong></div>
                <div class="card-dashboard">Earnings <strong>₹50,000</strong></div>
                <div class="card-dashboard">Total Bus <strong>15</strong></div>
                <div class="card-dashboard">Routes <strong>8</strong></div>
            </div>

            <div class="card">
                <div class="admin-section-head">
                    <div>
                        <span class="admin-kicker">Operations snapshot</span>
                        <h3>Admin quick actions</h3>
                        <p>Use the sidebar to add new buses, routes, and schedules, then review bookings and seat availability in real time.</p>
                    </div>
                    <span class="page-chip" style="color:#fff;">Bookings + schedules</span>
                </div>
                <div class="form-grid">
                    <div class="card-dashboard">Today departures <strong>24</strong></div>
                    <div class="card-dashboard">Active city pairs <strong>12</strong></div>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>