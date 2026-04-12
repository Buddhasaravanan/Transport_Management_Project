package controller;

import java.io.IOException;
import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        AdminDAO dao = new AdminDAO();   // rename later to UserDAO if you want
        User user = dao.login(email, password);

        // ❌ INVALID LOGIN
        if (user == null) {
            req.setAttribute("error", "Invalid email or password");
            req.getRequestDispatcher("/User/Login.jsp").forward(req, res);
            return;
        }

        // ✅ VALID LOGIN
        HttpSession session = req.getSession();
        session.setAttribute("loggedUser", user);

        // ✅ Redirect-after-login (seat booking case)
        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
        if (redirectUrl != null) {
            session.removeAttribute("redirectAfterLogin");
            res.sendRedirect(redirectUrl);
            return;
        }

        // ✅ Normal role-based redirect
        if ("ADMIN".equals(user.getRole())) {
            res.sendRedirect(req.getContextPath() + "/Admin/Dashboard.jsp");
        } else {
            res.sendRedirect(req.getContextPath() + "/User/home.jsp");
        }
    }
}