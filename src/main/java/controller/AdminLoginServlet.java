package controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminLoginServlet 
{
	
	@WebServlet("/Admin")
	public class AdminLogin extends HttpServlet {

	    protected void doPost(HttpServletRequest req, HttpServletResponse res)
	            throws IOException {

	        String u = req.getParameter("username");
	        String p = req.getParameter("password");

	        if ("admin".equals(u) && "admin".equals(p)) {
	            req.getSession().setAttribute("admin", true);
	            res.sendRedirect("Admin/Dashboard.jsp");
	        } else {
	            res.sendRedirect("User/Login.jsp?error=1");
	        }
	    }
	}


}
