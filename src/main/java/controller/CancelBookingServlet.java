package controller;

import java.io.IOException;

import dao.BookingDAO;
import dao.SeatDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/User/Login.jsp");
            return;
        }

        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));
        int seatNo = Integer.parseInt(req.getParameter("seatNo"));

        BookingDAO bookingDAO = new BookingDAO();
        boolean cancelled = bookingDAO.cancelBooking(bookingId, user.getUserId());

        if (cancelled) {
            SeatDAO seatDAO = new SeatDAO();
            seatDAO.markSeatAvailable(scheduleId, seatNo);
        }

        res.sendRedirect(req.getContextPath() + "/MyTicketsServlet");
    }
}
