<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Seat</title>

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
    List<Integer> seats = (List<Integer>) request.getAttribute("seats");
    int scheduleId = (int) request.getAttribute("scheduleId");
%>

<form action="<%=request.getContextPath()%>/BookServlet" method="get">
    <input type="hidden" name="scheduleId" value="<%= scheduleId %>">
    <input type="hidden" name="seatNo" id="seatNo">

    <div>
        <% 
            if (seats != null && !seats.isEmpty()) {
                for (Integer seat : seats) {
        %>
            <div id="seat-<%=seat%>"
                 class="seat available"
                 onclick="selectSeat(<%=seat%>)">
                <%= seat %>
            </div>
        <% 
                }
            } else {
        %>
            <p>No seats available</p>
        <% } %>
    </div>

    <br><br>
    <button type="submit">Confirm Booking</button>
</form>

</body>
</html>
