package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BoardingPointDAO;
import dao.DroppingPointDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PassengerServlet")
public class PassengerServlet extends HttpServlet {


	
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse res)
		        throws IOException {
	
		    HttpSession session = req.getSession();
	
		    // 1️⃣ Read parameters safely
		    String scheduleParam = req.getParameter("scheduleId");
		    String seatNosParam = req.getParameter("seatNos");
		    String seatPriceParam = req.getParameter("seatPrice");
		    String boardingIdParam = req.getParameter("boardingPointId");
		    String droppingIdParam = req.getParameter("droppingPointId");
	
		    if (scheduleParam == null || seatNosParam == null ||
		        boardingIdParam == null || droppingIdParam == null) {
	
		        res.sendRedirect(req.getContextPath() + "/User/home.jsp");
		        return;
		    }
	
		    int scheduleId = Integer.parseInt(scheduleParam);
		    int boardingId = Integer.parseInt(boardingIdParam);
		    int droppingId = Integer.parseInt(droppingIdParam);
	
		    double seatPrice = 0;
		    if (seatPriceParam != null && !seatPriceParam.equals("null")) {
		        seatPrice = Double.parseDouble(seatPriceParam);
		    }
	
		    // 2️⃣ Split seat numbers (comma separated)
		    String[] seatNos = seatNosParam.split(",");
		    session.setAttribute("seatNos", seatNos);
	
		    // 3️⃣ Build passenger list (empty for now)
		    List<Map<String,String>> passengers = new ArrayList<>();
	
		    for (String seat : seatNos) {
		        Map<String,String> p = new HashMap<>();
		        p.put("seatNo", seat);
		        passengers.add(p);
		    }
	
		    // 4️⃣ Store in session
		    session.setAttribute("passengers", passengers);
		    session.setAttribute("scheduleId", scheduleId);
		    session.setAttribute("boardingId", boardingId);
		    session.setAttribute("droppingId", droppingId);
		    session.setAttribute("seatPrice", seatPrice);
		    
		    BoardingPointDAO boardingDAO = new BoardingPointDAO();
		    DroppingPointDAO droppingDAO = new DroppingPointDAO();

		    String boardingName = boardingDAO.getNameById(boardingId);
		    String droppingName = droppingDAO.getNameById(droppingId);

		    session.setAttribute("boardingName", boardingName);
		    session.setAttribute("droppingName", droppingName);
	
		    // 5️⃣ Redirect
		    res.sendRedirect(req.getContextPath() + "/User/Passenger.jsp");
		}
	}


//protected void doPost(HttpServletRequest req, HttpServletResponse res)
//throws IOException {
//
//HttpSession session = req.getSession();
//
//String[] names = req.getParameterValues("name");
//String[] ages = req.getParameterValues("age");
//String[] genders = req.getParameterValues("gender");
//String[] seatNos = req.getParameterValues("seatNo");
//
//Integer scheduleId =
//Integer.parseInt(req.getParameter("scheduleId"));
//
//String boardingName =
//req.getParameter("boardingName");
//
//String droppingName =
//req.getParameter("droppingName");
//
//Double seatPrice =
//Double.parseDouble(req.getParameter("seatPrice"));
//
//// SAFETY CHECK
//if (seatNos == null || seatNos.length == 0) {
//res.sendRedirect(req.getContextPath() + "/User/home.jsp");
//return;
//}
//
//// BUILD PASSENGER LIST
//List<Map<String,String>> passengers = new ArrayList<>();
//
//for (int i = 0; i < seatNos.length; i++) {
//Map<String,String> p = new HashMap<>();
//p.put("seatNo", seatNos[i]);
//p.put("name", names[i]);
//p.put("age", ages[i]);
//p.put("gender", genders[i]);
//passengers.add(p);
//}
//
//// ✅ STORE EVERYTHING IN SESSION
//session.setAttribute("passengers", passengers);
//session.setAttribute("scheduleId", scheduleId);
//session.setAttribute("boardingName", boardingName);
//session.setAttribute("droppingName", droppingName);
//session.setAttribute("seatPrice", seatPrice);
//
//// 👉 REDIRECT (session survives)
//res.sendRedirect(req.getContextPath() + "/User/confirm-booking.jsp");
//
//System.out.println("PASSENGERS = " + passengers.size());
//System.out.println("PRICE = " + seatPrice);
//System.out.println("BOARDING = " + boardingName);
//System.out.println("DROPPING = " + droppingName);
//
//System.out.println("DEBUG scheduleId param = " + req.getParameter("scheduleId"));
//
//}
