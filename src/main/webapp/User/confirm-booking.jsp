<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirm Booking</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/style.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="card">
    <h2>Confirm Booking</h2>

    <p><strong>Schedule ID:</strong> ${scheduleId}</p>
    <p><strong>Seat Number:</strong> ${seatNo}</p>

    <form action="<%=request.getContextPath()%>/ConfirmBookingServlet" method="post">
        <input type="hidden" name="scheduleId" value="${scheduleId}">
        <input type="hidden" name="seatNo" value="${seatNo}">
        <button type="submit">Confirm Booking</button>
    </form>
</div>

</body>
</html>
