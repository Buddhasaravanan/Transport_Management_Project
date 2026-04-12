package controller;

import java.io.IOException;

import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (name == null || email == null || password == null
                || name.isBlank() || email.isBlank() || password.isBlank()) {
            req.setAttribute("error", "All fields are required");
            req.getRequestDispatcher("/User/Sign-up.jsp").forward(req, res);
            return;
        }

        User u = new User();
        u.setName(name.trim());
        u.setEmail(email.trim());
        u.setPassword(password);

        AdminDAO dao = new AdminDAO();

        if (dao.register(u)) {
            req.setAttribute("success", "Account created! Please login.");
            req.getRequestDispatcher("/User/Login.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "Email already exists");
            req.getRequestDispatcher("/User/Sign-up.jsp").forward(req, res);
        }
    }
}