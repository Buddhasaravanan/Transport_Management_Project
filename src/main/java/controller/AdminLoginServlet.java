package controller;

import java.io.IOException;
import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        AdminDAO dao = new AdminDAO();
        int adminId = dao.validateAdmin(email, password);

        if (adminId > 0) {
            res.sendRedirect("Admin/Dashboard.jsp");
        } else {
            req.setAttribute("error", "Invalid admin login");
            req.getRequestDispatcher("User/Login.jsp").forward(req, res);
        }
    }
}
