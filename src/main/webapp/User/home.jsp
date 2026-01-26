<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Classic Bus – Book Tickets</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<div>

 <div class= "search Box">
 
 <form action="<%= request.getContextPath() %>/SearchServlet" method="get">
    <input type="text" name= "from" placeholder="Going From" required>
    <input type="text" name= "to" placeholder="Going To" required>
    <input type="date" name= "date" required> 
    <button type="submit">Search</button>
</form>
     
 </div>

</div>

</body>
</html>