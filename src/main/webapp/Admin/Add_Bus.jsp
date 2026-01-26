<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Bus</title>
</head>
<body>



<h2>Add New Bus</h2>

<form action="<%= request.getContextPath() %>/BusServlet" method="post">

    <label>Bus Number</label><br>
    <input type="text" name="busNumber" required><br><br>

    <label>Total Seats</label><br>
    <input type="number" name="totalSeats" required><br><br>

    <label>Bus Type</label><br>
    <select name="busType">
        <option value="AC">AC</option>
        <option value="Non-AC">Non-AC</option>
        <option value="Sleeper">Sleeper</option>
        <option value="Seater">Seater</option>
        <option value="Volvo">Volvo</option>
    </select><br><br>

    <button type="submit">Add Bus</button>

</form>

</body>
</html>