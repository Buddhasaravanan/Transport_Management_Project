package controller;

import java.io.IOException;
import java.util.List;

import dao.BookingDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MyTickets")
public class MyTicketsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
    	
    	int userId = 1; 

        BookingDAO dao = new BookingDAO();
//      req.setAttribute("tickets", dao.getUserBookings(1)); 
        List<Object[]> tickets = dao.getMyBookings(userId);

        try {
            req.getRequestDispatcher("/User/my-tickets.jsp")
               .forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


