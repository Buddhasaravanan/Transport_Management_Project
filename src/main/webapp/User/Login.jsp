<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login | GreenBus</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/auth.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="auth-wrapper">
    <div class="auth-card">

        <h2>Login to continue</h2>

        <form action="<%=request.getContextPath()%>/LoginServlet" method="post">
            <input type="email" name="email" placeholder="Email address" required>
            <input type="password" name="password" placeholder="Password" required>

            <button type="submit">Login</button>
        </form>

        <% if (request.getAttribute("error") != null) { %>
            <p class="auth-error"><%= request.getAttribute("error") %></p>
        <% } %>

        <p class="auth-footer">
            New to GreenBus?
            <a href="<%=request.getContextPath()%>/User/Sign-up.jsp">Create account</a>
        </p>

    </div>
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>