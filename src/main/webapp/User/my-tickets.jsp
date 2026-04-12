<%@ page import="java.util.List" %>
<%@ include file="user-auth.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Tickets</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/BookingPages.css">
</head>

<body>
<jsp:include page="header.jsp" />

<div class="page-shell">
    <div class="page-head">
        <div>
            <p class="section-kicker">Booking history</p>
            <h2>My Tickets</h2>
        </div>
        <span class="page-badge">Manage confirmed and cancelled bookings</span>
    </div>

    <%
    List<Object[]> tickets = (List<Object[]>) request.getAttribute("tickets");

    if (tickets != null && !tickets.isEmpty()) {
    %>
    <div class="table-shell">
        <table>
            <tr>
                <th>Bus</th>
                <th>From</th>
                <th>To</th>
                <th>Date</th>
                <th>Departure</th>
                <th>Arrival</th>
                <th>Seat</th>
                <th>Price</th>
                <th>Status</th>
                <th>Action</th>
            </tr>

            <% for (Object[] t : tickets) { %>
            <tr>
                <td><%= t[0] %></td>
                <td><%= t[1] %></td>
                <td><%= t[2] %></td>
                <td><%= t[3] %></td>
                <td><%= t[4] %></td>
                <td><%= t[5] %></td>
                <td><%= t[6] %></td>
                <td>₹ <%= t[7] %></td>
                <td>
                    <% if ("CONFIRMED".equals(t[8])) { %>
                        <span class="status-pill">CONFIRMED</span>
                    <% } else { %>
                        <span class="meta-pill"><%= t[8] %></span>
                    <% } %>
                </td>
                <td class="ticket-actions">
                    <% if ("CONFIRMED".equals(t[8])) { %>
                    <form action="<%=request.getContextPath()%>/CancelBookingServlet" method="post">
                        <input type="hidden" name="bookingId" value="<%= t[9] %>">
                        <input type="hidden" name="scheduleId" value="<%= t[10] %>">
                        <input type="hidden" name="seatNo" value="<%= t[6] %>">
                        <button class="danger-btn" type="submit">Cancel</button>
                    </form>
                    <% } else { %>
                        <span class="meta-pill">Cancelled</span>
                    <% } %>
                </td>
            </tr>
            <% } %>
        </table>
    </div>
    <% } else { %>
    <div class="table-shell empty-state">
        <h3>No bookings found</h3>
        <p>You haven't booked any tickets yet. Search buses and start your next trip.</p>
        <div class="action-bar" style="justify-content:center;">
            <a class="primary-btn" href="<%=request.getContextPath()%>/User/home.jsp">Search Buses</a>
        </div>
    </div>
    <% } %>
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>