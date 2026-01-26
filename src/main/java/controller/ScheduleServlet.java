package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import dao.ScheduleDAO;
import dao.SearchDAO;
import dao.SeatDAO;
import model.Bus;
import model.Schedule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws IOException {

	    Schedule s = new Schedule();
	    s.setBusId(Integer.parseInt(req.getParameter("busId")));
	    s.setRouteId(Integer.parseInt(req.getParameter("routeId")));
	    
	    String dep = req.getParameter("departureTime");
	    String arr = req.getParameter("arrivalTime");

	    s.setDepartureTime(Time.valueOf(dep + ":00"));
	    s.setArrivalTime(Time.valueOf(arr + ":00"));

	    
	    s.setJourneyDate(Date.valueOf(req.getParameter("journeyDate")));

	    ScheduleDAO dao = new ScheduleDAO();
	    int scheduleId = dao.addSchedule(s);

	    SeatDAO seatDAO = new SeatDAO();
	    seatDAO.createSeats(scheduleId, 40);

	    res.sendRedirect("Admin/Add_Schedule.jsp");
	}


}
