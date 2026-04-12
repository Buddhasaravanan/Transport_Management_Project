package controller;

import java.io.IOException;
import java.util.List;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/MyTicketsServlet")
public class MyTicketsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 🔐 LOGIN CHECK
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/User/Login.jsp");
            return;
        }

        BookingDAO dao = new BookingDAO();
        List<Object[]> tickets = dao.getUserTickets(user.getUserId());

        req.setAttribute("tickets", tickets);
        req.getRequestDispatcher("/User/my-tickets.jsp").forward(req, res);
    }
}
