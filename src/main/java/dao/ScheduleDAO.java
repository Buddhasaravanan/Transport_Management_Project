package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Schedule;
import utill.DBconnection;

public class ScheduleDAO {

    
    public int addSchedule(Schedule s) {

        String sql =
            "INSERT INTO schedules (bus_id, route_id, departure_time, arrival_time, journey_date) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = DBconnection.getconnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, s.getBusId());
            ps.setInt(2, s.getRouteId());
            ps.setTime(3, s.getDepartureTime());   
            ps.setTime(4, s.getArrivalTime());     
            ps.setDate(5, s.getJourneyDate());     

            PreparedStatement ps1 = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
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
        		
        		"SELECT s.schedule_id, b.bus_number,r.source, r.destination, " +
        				" s.journey_date,  s.departure_time,  s.arrival_time, "+
						 " r.fare_base " +
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
            "SELECT b.bus_number, r.source, r.destination, " +
            "s.journey_date, s.departure_time, s.arrival_time, r.fare_base, b.bus_id " +
            "FROM schedules s " +
            "JOIN bus b ON s.bus_id = b.bus_id " +
            "JOIN routes r ON s.route_id = r.route_id " +
            "WHERE r.source = ? AND r.destination = ? AND s.journey_date = ?";

        try (
            Connection con = DBconnection.getconnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, from);
            ps.setString(2, to);
            ps.setDate(3, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString(1),  
                    rs.getString(2),  
                    rs.getString(3),  
                    rs.getDate(4),    
                    rs.getTime(5),    
                    rs.getTime(6),    
                    rs.getDouble(7),  
                    rs.getInt(8)      
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
