package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utill.DBconnection;

@WebServlet("/DroppingPointServlet")
public class DroppingPointServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String routeIdParam = req.getParameter("routeId");
        String location = req.getParameter("location");
        String timeParam = req.getParameter("time");

        if (routeIdParam == null || routeIdParam.isBlank()
                || location == null || location.isBlank()
                || timeParam == null || timeParam.isBlank()) {
            res.sendRedirect(req.getContextPath() + "/Admin/Add_DroppingPoint.jsp");
            return;
        }

        String sql =
            "INSERT INTO dropping_points (route_id, location, dropping_time) VALUES (?, ?, ?)";

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(routeIdParam));
            ps.setString(2, location.trim());
            ps.setTime(3, Time.valueOf(timeParam + ":00"));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.sendRedirect(req.getContextPath() + "/Admin/Add_DroppingPoint.jsp");
    }
}