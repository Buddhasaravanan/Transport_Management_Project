package controller;

import java.io.IOException;

import dao.BookingDAO;
import dao.SeatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ConfirmBookingServlet")
public class ConfirmBookingServlet extends HttpServlet {

    // STEP 1 — SHOW CONFIRM PAGE
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));
        int seatNo = Integer.parseInt(req.getParameter("seatNo"));

        req.setAttribute("scheduleId", scheduleId);
        req.setAttribute("seatNo", seatNo);

        req.getRequestDispatcher("/User/confirm-booking.jsp")
           .forward(req, res);
    }

    // STEP 2 — FINAL BOOKING (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));
        int seatNo = Integer.parseInt(req.getParameter("seatNo"));

        int userId = 1; // no user login

        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.book(scheduleId, userId, seatNo);

        SeatDAO seatDAO = new SeatDAO();
        seatDAO.markSeatBooked(scheduleId, seatNo);

        res.sendRedirect(req.getContextPath() + "/User/my-tickets.jsp");
    }
}
