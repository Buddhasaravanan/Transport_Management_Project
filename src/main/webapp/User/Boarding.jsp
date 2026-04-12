<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>

<%
    int scheduleId = (int) request.getAttribute("scheduleId");
    String seatNos = (String) request.getAttribute("seatNos");
    Double seatPrice = (Double) request.getAttribute("seatPrice");
%>

<!DOCTYPE html>
<html>
<head>
<title>Select Boarding & Dropping</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Passenger.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/BookingPages.css">
</head>

<body>
<jsp:include page="header.jsp" />

<div class="rb-steps">
    <span>1. Select seats</span>
    <span class="active">2. Board/Drop point</span>
    <span>3. Passenger Info</span>
    <span>4. Confirm booking</span>
</div>

<form action="PassengerServlet" method="post">
<input type="hidden" name="scheduleId" value="<%=scheduleId%>">
<input type="hidden" name="seatNos" value="<%=seatNos%>">
<input type="hidden" name="seatPrice" value="<%= seatPrice %>">

<div class="page-shell">
    <div class="page-head">
        <div>
            <p class="section-kicker">Pickup and drop selection</p>
            <h2>Choose boarding and dropping points</h2>
        </div>
        <span class="page-badge">Seats: <%= seatNos %></span>
    </div>

    <div class="grid-two">
        <div class="option-card">
            <h3>Boarding points</h3>
            <p class="muted-text">Select your preferred pickup point for this trip.</p>
            <div class="point-list">
            <%
                List<Object[]> boardingList = (List<Object[]>) request.getAttribute("boardingList");
                if(boardingList != null){
                    for(Object[] b : boardingList){
            %>
            <label class="point-item">
                <div class="time"><%= b[2] %></div>
                <div class="point-info">
                    <div class="point-name"><%= b[1] %></div>
                    <div class="point-address">Boarding Point</div>
                </div>
                <input type="radio" name="boardingPointId" value="<%= b[0] %>" required>
            </label>
            <%
                    }
                }
            %>
            </div>
        </div>

        <div class="option-card">
            <h3>Dropping points</h3>
            <p class="muted-text">Select where you want to get down.</p>
            <div class="point-list">
            <%
                List<Object[]> droppingList = (List<Object[]>) request.getAttribute("droppingList");
                if(droppingList != null){
                    for(Object[] d : droppingList){
            %>
            <label class="point-item">
                <div class="time"><%= d[2] %></div>
                <div class="point-info">
                    <div class="point-name"><%= d[1] %></div>
                    <div class="point-address">Dropping Point</div>
                </div>
                <input type="radio" name="droppingPointId" value="<%= d[0] %>" required>
            </label>
            <%
                    }
                }
            %>
            </div>
        </div>
    </div>

    <div class="summary-card-clean">
        <h3>Trip summary</h3>
        <div class="summary-row-clean"><span>Selected seats</span><strong><%= seatNos %></strong></div>
        <div class="summary-row-clean"><span>Fare / Seat</span><strong>₹ <%= seatPrice %></strong></div>
    </div>

    <div class="action-bar">
        <button type="submit" class="primary-btn">Continue to Passenger Details</button>
    </div>
</div>
</form>

<jsp:include page="Footer.jsp" />

</body>
</html>