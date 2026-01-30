<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin | Seats</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-container">

    <%@ include file="Sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="Admin-Header.jsp" %>

        <div class="content">
            <h2>Seat Management</h2>

            <div class="card">

                <div class="seat-filter">
                    <label>Schedule ID:</label>
                    <input type="text" placeholder="Enter Schedule ID">
                    <button class="btn">Search</button>
                </div>

                <div class="seat-grid">
                    
                    <a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=1">
    <div class="seat available">1</div>
</a>
                    <a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=2">
    <div class="seat available">2</div>
</a>
<a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=3">
    <div class="seat available">3</div>
</a>
<a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=4">
    <div class="seat available">4</div>
</a>
<a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=5">
    <div class="seat available">5</div>
</a>
<a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=6">
    <div class="seat available">6</div>
</a>
<a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=7">
    <div class="seat available">7</div>
</a>
<a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=8">
    <div class="seat available">8</div>
</a>
<a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=9">
    <div class="seat available">9</div>
</a>
<a href="<%=request.getContextPath()%>/ConfirmBookingServlet?scheduleId=1&seatNo=10">
    <div class="seat available">10</div>
</a>
                </div>

            </div>
        </div>
    </div>

</div>

</body>
</html>
