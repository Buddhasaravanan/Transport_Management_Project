package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utill.DBconnection;

public class BookingDAO {

    // 🔒 FINAL BOOKING METHOD (used by ConfirmBookingServlet)
    public boolean createBooking(int scheduleId, int userId, int seatNo, double price) {

        String sql = """
            INSERT INTO bookings 
            (schedule_id, user_id, seat_no, booking_time, status, price)
            VALUES (?, ?, ?, NOW(), 'CONFIRMED', ?)
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ps.setInt(2, userId);
            ps.setInt(3, seatNo);
            ps.setDouble(4, price);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🎫 USER TICKETS (My Tickets page)
    public List<Object[]> getUserTickets(int userId) {

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
                bk.status,
                bk.booking_id,
                bk.schedule_id
            FROM bookings bk
            JOIN schedules s ON bk.schedule_id = s.schedule_id
            JOIN bus b ON s.bus_id = b.bus_id
            JOIN routes r ON s.route_id = r.route_id
            WHERE bk.user_id = ?
            ORDER BY bk.booking_time DESC
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
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getInt(11)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public boolean cancelBooking(int bookingId, int userId) {

        String sql = """
            UPDATE bookings
            SET status = 'CANCELLED'
            WHERE booking_id = ? AND user_id = ? AND status = 'CONFIRMED'
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static double calculatePrice(int scheduleId) {

        String sql = """
            SELECT r.fare_base, b.bus_type
            FROM schedules s
            JOIN routes r ON s.route_id = r.route_id
            JOIN bus b ON s.bus_id = b.bus_id
            WHERE s.schedule_id = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double base = rs.getDouble("fare_base");
                String type = rs.getString("bus_type");

                double extra = 0;
                if ("AC".equals(type)) extra = 150;
                else if ("VOLVO".equals(type)) extra = 250;
                else if (type.contains("SLEEPER")) extra = 300;

                return base + extra;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean bookWithSeat(int scheduleId, int userId, int seatNo, double price) {

        String insertBookingSql = """
            INSERT INTO bookings
            (schedule_id, user_id, seat_no, status, price)
            VALUES (?, ?, ?, 'BOOKED', ?)
        """;

        String updateSeatSql = """
            UPDATE seats
            SET status = 'BOOKED'
            WHERE schedule_id = ? AND seat_no = ?
        """;

        try (Connection con = DBconnection.getconnection()) {

            // 1️⃣ Insert booking
            PreparedStatement ps1 = con.prepareStatement(insertBookingSql);
            ps1.setInt(1, scheduleId);
            ps1.setInt(2, userId);
            ps1.setInt(3, seatNo);
            ps1.setDouble(4, price);
            ps1.executeUpdate();

            // 2️⃣ Mark seat as BOOKED
            PreparedStatement ps2 = con.prepareStatement(updateSeatSql);
            ps2.setInt(1, scheduleId);
            ps2.setInt(2, seatNo);
            ps2.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int saveBookingAndReturnId(
            int scheduleId,
            int userId,
            List<Map<String,String>> passengers,
            int boardingId,
            int droppingId,
            double seatPrice) {

        String bookingSql = """
            INSERT INTO bookings
            (schedule_id, user_id, seat_no, price, status, boarding_id, dropping_id)
            VALUES (?, ?, ?, ?, 'BOOKED', ?, ?)
        """;

        String passengerSql = """
            INSERT INTO passengers
            (booking_id, seat_no, name, age, gender)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DBconnection.getconnection()) {

            con.setAutoCommit(false);

            int bookingId = -1;

            // 1️⃣ Create booking (use FIRST seat)
            Map<String,String> first = passengers.get(0);

            PreparedStatement ps = con.prepareStatement(
                    bookingSql,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, scheduleId);
            ps.setInt(2, userId);
            ps.setInt(3, Integer.parseInt(first.get("seatNo")));
            ps.setDouble(4, seatPrice);
            ps.setInt(5, boardingId);
            ps.setInt(6, droppingId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bookingId = rs.getInt(1);
            }

            // 2️⃣ Save passengers
            PreparedStatement ps2 = con.prepareStatement(passengerSql);

            for (Map<String,String> p : passengers) {
                ps2.setInt(1, bookingId);
                ps2.setInt(2, Integer.parseInt(p.get("seatNo")));
                ps2.setString(3, p.get("name"));
                ps2.setInt(4, Integer.parseInt(p.get("age")));
                ps2.setString(5, p.get("gender"));
                ps2.addBatch();
            }

            ps2.executeBatch();
            con.commit();

            return bookingId;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Map<String,String> getTicketDetails(int bookingId) {

        Map<String,String> map = new HashMap<>();

        String sql = """
            SELECT
                b.bus_number,
                r.source,
                r.destination,
                s.journey_date,
                bp.name AS boarding,
                dp.name AS dropping
            FROM bookings bk
            JOIN schedules s ON bk.schedule_id = s.schedule_id
            JOIN bus b ON s.bus_id = b.bus_id
            JOIN routes r ON s.route_id = r.route_id
            JOIN boarding_points bp ON bk.boarding_id = bp.id
            JOIN dropping_points dp ON bk.dropping_id = dp.id
            WHERE bk.booking_id = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                map.put("bus_number", rs.getString("bus_number"));
                map.put("source", rs.getString("source"));
                map.put("destination", rs.getString("destination"));
                map.put("journey_date", rs.getString("journey_date"));
                map.put("boarding", rs.getString("boarding"));
                map.put("dropping", rs.getString("dropping"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<Map<String,String>> getTicketPassengers(int bookingId) {

        List<Map<String,String>> list = new ArrayList<>();

        String sql = """
            SELECT seat_no, name, age, gender
            FROM passengers
            WHERE booking_id = ?
            ORDER BY seat_no
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String,String> p = new HashMap<>();
                p.put("seat_no", rs.getString("seat_no"));
                p.put("name", rs.getString("name"));
                p.put("age", rs.getString("age"));
                p.put("gender", rs.getString("gender"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

	public double getSeatPrice(int scheduleId) {
		
				String sql = """
			SELECT r.fare_base, b.bus_type
			FROM schedules s
			JOIN routes r ON s.route_id = r.route_id
			JOIN bus b ON s.bus_id = b.bus_id
			WHERE s.schedule_id = ?
		""";

		try (Connection con = DBconnection.getconnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, scheduleId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				double base = rs.getDouble("fare_base");
				String type = rs.getString("bus_type");

				double extra = 0;
				if ("AC".equals(type)) extra = 150;
				else if ("VOLVO".equals(type)) extra = 250;
				else if (type.contains("SLEEPER")) extra = 300;

				return base + extra;
			}

		} catch (Exception e) {
		 e.printStackTrace();
		}
		return 0;
	}

    public List<Object[]> getAllBookingsForAdmin() {

        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT
                bk.booking_id,
                b.bus_number,
                r.source,
                r.destination,
                s.journey_date,
                bk.seat_no,
                bk.price,
                bk.status
            FROM bookings bk
            JOIN schedules s ON bk.schedule_id = s.schedule_id
            JOIN bus b ON s.bus_id = b.bus_id
            JOIN routes r ON s.route_id = r.route_id
            ORDER BY bk.booking_id DESC
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("booking_id"),
                    rs.getString("bus_number"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getDate("journey_date"),
                    rs.getInt("seat_no"),
                    rs.getDouble("price"),
                    rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}