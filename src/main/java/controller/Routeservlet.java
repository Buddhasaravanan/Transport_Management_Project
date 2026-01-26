package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RouteDAO;
import model.Route;

@WebServlet("/RouteServlet")
public class Routeservlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Route r = new Route();
        r.setSource(req.getParameter("source"));
        r.setDestination(req.getParameter("destination"));
        r.setDistance(Double.parseDouble(req.getParameter("distanceKm")));
        r.setFare(Double.parseDouble(req.getParameter("fareBase")));

        new RouteDAO().addRoute(r);

        res.sendRedirect("Admin/Add_routes.jsp");
    }
}
