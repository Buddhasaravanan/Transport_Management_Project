package controller;

import java.io.IOException;
import java.util.List;

import dao.SeatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SeatServlet")
public class SeatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));

        SeatDAO dao = new SeatDAO();
        List<String[]> seats = dao.getAllSeats(scheduleId);

        req.setAttribute("seats", seats);
        req.setAttribute("scheduleId", scheduleId);

        req.getRequestDispatcher("/User/select-seat.jsp").forward(req, res);

    }
}
