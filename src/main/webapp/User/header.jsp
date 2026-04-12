<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%
User u = (User) session.getAttribute("loggedUser");
%>
<div class="gb-header">
<div class="gb-header-inner">
    <div class="gb-logo">Green Bus</div>

    <nav class="gb-nav">
        <a href="<%=request.getContextPath()%>/User/home.jsp">Home</a>
        <a href="<%=request.getContextPath()%>/User/Contact.jsp">Contact</a>

        <% if (u != null) { %>
            <a href="<%=request.getContextPath()%>/MyTicketsServlet">My Tickets</a>
            <a class="btn-outline" href="<%=request.getContextPath()%>/LogoutServlet">Logout</a>
        <% } else { %>
            <a href="<%=request.getContextPath()%>/User/Login.jsp">Sign In</a>
            <a class="gb-signup" href="<%=request.getContextPath()%>/User/Sign-up.jsp">Sign Up</a>
        <% } %>
    </nav>
</div>
</div>