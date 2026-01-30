package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Route> getAllRoutes() {
	    List<Route> list = new ArrayList<>();

	    String sql = "SELECT * FROM routes";
	    try (Connection con = DBconnection.getconnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Route r = new Route();
	            r.setRouteId(rs.getInt("route_id"));
	            r.setSource(rs.getString("source"));
	            r.setDestination(rs.getString("destination"));
	            r.setDistance(rs.getDouble("distance_km"));
	            r.setFare(rs.getDouble("fare_base"));
	            list.add(r);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}


}
