package controller;

import java.io.IOException;

import dao.BoardingPointDAO;
import dao.DroppingPointDAO;
import dao.ScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PassengerEntryServlet")
public class PassengerEntryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        String seatNos = req.getParameter("seatNos");
        String scheduleIdStr = req.getParameter("scheduleId");
        String boardingIdStr = req.getParameter("boardingId");
        String droppingIdStr = req.getParameter("droppingId");

        if (seatNos == null || seatNos.isEmpty()
                || scheduleIdStr == null
                || boardingIdStr == null
                || droppingIdStr == null) {

            req.setAttribute("error", "Booking data missing. Please restart booking.");
            req.getRequestDispatcher("/User/Error.jsp").forward(req, res);
            return;
        }

        int scheduleId = Integer.parseInt(scheduleIdStr);
        int boardingId = Integer.parseInt(boardingIdStr);
        int droppingId = Integer.parseInt(droppingIdStr);

        BoardingPointDAO boardingDAO = new BoardingPointDAO();
        DroppingPointDAO droppingDAO = new DroppingPointDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();

        String boardingName = boardingDAO.getNameById(boardingId);
        String droppingName = droppingDAO.getNameById(droppingId);
        Double seatPrice = Double.valueOf(
        	    scheduleDAO.getPriceBySchedule(scheduleId)
        	);
        	


        // 🔐 STORE IN SESSION (CRITICAL)
        HttpSession session = req.getSession();
        session.setAttribute("seatNos", seatNos.split(","));
        session.setAttribute("scheduleId", scheduleId);
        session.setAttribute("boardingId", boardingId);
        session.setAttribute("droppingId", droppingId);
        session.setAttribute("boardingName", boardingName);
        session.setAttribute("droppingName", droppingName);
        session.setAttribute("seatPrice", seatPrice);

        // Optional: also send to JSP for rendering
        req.setAttribute("seatNos", seatNos.split(","));
        req.setAttribute("boardingName", boardingName);
        req.setAttribute("droppingName", droppingName);
        req.setAttribute("seatPrice", seatPrice);

        req.getRequestDispatcher("/User/Passenger.jsp").forward(req, res);
        
        
        System.out.println("DEBUG seatNos = " + seatNos);
        System.out.println("DEBUG scheduleId = " + scheduleIdStr);
        System.out.println("DEBUG boardingId = " + boardingIdStr);
        System.out.println("DEBUG droppingId = " + droppingIdStr);

    }
}
