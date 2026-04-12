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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String source = req.getParameter("source");
        String destination = req.getParameter("destination");
        String distanceKm = req.getParameter("distanceKm");
        String fare = req.getParameter("fare");

        if (source == null || destination == null || distanceKm == null || fare == null
                || source.isBlank() || destination.isBlank() || distanceKm.isBlank() || fare.isBlank()) {
            res.sendRedirect(req.getContextPath() + "/Admin/Add_routes.jsp");
            return;
        }

        Route r = new Route();
        r.setSource(source.trim());
        r.setDestination(destination.trim());
        r.setDistance(Double.parseDouble(distanceKm));
        r.setFare(Double.parseDouble(fare));

        new RouteDAO().addRoute(r);

        res.sendRedirect(req.getContextPath() + "/Admin/Add_routes.jsp");
    }
}