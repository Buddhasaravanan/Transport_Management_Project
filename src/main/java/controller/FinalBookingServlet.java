package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import dao.BookingDAO;
import dao.ScheduleDAO;
import dao.SeatDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/FinalBookingServlet")
public class FinalBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession();

        int scheduleId = (int) session.getAttribute("scheduleId");
        int boardingId = (int) session.getAttribute("boardingId");
        int droppingId = (int) session.getAttribute("droppingId");
        double seatPrice = (double) session.getAttribute("seatPrice");

        List<Map<String,String>> passengers =
            (List<Map<String,String>>) session.getAttribute("passengers");

        User user = (User) session.getAttribute("loggedUser");
        int userId = (user != null) ? user.getUserId() : 1;

        BookingDAO bookingDAO = new BookingDAO();
        SeatDAO seatDAO = new SeatDAO();

        for (Map<String,String> p : passengers) {
            int seatNo = Integer.parseInt(p.get("seatNo"));

            bookingDAO.bookWithSeat(
                scheduleId,
                userId,
                seatNo,
                seatPrice
            );

            seatDAO.markSeatBooked(scheduleId, seatNo);
        }

        // ✅ DO NOT clear session yet
        res.sendRedirect(req.getContextPath() + "/User/TicketSuccess.jsp");
    }
}
