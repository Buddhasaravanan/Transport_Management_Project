<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Classic Bus</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Styles.css">
</head>
<body class="home-bg">

<jsp:include page="header.jsp"/>

<section class="hero">
    <div class="search-card">

        <form action="<%=request.getContextPath()%>/SearchServlet" method="get" class="search-form">
            <input type="text" name="from" placeholder="Going From" required>
            <span class="swap">⇄</span>
            <input type="text" name="to" placeholder="Going To" required>
            <input type="date" name="date" required>

            <button type="submit">Search</button>
        </form>
        

    </div>
</section>

<!-- STEPS -->
<section class="steps">
    <h2>Buy tickets in 3 easy steps</h2>

    <div class="step-grid">
        <div class="step">
            <h3>Search</h3>
            <p>Choose source, destination & date</p>
        </div>
        <div class="step">
            <h3>Select</h3>
            <p>Choose bus & seat</p>
        </div>
        <div class="step">
            <h3>Payment</h3>
            <p>Confirm booking</p>
        </div>
    </div>
</section>


</body>
</html>
