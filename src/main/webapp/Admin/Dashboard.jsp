<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-container">

    <jsp:include page="Sidebar.jsp" />

    <div class="main-content">

        <jsp:include page="Admin-Header.jsp" />

        <div class="content">
            <h2>Dashboard</h2>

            
            <div class="card-grid">
                <div class="card">Total Users <strong>256</strong></div>
                <div class="card">Total Bookings <strong>1050</strong></div>
                <div class="card">Cancelled <strong>98</strong></div>
                <div class="card">Earnings <strong>₹50,000</strong></div>
                <div class="card">Total Bus <strong>15</strong></div>
                <div class="card">Routes <strong>8</strong></div>
            </div>

        </div>
    </div>

</div>

</body>
</html>
