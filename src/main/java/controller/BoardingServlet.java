package controller;

import java.io.IOException;

import dao.BoardingPointDAO;
import dao.BookingDAO;
import dao.DroppingPointDAO;
import dao.ScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BoardingServlet")
public class BoardingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));
        String seatNos = req.getParameter("seatNos"); // <<< IMPORTANT

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        int routeId = scheduleDAO.getRouteIdBySchedule(scheduleId);

        BoardingPointDAO boardingDAO = new BoardingPointDAO();
        DroppingPointDAO droppingDAO = new DroppingPointDAO();
        
        BookingDAO bookingDAO = new BookingDAO();
        double seatPrice = bookingDAO.getSeatPrice(scheduleId);

        req.setAttribute("boardingList", boardingDAO.getByRoute(routeId));
        req.setAttribute("droppingList", droppingDAO.getByRoute(routeId));
        req.setAttribute("scheduleId", scheduleId);
        req.setAttribute("seatNos", seatNos); // <<< PASS FORWARD
        req.setAttribute("seatPrice", seatPrice);

        req.getRequestDispatcher("/User/Boarding.jsp")
           .forward(req, res);
        
        
    }
}


