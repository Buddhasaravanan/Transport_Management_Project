<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up | GreenBus</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/auth.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="auth-wrapper">
    <div class="auth-card">

        <h2>Create your account</h2>

        <form action="<%=request.getContextPath()%>/SignupServlet" method="post">
            <input type="text" name="name" placeholder="Full Name" required>
            <input type="email" name="email" placeholder="Email address" required>
            <input type="password" name="password" placeholder="Password" required>

            <button type="submit">Sign Up</button>
        </form>

        <% if (request.getAttribute("error") != null) { %>
            <p class="auth-error"><%= request.getAttribute("error") %></p>
        <% } %>

        <% if (request.getAttribute("success") != null) { %>
            <p class="auth-success"><%= request.getAttribute("success") %></p>
        <% } %>

        <p class="auth-footer">
            Already have an account?
            <a href="<%=request.getContextPath()%>/User/Login.jsp">Login</a>
        </p>

    </div>
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>