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

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));
        int seatNo = Integer.parseInt(req.getParameter("seatNo"));

        int userId = 1; // HARD CODE (no user login as you said)

        // 1️⃣ BOOK SEAT
        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.book(scheduleId, userId, seatNo);

        // 2️⃣ MARK SEAT AS BOOKED
        SeatDAO seatDAO = new SeatDAO();
        seatDAO.markSeatBooked(scheduleId, seatNo);

        // 3️⃣ REDIRECT TO MY TICKETS
        res.sendRedirect(req.getContextPath() + "/MyTicketsServlet");
    }
}
