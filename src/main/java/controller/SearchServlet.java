package controller;

import java.io.IOException;
import java.util.List;

import dao.ScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String date = request.getParameter("date");

        ScheduleDAO dao = new ScheduleDAO();
        List<Object[]> results = dao.searchSchedules(from, to, date);

        request.setAttribute("results", results);
        request.getRequestDispatcher("/User/Bus-List.jsp")
               .forward(request, response);
    }
}

