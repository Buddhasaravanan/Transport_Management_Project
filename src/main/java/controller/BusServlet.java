package controller;

import java.io.IOException;

import dao.BusDAO;
import model.Bus;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/BusServlet")
public class BusServlet extends HttpServlet
{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
            
            {
				String busNumber = request.getParameter("busNumber");
				int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
				String busType = request.getParameter("busType");
				
				Bus bus = new Bus();
		        bus.setBusnumber(busNumber);
		        bus.setTotalSeats(totalSeats);
		        bus.setBusType(busType);
		        
		        BusDAO dao = new BusDAO();
		        dao.addBus(bus);
		        
		        response.sendRedirect("Admin/Add_Bus.jsp");
            }


}
