package controller;

import java.io.IOException;

import dao.BookingDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));
        double price = Double.parseDouble(req.getParameter("price"));

        int userId = 1; 

        BookingDAO dao = new BookingDAO();
        dao.book(scheduleId, userId, price);

        res.sendRedirect(req.getContextPath() + "/MyTickets");
    }
}
