package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Schedule;
import utill.DBconnection;

public class ScheduleDAO {

    
	public int addSchedule(Schedule s) {

	    String sql = """
	        INSERT INTO schedules
	        (bus_id, route_id, departure_time, arrival_time, journey_date, price)
	        SELECT ?, ?, ?, ?, ?, r.fare_base
	        FROM routes r
	        WHERE r.route_id = ?
	    """;

	    try (Connection con = DBconnection.getconnection();
	         PreparedStatement ps =
	             con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setInt(1, s.getBusId());
	        ps.setInt(2, s.getRouteId());
	        ps.setTime(3, s.getDepartureTime());
	        ps.setTime(4, s.getArrivalTime());
	        ps.setDate(5, s.getJourneyDate());
	        ps.setInt(6, s.getRouteId()); // for SELECT

	        ps.executeUpdate();

	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            return rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return -1;
	}



    
    public List<Object[]> getAllSchedules() {

        List<Object[]> list = new ArrayList<>();

        String sql =
        	    "SELECT s.schedule_id, b.bus_number, r.source, r.destination, " +
        	    "s.journey_date, s.departure_time, s.arrival_time, r.fare_base " +
        	    "FROM schedules s " +
        	    "JOIN bus b ON s.bus_id = b.bus_id " +
        	    "JOIN routes r ON s.route_id = r.route_id";

        

        try (
            Connection con = DBconnection.getconnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
            	list.add(new Object[]{
            			 rs.getInt("schedule_id"),  
            			 rs.getString("bus_number"),
            			 rs.getString("source"),
            			 rs.getString("destination"),
            			 rs.getDate("journey_date"),
            			 rs.getTime("departure_time"),
            			 rs.getTime("arrival_time"),
            			 rs.getDouble("fare_base")
            			});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Object[]> searchSchedules(String from, String to, String date) {

        List<Object[]> list = new ArrayList<>();

        String sql =
            "SELECT " +
            "s.schedule_id, " +
            "b.bus_number, " +
            "r.source, " +
            "r.destination, " +
            "s.journey_date, " +
            "s.departure_time, " +
            "s.arrival_time, " +
            "r.fare_base " +
            "FROM schedules s " +
            "JOIN routes r ON s.route_id = r.route_id " +
            "JOIN bus b ON s.bus_id = b.bus_id " +
            "WHERE r.source = ? " +
            "AND r.destination = ? " +
            "AND s.journey_date = ?";

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, from);
            ps.setString(2, to);
            ps.setDate(3, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt(1),      // scheduleId
                    rs.getString(2),   // bus number
                    rs.getString(3),   // source
                    rs.getString(4),   // destination
                    rs.getDate(5),
                    rs.getTime(6),
                    rs.getTime(7),
                    rs.getDouble(8)    // fare
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public int getRouteIdBySchedule(int scheduleId) {

        String sql = "SELECT route_id FROM schedules WHERE schedule_id = ?";

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("route_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // not found
    }
    
    public double getPriceBySchedule(int scheduleId) {

        String sql = """
            SELECT r.fare_base
            FROM schedules s
            JOIN routes r ON s.route_id = r.route_id
            WHERE s.schedule_id = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("fare_base");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }





}
