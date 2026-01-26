package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utill.DBconnection;

public class BookingDAO {

    public boolean book(int scheduleId, int userId, double price) {

        String sql = """
            INSERT INTO bookings (schedule_id, user_id, seat_no, status, price)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ps.setInt(2, userId);
            ps.setInt(3, 1);           
            ps.setString(4, "BOOKED");
            ps.setDouble(5, price);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Object[]> getMyBookings(int userId) {

        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT 
                b.bus_number,
                r.source,
                r.destination,
                s.journey_date,
                s.departure_time,
                s.arrival_time,
                bk.seat_no,
                bk.price,
                bk.status
            FROM bookings bk
            JOIN schedules s ON bk.schedule_id = s.schedule_id
            JOIN bus b ON s.bus_id = b.bus_id
            JOIN routes r ON s.route_id = r.route_id
            WHERE bk.user_id = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getDate(4),   
                    rs.getTime(5),   
                    rs.getTime(6),   
                    rs.getInt(7),    
                    rs.getDouble(8),
                    rs.getString(9)  
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Object[]> getMyTickets(int userId) {

        List<Object[]> list = new ArrayList<>();

        String sql =
            "SELECT b.bus_number, r.source, r.destination, " +
            "s.journey_date, s.departure_time, s.arrival_time, " +
            "bk.seat_no, bk.price, bk.status " +
            "FROM bookings bk " +
            "JOIN schedules s ON bk.schedule_id = s.schedule_id " +
            "JOIN bus b ON s.bus_id = b.bus_id " +
            "JOIN routes r ON s.route_id = r.route_id " +
            "WHERE bk.user_id = ?";

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getDate(4),
                    rs.getTime(5),
                    rs.getTime(6),
                    rs.getInt(7),
                    rs.getDouble(8),
                    rs.getString(9)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Object[]> getUserBookings(int userId) {

        List<Object[]> list = new ArrayList<>();

        String sql = """
        SELECT
         b.bus_number,
         r.source,
         r.destination,
         s.journey_date,
         s.departure_time,
         s.arrival_time,
         bk.seat_no,
         bk.price,
         bk.status
        FROM bookings bk
        JOIN schedules s ON bk.schedule_id = s.schedule_id
        JOIN bus b ON s.bus_id = b.bus_id
        JOIN routes r ON s.route_id = r.route_id
        WHERE bk.user_id = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                  rs.getString(1),
                  rs.getString(2),
                  rs.getString(3),
                  rs.getDate(4),
                  rs.getTime(5),
                  rs.getTime(6),
                  rs.getInt(7),
                  rs.getDouble(8),
                  rs.getString(9)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean bookWithSeat(int scheduleId, int userId, int seatNo, double price) {

        String insertBooking =
            "INSERT INTO bookings (schedule_id, user_id, seat_no, status, price) " +
            "VALUES (?, ?, ?, 'BOOKED', ?)";

        String updateSeat =
            "UPDATE seats SET status='BOOKED' " +
            "WHERE schedule_id=? AND seat_no=?";

        try (Connection con = DBconnection.getconnection()) {

            
            PreparedStatement ps1 = con.prepareStatement(insertBooking);
            ps1.setInt(1, scheduleId);
            ps1.setInt(2, userId);
            ps1.setInt(3, seatNo);
            ps1.setDouble(4, price);
            ps1.executeUpdate();

            
            PreparedStatement ps2 = con.prepareStatement(updateSeat);
            ps2.setInt(1, scheduleId);
            ps2.setInt(2, seatNo);
            ps2.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}
