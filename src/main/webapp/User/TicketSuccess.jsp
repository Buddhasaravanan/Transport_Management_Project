<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>

<%
User loggedUser = (User) session.getAttribute("loggedUser");
String bookingRef = (String) session.getAttribute("bookingRef");
String[] seatNos = (String[]) session.getAttribute("seatNos");
String boardingName = (String) session.getAttribute("boardingName");
String droppingName = (String) session.getAttribute("droppingName");
Double seatPrice = (Double) session.getAttribute("seatPrice");
int seatCount = seatNos == null ? 0 : seatNos.length;
%>

<!DOCTYPE html>
<html>
<head>
<title>Booking Confirmed</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/BookingPages.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="page-shell">
    <div class="success-card">
        <div class="success-icon">✓</div>
        <h2>Booking Confirmed</h2>
        <p class="success-note">Your bus ticket has been booked successfully. You can download the PDF ticket or return to the home page.</p>

        <div class="success-meta">
            <div class="meta-box">
                <span>Booking ID</span>
                <strong><%= bookingRef != null ? bookingRef : "N/A" %></strong>
            </div>
            <div class="meta-box">
                <span>Email</span>
                <strong><%= (loggedUser != null && loggedUser.getEmail() != null) ? loggedUser.getEmail() : "Guest Booking" %></strong>
            </div>
            <div class="meta-box">
                <span>Seats</span>
                <strong><%= seatNos != null ? String.join(", ", seatNos) : "N/A" %></strong>
            </div>
            <div class="meta-box">
                <span>Total Fare</span>
                <strong>₹ <%= seatPrice != null ? seatPrice * seatCount : 0 %></strong>
            </div>
            <div class="meta-box">
                <span>Boarding</span>
                <strong><%= boardingName != null ? boardingName : "-" %></strong>
            </div>
            <div class="meta-box">
                <span>Dropping</span>
                <strong><%= droppingName != null ? droppingName : "-" %></strong>
            </div>
        </div>

        <div class="action-bar" style="justify-content:center;">
            <a class="primary-btn" href="<%=request.getContextPath()%>/TicketPDFServlet">Download Ticket (PDF)</a>
            <a class="secondary-btn" href="<%=request.getContextPath()%>/User/home.jsp">Back to Home</a>
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>