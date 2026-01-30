<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Seat</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Styles.css">

    <style>
        .seat {
            width: 40px;
            height: 40px;
            margin: 5px;
            display: inline-block;
            text-align: center;
            line-height: 40px;
            border: 1px solid #333;
            cursor: pointer;
        }
        .available { background-color: #90ee90; }
        .booked { background-color: #ff7f7f; cursor: not-allowed; }
        .selected { background-color: #ffa500; }
    </style>

    <script>
        function selectSeat(seatNo) {
            document.getElementById("seatNo").value = seatNo;

            let seats = document.getElementsByClassName("seat");
            for (let i = 0; i < seats.length; i++) {
                seats[i].classList.remove("selected");
            }
            document.getElementById("seat-" + seatNo).classList.add("selected");
        }
    </script>
</head>
<body>

<h2>Select Your Seat</h2>

<%
List<String[]> seats = (List<String[]>) request.getAttribute("seats");
    int scheduleId = (int) request.getAttribute("scheduleId");
%>

<form action="<%=request.getContextPath()%>/ConfirmBookingServlet" method="get">
    <input type="hidden" name="scheduleId" value="<%= scheduleId %>">
    <input type="hidden" name="seatNo" id="seatNo">

    <div>
<%
if (seats != null && !seats.isEmpty()) {
    for (String[] s : seats) {
        String seatNo = s[0];
        String status = s[1];

        if ("BOOKED".equals(status)) {
%>
    <div class="seat booked"><%= seatNo %></div>
<%
        } else {
%>
    <div id="seat-<%=seatNo%>"
         class="seat available"
         onclick="selectSeat(<%=seatNo%>)">
        <%= seatNo %>
    </div>
<%
        }
    }
} else {
%>
    <p>No seats available</p>
<%
}
%>
</div>


    <br><br>
    <button type="submit">Confirm Booking</button>
</form>

</body>
</html>
