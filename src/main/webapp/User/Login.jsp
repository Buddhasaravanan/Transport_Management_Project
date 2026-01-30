<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css">

<style>
.login-container {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, #1faa59, #3ccf7f);
}

.login-box {
    background: white;
    padding: 30px;
    width: 350px;
    border-radius: 12px;
    box-shadow: 0 10px 30px rgba(0,0,0,0.2);
}

.login-box h2 {
    text-align: center;
    margin-bottom: 20px;
    color: #1faa59;
}

.login-box input {
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    border-radius: 6px;
    border: 1px solid #ccc;
}

.login-box button {
    width: 100%;
    padding: 10px;
    margin-top: 15px;
    background: #1faa59;
    color: white;
    border: none;
    border-radius: 6px;
    font-size: 16px;
    cursor: pointer;
}

.login-box button:hover {
    background: #148947;
}

.error {
    color: red;
    text-align: center;
    margin-top: 10px;
}
</style>

</head>
<body>

<div class="login-container">
    <div class="login-box">

        <h2>Admin Login</h2>

        <form action="<%=request.getContextPath()%>/AdminLoginServlet" method="post">
            <input type="email" name="email" placeholder="Admin Email" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>

        <% if (request.getParameter("error") != null) { %>
            <div class="error">Invalid admin credentials</div>
        <% } %>

    </div>
</div>

</body>
</html>
