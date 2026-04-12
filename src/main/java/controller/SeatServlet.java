package controller;

import java.io.IOException;
import java.util.List;

import dao.ScheduleDAO;
import dao.SeatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/SeatServlet")
public class SeatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	
    	// 🔐 LOGIN CHECK
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user == null) {
        	
            // Save intended URL (optional but professional)
            req.getSession().setAttribute("redirectAfterLogin", 
                req.getRequestURI() + (req.getQueryString() == null ? "" : "?" + req.getQueryString()));

            res.sendRedirect(req.getContextPath() + "/User/Login.jsp");
            return;
        }

        String scheduleIdParam = req.getParameter("scheduleId");
        int scheduleId;
        try {
            scheduleId = Integer.parseInt(scheduleIdParam);
            if (scheduleId <= 0) {
                throw new NumberFormatException("scheduleId must be positive");
            }
        } catch (NumberFormatException ex) {
            res.sendRedirect(req.getContextPath() + "/User/home.jsp");
            return;
        }

        SeatDAO dao = new SeatDAO();
        List<String[]> seats = dao.getAllSeats(scheduleId);

        req.setAttribute("seats", seats);
        req.setAttribute("scheduleId", scheduleId);
        
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        double seatPrice = scheduleDAO.getPriceBySchedule(scheduleId);

        req.setAttribute("seatPrice", seatPrice);


        req.getRequestDispatcher("/User/Seats.jsp").forward(req, res);

    }
}