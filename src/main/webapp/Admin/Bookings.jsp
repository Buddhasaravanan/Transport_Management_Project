<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin | Bookings</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Admin.css">
</head>
<body>

<div class="admin-container">

    <%@ include file="Sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="Admin-Header.jsp" %>

        <div class="content">
            <h2>Bookings</h2>

            <div class="card">
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
                        <tr>
                            <td>1</td>
                            <td>TN 01 AB 1234</td>
                            <td>Chennai</td>
                            <td>Madurai</td>
                            <td>2026-02-01</td>
                            <td>12</td>
                            <td>₹750</td>
                            <td class="status booked">BOOKED</td>
                        </tr>

                        <tr>
                            <td>2</td>
                            <td>TN 09 XY 5678</td>
                            <td>Coimbatore</td>
                            <td>Bangalore</td>
                            <td>2026-02-03</td>
                            <td>8</td>
                            <td>₹900</td>
                            <td class="status cancelled">CANCELLED</td>
                        </tr>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

</div>

</body>
</html>
