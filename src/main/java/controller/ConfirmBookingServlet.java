package controller;

import java.io.IOException;
import java.util.UUID;

import dao.BookingDAO;
import dao.SeatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;

@WebServlet("/ConfirmBookingServlet")
public class ConfirmBookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/User/Login.jsp");
            return;
        }

        HttpSession session = req.getSession();

        req.setAttribute("scheduleId", session.getAttribute("scheduleId"));
        req.setAttribute("seatNos", session.getAttribute("seatNos"));
        req.setAttribute("boardingName", session.getAttribute("boardingName"));
        req.setAttribute("droppingName", session.getAttribute("droppingName"));
        req.setAttribute("seatPrice", session.getAttribute("seatPrice"));
        req.setAttribute("names", session.getAttribute("names"));
        req.setAttribute("ages", session.getAttribute("ages"));
        req.setAttribute("genders", session.getAttribute("genders"));

        req.getRequestDispatcher("/User/confirm-booking.jsp")
           .forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/User/Login.jsp");
            return;
        }

        String[] names = req.getParameterValues("name");

        if (names != null) {
            String[] ages = req.getParameterValues("age");
            String[] seatNos = (String[]) session.getAttribute("seatNos");

            if (seatNos == null || ages == null || names.length != seatNos.length || ages.length != seatNos.length) {
                res.sendRedirect(req.getContextPath() + "/User/home.jsp");
                return;
            }

            String[] genders = new String[seatNos.length];
            for (int i = 0; i < seatNos.length; i++) {
                genders[i] = req.getParameter("gender_" + i);
                if (genders[i] == null || genders[i].isBlank()) {
                    res.sendRedirect(req.getContextPath() + "/User/Passenger.jsp");
                    return;
                }
            }

            session.setAttribute("names", names);
            session.setAttribute("ages", ages);
            session.setAttribute("genders", genders);
            session.setAttribute("phone", req.getParameter("phone"));
            session.setAttribute("email", req.getParameter("email"));

            res.sendRedirect(req.getContextPath() + "/ConfirmBookingServlet");
            return;
        }

        Integer scheduleId = (Integer) session.getAttribute("scheduleId");
        String[] seatNos = (String[]) session.getAttribute("seatNos");
        Double seatPrice = (Double) session.getAttribute("seatPrice");
        String[] savedNames = (String[]) session.getAttribute("names");
        String[] savedAges = (String[]) session.getAttribute("ages");
        String[] savedGenders = (String[]) session.getAttribute("genders");

        if (scheduleId == null || seatNos == null || seatPrice == null || savedNames == null || savedAges == null || savedGenders == null) {
            res.sendRedirect(req.getContextPath() + "/User/home.jsp");
            return;
        }

        BookingDAO bookingDAO = new BookingDAO();
        SeatDAO seatDAO = new SeatDAO();

        for (String seat : seatNos) {
            int seatNo = Integer.parseInt(seat);
            bookingDAO.createBooking(scheduleId, user.getUserId(), seatNo, seatPrice);
            seatDAO.markSeatBooked(scheduleId, seatNo);
        }

        session.setAttribute("bookingRef", "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        session.removeAttribute("boardingId");
        session.removeAttribute("droppingId");

        res.sendRedirect(req.getContextPath() + "/User/TicketSuccess.jsp");
    }
}