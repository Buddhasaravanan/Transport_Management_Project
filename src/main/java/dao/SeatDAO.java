package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utill.DBconnection;

public class SeatDAO {

	public static void createSeatsIfNotExist(int scheduleId, int totalSeats) {

	    String checkSql = "SELECT COUNT(*) FROM seats WHERE schedule_id = ?";
	    String insertSql = "INSERT INTO seats (schedule_id, seat_no, status) VALUES (?, ?, 'AVAILABLE')";

	    try (Connection con = DBconnection.getconnection();
	         PreparedStatement checkPs = con.prepareStatement(checkSql)) {

	        checkPs.setInt(1, scheduleId);
	        ResultSet rs = checkPs.executeQuery();
	        rs.next();

	        // seats already created → STOP
	        if (rs.getInt(1) > 0) {
	            return;
	        }

	        PreparedStatement insertPs = con.prepareStatement(insertSql);
	        for (int i = 1; i <= totalSeats; i++) {
	            insertPs.setInt(1, scheduleId);
	            insertPs.setInt(2, i);
	            insertPs.addBatch();
	        }
	        insertPs.executeBatch();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


//    public List<Integer> getAvailableSeats(int scheduleId) {
//
//        List<Integer> list = new ArrayList<>();
//
//        String sql = """
//            SELECT seat_no FROM seats
//            WHERE schedule_id = ? AND status = 'AVAILABLE'
//        """;
//
//        try (Connection con = DBconnection.getconnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, scheduleId);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                list.add(rs.getInt("seat_no"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
    
    public boolean markSeatBooked(int scheduleId, int seatNo) {

        String sql = """
            UPDATE seats 
            SET status = 'BOOKED'
            WHERE schedule_id = ? AND seat_no = ? AND status = 'AVAILABLE'
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ps.setInt(2, seatNo);

            return ps.executeUpdate() == 1; // 1 row = success

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public List<String[]> getAllSeats(int scheduleId) {

        List<String[]> list = new ArrayList<>();

        String sql = """
            SELECT seat_no, status 
            FROM seats 
            WHERE schedule_id = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("seat_no"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean markSeatAvailable(int scheduleId, int seatNo) {

        String sql = """
            UPDATE seats 
            SET status = 'AVAILABLE'
            WHERE schedule_id = ? AND seat_no = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ps.setInt(2, seatNo);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    

}
