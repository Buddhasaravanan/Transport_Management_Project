package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.Route;
import utill.DBconnection;

public class RouteDAO 
{
	
	public boolean addRoute(Route route) {

        String sql =
            "INSERT INTO routes (source, destination, distance_km, fare_base) " +
            "VALUES (?, ?, ?, ?)";

        try (
            Connection con = DBconnection.getconnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, route.getSource());
            ps.setString(2, route.getDestination());
            ps.setDouble(3, route.getDistance());   
            ps.setDouble(4, route.getFare());     

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
