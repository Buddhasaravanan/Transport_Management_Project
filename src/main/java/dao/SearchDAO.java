package dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import model.Bus;
import utill.DBconnection;

public class SearchDAO {

    public List<Bus> searchBuses(String from, String to, String date) {
        List<Bus> list = new ArrayList<>();

        String sql = """
            SELECT b.bus_id, b.bus_number, b.bus_type,
                   r.source, r.destination, r.fare_base,
                   s.journey_date, s.departure_time, s.arrival_time
            FROM schedules s
            JOIN bus b ON s.bus_id = b.bus_id
            JOIN routes r ON s.route_id = r.route_id
            WHERE r.source = ? AND r.destination = ? AND s.journey_date = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, from);
            ps.setString(2, to);
            ps.setDate(3, Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bus bus = new Bus();
                bus.setBusId(rs.getInt("bus_id"));
                bus.setBusnumber(rs.getString("bus_number"));
                bus.setBusType(rs.getString("bus_type"));
                bus.setFromLocation(rs.getString("source"));
                bus.setToLocation(rs.getString("destination"));
                bus.setPrice(rs.getDouble("fare_base"));
                bus.setDepartureTime(rs.getTime("departure_time"));
                bus.setArrivalTime(rs.getTime("arrival_time"));
                bus.setTravelDate(rs.getDate("journey_date"));

                list.add(bus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
