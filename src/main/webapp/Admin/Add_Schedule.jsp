<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Schedule</title>
</head>
<body>

<form action="<%=request.getContextPath()%>/ScheduleServlet" method="post">

    <input type="number" name="busId" placeholder="Bus ID" required>
    <input type="number" name="routeId" placeholder="Route ID" required>

    <input type="time" name="departureTime" required>
    <input type="time" name="arrivalTime" required>

    <input type="date" name="journeyDate" required>

    <button type="submit">Add Schedule</button>
</form>


</body>
</html>