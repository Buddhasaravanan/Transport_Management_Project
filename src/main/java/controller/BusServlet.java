package controller;

import java.io.IOException;

import dao.BusDAO;
import model.Bus;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/BusServlet")
public class BusServlet extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException 
            
            {
				String busNumber = request.getParameter("busNumber");
				String totalSeatsParam = request.getParameter("totalSeats");
				String busType = request.getParameter("busType");
				
				if (busNumber == null || busNumber.isBlank()
						|| totalSeatsParam == null || totalSeatsParam.isBlank()
						|| busType == null || busType.isBlank()) {
					response.sendRedirect(request.getContextPath() + "/Admin/Add_Bus.jsp");
					return;
				}
				
				Bus bus = new Bus();
		        bus.setBusnumber(busNumber.trim());
		        bus.setTotalSeats(Integer.parseInt(totalSeatsParam));
		        bus.setBusType(busType.trim());
		        
		        BusDAO dao = new BusDAO();
		        dao.addBus(bus);
		        
		        response.sendRedirect(request.getContextPath() + "/Admin/Add_Bus.jsp");
            }


}