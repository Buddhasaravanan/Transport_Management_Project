<%@ page import="java.util.*" %>

<h2>My Tickets</h2>

<table border="1">
<tr>
 <th>Bus</th><th>From</th><th>To</th><th>Date</th>
 <th>Departure</th><th>Arrival</th>
 <th>Seat</th><th>Price</th><th>Status</th>
</tr>

<%
List<Object[]> list = (List<Object[]>) request.getAttribute("tickets");

if (list != null && !list.isEmpty()) {
    for (Object[] row : list) {
%>
<tr>
 <td><%= row[0] %></td>
 <td><%= row[1] %></td>
 <td><%= row[2] %></td>
 <td><%= row[3] %></td>
 <td><%= row[4] %></td>
 <td><%= row[5] %></td>
 <td><%= row[6] %></td>
 <td><%= row[7] %></td>
 <td><%= row[8] %></td>
</tr>
<%
    }
} else {
%>
<tr>
 <td colspan="9">No bookings found</td>
</tr>
<%
}
%>
</table>
