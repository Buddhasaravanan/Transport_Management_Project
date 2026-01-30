<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Buses</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Styles.css"> 
</head>
<body>

<jsp:include page="header.jsp" />

<h2>Available Buses</h2>

<table border="1" cellpadding="5">
    <tr>
        <th>Bus Number</th>
        <th>From</th>
        <th>To</th>
        <th>Date</th>
        <th>Departure</th>
        <th>Arrival</th>
        <th>Fare</th>
        <th>Action</th>
    </tr>

<%
List<Object[]> list = (List<Object[]>) request.getAttribute("results");
%>

<% if (list != null && !list.isEmpty()) {
   for (Object[] row : list) {
%>
<tr>
    <td><%= row[0] %></td>
    <td><%= row[1] %></td>
    <td><%= row[2] %></td>
    <td><%= row[3] %></td>
    <td><%= row[4] %></td>
    <td><%= row[5] %></td>
    <td>₹ <%= row[6] %></td>
    <td>
        <a href="<%=request.getContextPath()%>/SeatServlet?scheduleId=<%=row[7]%>">
            Select Seat
        </a>
    </td>
</tr>
<%
   }
} else {
%>
<tr>
    <td colspan="8">No buses available</td>
</tr>
<% } %>


</table>

</body>
</html>
