<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dao.BookingDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin | Bookings</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>
<%
BookingDAO bookingDAO = new BookingDAO();
List<Object[]> bookings = bookingDAO.getAllBookingsForAdmin();
%>
<div class="admin-container">

    <%@ include file="Sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="Admin-Header.jsp" %>

        <div class="content">
            <section class="page-hero">
                <div>
                    <h2>Booking Overview</h2>
                    <p>Track customer reservations, cancellation status, seats, and route demand from the admin panel.</p>
                </div>
                <span class="page-chip">Booking admin</span>
            </section>

            <div class="card table-card">
                <div class="admin-section-head">
                    <div>
                        <span class="admin-kicker">Recent reservations</span>
                        <h3>Bookings</h3>
                        <p>Live booking records from your database are shown below for quick operational review.</p>
                    </div>
                </div>

<%
if (bookings == null || bookings.isEmpty()) {
%>
                <p style="color:#667a6d; line-height:1.7;">No bookings are available yet. Once users confirm seats, their reservations will appear here automatically.</p>
<%
} else {
%>
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Bus</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Date</th>
                            <th>Seat</th>
                            <th>Price</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
<%
    for (Object[] row : bookings) {
        String status = row[7] == null ? "UNKNOWN" : row[7].toString();
        String statusClass = "CANCELLED".equalsIgnoreCase(status) ? "cancelled" : "booked";
%>
                        <tr>
                            <td><%= row[0] %></td>
                            <td><%= row[1] %></td>
                            <td><%= row[2] %></td>
                            <td><%= row[3] %></td>
                            <td><%= row[4] %></td>
                            <td><%= row[5] %></td>
                            <td>₹<%= row[6] %></td>
                            <td class="status <%= statusClass %>"><%= status %></td>
                        </tr>
<%
    }
%>
                    </tbody>
                </table>
<%
}
%>
            </div>

        </div>
    </div>

</div>

</body>
</html>