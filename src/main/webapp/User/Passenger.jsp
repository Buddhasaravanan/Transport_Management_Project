<%@ page contentType="text/html;charset=UTF-8" %>

<%
String[] seats = (String[]) session.getAttribute("seatNos");
Integer scheduleId = (Integer) session.getAttribute("scheduleId");
Integer boardingId = (Integer) session.getAttribute("boardingId");
Integer droppingId = (Integer) session.getAttribute("droppingId");
String boardingName = (String) session.getAttribute("boardingName");
String droppingName = (String) session.getAttribute("droppingName");
Double seatPrice = (Double) session.getAttribute("seatPrice");

if (scheduleId == null || seats == null || seats.length == 0) {
%>
<h2 style="color:red">Booking data missing. Please restart booking.</h2>
<%
return;
}
%>

<!DOCTYPE html>
<html>
<head>
<title>Passenger Details</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Passenger.css">
</head>

<body>

<jsp:include page="header.jsp" />

<div class="rb-steps">
    <span>1. Select seats</span>
    <span>2. Board/Drop point</span>
    <span class="active">3. Passenger Info</span>
    <span>4. Confirm booking</span>
</div>

<div class="rb-page">

<div class="rb-left">
<form action="<%=request.getContextPath()%>/ConfirmBookingServlet" method="post">

<input type="hidden" name="scheduleId" value="<%= scheduleId != null ? scheduleId : "" %>">
<input type="hidden" name="seatPrice" value="<%=seatPrice%>">
<input type="hidden" name="boardingPointId" value="<%=boardingId%>">
<input type="hidden" name="droppingPointId" value="<%=droppingId%>">

<div class="card intro-card">
    <p class="section-kicker">Passenger and contact details</p>
    <h2>Enter details for your selected seats</h2>
    <p class="intro-text">We'll use these details for ticket confirmation, boarding verification, and your PDF bus ticket.</p>
</div>

<div class="card">
    <h3>Contact Details</h3>
    <div class="form-grid two-col">
        <input type="text" name="phone" placeholder="Mobile Number" required>
        <input type="email" name="email" placeholder="Email ID" required>
    </div>
</div>

<div class="card">
    <h3>Passenger Details</h3>

   <% for (int i = 0; i < seats.length; i++) { String seat = seats[i]; %>
<div class="passenger-box">

    <div class="passenger-head">
        <h4>Passenger for seat <%=seat%></h4>
        <span class="seat-tag"><%=seat%></span>
    </div>

    <input type="hidden" name="seatNo" value="<%=seat%>">

    <div class="form-grid three-col">
        <input type="text" name="name" placeholder="Passenger Name" required>
        <input type="number" name="age" placeholder="Age" required min="1">

        <div class="gender-group">
            <label>
                <input type="radio" name="gender_<%=i%>" value="Male" required> Male
            </label>
            <label>
                <input type="radio" name="gender_<%=i%>" value="Female"> Female
            </label>
        </div>
    </div>

</div>
<% } %>
</div>

<button class="continue-btn" type="submit">
    Continue to Book Ticket
</button>

</form>
</div>

<aside class="rb-right">
    <div class="card summary">
        <h3>Trip Summary</h3>

        <div class="summary-row stacked-row">
            <span>Seats</span>
            <div class="seat-list">
                <% for (String s : seats) { %>
                    <span class="seat-tag"><%= s %></span>
                <% } %>
            </div>
        </div>

        <div class="summary-row">
            <span>Boarding</span>
            <span class="summary-value"><%= boardingName != null ? boardingName : "Not selected" %></span>
        </div>

        <div class="summary-row">
            <span>Dropping</span>
            <span class="summary-value"><%= droppingName != null ? droppingName : "Not selected" %></span>
        </div>

        <div class="fare">
            Total Fare: ₹ <%= seats.length * seatPrice %>
        </div>
    </div>
</aside>

</div>

<jsp:include page="Footer.jsp" />

</body>
</html>