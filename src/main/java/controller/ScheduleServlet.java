package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import dao.BusDAO;
import dao.ScheduleDAO;
import dao.SeatDAO;
import model.Schedule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String busIdParam = req.getParameter("busId");
        String routeIdParam = req.getParameter("routeId");
        String dep = req.getParameter("departureTime");
        String arr = req.getParameter("arrivalTime");
        String journeyDate = req.getParameter("journeyDate");

        if (busIdParam == null || routeIdParam == null || dep == null || arr == null || journeyDate == null
                || busIdParam.isBlank() || routeIdParam.isBlank() || dep.isBlank() || arr.isBlank() || journeyDate.isBlank()) {
            res.sendRedirect(req.getContextPath() + "/Admin/Add_Schedule.jsp");
            return;
        }

        Schedule s = new Schedule();
        s.setBusId(Integer.parseInt(busIdParam));
        s.setRouteId(Integer.parseInt(routeIdParam));
        s.setDepartureTime(Time.valueOf(dep + ":00"));
        s.setArrivalTime(Time.valueOf(arr + ":00"));
        s.setJourneyDate(Date.valueOf(journeyDate));

        ScheduleDAO dao = new ScheduleDAO();
        int scheduleId = dao.addSchedule(s);

        BusDAO busDAO = new BusDAO();
        int totalSeats = busDAO.getTotalSeats(s.getBusId());
        SeatDAO.createSeatsIfNotExist(scheduleId, totalSeats);

        res.sendRedirect(req.getContextPath() + "/Admin/Add_Schedule.jsp");
    }
}